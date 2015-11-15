package persistence.dal

import persistence.entities.{Survey}
import scala.concurrent.Future
import org.junit.runner.RunWith
import org.scalatest.{BeforeAndAfterAll, FunSuite}
import org.scalatest.junit.JUnitRunner
import akka.pattern.ask
import scala.concurrent.Await
import scala.concurrent.duration._
import akka.util.Timeout


@RunWith(classOf[JUnitRunner])
class SurveysDalTest extends FunSuite with AbstractPersistenceTest with BeforeAndAfterAll{
  implicit val timeout = Timeout(5.seconds)

  val modules = new Modules {
  }

  test("SurveysActor: Testing Surveys Actor") {
    Await.result(modules.surveysDal.createTables(),5.seconds)
    val numberOfEntities : Int = Await.result((modules.surveysDal.save(Survey(Option("000aa118-88f2-4b3f-863f-c408ad0fdb06"),"sup"))),5.seconds)
    assert (numberOfEntities == 1)
    val survey : Seq[Survey] = Await.result((modules.surveysDal.getSurveyById("000aa118-88f2-4b3f-863f-c408ad0fdb06")),5.seconds)
    assert (survey.length == 1 &&  survey.head.name.compareTo("sup") == 0)
    val empty : Seq[Survey] = Await.result((modules.surveysDal.getSurveyById("000aa118-88f2-4b3f-863f-c408ad0fdb07")),5.seconds)
    assert (empty.length == 0)
  }

  override def afterAll: Unit ={
    modules.db.close()
  }
}