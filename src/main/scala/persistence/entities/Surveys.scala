package persistence.entities

import java.util.UUID

import utils.Profile


case class Survey(id: Option[String],name: String)

case class SimpleSurvey(name: String)


trait Surveys extends Profile{
  import profile.api._

  class Surveys(tag: Tag) extends Table[Survey](tag, "surveys") {
    def id = column[String]("id", O.PrimaryKey)
    def name = column[String]("title")
    def * = (id.?, name) <> (Survey.tupled, Survey.unapply)
  }
  val surveys = TableQuery[Surveys]

}