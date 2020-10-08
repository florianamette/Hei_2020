package com.hei.fp.project

class PatternMatching {

  def sumKub(l:List[Int]) : Int = { l match {
    case x::xs => x + sumKub(xs)
    case Nil => 0 }}

  val l = List(1,2,3,4)

  sumKub(l)

  // Euler 1

  val listInt = Range(0,10)

}
