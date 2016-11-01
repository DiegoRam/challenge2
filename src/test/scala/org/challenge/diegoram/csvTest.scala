package org.challenge.diegoram

import org.specs2.Specification
import org.specs2.specification.core.SpecStructure

class CsvTest extends Specification {

  override def is: SpecStructure = s2"""
      should parse a whole genre file  $openFile
      should count total 19 lines on genre file   $countFile
      should count single values on a row $elementsOnARow
      should create a genre list from a file $createGenreInstances
      should create a User list from a file with 925 element in it $createUserInstances
      should create a rating list of 100000 elements $createRatingInstances
    """

  lazy val reader = scala.io.Source.fromInputStream(getClass.getResourceAsStream("/u.genre"))
  lazy val lines = reader.getLines.toList
  def openFile = reader mustNotEqual(None)
  def countFile = lines must haveSize(19)
  def elementsOnARow = lines(0).split('|') must haveSize(2)

  def createGenreInstances = {
    val reader = new PipeFileReader[Genre]("u.genre")
    reader.createObjectList must haveSize(19)
  }

  def createUserInstances = {
    val reader = new PipeFileReader[User]("u.user")
    reader.createObjectList must haveSize(925)
  }

  def createRatingInstances ={
    val reader = new TabFileReader[Rating]("u.data")
    reader.createObjectList must haveSize(100000)
  }

}

