package org.challenge.diegoram

import Repositories._

abstract class BaseDao[T <: Table](table: Seq[T]) {
  def findById(id: Int): Option[T] = table.filter(_.id == id).headOption
  def findAll(pr: T => Boolean): Seq[T] = table.filter(pr)
  def findOne(pr: T => Boolean) = findAll(pr).headOption
}

case object MovieDao extends BaseDao[Movie](movieTable) {
  def averageRating(name: String): Option[Double] = {
    findOne(_.title == name) match {
      case None => None
      case Some(movie) => {
        val list = RatingDao.findAll(_.movieId == movie.id)
        Some(list.foldLeft(0)((a,b) => a + b.rating) / list.size)
      }
    }
  }

  def getGenreArray(str: String): Seq[Genre] = {
    def loop(xs: Seq[Char], count: Int, acc: Seq[Genre]): Seq[Genre] ={
      if(xs.isEmpty) acc
      else {
        if(xs.head == 0) loop(xs.tail, count + 1, acc :+ genreTable(count))
        else loop(xs.tail, count + 1, acc)
      }
    }
    loop(str.toCharArray, 0 , Seq())
  }
}

case object RatingDao extends BaseDao[Rating](ratingTable)
case object UserDao extends BaseDao[User](userTable)
case object GenreDao extends BaseDao[Genre](genreTable)
