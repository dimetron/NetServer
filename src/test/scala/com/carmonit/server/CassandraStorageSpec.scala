/*
 * Copyright 2017 Dmytro Rashko
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.carmonit.server

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.alpakka.cassandra.scaladsl.{ CassandraSink, CassandraSource }
import akka.stream.scaladsl.{ Sink, Source }
import com.datastax.driver.core.{ Cluster, PreparedStatement, SimpleStatement }
import org.scalatest._
import org.scalatest.concurrent.ScalaFutures

import scala.collection.JavaConverters._
import scala.concurrent._
import scala.concurrent.duration._

/**
 *  Modified Example test from Alpakka cassandra connector
 */

class CassandraStorageSpec extends WordSpec
    with ScalaFutures
    with BeforeAndAfterEach
    with BeforeAndAfterAll
    with MustMatchers {

  //#init-mat
  implicit val system = ActorSystem()
  implicit val mat = ActorMaterializer()
  //#init-mat

  //#init-session
  implicit val session = Cluster.builder.addContactPoint("database").withPort(9042).build.connect()
  //#init-session

  implicit val defaultPatience =
    PatienceConfig(timeout = 2.seconds, interval = 50.millis)

  override def beforeEach(): Unit = {
    session.execute(
      """
        |CREATE KEYSPACE IF NOT EXISTS akka_stream_scala_test WITH replication = {
        |  'class': 'SimpleStrategy',
        |  'replication_factor': '1'
        |};
      """.stripMargin
    )
    session.execute(
      """
        |CREATE TABLE IF NOT EXISTS akka_stream_scala_test.test (
        |    id int PRIMARY KEY
        |);
      """.stripMargin
    )
  }

  override def afterEach(): Unit = {
    session.execute("DROP TABLE IF EXISTS akka_stream_scala_test.test;")
    session.execute("DROP KEYSPACE IF EXISTS akka_stream_scala_test;")
  }

  override def afterAll(): Unit =
    Await.result(system.terminate(), 5.seconds)

  def populate() =
    (1 until 103).map { i =>
      session.execute(s"INSERT INTO akka_stream_scala_test.test(id) VALUES ($i)")
      i
    }

  "CassandraSourceSpec" must {

    "stream the result of a Cassandra statement with one page" in {
      val data = populate()
      val stmt = new SimpleStatement("SELECT * FROM akka_stream_scala_test.test").setFetchSize(200)

      val rows = CassandraSource(stmt).runWith(Sink.seq).futureValue

      rows.map(_.getInt("id")) must contain theSameElementsAs data
    }

    "sink should write to the table" in {
      import system.dispatcher

      val source = Source(0 to 10).map(i => i: Integer)

      //#prepared-statement
      val preparedStatement = session.prepare("INSERT INTO akka_stream_scala_test.test(id) VALUES (?)")
      //#prepared-statement

      //#statement-binder
      val statementBinder = (myInteger: Integer, statement: PreparedStatement) => statement.bind(myInteger)
      //#statement-binder

      //#run-sink
      val sink = CassandraSink[Integer](parallelism = 2, preparedStatement, statementBinder)

      val result = source.runWith(sink)
      //#run-sink

      result.futureValue

      val found = session.execute("select id from akka_stream_scala_test.test").all().asScala.map(_.getInt("id"))

      found.toSet mustBe (0 to 10).toSet
    }
  }
}
