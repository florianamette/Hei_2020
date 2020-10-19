import scala.math.sqrt

class Rational(x: Int, y: Int ){
  require( y != 0, "denominator must be non zero")
  // assert( sqrt(y) >= 0, " sqrt y should be positive ")

  private def gcd(a :Int, b : Int) : Int =
    if (b == 0 ) a
    else gcd(b, a % b)

  private val g = gcd(x, y)
  def numer = x  / g
  def denom = y  / g

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

//  class() => new class()
//  Object  = comme un singleton en java, instancié une fois au lancement, pour sa seule et unique valeur



val x = new Rational(1,3)
x.numer
x.denom
val y = new Rational(5,7)
x + y
x.neg
val z = new Rational(3,2)
y + y
x < y
x.max(y)
y < x
y max x
val w = new Rational(3)
//val a = new Rational(1,0)

// todo créer une fonction neg qui donnera x.neg  => -x
// todo ajouter une fonction sub qui soustraira deux nombres rationnels
// todo avec x = 1/3 , y  = 5/7 et z =3/2 combien vaut x - y - z


/* la classe a 2 membres : numer & denom
* on y accède avec "." comme java
* */
/*
def addRationnal (r : Rational, s : Rational) : Rational =
  new Rational(
    r.numer * s.denom + s.numer * r.denom,
    r.denom * s.denom
  )
def makeString(r : Rational) : String = r.numer + "/" + r.denom
makeString(x)
// additionner 1/2 + 2/3 & afficher
makeString(addRationnal(new Rational(1,2), new Rational(2,3)))
*/