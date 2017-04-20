package com.dimetron

import scala.concurrent.duration._
import scala.language.postfixOps

import io.gatling.core.Predef._
import io.gatling.http.Predef._

class RecordedSimulation1 extends Simulation {

	val httpProtocol = http
		.baseURL("http://scw.carmonit.com:8080")
		//.baseURL("http://127.0.0.1:8080")
		.inferHtmlResources()
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
		.acceptEncodingHeader("gzip, deflate, sdch")
		.acceptLanguageHeader("uk-UA,uk;q=0.8,ru;q=0.6,en-US;q=0.4,en;q=0.2,cs;q=0.2")
		.userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.133 Safari/537.36")

	val headers_0 = Map(
		"Cache-Control" -> "max-age=0",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_1 = Map("Accept" -> "image/webp,image/*,*/*;q=0.8")

	val scn = scenario("RecordedSimulation1")
		.exec(http("request_0")
			.get("/svc")
			.headers(headers_0)
			.resources(http("request_1")
			.get("/favicon.ico")
			.headers(headers_1)))

	/*
	
	http://gatling.io/docs/2.2/general/simulation_setup

	*/

	setUp(scn.inject(
		atOnceUsers(200),    
		constantUsersPerSec(100) during(30 minute)
	)).throttle(
	  		reachRps(100) in (15 seconds),
	  		jumpToRps(200),
	  		holdFor(20 minute),
	  		jumpToRps(250),
	  		holdFor(10 minute)
	).protocols(httpProtocol)



}