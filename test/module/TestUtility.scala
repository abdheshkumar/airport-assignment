package module

trait TestUtility {
  class RunwayDefaultOpsMock extends RunwayDefaultOps {
    override lazy val fileName: String = "test/data/runway.csv"
  }

  class AirportDefaultOpsMock(runways: RunwayDefaultOpsMock) extends AirportDefaultOps(runways) {
    override lazy val fileName: String = "test/data/airport.csv"
  }

  class CountryDefaultOpsMock(airportService: AirportDefaultOpsMock) extends CountryDefaultOps(airportService) {
    override lazy val fileName: String = "test/data/country.csv"
  }

  lazy val runwaysDefaultOps = new RunwayDefaultOpsMock()
  lazy val airportDefaultOps = new AirportDefaultOpsMock(runwaysDefaultOps)
  lazy val countryDefaultOps = new CountryDefaultOpsMock(airportDefaultOps)
}
