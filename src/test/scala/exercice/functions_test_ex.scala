package project

import com.hei.fp.project.Functions
import org.scalatest.freespec.AnyFreeSpec

class functions_test_ex extends AnyFreeSpec{

  "My function  " - {
    ": cube(Int) for 3 " - {
      "should return 27" in {
        assert( ??? == ??? )
      }
    }
    "addThree(Int) for 3 given " - {
      "should return 3 + (3+2) = 8" in {
        val fnt = new Functions()
        assert(??? == ???)
      }
    }
    "addThree(Int, f(Int)) for 3 and anonymous(x + 1) given " - {
      "should return 3 + (3+1) = 7" in {
        val fnt = new Functions()
        assert( ??? == ??? )
      }
    }
  }
}

