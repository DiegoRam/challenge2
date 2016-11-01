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
}
case object RatingDao extends BaseDao[Rating](ratingTable)
