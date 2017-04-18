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

import java.net.InetSocketAddress

import akka.actor.ActorSystem
import akka.http.scaladsl.testkit.ScalatestRouteTest
import akka.stream.ActorMaterializer
import akka.stream.scaladsl._
import akka.util.ByteString
import org.scalatest.{ Matchers, WordSpec }

import scala.concurrent.Future

class TcpServerSpec extends WordSpec with Matchers with ScalatestRouteTest {

  "Outgoing TCP stream" must {

    "be able to write a sequence of ByteStrings" in {

      TcpServer.startServer

      val requestStr = "Test String!\n"
      val responsStr = new StringBuffer

      def response(bs: ByteString) = {
        println("client received: " + bs.utf8String)
        responsStr.append(bs.utf8String)
      }

      val clientFlow = Tcp().outgoingConnection(new InetSocketAddress(TcpServer.HOST, TcpServer.PORT))
      clientFlow.runWith(Source(List(ByteString(requestStr))), Sink.foreach(bs => response(bs)))

      //todo: Fix it
      Thread.sleep(1000)

      responsStr.toString should be(requestStr)
    }
  }
}
