package module

import org.junit.runner.RunWith
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.junit.JUnitRunner
import org.scalatest.{FlatSpec, Matchers}
import scala.concurrent.duration._

@RunWith(classOf[JUnitRunner])
class RunwaySpec extends FlatSpec with Matchers with ScalaFutures with TestUtility {

  "runway service" should "return no of records in file" in {
    runwaysDefaultOps.runways.length shouldBe 2
  }

  "getRunways function" should "return no of runways in airport" in {
    val airport = new Airport("6524","00AK","small_airport","Lowell Field","59.94919968","-151.695999146","450","NA","US","US-AK","Anchor Point","no","00AK","","00AK","","","")
    whenReady(runwaysDefaultOps.getRunways(airport)) {
      result =>
        result.length shouldBe 1
    }
  }

  "frequentRunways function" should "return runway identifications" in {
    val expectedResult = List(("H1",1), ("N",1))
    whenReady(runwaysDefaultOps.frequentRunways,timeout(2 seconds), interval(500 millis)) {
      result =>
        result shouldBe expectedResult
    }
  }


}
