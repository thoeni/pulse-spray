package entities

import persistence.entities.{SimpleSurvey, Survey}
import spray.json.DefaultJsonProtocol

object JsonProtocol extends DefaultJsonProtocol {
  implicit val surveyFormat = jsonFormat2(Survey)
  implicit val simpleSurveyFormat = jsonFormat1(SimpleSurvey)
}