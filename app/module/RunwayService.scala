package module

import csv.CsvReader
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

trait RunwayOps {
  val fileName: String

  val runways: Vector[Runway]

  def getRunways(airport: Airport): Future[Vector[Runway]]

  def frequentRunways: Future[List[(String, Int)]]
}

class RunwayDefaultOps extends RunwayOps {

  override lazy val fileName: String = "conf/data/runways.csv"
  lazy val runways: Vector[Runway] = new CsvReader(fileName).readAndParse((line, headers) => Runway(line, headers))

  /**
    * get runways in the airport
    * @param airport : Airport
    * @return : `Future[Vector[Runway]]`
    */
  def getRunways(airport: Airport): Future[Vector[Runway]] = Future {
    val id = airport.id
    val filter = (runway: Runway) => runway.airportRef == id
    runways.filter(filter)
  }

  /**
    * get frequent runways identification
    * @return : `Future[List[(String, Int)]]`
    */
  def frequentRunways: Future[List[(String, Int)]] = Future {
    runways.groupBy(_.le_ident)
      .map {
        case (ident, runways) => ident -> runways.length
      }.toList
      .sortBy(-_._2)
      .take(10)
  }
}

object RunwayService extends RunwayDefaultOps
