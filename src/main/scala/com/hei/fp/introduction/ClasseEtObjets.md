
Scala n'a pas de type prédéfini pour représenter les nombres rationnels, mais il est relativement simple d'en définir un à l'aide d'une classe. Voici une implémentation possible :
 
```scala
class Rational(n: Int, d: Int) {
    private def gcd(x: Int, y: Int): Int = {
        if (x == 0) y
        else if (x < 0) gcd(-x, y)
        else if (y < 0) -gcd(x, -y)
        else gcd(y % x, x)
    }
    private val g = gcd(n, d)

    val numer: Int = n/g
    val denom: Int = d/g
    def +(that: Rational) =
        new Rational(numer * that.denom + that.numer * denom,
                     denom * that.denom)
    def -(that: Rational) =
        new Rational(numer * that.denom - that.numer * denom,
                     denom * that.denom)
    def *(that: Rational) =
        new Rational(numer * that.numer, denom * that.denom)
    def /(that: Rational) =
        new Rational(numer * that.denom, denom * that.numer)
}
```

Ce code définit Rational comme une classe avec un constructeur prenant deux arguments n et d correspondant au numérateur et au dénominateur. Cette classe fournit des champs qui renverront ces composantes ainsi que des méthodes permettant d'effectuer les opérations arithmétiques classiques sur des nombres rationnels. Chacune de ces méthodes prend en paramètre l'opérande droit de l'opération ; l'opérande gauche est toujours le nombre rationnel invoquant la méthode concernée.

**Membres privés**. L'implémentation des nombres rationnels définit une méthode privée gcd qui calcule le plus grand commun diviseur de deux entiers et un champ privé g qui contient le PGCD des arguments passés au constructeur. Ces membres sont inaccessibles à l'extérieur de la classe Rational et sont utilisés par l'implémentation de la classe pour éliminer les facteurs communs afin de garantir que le numérateur et le dénominateur soient toujours sous une forme simplifiée.

Création et accès aux objets. Voici un programme qui affiche la somme de tous les nombres 1/i, avec i variant de 1 à 10 :

```scala 
var i = 1
var x = new Rational(0, 1)
while (i <= 10) {
    x += new Rational(1, i)
    i += 1
}
println("" + x.numer + "/" + x.denom)
```

Ici, l'opérateur + de la dernière instruction prend une chaîne de caractères comme opérande gauche et une valeur de type quelconque comme opérande droit. Il convertit cet opérande droit en chaîne et renvoie le résultat de sa concaténation avec l'opérande gauche.

**Héritage et redéfinition**. Toute classe Scala étend une superclasse. Si la classe ne mentionne pas de superclasse dans sa définition, Scala considère qu'il s'agit du type racine scala.AnyRef (dans les implémentations Java, ce type est un alias de java.lang.Object). La classe Rational aurait donc également pu être définie de la façon suivante :

```scala 
class Rational(n: Int, d: Int) extends AnyRef {
    ... // comme précédemment
}
```
Une classe hérite de tous les membres de sa superclasse. Elle peut également redéfinir certains d'entre eux. La classe java.lang.Object, par exemple, définit une méthode toString qui renvoie une représentation de l'objet sous la forme d'une chaîne de caractères :

```scala 
class Object {
    ...
    def toString: String = ...
}
```

L'implémentation de toString dans Object renvoie une chaîne contenant le nom de la classe de l'objet suivi d'un nombre. Il est donc souhaitable de redéfinir cette méthode pour les nombres rationnels :
 
```scala 
class Rational(n: Int, d: Int) extends AnyRef {
    ... // Comme précédemment
    override def toString = "" + numer + "/" + denom
}
```

Notez qu'à la différence de Java, les définitions redéfinies doivent être précédées du modificateur **override**.

Si la classe A étend la classe B, vous pouvez utiliser des objets de type A à chaque fois que des objets de type B sont attendus - le type A est dit conforme au type B. Ici, Rational étant conforme à AnyRef, vous pouvez donc affecter une valeur Rational à une variable de type AnyRef :
 
```scala 
var x: AnyRef = new Rational(1, 2)
```
**Méthodes sans paramètres.** À la différence de Java, les méthodes Scala n'ont pas nécessairement une liste de paramètres comme le montre la méthode square ci-dessous. Cette méthode est simplement invoquée en mentionnant son nom.
 
