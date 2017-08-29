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

import akka.Done
import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.alpakka.cassandra.scaladsl.CassandraSink
import akka.stream.scaladsl.Sink
import com.datastax.driver.core.{ Cluster, PreparedStatement, Session }

import scala.concurrent.Future

object CassandraStorage {

  //#init-mat
  implicit val system = ActorSystem()
  implicit val executionContext = system.dispatcher
  implicit val materializer = ActorMaterializer()
  //#init-mat

  implicit lazy val session = getSessionWithSchema

  //#prepared-statement
  lazy val preparedStatement = prepareInsert

  def getSessionWithSchema: Session = {

    println("==================================")
    println("Connecting to the database ...")

    val sess = Cluster.builder.addContactPoint("database")
      .withPort(9042)
      .withMaxSchemaAgreementWaitSeconds(20)
      .build.connect()

    println("===== Running schema Check =====")
    sess.execute(
      """
        |CREATE KEYSPACE IF NOT EXISTS location_data WITH replication = {
        |  'class': 'SimpleStrategy',
        |  'replication_factor': '1'
        |};
      """.
      stripMargin
    )

    sess.execute(
      """
        |CREATE TABLE IF NOT EXISTS location_data.request_log (
        |    id text PRIMARY KEY
        |);
      """.
      stripMargin
    ).getExecutionInfo.isSchemaInAgreement

    println("==================================")

    sess
  }

  def prepareInsert = session.prepare("INSERT INTO location_data.request_log(id) VALUES (?)")

  def getInsertLogDataSink: Sink[String, Future[Done]] = {

    //#statement-binder
    val statementBinder = (par: String, statement: PreparedStatement) => statement.bind(par)
    CassandraSink[String](parallelism = 2, preparedStatement, statementBinder)
  }
}
