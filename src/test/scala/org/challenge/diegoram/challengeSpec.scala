package org.challenge.diegoram

import org.specs2.Specification
import org.specs2.specification.core.SpecStructure

class ChallengeSpec extends Specification {

  override def is: SpecStructure = s2"""
    find all rating for a Movie By Title        $finAllRatingForAMovie
    find average rating for a movie by title    $findAverageForMovie
    find user favourite gender by name          $findFavouriteGenderForUser
    find recomendations for user 345            $findRecomendationsForUser

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


  def movieMatch(a: Seq[Movie], b: Seq[Movie]): Boolean = {
    def loop(xs: Seq[Movie], sample: Seq[Movie], count: Int): Boolean = {
      if(count >=5) true
      else{
        xs match {
          case Nil => count >=5
          case head::tail => loop(tail, sample, if(sample.contains(head)) count + 1 else count)
        }
      }
    }
    loop(a,b,0)
  }

  def findRecomendationsForUser = {
    val myTopRatedMovies =
      for{
        rating <- RatingDao.findAll(rat => rat.userId == 345 && rat.rating >= 4).take(10)
        movie <- MovieDao.findById(rating.movieId)
      } yield movie

    val recommended =
      (for {
        otherUserRat <- RatingDao.findAll(ot => ot.rating >=4 && myTopRatedMovies.exists(_.id == ot.movieId))
          .take(10).map(_.userId)
        userRat <- RatingDao.findAll(ot => ot.rating >=4 && otherUserRat == ot.userId)
        newMov <- MovieDao.findById(userRat.movieId)
      } yield newMov)
        .toSet
        .filterNot(myTopRatedMovies.contains)

    recommended must haveSize(488)
  }
}
