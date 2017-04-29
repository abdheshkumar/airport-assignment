package csv

import scala.io.Source

class CsvReader(fileName: String) {

  private var headers: Map[String, Int] = Map.empty

  def getHeaders: Map[String, Int] = headers

  def readAndParse[A](converter: (String, Map[String, Int]) => A): Vector[A] = {
    val lines = Source
      .fromFile(fileName)
      .getLines()
      .toVector

    lines.take(1)
      .toList
      .map(_.split(",", -1).toList.map(_.replaceAll("\"", "").trim).zipWithIndex.toMap)
      .headOption
      .foreach(headers = _)

    lines.drop(1)
      .map(converter(_, getHeaders))
  }
}




