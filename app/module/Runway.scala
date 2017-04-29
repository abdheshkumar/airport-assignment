package module

case class Runway(
                   id: String,
                   airportRef: String,
                   airportIdent: String,
                   lengthFt: String,
                   widthFt: String,
                   surface: String,
                   lighted: String,
                   closed: String,
                   le_ident: String,
                   leLatitudeDeg: String,
                   leLongitudeDeg: String,
                   leElevationFt: String,
                   leHeadingDegT: String,
                   leDisplacedThresholdFt: String,
                   heIdent: String,
                   heLatitudeDeg: String,
                   heLongitudeDeg: String,
                   heElevationFt: String,
                   heHeadingDegT: String,
                   heDisplacedThresholdFt: String)


object Runway {

  def apply(line: String, headers: Map[String, Int]): Runway = {
    import Utility._
    implicit val _headers = headers
    val values = split(line)
    new Runway(
      values(get("id")),
      values(get("airport_ref")),
      values(get("airport_ident")),
      values(get("length_ft")),
      values(get("width_ft")),
      values(get("surface")),
      values(get("lighted")),
      values(get("closed")),
      values(get("le_ident")),
      values(get("le_latitude_deg")),
      values(get("le_longitude_deg")),
      values(get("le_elevation_ft")),
      values(get("le_heading_degT")),
      values(get("le_displaced_threshold_ft")),
      values(get("he_ident")),
      values(get("he_latitude_deg")),
      values(get("he_longitude_deg")),
      values(get("he_elevation_ft")),
      values(get("he_heading_degT")),
      values(get("he_displaced_threshold_ft")))
  }
}

