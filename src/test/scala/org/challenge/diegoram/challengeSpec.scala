package org.challenge.diegoram

import org.specs2.Specification
import org.specs2.specification.core.SpecStructure

class ChallengeSpec extends Specification {

  override def is: SpecStructure = s2"""
    find all rating for a Movie By Title $finAllRatingForAMovie
    find average rating for a movie by title $failure
    find user favourite gender my name $failure
    find recomendations for user XXX $failure

    """


  def finAllRatingForAMovie = {

    (MovieDao.findOne(_.title == "Toy Story (1995)") match {
      case Some(movie) => RatingDao.findAll(_.movieId == movie.id)
      case None => Seq()
    }).size must beGreaterThan(0)

  }

}
