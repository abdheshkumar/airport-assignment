package controllers

import module.{CountryService, RunwayService}
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.mvc.{Controller, _}

class SearchController extends Controller {
  def searchCountry(countryName: String): Action[AnyContent] = Action.async {
    CountryService.searchByCodeOrCountry(countryName).map {
      queryResult =>
        Ok(views.html.search(queryResult))
    }.recover {
      case ex => Ok(views.html.error(ex.getMessage))
    }
  }

  def countriesWithAirports: Action[AnyContent] = Action.async {
    CountryService.countriesNumberOfAirports.map {
      result => Ok(views.html.countryCount(result))
    }
  }

  def runways: Action[AnyContent] = Action.async {
    CountryService.typeOfRunways.map {
      cRunways =>
        Ok(views.html.typeOfRunways(cRunways))
    }
  }

  def frequentRunways: Action[AnyContent] = Action.async {
    RunwayService.frequentRunways.map {
      result =>
        Ok(views.html.frequentRunways(result))
    }
  }
}
