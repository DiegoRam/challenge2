package org.challenge.diegoram

import org.specs2.Specification
import org.specs2.specification.core.SpecStructure

class ChallengeSpec extends Specification {

  override def is: SpecStructure = s2"""
    find all rating for a Movie By Title        $finAllRatingForAMovie
    find average rating for a movie by title    $findAverageForMovie
    find user favourite gender by name          $findFavouriteGenderForUser
    find recomendations for user XXX            $failure

    """


  def finAllRatingForAMovie = {

    (MovieDao.findOne(_.title == "Toy Story (1995)") match {
      case Some(movie) => RatingDao.findAll(_.movieId == movie.id)
      case None => Seq()
    }) must haveSize(452)
  }

  def findAverageForMovie = {
    MovieDao.averageRating("Toy Story (1995)") must beEqualTo(Some(3.0))
  }


  def findFavouriteGenderForUser =
    (UserDao.findById(2) match {
      case Some(user) => {
        Some((for {
          rat <- RatingDao.findAll(_.userId == user.id).sortBy(_.rating).reverse.take(10)
          movie = MovieDao.findById(rat.movieId)
        } yield movie)
          .flatten
          .foldLeft(Seq[Genre]())((acc,el) => acc ++ el.genres)
          .groupBy(_.id).maxBy(_._2.size)._2.head)
      }
      case None => None
    }) mustEqual Some(Genre(8,"Drama"))
}
