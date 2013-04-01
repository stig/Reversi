package org.brautaset.reversi

import org.scalatest.matchers.MustMatchers
import org.scalatest.WordSpec

class FitnessSpec extends WordSpec with MustMatchers {

  import Fitness._

  "capture" should {

    "return 0 for initial board" in {
      capture(Board()) must be (0)
    }

    "be negative when current player is disadvantaged" in {
      capture(Board().successor(Location(4, 5))) must be (-3)
    }

    "be positive when current player has advantage" in {
      val board = Board().successor(Location(4, 5))
      capture(Board(X, board.grid)) must be (3)
    }

  }

  "corner" should {

    "score initial board 0" in {
      corner(Board()) must be (0)
    }

    "score board with corner" in {
      val board = Board(X, Map(Location(0, 0) -> X))
      corner(board) must be (1)
    }

    "score board with opponent's corner" in {
      val board = Board(O, Map(Location(0, 0) -> X))
      corner(board) must be (-1)
    }

    "score board with more than a corner" in {
      val board = Board(X, Map(Location(0, 0) -> X, Location(1, 0) -> O))
      corner(board) must be (1)
    }

    "score board with three corners" in {
      val board = Board(X, Map(Location(0, 0) -> X, Location(7, 0) -> O, Location(7, 7) -> X))
      corner(board) must be (1)
    }

  }

  "mobility" should {

    "be 0 originally" in {
      mobility(Board()) must be (0)
    }

    "be 0 when mobility is even, even if capture is not" in {
      val board = Board().successor(Location(4, 5))
      mobility(board) must be (0)
    }

    "be positive when current player has advantage" in {
      val board0 = Board().successor(Location(4, 5))
      val board1 = board0.successor(Location(5, 3))
      mobility(board1) must be (1)
    }

    "be negative when current player is disadvantaged" in {
      val board0 = Board().successor(Location(4, 5))
      val board1 = board0.successor(Location(5, 3))
      mobility(Board(O, board1.grid)) must be (-1)
    }

  }



}
