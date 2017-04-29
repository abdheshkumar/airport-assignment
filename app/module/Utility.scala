package module


object Utility {

  def split(str: String) = str.split(",", -1).toList.map(_.replaceAll("\"", ""))

  @throws[Exception]
  def get(key: String)(implicit headers: Map[String, Int]) = headers.get(key) match {
    case Some(value) => value
    case None => throw new Exception(s"$key column is missing")
  }

}
