package org.challenge.diegoram

trait Table {
  val id: Int
}

case class Genre(id: Int, name: String) extends Table
case class User(id: Int, age: Int, gender: Char, occupation: String, zipCode: Int) extends Table
case class Rating(id: Int, userId: Int, movieId: Int, rating: Int, timestamp: Int) extends Table
case class Movie(
  id: Int,
  title: String,
  releaseDate: String,
  videoReleaseDate: String,
  imdbUrl: String,
  genres: String
) extends Table
//1111111111111111111
//unknown | Action | Adventure | Animation |Children's | Comedy | Crime | Documentary | Drama | Fantasy |
//Film-Noir | Horror | Musical | Mystery | Romance | Sci-Fi |Thriller | War | Western |