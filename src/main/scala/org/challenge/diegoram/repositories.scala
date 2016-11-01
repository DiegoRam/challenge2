package org.challenge.diegoram

object Repositories {

  lazy val movieTable = new PipeFileReader[Movie]("u.item").createObjectList
  lazy val userTable = new PipeFileReader[User]("u.user").createObjectList
  lazy val ratingTable = new TabFileReader[Rating]("u.data").createObjectList
  lazy val genreTable = new PipeFileReader[Genre]("u.genre").createObjectList

}
