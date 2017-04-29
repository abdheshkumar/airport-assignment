package controllers

import play.api.mvc._
import play.api.routing._

class Application extends Controller {

  def index = Action {
    Ok(views.html.index("Welcome"))
  }

  def javascriptRoutes = Action { implicit request =>
    Ok(
      JavaScriptReverseRouter("jsRoutes")(
        routes.javascript.SearchController.searchCountry,
        routes.javascript.SearchController.countriesWithAirports,
        routes.javascript.SearchController.runways,
        routes.javascript.SearchController.frequentRunways
      )
    ).as("text/javascript")
  }


}