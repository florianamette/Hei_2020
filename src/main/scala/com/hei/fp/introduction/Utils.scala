package com.hei.fp.exercices

trait Utils {

  def isPositive(x : Int) : Boolean = if (x >= 0) true else false
  def conditionTrue(b : Boolean) = if (b == true) "yes positive" else "should be negative ?!"

}
