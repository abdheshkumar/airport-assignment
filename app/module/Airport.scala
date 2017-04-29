package module

case class Airport(
                    id: String,
                    ident: String,
                    `type`: String,
                    name: String,
                    latitude: String,
                    longitude: String,
                    elevationFt: String,
                    continent: String,
                    isoCountry: String,
                    isoRegion: String,
                    municipality: String,
                    scheduledService: String,
                    gpsCode: String,
                    iataCode: String,
                    localCode: String,
                    homeLink: String,
                    wikipediaLink: String,
                    keywords: String)

object Airport {
  def apply(line: String, headers: Map[String, Int]): Airport = {

    import Utility._

    implicit val _headers = headers
    val values = split(line)
    new Airport(
      values(get("id")),
      values(get("ident")),
      values(get("type")),
      values(get("name")),
      values(get("latitude_deg")),
      values(get("longitude_deg")),
      values(get("elevation_ft")),
      values(get("continent")),
      values(get("iso_country")),
      values(get("iso_region")),
      values(get("municipality")),
      values(get("scheduled_service")),
      values(get("gps_code")),
      values(get("iata_code")),
      values(get("local_code")),
      values(get("home_link")),
      values(get("wikipedia_link")),
      values(get("keywords")))
  }
}
