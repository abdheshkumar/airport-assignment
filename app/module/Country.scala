package module

case class Country(
                    id: String,
                    code: String,
                    name: String,
                    continent: String,
                    wikipediaLink: String,
                    keywords: String)

object Country {
  def apply(line: String, headers: Map[String, Int]): Country = {
    import Utility._
    implicit val _headers = headers
    val values = split(line)
    new Country(
      values(get("id")),
      values(get("code")),
      values(get("name")),
      values(get("continent")),
      values(get("wikipedia_link")),
      values(get("keywords")))
  }
}