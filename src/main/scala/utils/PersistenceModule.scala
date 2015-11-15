package utils

import akka.actor.{ActorPath, ActorSelection, Props, ActorRef}
import persistence.dal.{QuestionsDal, QuestionsDalImpl, SurveysDalImpl, SurveysDal}
import slick.backend.DatabaseConfig
import slick.driver.{JdbcProfile}



trait Profile {
	val profile: JdbcProfile
}


trait DbModule extends Profile{
	val db: JdbcProfile#Backend#Database
}

trait PersistenceModule {
	val surveysDal: SurveysDal
	val questionsDal: QuestionsDal
}


trait PersistenceModuleImpl extends PersistenceModule with DbModule{
	this: Configuration  =>

	// use an alternative database configuration ex:
	// private val dbConfig : DatabaseConfig[JdbcProfile]  = DatabaseConfig.forConfig("pgdb")
	private val dbConfig : DatabaseConfig[JdbcProfile]  = DatabaseConfig.forConfig("mysql")

	override implicit val profile: JdbcProfile = dbConfig.driver
	override implicit val db: JdbcProfile#Backend#Database = dbConfig.db

	override val surveysDal = new SurveysDalImpl()
	override val questionsDal = new QuestionsDalImpl()

	val self = this

}
