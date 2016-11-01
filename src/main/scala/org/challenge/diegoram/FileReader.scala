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
    private val r = new java.util.Random
    override def createSingleObject(arr: Seq[String]): Option[Rating] =
      Try {
        Rating(r.nextInt,arr(0).toInt, arr(1).toInt, arr(2).toInt, arr(3).toInt)
      } match {
        case Success(rating) => Some(rating)
        case Failure(ex) => None
      }
  }

  implicit object MovieParser extends Parser[Movie] {
    override def createSingleObject(arr: Seq[String]): Option[Movie] =
      Try {
          Some(Movie(arr(0).toInt, arr(1), arr(2), arr(3), arr(4), arr.drop(5).mkString))
      } match {
        case Success(v) => v
        case Failure(ex) => None
      }


    //TODO change it to have a parseable string in order to extract gender array from it
    private def arrayToGenreBias(arr: Seq[String]): Option[Long] = {
      def loop(xs: Seq[String], exp: Int, acc: Long): Long = {
        if(xs.isEmpty) acc
        else {
          loop(
            xs.tail,
            exp - 1,
            acc + Math.pow(if(xs.head == "1") 10.00 else 0.00, if(exp>0) exp.toDouble else 1).toLong)
        }
      }

      if (arr.size != 19) None
      else {
        Some(loop(arr, 18, 0))
      }
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