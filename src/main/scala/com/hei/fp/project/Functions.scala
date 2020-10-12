package com.hei.fp.project

import scala.annotation.tailrec
import scala.math.Numeric.BigDecimalAsIfIntegral.mkOrderingOps

// A comment!
/* Another comment */
/** A documentation comment */

class Functions extends Utils {

  var x = 12 // variable mutable
  val y = 14 // variable immutable - y sera évalué à l'execution, une fois pour toutes.
  def z = 14 // la partie droite sera évaluée lors de l'appel de z.

  /*------------ Les paramètres -----------------*/
  def square(x : Double) : Double = x * x
  // square: (x: Double)Double
  // scala> square(2)
  // res0: Double = 4.0
  // scala> square(5 + 3)
  // res1: Double = 64.0
  // scala> square(square(4))
  // res2: Double = 256.0

  /**
   * Write a function sumOfSquare that returns x^2 + y2 and test it with 3 , 2+2
   */
  def sumOfSquares(x: Double, y: Double) = square(x) + square(y)

  /*-------------- Expressions conditionnelles : if - else ------------------------*/
  def abs(x: Double) = if (x < 0) -x else x

  //-------------- Call by name - call by value
  def loop : Int = loop
  //  loop: Int
  def first(x: Int, y: Int) = x  // ----- Call by name => first(1,loop) = execution infinie
  // first: (x: Int, y: Int)Int
  def first(x: Int, y: => Int) = x  // ---- Call by value => first(1,loop)    res0: Int = 1

  // Anonymous function
  (1 to 5).map( x => x * 1)
  ///(x: Int, y: Int) => x + y

  /**
   * Write a val function with parameters
   */
  val s = (x:Int) => x + 1

  /**
   * @param x : Int
   * @return x + 1
   */
  def addOne(x:Int) : Int = x + 1

  /**
   * define and test a function for x : int
   * @param x
   * @return x^3
   */
  def cube(x:Int) : Int = x * x * x

  /**
   * Define addTwo trait function then define and test a function take an integer x
   * @param x
   * @return  : x + addTwo(x) trait function
   */
  def addTwoToGivenInt(x : Int) : Int = x + addTwo(x)

  /**
   * Define and test a function take a integer and a function taking int & return Int with
   * // test it with anonymous function : f(x) = x + 2
   * @param x
   * @return  : x + f(x)
   */
  def addAnonymousToInt(x : Int, f:(Int) => Int ) : Int = x + f(x)

  // todo work on the array of list & functions applied

  val numbers = Array(Array(1,2,3,4,5),
    Array(6,7,8,9,10),
    Array.fill(10)(2))

  /**
  write a function that takes as parameter :
  - an object of Type [T]
  - a function that returns an object of Type [T]
  - return an objet [T]
   */
  def operation(xs : Array[Int], f :(Int) => Int) : Array[Int] = {
    xs.map(x => f(x))
  }

  def runAllOperations : Unit =
    numbers
    .flatMap(operation(_,cube))
    .foreach(println)

  /*-------------- Recursivité ---------------------------------------------------*/
  /**
   * Write and test a function return the factorial value of an integer
   * @param n : Int
   * @return fact(x)
   */
  def factorielle(n : Int) : Int = {
    if (n == 1) 1
    else n * factorielle(n - 1)
  }

  /*------------- Calcul des la racine carrée par la méthode de Newton ------------*/
  // Objectif : définir la fonction
  // def sqrt(x:Double) : Double = ???


  // todo functions imbriquées : racines carrées par la methode de Newton
  // Algorithme y = sqrt(x)
  // On part d'une approximation de y => par exemple y  = 1 pour la racine carrée de 2
  // Puis on améliore à chaque itération en prenant la valeur (nouvelle estimation) de (y + x/y)/2
  //  y     x/y       (y + x/y)/2
  //  1     2/1        1.5
  //  1.5   2/1.5      1.4167    ....

  def sqrtIter(guess: Double, x: Double): Double =
    if (isGoodEnough(guess, x)) guess
    else sqrtIter(improve(guess, x), x)

  val e : Double = 0.001
  def improve(guess: Double, x: Double) : Double = (guess + x / guess) / 2
  def isGoodEnough(guess: Double, x: Double): Boolean = abs(square(guess) - x) < e

  def sqrt(x: Double) = sqrtIter(1.0, x)


   // todo rewrite the function sqrt(x) with only one block -  What about the parameters ? Can we simplify ?
   def sqrt = ???


  /* ----------- recursivité terminale ------------------*/
  def gcd(a :Int, b : Int) : Int =
    if (b == 0 ) a
    else gcd(b, a % b)

  // gcd(14, 21)
  // if (21 == 0) 14 else gcd(21, 14 % 21) if (false) 14 else gcd(21, 14 % 21) gcd(21, 14 % 21)
  // gcd(21, 14)
  // if (14 == 0) 21 else gcd(14, 21 % 14) gcd(14, 21 % 14)
  // gcd(14, 7)
  // if (7==0) 14 else gcd(7, 14 % 7) gcd(7,14 % 7)
  // gcd(7, 0)
  // if(0 = =0) 7 else gcd(0, 7 % 0)
  // 7

  // -> todo compare with the last factorielle formulation
  // -> todo write a tailrec factorielle function

 def fact(acc : Int, n : Int) : Int = {
      if (n <= 1) acc
      else fact(n * acc, n - 1)
  }

  // todo currying



  /**
   * Write a functions
   * @param l : List[Int]
   * @return : sum of list elements
   */
  def foldSum(l : List[Int]) = {
    l.foldLeft(0)(_ + _)
  }


}
