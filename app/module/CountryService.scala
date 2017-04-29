package module

import csv.CsvReader

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

trait CountryOps {
  val fileName: String
  val countries: Vector[Country]

  def searchByCodeOrCountry(codeOrCountry: String): Future[QueryResult]

  def typeOfRunways: Future[Vector[(String, String)]]

  def getByCodeOrCountry(codeOrCountry: String): Future[Option[Country]]

  def countriesNumberOfAirports: Future[(List[CountryCount], List[CountryCount])]

  def getTypeOfRunways(country: Country): Future[String]


  def totalAirports(country: Country): Future[Int]
}

class CountryDefaultOps(airportService: AirportDefaultOps) extends CountryOps {
  override lazy val fileName: String = "conf/data/countries.csv"
  override lazy val countries: Vector[Country] = new CsvReader(fileName)
    .readAndParse((line, headers) => Country(line, headers))

  /**
    * get type of runways in country
    * @param country : Country
    * @return : Future[String]
    */

  def getTypeOfRunways(country: Country): Future[String] = {
    val typeOfRunways = airportService.getAirports(country).flatMap { airports =>
      val runways = airports.take(500).map(f => airportService.getTypeOfRunways(f))
      Future.sequence(runways)
        .map(_.flatten)
    }
    typeOfRunways.map(_.toSet.filterNot(_.isEmpty).mkString(","))
  }

  /**
    * get total airports in country
    * @param country : Country
    * @return : Future[Int]
    */
  def totalAirports(country: Country): Future[Int] =
    airportService.getAirports(country).map(_.length)

  /**
    * get type of runways in country
    * @return : `Future[Vector[(String, String)]]`
    */
  def typeOfRunways: Future[Vector[(String, String)]] = {
    Future {
      val runwayTypes = countries.map { country =>
        getTypeOfRunways(country).map(runways => country.name -> runways)
      }
      Future.sequence(runwayTypes)
    }.flatMap(f => f)
  }

  /**
    * get number of airports in country
    * @return : Future[(List[CountryCount], List[CountryCount])]
    */
  def countriesNumberOfAirports: Future[(List[CountryCount], List[CountryCount])] = {
    val result = countries.map {
      country =>
        totalAirports(country).map(totalAirport => CountryCount(country, totalAirport))
    }
    val sortedResult = Future.sequence(result).map {
      countryStream => countryStream.toList.sortBy(-_.count)
    }
    sortedResult.map {
      list => list.take(10) -> list.reverse.take(10)
    }
  }

  /**
    * search country by code or country name
    * @param codeOrCountry : String
    * @return : `Future[Option[Country]]`
    */
  def getByCodeOrCountry(codeOrCountry: String): Future[Option[Country]] = Future {
    val searchKeyword = codeOrCountry.toLowerCase
    countries.find {
      country =>
        country.code.toLowerCase.startsWith(searchKeyword) ||
          country.name.toLowerCase.startsWith(searchKeyword)
    }
  }

  /**
    * search airports and runways by country code or name
    * @param codeOrCountry : String
    * @return : Future[QueryResult]
    */
  def searchByCodeOrCountry(codeOrCountry: String): Future[QueryResult] = {
    getByCodeOrCountry(codeOrCountry.toLowerCase).flatMap {
      case Some(country) =>
        val airportsWithRunways = airportService.getAirports(country).map {
          airports =>
            val airportWithRunways = airports.map {
              airport =>
                airportService.getRunways(airport)
                  .map(airport -> _)
            }
            Future.sequence(airportWithRunways)
        }
        val finalResult = airportsWithRunways
          .flatMap(f => f)
          .map(_.toMap)
        finalResult.map(QueryResult(country, _))

      case None => Future.failed(new Exception(s"There is no country found with search: $codeOrCountry"))
    }
  }
}

object CountryService extends CountryDefaultOps(new AirportDefaultOps(new RunwayDefaultOps))