```scala
class Rational(n: Int, d: Int) extends AnyRef {
    ... // comme précédemment
    def square = new Rational(numer*numer, denom*denom)
}
val r = new Rational(3, 4)
println(r.square)                 // affiche "9/16"
```
L'accès aux méthodes sans paramètre est donc identique à l'accès aux valeurs des champs comme numer. La différence se situe au niveau de leur définition : la partie droite d'une valeur est évaluée lorsque l'objet est créé et ne change plus après, tandis que la partie droite d'une méthode sans paramètre est évaluée à chaque appel de cette méthode. Cet accès uniforme aux champs et aux méthodes sans paramètre donne bien plus de souplesse au développeur de la classe : souvent, un champ dans une version d'une classe devient une valeur calculée dans la version suivante. L'accès uniforme garantit que les applications clientes n'auront pas besoin d'être réécrites à cause de cette modification.

**Classes abstraites.** Supposons que nous voulions écrire une classe pour représenter des ensembles d'entiers, dotée des deux opérations incl et contains. (s incl x) devra renvoyer un nouvel ensemble contenant l'élément x et tous les éléments de s. (s contains x) renverra true si l'ensemble s contient l'élément x ; false dans le cas contraire. L'interface de ces ensembles est décrite par le code suivant :

```scala 
abstract class IntSet {
    def incl(x: Int): IntSet
    def contains(x: Int): Boolean
}
```

IntSet est une classe abstraite, ce qui a deux conséquences. La première est qu'une classe abstraite peut déclarer des membres qui n'ont pas d'implémentation (ces membres sont dits différés). Ici, incl et contains sont dans ce cas. La seconde est qu'une classe abstraite pouvant contenir des membres non implémentés, on ne peut pas créer d'objets de cette classe avec new. En revanche, une classe abstraite peut servir de classe de base pour une autre classe qui implémentera les membres non encore définis.

**Traits.** À la place d'une classe abstraite, on peut également souvent utiliser le mot-clé trait en Scala. Les traits sont des classes abstraites conçues pour être ajoutées à d'autres classes. Un trait permet d'ajouter certaines méthodes ou certains champs à une classe encore inconnue. Un trait Bordered, par exemple, pourrait servir à ajouter un contour à différents composants graphiques. Un autre cas d'utilisation est celui où un trait rassemble les signatures d'une fonctionnalité fournie par différentes classes, comme le font les interfaces Java.

IntSet appartenant à cette catégorie, nous pouvons donc également le définir comme un trait :
 
```scala trait IntSet {
    def incl(x: Int): IntSet
    def contains(x: Int): Boolean
}
```

**Implémentation des classes abstraites.** Supposons que nous voulions implémenter nos ensembles comme des arbres binaires. Il y a deux formes possibles pour les arbres : un arbre pour représenter l'ensemble vide et un arbre formé d'un entier et de deux sous-arbres pour un ensemble non vide. Voici leurs implémentations :
 
```scala 
class EmptySet extends IntSet {
    def contains(x: Int): Boolean = false
    def incl(x: Int): IntSet = new NonEmptySet(x, new EmptySet, new EmptySet)
}

class NonEmptySet(elem: Int, left: IntSet, right: IntSet) extends IntSet {
    def contains(x: Int): Boolean =
        if (x < elem) left contains x
        else if (x > elem) right contains x
        else true
    def incl(x: Int): IntSet =
        if (x < elem) new NonEmptySet(elem, left incl x, right)
        else if (x > elem) new NonEmptySet(elem, left, right incl x)
        else this
}
```

EmptySet et NonEmptySet étendent toutes les deux la classe IntSet, ce qui implique que les types EmptySet et NonEmptySet sont conformes au type IntSet - on peut donc utiliser une valeur de type EmptySet ou NonEmptySet partout où l'on attend une valeur de type IntSet.

Exercice : Écrire les méthodes union et intersection qui renvoient respectivement l'union et l'intersection de deux ensembles.

Exercice : Ajouter la méthode def excl(x: Int) qui renvoie l'ensemble sans l'élément x. Pour ce faire, il est préférable d'implémenter la méthode def isEmpty: Boolean qui teste si l'ensemble est vide.

**Liaison Dynamique.** Les langages orientés objet (dont Scala) utilisent une recherche dynamique pour trouver les méthodes qui sont appelées. Le code invoqué par un appel de méthode dépend donc du type qu'a l'objet lors de l'exécution. Supposons par exemple que s a été déclaré par IntSet et que l'on utilise l'expression (s contains 7) : le code qui sera exécuté lors de l'appel à contains dépendra du type de la valeur de s à l'exécution. Si c'est une valeur EmptySet, c'est l'implémentation de contains dans la classe EmptySet qui s'exécutera (même principe si cette valeur est de type NonEmptySet). Ce comportement est une conséquence directe du modèle de substitution utilisé pour l'évaluation :
 
```scala 
(new EmptySet).contains(7)
```

