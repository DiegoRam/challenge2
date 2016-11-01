package org.challenge.diegoram

import org.specs2.Specification
import org.specs2.specification.core.SpecStructure

class ChallengeSpec extends Specification {

  override def is: SpecStructure = s2"""
    find all rating for a Movie By Title $finAllRatingForAMovie
    find average rating for a movie by title $findAverageForMovie
    find user favourite gender my name $failure
    find recomendations for user XXX $failure

    """


  def finAllRatingForAMovie = {

    (MovieDao.findOne(_.title == "Toy Story (1995)") match {
      case Some(movie) => RatingDao.findAll(_.movieId == movie.id)
      case None => Seq()
    }).size must beGreaterThan(0)

  }

  def findAverageForMovie = {
    MovieDao.averageRating("Toy Story (1995)") must beEqualTo(Some(3.0))
  }

  def findFavouriteGenderForUser = {
    UserDao.findOne(_.id == 1) match {
      case Some(user) => {
        RatingDao.findAll(_.userId == user.id).sortBy(_.rating).reverse.take(10).map { rat =>
          MovieDao.findById(rat.movieId).map( mov => MovieDao.getGenreArray(mov.genres))
        }
      }
    }
  }

}
