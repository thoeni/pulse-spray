package rest

import entities.JsonProtocol._
import persistence.entities.{SimpleSurvey, Survey}
import spray.http.StatusCodes._
import spray.httpx.SprayJsonSupport._

import scala.concurrent.Future

class RoutesSpec  extends AbstractRestTest {
  sequential

  def actorRefFactory = system

  val modules = new Modules {}

  val surveys = new SurveyHttpService(modules){
    override def actorRefFactory = system
  }

  val questions = new QuestionHttpService(modules){
    override def actorRefFactory = system
  }

  "Survey Routes" should {

    "return an empty array of surveys" in {
     modules.surveysDal.getSurveyById("000aa118-88f2-4b3f-863f-c408ad0fdb06") returns Future(Vector())

      Get("/survey/000aa118-88f2-4b3f-863f-c408ad0fdb06") ~> surveys.SurveyGetRoute ~> check {
        handled must beTrue
        status mustEqual OK
        responseAs[Seq[Survey]].length == 0
      }
    }

    "return an array with 2 surveys" in {
      modules.surveysDal.getSurveyById("000aa118-88f2-4b3f-863f-c408ad0fdb06") returns Future(Vector(Survey(Some("000aa118-88f2-4b3f-863f-c408ad0fdb06"),"name 1"),Survey(Some("000aa118-88f2-4b3f-863f-c408ad0fdb06"),"name 2")))
      Get("/survey/000aa118-88f2-4b3f-863f-c408ad0fdb06") ~> surveys.SurveyGetRoute ~> check {
        handled must beTrue
        status mustEqual OK
        responseAs[Seq[Survey]].length == 2
      }
    }

    "create a survey with the json in post" in {
      modules.surveysDal.save(Survey(None,"name 1")) returns  Future(1)
      Post("/survey",SimpleSurvey("name 1")) ~> surveys.SurveyPostRoute ~> check {
        handled must beTrue
        status mustEqual Created
      }
    }

    "not handle the invalid json" in {
      Post("/survey","{\"name\":\"1\"}") ~> surveys.SurveyPostRoute ~> check {
        handled must beFalse
      }
    }

    "not handle an empty post" in {
      Post("/survey") ~> surveys.SurveyPostRoute ~> check {
        handled must beFalse
      }
    }

  }

}
