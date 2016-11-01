package org.challenge.diegoram


case class Genre(id: Int, name: String)
case class User(id: Int, age: Int, gender: Char, occupation: String, zipCode: Int)
case class Rating(userId: Int, movieId: Int, rating: Int, timestamp: Int)
case class Movie(
  id: Int,
  title: String,
  releaseDate: String,
  videoReleaseDate: String,
  imdbUrl: String,
  genres: Long
)
//1111111111111111111
//unknown | Action | Adventure | Animation |Children's | Comedy | Crime | Documentary | Drama | Fantasy |
//Film-Noir | Horror | Musical | Mystery | Romance | Sci-Fi |Thriller | War | Western |