renvoie false après remplacement de contains par son corps dans la classe EmptySet.
 
```scala 
new NonEmptySet(7, new EmptySet, new EmptySet).contains(1)
```

après remplacement de contains par son corps dans la classe NonEmptySet donne :
 
```scala 
if (1 < 7) new EmptySet contains 1
else if (1 > 7) new EmptySet contains 1
else true
```
soit, après réécriture de l'expression conditionnelle :
 
```scala 
new EmptySet contains 1
```

et le résultat final est donc false.

La recherche dynamique des méthodes est analogue aux appels de fonctions d'ordre supérieur, car, dans les deux cas, l'identité du code qui sera exécuté n'est connue qu'au moment de l'exécution. Cette similitude n'est pas accidentelle : Scala représente chaque valeur de fonction par un objet (voir la section 8.6).

#Objets. 

Dans l'implémentation précédente des ensembles d'entiers, les ensembles vides étaient créés par new EmptySet : un nouvel objet était donc créé à chaque fois que l'on avait besoin d'une valeur ensemble vide. Nous aurions pu éviter cette création inutile en définissant une fois pour toutes une valeur « ensemble vide » et en l'utilisant à la place de chaque occurrence de new EmptySet :
 
```scala 
val EmptySetVal = new EmptySet
```

Un problème de cette approche est qu'une définition de valeur comme celle-ci ne peut pas être une définition de haut niveau en Scala : elle doit faire partie d'une autre classe ou d'un objet. En outre, la définition de la classe EmptySet semble désormais un peu superflue - pourquoi définir une classe d'objet si l'on n'a besoin que d'un seul objet de cette classe ? Une approche plus directe consiste donc à utiliser une définition d'objet :

```scala
object EmptySet extends IntSet {
    def contains(x: Int): Boolean = false
    def incl(x: Int): IntSet = new NonEmptySet(x, EmptySet, EmptySet)
}
```

La syntaxe d'une définition d'objet suit la syntaxe d'une définition de classe ; elle peut avoir une clause extends et elle a un corps. Comme pour les classes, la clause extends définit les membres hérités par l'objet tandis que le corps définit les nouveaux membres ou redéfinit certains membres hérités. Une définition d'objet, par contre, ne définit qu'un seul objet : il est impossible de créer d'autres objets de même structure à l'aide de new. Les définitions d'objets n'ont donc pas non plus de paramètres constructeurs, alors qu'une définition de classe peut en utiliser.

Les définitions d'objets peuvent apparaître n'importe où dans un programme Scala, y compris au niveau supérieur. L'ordre d'exécution des entités de niveau supérieur n'étant pas fixé par Scala, vous pourriez vous demander quand l'objet ainsi défini est exactement créé et initialisé : la réponse est que cet objet est créé dès que l'un de ses membres est utilisé. Cette stratégie est appelée évaluation paresseuse.

Classes standard. Scala est un langage orienté objet pur, ce qui signifie que toute valeur peut être considérée comme un objet. En fait, même les types primitifs comme int ou boolean ne sont pas traités spécialement : ils sont définis comme des alias de classes Scala dans le module Predef :
 
```scala 
type boolean = scala.Boolean
type int = scala.Int
type long = scala.Long
...
```
Pour des raisons d'efficacité, le compilateur représente généralement les valeurs de type scala.Int par des entiers sur 32 bits, les valeurs scala.Boolean par des booléens Java, etc. Mais il convertit ces représentations spéciales en objets lorsque cela est nécessaire - lorsque, par exemple, une valeur primitive entière est passée à une fonction qui attend un paramètre de type AnyRef. La représentation des valeurs primitives n'est donc qu'une optimisation : elle ne modifie pas le sens d'un programme.

Voici la spécification de la classe Boolean :
 ```scala 
package scala
abstract class Boolean {
    def && (x: => Boolean): Boolean
    def || (x: => Boolean): Boolean
    def !                 : Boolean

    def == (x: Boolean)   : Boolean
    def != (x: Boolean)   : Boolean
    def < (x: Boolean)    : Boolean
    def > (x: Boolean)    : Boolean
    def <= (x: Boolean)   : Boolean
    def >= (x: Boolean)   : Boolean
}
```
Les booléens peuvent être définis uniquement à l'aide de classes et d'objets, sans aucune référence à un type prédéfini de booléens ou d'entiers. Nous donnons ci-dessous une implémentation possible, qui n'est pas celle de la bibliothèque standard de Scala, car, comme nous l'avons déjà évoqué, celle-ci utilise des booléens prédéfinis pour des raisons d'efficacité.
 
