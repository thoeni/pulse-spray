package persistence.dal

import com.typesafe.scalalogging.LazyLogging
import persistence.entities.{Question, Questions}
import slick.driver.JdbcProfile
import utils.DbModule

import scala.concurrent.Future

/**
  * @author antoniotroina
  */
trait QuestionsDal {
  def getAllQuestions(): Future[Vector[Question]]
}

class QuestionsDalImpl(implicit val db: JdbcProfile#Backend#Database,implicit val profile: JdbcProfile) extends QuestionsDal with DbModule with Questions with LazyLogging {

  import profile.api._

  override def getAllQuestions(): Future[Vector[Question]] = {
    db.run(questions.map(q => (q.id, q.text)).result).mapTo[Vector[Question]]
  }
}
