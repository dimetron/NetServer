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
import akka.event.{ Logging, LoggingAdapter }
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.Tcp.ServerBinding
import akka.stream.scaladsl.{ Flow, Framing, Tcp }
import akka.util.ByteString

import scala.concurrent.Future

object TcpServer {

  val host = "127.0.0.1"
  val port = 8888

  implicit val system = ActorSystem("tcp-system")
  implicit val materializer = ActorMaterializer()
  implicit val executionContext = system.dispatcher
  implicit val log: LoggingAdapter = Logging(system, this.getClass)

  def getFlow = {
    Flow[ByteString]
      .via(Framing.delimiter(
        ByteString("\n"),
        maximumFrameLength = 256,
        allowTruncation = true
      ))
      .map(_.utf8String)
      .map(_ + "!!!\n")
      .map(ByteString(_))
  }

  def startServer = {
    val bindingFuture = Tcp().bindAndHandle(getFlow, host, port)
    println(s"TCP Server started at ${host}:${port}")
    bindingFuture
  }

  def stopServer(bindingFuture: Future[ServerBinding]) {
    println(s"TCP Server stopping")
    bindingFuture
      .flatMap(_.unbind()) // trigger unbinding from the port
      .onComplete(_ => system.terminate()) // and shutdown when done
  }
}
