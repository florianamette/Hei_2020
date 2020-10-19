package com.hei.fp.exercices

class Rational(x: Int, y: Int ) extends Utils {
    require( y != 0, "denominator must be non zero")
    // assert( sqrt(y) >= 0, " sqrt y should be positive ")

    private def gcd(a :Int, b : Int) : Int =
      if (b == 0 ) a
      else gcd(b, a % b)

    private val g = gcd(x, y)
    val numer = x / g
    val denom = y / g

    def numerSign : String = s" num : $denom => ${conditionTrue(isPositive(denom))}"

    def this(x : Int) = this(x, 1)

    def +(that : Rational) : Rational =
      new Rational( numer * that.denom + that.numer * denom,
        denom * that.denom )

    def neg : Rational = new Rational(- numer, denom)

    def -(that : Rational) : Rational = this + that.neg

    /* less et max */
    def <(that : Rational) : Boolean = numer * that.denom < that.numer * denom

    def max(that : Rational) = if (this < that) that else this

    override def toString = numer + " / " + denom
}

