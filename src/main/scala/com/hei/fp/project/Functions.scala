package com.hei.fp.project

class Functions extends Utils {

  // Anonymous function
  (1 to 5).map( x => x + 1)

  (x: Int, y: Int) => x + y

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
   * Define addTwo trait function then define and test a function take a integer
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


  // todo map on array list & functions

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

  // todo correct spelling
  val test =  operation(numbers(0),cube)

  def runAllOperation = ???

  numbers
    .flatMap(operation(_,cube))
    .foreach(println)


  // todo example with implicits

  // todo define high oder function

  // todo functions imbriquées : racines carrées par la methode de Newton

  // todo recursive : go to gcd : Fibionacci  -> trait gcd

  // todo currying



}
