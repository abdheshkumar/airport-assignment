package module

import org.junit.runner.RunWith
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.junit.JUnitRunner
import org.scalatest.{FlatSpec, Matchers}

@RunWith(classOf[JUnitRunner])
class AirportSpec extends FlatSpec with Matchers with ScalaFutures with TestUtility {

  "airport service" should "return no of records in file" in {
    airportDefaultOps.airports.length shouldBe 4
  }

  "getAirports function" should "return no of airports in country" in {
    val country = new Country("302755","US","United States","NA","http://en.wikipedia.org/wiki/United_States","America")
    whenReady(airportDefaultOps.getAirports(country)) {
      result =>
        result.length shouldBe 3
    }
  }

  "getRunways function" should "return no of runways in airport" in {
    val airport = new Airport("6524","00AK","small_airport","Lowell Field","59.94919968","-151.695999146","450","NA","US","US-AK","Anchor Point","no","00AK","","00AK","","","")
    whenReady(airportDefaultOps.getRunways(airport)) {
      result =>
        result.length shouldBe 1
    }
  }

  "getTypeOfRunways function" should "return type of runways in airport" in {
    val airport = new Airport("6524","00AK","small_airport","Lowell Field","59.94919968","-151.695999146","450","NA","US","US-AK","Anchor Point","no","00AK","","00AK","","","")
    whenReady(airportDefaultOps.getTypeOfRunways(airport)) {
      result =>
        result.length shouldBe 1
    }
  }

  "totalRunways function" should "return total runways in airport" in {
    val airport = new Airport("6524","00AK","small_airport","Lowell Field","59.94919968","-151.695999146","450","NA","US","US-AK","Anchor Point","no","00AK","","00AK","","","")
    whenReady(airportDefaultOps.getTypeOfRunways(airport)) {
      result =>
        result.length shouldBe 1
    }
  }
}
