package persistence.dal

import java.util.UUID

import com.typesafe.scalalogging.LazyLogging
import persistence.entities.{Survey, Surveys}
import slick.driver.JdbcProfile
import utils.DbModule

import scala.concurrent.Future


trait SurveysDal {
  def save(sup: Survey) : Future[Int]
  def getSurveyById(id: String) : Future[Vector[Survey]]
  def createTables() : Future[Unit]
}


class SurveysDalImpl(implicit val db: JdbcProfile#Backend#Database,implicit val profile: JdbcProfile) extends SurveysDal with DbModule with Surveys with LazyLogging{
  import profile.api._

  override def save(sup: Survey) : Future[Int] = {
    db.run(surveys += new Survey(
      if (sup.id.isEmpty) Option(UUID.randomUUID.toString) else sup.id,
      sup.name
    )).mapTo[Int]
  }

  override def getSurveyById(id: String) : Future[Vector[Survey]] = { db.run(surveys.filter(_.id === id).result).mapTo[Vector[Survey]] }

  override def createTables() : Future[Unit] = {
      db.run(DBIO.seq(surveys.schema.create))
  }

}
