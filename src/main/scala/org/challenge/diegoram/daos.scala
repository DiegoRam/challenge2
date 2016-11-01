package org.challenge.diegoram

import Repositories._

abstract class BaseDao[T <: Table](table: Seq[T]) {
  def findById(id: Int): Option[T] = table.filter(_.id == id).headOption
  def findAll(pr: T => Boolean): Seq[T] = table.filter(pr)
  def findOne(pr: T => Boolean) = findAll(pr).headOption
}

case object MovieDao extends BaseDao[Movie](movieTable)
case object RatingDao extends BaseDao[Rating](ratingTable)
