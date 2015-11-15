package entities

import persistence.entities.{SimpleQuestion, Question, SimpleSurvey, Survey}
import spray.json.DefaultJsonProtocol

object JsonProtocol extends DefaultJsonProtocol {
  implicit val surveyFormat = jsonFormat2(Survey)
  implicit val simpleSurveyFormat = jsonFormat1(SimpleSurvey)

  implicit val questionFormat = jsonFormat2(Question)
  implicit val simpleQuestionFormat = jsonFormat1(SimpleQuestion)
}