package module

import org.junit.runner.RunWith
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.junit.JUnitRunner
import org.scalatest.{FlatSpec, Matchers}
import scala.concurrent.duration._
@RunWith(classOf[JUnitRunner])
class CountrySpec extends FlatSpec with Matchers with ScalaFutures with TestUtility{

  "Country Service" should "return no of records in file" in {
    countryDefaultOps.countries.length shouldBe 4
  }

  "searchByCodeOrCountry function" should "return search result when country code is given" in {
    whenReady(countryDefaultOps.searchByCodeOrCountry("us")) {
      result =>
        result.airports should not be empty
    }
  }

  "searchByCodeOrCountry function" should "return search result when country name is given" in {
    whenReady(countryDefaultOps.searchByCodeOrCountry("United States")) {
      result =>
        result.airports should not be empty
    }
  }

  "searchByCodeOrCountry function" should "return empty result when country not found" in {
    whenReady(countryDefaultOps.searchByCodeOrCountry("dummy-county-name").failed) {
      result =>
        result.getMessage shouldBe s"There is no country found with search: dummy-county-name"
    }
  }

  "getByCodeOrCountry function" should "return country for `us` search" in {
    whenReady(countryDefaultOps.getByCodeOrCountry("US")) {
      result =>
        result shouldBe defined
        result.get.name shouldBe "United States"
    }
  }

  "countriesNumberOfAirports function" should "return highest and lowest number of airports in country" in {
    whenReady(countryDefaultOps.countriesNumberOfAirports,timeout(2 seconds), interval(500 millis)) {
      result =>
        val (highest, lowest) = result
        val h = highest.headOption
        val l = lowest.headOption
        h shouldBe defined
        h.get.count shouldBe 3
        l shouldBe defined
        l.get.count shouldBe 0
    }
  }

  "totalAirports function" should "return number of airports in the country" in {
    val country = countryDefaultOps.countries.find(_.code == "US")
    country shouldBe defined
    whenReady(countryDefaultOps.totalAirports(country.get)) {
      result => result shouldBe 3
    }
  }
  "getTypeOfRunways function" should "return type of runways in the country" in {
    val country = countryDefaultOps.countries.find(_.code == "US")
    country shouldBe defined
    whenReady(countryDefaultOps.getTypeOfRunways(country.get)) {
      result => result shouldBe "ASPH-G,GRVL"
    }
  }

}
