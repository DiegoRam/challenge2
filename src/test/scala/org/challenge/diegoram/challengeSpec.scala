package org.challenge.diegoram

import org.specs2.Specification
import org.specs2.specification.core.SpecStructure

class ChallengeSpec extends Specification {

  override def is: SpecStructure = s2"""
    find all rating for a Movie By Title        $finAllRatingForAMovie
    find average rating for a movie by title    $findAverageForMovie
    find user 1 by id                           $findUserByIUd
    find all rated movies for user 1            $findRatingForAGivenUser
    find all genre Seq for a given movie        $failure
    find user favourite gender by name          $findFavouriteGenderForUser
    find recomendations for user XXX            $failure

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

  def findUserByIUd =
    UserDao.findById(1) mustEqual Some(User(1,24,'M',"technician",85711))

  def findRatingForAGivenUser = {
    UserDao.findById(1).map{ x =>
      RatingDao.findAll(_.userId == x.id)
    }.getOrElse(Seq()) must haveSize(272)
  }

  //TODO remove genre form first for cause this is not same monad, try another nested for or monad trans
  def findFavouriteGenderForUser =
    (UserDao.findOne(_.id == 1) match {
      case Some(user) => {
        (for {
          rat <- RatingDao.findAll(_.userId == user.id).sortBy(_.rating).reverse.take(10)
          movie = MovieDao.findById(rat.movieId)
          genre <- MovieDao.getGenreArray(movie.get.genres)
            if movie.isDefined
        } yield genre).groupBy(_.name).maxBy(_._2.size)._1
      }
      case None => ""
    }) must beEqualTo("Horror")


}
