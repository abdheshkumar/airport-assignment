package module


case class QueryResult(country: Country, airports: Map[Airport, Vector[Runway]])

case class CountryCount(country: Country, count: Int)
