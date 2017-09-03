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

import java.util.Calendar

import akka.actor.ActorSystem
import akka.event.{ Logging, LoggingAdapter }
import akka.http.scaladsl.Http
import akka.http.scaladsl.Http.ServerBinding
import akka.http.scaladsl.model.{ ContentTypes, HttpEntity }
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.Source
import kamon.akka.http.KamonTraceDirectives

import scala.concurrent.Future

object HttpServer extends KamonTraceDirectives {

  val HOST = "0.0.0.0"
  val PORT = 8080

  implicit val system = ActorSystem("http-system")
  implicit val materializer = ActorMaterializer()
  implicit val executionContext = system.dispatcher

  implicit val log: LoggingAdapter = Logging(system, this.getClass)

  //https://github.com/akka/akka-http/blob/master/docs/src/test/scala/docs/http/scaladsl/HttpServerExampleSpec.scala

  def startServer = {
    val bindingFuture = Http().bindAndHandle(getRoutes, HOST, PORT)
    println(s"Http Server started at http://${HOST}:${PORT}")
    bindingFuture
  }

  def getRoutes = {
    get {
      traceName("get-status") {
        withoutSizeLimit {
          extractDataBytes { data =>
            extractMaterializer { materializer =>

              val now = Calendar.getInstance().getTime()
              val nanos = System.nanoTime().toLong
              val source = s"""{"Status": "UP", "time": "$now-$nanos"}"""

              val result = Source.single(source).runWith(CassandraStorage.getInsertLogDataSink)(materializer)

              // we only want to respond once the incoming data has been handled:
              onComplete(result) { res =>
                complete(HttpEntity(ContentTypes.`application/json`, source))
              }
            }
          }
        }
      }
    }
  }

  def stopServer(bindingFuture: Future[ServerBinding]) = {
    println(s"Http Server stopping")
    bindingFuture
      .flatMap(_.unbind()) // trigger unbinding from the port
      .onComplete(_ => system.terminate()) // and shutdown when done
  }
}
