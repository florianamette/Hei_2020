package project


import com.hei.fp.project.Functions
import com.hei.fp.project.Utils
import org.scalatest.freespec.AnyFreeSpec


class functions_test extends AnyFreeSpec{

  "My function  " - {
    ": cube() for 3 " - {
      "should return 81" in {
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
  }
}

