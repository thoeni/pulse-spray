package persistence.entities

import utils.Profile

/**
  * @author antoniotroina
  */

case class SimpleQuestion(text: String)
case class Question(id: String, text: String)

trait Questions extends Profile {
  import profile.api._

  class Questions(tag: Tag) extends Table[Question](tag, "questions") {
    def id = column[String]("id", O.PrimaryKey)
    def text = column[String]("text")
    def * = (id, text) <> (Question.tupled, Question.unapply)
  }

  val questions = TableQuery[Questions]
}

