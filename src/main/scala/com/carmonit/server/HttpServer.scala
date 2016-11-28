/*
 * Copyright 2016 Dmytro Rashko
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

import scala.concurrent.Future

import akka.actor.ActorSystem
import akka.event.{ Logging, LoggingAdapter }
import akka.http.scaladsl.Http
import akka.http.scaladsl.Http.ServerBinding
import akka.http.scaladsl.model.{ ContentTypes, HttpEntity }
import akka.http.scaladsl.server.Directives.{ complete, get, path }
import akka.stream.ActorMaterializer

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import spray.json.DefaultJsonProtocol._

object HttpServer {

  val host = "127.0.0.1"
  val port = 8080

  implicit val system = ActorSystem("http-system")
  implicit val materializer = ActorMaterializer()
  implicit val executionContext = system.dispatcher
  implicit val log: LoggingAdapter = Logging(system, this.getClass)

  def getRoutes = {
    get {
      val source = """{ "Status": "UP" }"""
      complete(HttpEntity(ContentTypes.`application/json`, source))
    }
  }

  def startServer = {
    val bindingFuture = Http().bindAndHandle(getRoutes, host, port)
    println(s"Http Server started at http://${host}:${port}")
    bindingFuture
  }

  def stopServer(bindingFuture: Future[ServerBinding]) {
    println(s"Http Server stopping")
    bindingFuture
      .flatMap(_.unbind()) // trigger unbinding from the port
      .onComplete(_ => system.terminate()) // and shutdown when done
  }
}
