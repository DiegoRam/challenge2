package org.challenge.diegoram

import org.specs2.Specification
import org.specs2.specification.core.SpecStructure
import org.challenge.diegoram.Parser._

class UtilSpecs extends Specification{

  override def is: SpecStructure = s2"""
    given a string 0001110000000000000 must return Seq("Animation","Children's,"Comedy") $firstStrToSeq
    given a string 0110000000000000100 must return Seq("Action","Adventure","Thriller") $secondStrToSeq
    checks Movie instance creation        $checkMovieCreation
    find user 1 by id                     $findUserByIUd
    find all rated movies for user 1      $findRatingForAGivenUser
    find top ten rating for user 1        $findTopRatingforUser
    find top ten rated movies for user 1  $findTopRatedMoviesforUser

    """

  def firstStrToSeq =
    MovieParser.getGenreArray("0001110000000000000") mustEqual
      Seq(Genre(3,"Animation"),Genre(4,"Children's"),Genre(5,"Comedy"))

  def secondStrToSeq =
    MovieParser.getGenreArray("0110000000000000100") mustEqual
      Seq(Genre(1,"Action"),Genre(2,"Adventure"),Genre(16,"Thriller"))

  def checkMovieCreation =
    Movie(1, "MyMovie", "date", "blah", "blah", MovieParser.getGenreArray("0001110000000000000")) mustEqual
      Movie(1, "MyMovie", "date", "blah", "blah", Seq(Genre(3,"Animation"),Genre(4,"Children's"),Genre(5,"Comedy")))

  val dummieMovie = Movie(1,"Toy Story (1995)","01-Jan-1995","","http://us.imdb.com/M/title-exact?Toy%20Story%20(1995)",
    Seq())

  def findUserByIUd =
    UserDao.findById(1) mustEqual Some(User(1,24,'M',"technician",85711))

  def findRatingForAGivenUser = {
    UserDao.findById(1).map{ x =>
      RatingDao.findAll(_.userId == x.id)
    }.getOrElse(Seq()) must haveSize(272)
  }

  def findTopRatingforUser =
    (UserDao.findById(1) match {
      case Some(user) => RatingDao.findAll(_.userId == user.id).sortBy(_.rating).reverse.take(10)
      case None => Seq[Rating]()
    }) must haveSize(10)

  def findTopRatedMoviesforUser =
    (UserDao.findById(1) match {
      case Some(user) =>
        for {
          rat <- RatingDao.findAll(_.userId == user.id).sortBy(_.rating).reverse.take(10)
          movie <- MovieDao.findById(rat.movieId)
        } yield movie
      case None => Seq[Movie]()
    }) must haveSize(10)


}
