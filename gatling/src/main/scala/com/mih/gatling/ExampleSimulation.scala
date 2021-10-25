package com.mih.gatling


import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder
import io.gatling.http.Predef._
import io.gatling.core.scenario.Simulation

import scala.concurrent.duration.DurationInt
import scala.language.postfixOps
import scala.util.Random

class ExampleSimulation extends Simulation {

  val tps: Int = Integer.getInteger("tps", 10)
  val durationMinutes: Int = Integer.getInteger("durationMinutes", 1)
  val target: String = Option(System.getProperty("target")).getOrElse("http://localhost:8081")

  val httpConf = http.baseUrl(target)


  val start = {
    exec(session => {
      println("Starting with username: " + session("username").as[String])
      session
    })
  }

  val feeder = Iterator.continually(Map("email" -> (Random.alphanumeric.take(20).mkString + "@foo.com")))

  val login = http("Login call")
      .post("/login")
      .header("username", "${email}")
      .check(status.is(200))
      .check(jsonPath("$.token").find.saveAs("token"))

  val getAccounts =
       http("Get accounts call")
      .get("/accounts")
      .header("X-AUTH", "${token}")
      .check(status.is(200))

  val getTransactions = http("Get transactions call")
      .get("/transactions")
      .header("X-AUTH", "${token}")
      .check(status.is(200))

  val scn = scenario("Login")
    .feed(feeder)
    .exec(login)
    .exec(getAccounts)
    .repeat(5) {
      exec(getTransactions)
        .pause(2 seconds)
    }

  setUp(scn.inject(
    nothingFor(2.seconds),
    rampUsers(100) during ( 10 seconds))
    .throttle(reachRps(tps).in(2 seconds),
      holdFor(durationMinutes minutes),
      reachRps(0).in(2 seconds))
    .protocols(httpConf))
    .assertions(
      global.responseTime.max.lt(500 ),
      global.responseTime.mean.lt(1000),
      global.successfulRequests.percent.gt(95)
    )
}