```scala 
package scala
abstract class Boolean {
    def ifThenElse(thenpart: => Boolean, elsepart: => Boolean)

    def && (x: => Boolean): Boolean = ifThenElse(x, false)
    def || (x: => Boolean): Boolean = ifThenElse(true, x)
    def !                 : Boolean = ifThenElse(false, true)

    def == (x: Boolean) : Boolean = ifThenElse(x, x.!)
    def != (x: Boolean) : Boolean = ifThenElse(x.!, x)
    def < (x: Boolean)  : Boolean = ifThenElse(false, x)
    def > (x: Boolean)  : Boolean = ifThenElse(x.!, false)
    def <= (x: Boolean) : Boolean = ifThenElse(x, true)
    def >= (x: Boolean) : Boolean = ifThenElse(true, x.!)
}
case object True extends Boolean {
    def ifThenElse(t: => Boolean, e: => Boolean) = t
}
case object False extends Boolean {
    def ifThenElse(t: => Boolean, e: => Boolean) = e
}
```

Voici une spécification partielle de la classe Int :
 
```scala 
package scala
abstract class Int extends AnyVal {
    def toLong: Long
    def toFloat: Float
    def toDouble: Double

    def + (that: Double): Double
    def + (that: Float): Float
    def + (that: Long): Long
    def + (that: Int): Int             // Idem pour -, *, /, %

    def << (cnt: Int): Int             // Idem pour >>, >>>

    def & (that: Long): Long
    def & (that: Int): Int             // Idem pour |, ^

    def == (that: Double): Boolean
    def == (that: Float): Boolean
    def == (that: Long): Boolean       // Idem pour !=, <, >, <=, >=
}
```

En principe, la classe Int pourrait aussi être implémentée uniquement à l'aide d'objets et des classes, sans utiliser de type entier prédéfini. Pour voir comment y parvenir, considérons un problème un peu plus simple : l'implémentation d'un type Nat pour représenter les entiers naturels (c'est-à-dire positifs ou nuls). Voici la définition d'une classe Nat abstraite :
 
```scala 
abstract class Nat {
    def isZero: Boolean
    def predecessor: Nat
    def successor: Nat
    def + (that: Nat): Nat
    def - (that: Nat): Nat
}
```

Pour implémenter les opérations de la classe Nat, nous définirons un sous-objet Zero et une sous-classe Succ (pour successeur). Chaque nombre N est représenté par N applications du constructeur Succ à Zero :
 
```scala 
new Succ( ... new Succ (Zero) ... ) // N fois
```
L'implémentation de l'objet Zero est triviale :
 
```scala 
object Zero extends Nat {
    def isZero: Boolean = true
    def predecessor: Nat = error("negative number")
    def successor: Nat = new Succ(Zero)
    def + (that: Nat): Nat = that
    def - (that: Nat): Nat = if (that.isZero) Zero
                             else error("negative number")
}
```
L'implémentation des fonctions predecessor et de soustraction sur Zero lancent une exception Error qui met fin au programme et affiche le message indiqué. Voici l'implémentation de la classe Succ :
 
```scala 
class Succ(x: Nat) extends Nat {
    def isZero: Boolean = false
    def predecessor: Nat = x
    def successor: Nat = new Succ(this)
    def + (that: Nat): Nat = x + that.successor
    def - (that: Nat): Nat = if (that.isZero) this
                             else x - that.predecessor
}
```

Notez l'implémentation de la méthode successor. Pour créer le successeur d'un nombre, nous devons passer l'objet lui-même (désigné par le mot-clé this) comme paramètre du constructeur de Succ.

Les implémentations de + et - contiennent toutes les deux un appel récursif dont l'invocant est le paramètre qui a été passé au constructeur. Cette récursivité se termine lorsque le récepteur est l'objet Zero (ce qui arrivera nécessairement à cause de la façon dont les nombres sont formés).

Exercice : Écrivez une implémentation Integer permettant de représenter les nombres entiers. Cette implémentation doit fournir toutes les opérations de la classe Nat et lui ajouter les deux méthodes suivantes :
 
```scala 
def isPositive: Boolean
def negate: Integer
```

La première méthode doit renvoyer true si le nombre est positif. La seconde méthode doit renvoyer l'opposé du nombre. N'utilisez pas les classes numériques standard de Scala dans cette implémentation (il y a deux façons d'implémenter Integer : on peut partir de l'implémentation actuelle de Nat et représenter un entier comme un nombre naturel avec un signe, ou l'on peut généraliser l'implémentation de Nat en Integer, en utilisant les trois sous-classes Zero pour 0, Succ pour les nombres positifs et Pred pour les nombres négatifs).