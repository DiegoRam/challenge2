package org.challenge.diegoram

import scala.annotation.implicitNotFound
import scala.util.{Failure, Success, Try}

@implicitNotFound("Parser not found for type [${A}]")
trait Parser[A] {
  def createSingleObject(arr: Seq[String]): Option[A]
}

object Parser {
  implicit object GenreParser extends Parser[Genre]{
    override def createSingleObject(arr: Seq[String]): Option[Genre] = Some(Genre(arr(1).toInt, arr(0)))
  }

  implicit object UserParser extends Parser[User] {
    override def createSingleObject(arr: Seq[String]): Option[User] =
      Try {
        User(arr(0).toInt, arr(1).toInt, arr(2).toList(0), arr(3), arr(4).toInt)
      } match {
        case Success(user) => Some(user)
        case Failure(ex) => None
      }
  }

  implicit object RatingParser extends Parser[Rating] {
    override def createSingleObject(arr: Seq[String]): Option[Rating] =
      Try {
        Rating(arr(0).toInt, arr(1).toInt, arr(2).toInt, arr(3).toInt)
      } match {
        case Success(rating) => Some(rating)
        case Failure(ex) => None
      }
  }


}

abstract class FileReader[T](separation: Char, fileName: String) {

  private val file = scala.io.Source.fromInputStream(getClass.getResourceAsStream("/" + fileName))

  def createObjectList(implicit p: Parser[T]): Seq[T] =
    (for {
      line <- file.getLines
      entity <- p.createSingleObject(line.split(separation))
    } yield entity).toSeq

}


class PipeFileReader[T](fileName: String)
  extends FileReader[T]('|', fileName )

class TabFileReader[T](fileName: String)
  extends FileReader[T]('\t', fileName )