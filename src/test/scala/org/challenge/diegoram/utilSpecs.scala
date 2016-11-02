package org.challenge.diegoram

import org.specs2.Specification
import org.specs2.specification.core.SpecStructure

class UtilSpecs extends Specification{

  override def is: SpecStructure = s2"""
    given a string 0001110000000000000 must return Seq("Animation","Children's,"Comedy") $firstStrToSeq
    given a string 0110000000000000100 must return Seq("Action","Adventure","Thriller") $secondStrToSeq
    """

  def firstStrToSeq =
    MovieDao.getGenreArray("0001110000000000000") mustEqual
      Seq(Genre(3,"Animation"),Genre(4,"Children's"),Genre(5,"Comedy"))

  def secondStrToSeq =
    MovieDao.getGenreArray("0110000000000000100") mustEqual
      Seq(Genre(1,"Action"),Genre(2,"Adventure"),Genre(16,"Thriller"))
}
