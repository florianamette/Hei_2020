package project


import com.hei.fp.project.Functions
import com.hei.fp.project.Utils
import org.scalatest.freespec.AnyFreeSpec


class functions_test extends AnyFreeSpec{


  "My function  " - {
    ": sumOfQuare for 3, 2+2 " - {
      "should return 25" in {
        assert(new Functions().sumOfSquares(3,2+2) == 25)
      }
    }
  }

  "My function  " - {
    ": foldSum for 3 " - {
      "should return 27" in {
        val l = List(1, 1, 3, 5, 8)
        assert(new Functions().foldSum(l) == 18)
      }
    }
  }

  "My function  " - {
    ": cube() for 3 " - {
      "should return 27" in {
        assert(new Functions().cube(3) == 27)
      }
    }

    "addTwoToGivenInt(Int) for 3 given " - {
      "should return 3 + (3+2) = 8" in {
        val fnt = new Functions()
        assert(fnt.addTwoToGivenInt(3) == 8)
      }
    }

    "addThree(Int, f(Int)) for 3 and anonymous x + 2 given " - {
      "should return 1 + (3+1) = " in {
        val fnt = new Functions()
        assert(fnt.addAnonymousToInt(3, x  => x + 2) == 8)
      }
    }
    "factoriel(3) " - {
      "should return 3*2*1 = 6 " in {
        val fnt = new Functions()
        assert(fnt.factorielle(3) == 6)
      }
    }
    "fact(3) " - {
      "should return 3*2*1 = 6 " in {
        val fnt = new Functions()
        assert(fnt.fact(1,3) == 6)
      }
    }

  }
}

