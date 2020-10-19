# Case Classes et Pattern Matching
Supposons que nous voulions écrire un interpréteur d’expressions arithmétiques. Pour commencer simplement, nous nous limiterons aux nombres et à l’opération d’addition. Ces expressions peuvent être représentées par une hiérarchie de classes dont la racine est la classe Expr, avec ses deux sous-classes Number et Sum. Une expression comme 1 + (3 + 7) peut alors être représentée par
          new Sum(new Number(1), new Sum(new Number(3), new Number(7)))
Pour évaluer une expression comme celle-ci, il faut connaître sa forme (Sum ou Number) et accéder à ses composants. L’implémentation suivante fournit les méthodes nécessaires :
```scala
abstract class Expr {
  def isNumber: Boolean
  def isSum: Boolean
  def numValue: Int
  def leftOp: Expr
  def rightOp: Expr
}

class Number(n: Int) extends Expr {
    def isNumber: Boolean = true
    def isSum: Boolean = false
    def numValue: Int = n
    def leftOp: Expr = error("Number.leftOp")
    def rightOp: Expr = error("Number.rightOp") 
}

class Sum(e1: Expr, e2: Expr) extends Expr { 
    def isNumber: Boolean = false
    def isSum: Boolean = true
    def numValue: Int = error("Sum.numValue")
    def leftOp: Expr = e1
    def rightOp: Expr = e2 
}
```

Grâce à ces méthodes de classification et d’accès, l'écriture d’une fonction d'évalution est relativement simple :

```Scala
def eval(e: Expr): Int = {
    if (e.isNumber) e.numValue
    else if (e.isSum) eval(e.leftOp) + eval(e.rightOp) else error("unrecognized expression kind")
}
```
Cependant, définir toutes ces méthodes dans les classes Sum et Number est assez pénible. En outre, le problème empirera lorsque nous voudrons ajouter d’autres formes d’expressions. Si, par exemple, nous voulons ajouter la multiplication, nous devrons non seulement im- plémenter une classe Prod avec toutes les méthodes de classification et d’accès, mais également introduire une nouvelle méthode abstraite isProduct dans la classe Expr et l’implémenter dans les sous-classes Number, Sum et Product. Devoir modifier un code existant lorsqu’un système évolue est toujours un problème car cela introduit des soucis de versions et de maintenance.
La promesse de la programmation orientée objet est que ce genre de modifications devraient être inutiles car elles peuvent être évitée en réutilisant par héritage le code existant et non modifié. Évidemment, notre problème se résoud en utilisant une décomposition mieux orientée objet. Le principe est de faire de l’opération eval de "haut niveau" une méthode

 de chaque classe d’expression au lieu de l’implémenter comme une fonction extérieure à la hiérarchie des classes comme nous l’avons fait plus haut. eval étant désormais un membre de chaque expression, toutes les méthodes de classification et d’accès deviennent inutiles et l’implémentation s’en trouve considérablement simplifiée :
```Scala
abstract class Expr {
  def eval: Int
}

class Number(n: Int) extends Expr { 
  def eval: Int = n
}

class Sum(e1: Expr, e2: Expr) extends Expr { 
  def eval: Int = e1.eval + e2.eval
}
```

En outre, l’ajout d’une nouvelle classe Prod n’implique plus de modifier le code existant :

```Scala
class Prod(e1: Expr, e2: Expr) extends Expr { 
  def eval: Int = e1.eval * e2.eval
}
```
La conclusion que nous pouvons tirer de cet exemple est qu’une décomposition orientée objet convient parfaitement à la construction des systèmes susceptibles d'être étendus par de nouveaux types de données. Mais nous pourrions aussi étendre notre exemple d’une autre façon, en ajoutant de nouvelles opérations sur les expressions. Nous pourrions, par exemple, vouloir ajouter une opération qui affiche proprement un arbre d’expressions sur la sortie standard.

Si nous avons défini toutes les méthodes de classification et d’accès, cette opération peut aisément être implémentée comme une fonction externe :

```Scala
def print(e: Expr) {
  if (e.isNumber) Console.print(e.numValue) 
  else if (e.isSum) {
  Console.print("(") 
  print(e.leftOp)
  Console.print("+")
  print(e.rightOp)
  Console.print(")")
  }
  else error("unrecognized expression kind") 
}
```

Si, par contre, nous avons choisi une décomposition orientée objet des expressions, nous devrons ajouter une nouvelle procédure print à chaque classe :
```Scala
abstract class Expr {
  def eval: Int
  def print
}
class Number(n: Int) extends Expr { 
    def eval: Int = n
    def print { Console.print(n) }
}
class Sum(e1: Expr, e2: Expr) extends Expr {
  def eval: Int = e1.eval + e2.eval
  def print {
    Console.print("(")
    print(e1)
    Console.print("+")
    print(e2)
  Console.print(")")
  }
}
```
Une décomposition orientée objet classique nécessite donc la modification de toutes les classes lorsque l’on ajoute de nouvelles opérations à un système.

Comme autre moyen d'étendre l’interpréteur, pensez à la simplification des expressions. Nous pourrions vouloir écrire, par exemple, une fonction qui réécrit les expressions de la forme a * b + a * c en a * (b + c). Ce traitement nécessite d’inspecter plusieurs noeuds de l’arbre d’expresson en même temps et ne peut donc pas être implémenté par une méthode dans chaque classe, sauf si cette méthode inspecte également les autres noeuds. Nous sommes donc obligés ici d’avoir des méthodes de classification et d’accès, ce qui semble nous ramener au point de départ, avec tous les problèmes d’extension que nous avons déjà évoqués.

Si l’on examine ce problème de plus près, on observe que le seul but des méthodes de classification et d’accès consiste à inverser le processus de construction des données. Elles nous permettent, d’abord, de déterminer quelle est la sous-classe utilisée et, ensuite, de savoir quels étaient les paramètres du constructeur. Cette situation étant relativement fréquente, Scals dispose d’un mécanisme permettant de l’automatiquer : les case classes.
# Case classes et case objets

Les _case classes_ et les _case objets_ sont définis comme des classes ou des objets normaux, sauf que leur définition est préfixée du modificateur **case**.
Les définitions suivantes, par exemple :
```scala
abstract class Expr
case class Number(n: Int) extends Expr
case class Sum(e1: Expr, e2: Expr) extends Expr
```

créent les case classes Number et Sum. Le modificateur case devant une définition de classes ou d’objet a les effets suivants :
```scala
def Number(n: Int) = new Number(n)
def Sum(e1: Expr, e2: Expr) = new Sum(e1, e2)
```
1- Les case classes fournissent implicitement une méthode constructeur de même nom que la classe. Dans notre exemple, les deux méthodes suivantes sont donc implicitement créées :
Nous pouvons donc désormais construire des arbres d’expressions de façon un peu plus concise :
```scala
Sum(Sum(Number(1), Number(2)), Number(3))
```

2- Les case classes et les case objets fournissent implicitement les méthodes toString, equals et hashCode, qui redéfinissent les méthodes de même nom dans la classe AnyRef. L’implémentation de ces méthodes prend en compte la structure d’une instance de la classe. La méthode toString représente un arbre d’expressions comme il a été construit :
```scala
Sum(Sum(Number(1), Number(2)), Number(3))
```
sera donc converti exactement en cette chaîne alors que l’implémentation par défaut de la classe AnyRef aurait renvoyé une chaîne formée du nom du constructeur le plus externe, Sum, suivi d’un nombre. La méthode equals considère que deux instances de la classe sont égales si elles ont été construites avec le même constructeur et les mêmes paramètres égaux deux à deux. La redéfinition de cette méthode affecte également l’implémentation de == et != puisque, en Scala, ces opérateurs sont implémentés en termes d'equals :

```scala
Sum(Number(1), Number(2)) == Sum(Number(1), Number(2))
```

renverra donc **true**. Si Sum ou Number n'étaient pas des case classes, cette expression aurait renvoyé false puisque l’implémentation standard de equals dans la classe AnyRef considère toujours que des objets créés par des appels différents sont également différents. La méthode hashCode suit le même principe que les deux autres. Elle calcule un code de hachage à partir du nom du constructeur de la case classe et des paramètres passés au constructeur au lieu d’utiliser l’adresse de l’objet, qui est le comportement de l’implémentation par défaut de hashCode.

3- Les case classes fournissent implicitement des méthodes d’accès permettant de récupérer les paramètres passés au constructeur. Dans notre exemple, Number disposerait donc d’une méthode d’accès renvoyant la valeur du paramètre n du constructeur :
```scala
def n: Int
```
alors que Sum aurait deux méthodes d’accès :
```scala
def e1: Expr
def e2: Expr
```

Pour une valeur s de type Sum, nous pouvons donc écrire s.e1 pour accéder à l’opérande gauche. En revanche, pour une valeur e de type Expr, le terme e.e1 serait illégal car e1 est définie dans Sum – ce n’est pas un membre de la classe de base Expr. Comment alors déterminer le constructeur et accéder aux paramètres du constructeur pour les valeurs dont le type statique est Expr ? La réponse est donnée par la quatrième et dernière particularité des case classes.

4- Les case classes permettent de construire des _motifs_ permettant de désigner leur constructeur.

# Pattern Matching

Le pattern matching est une généralisation de l’instruction switch de C ou Java aux hiérarchies de classes. À la place de l’instruction switch existe une méthode match qui est définie dans la classe racine Any de Scala et qui est donc disponible pour tous les objets. Cette méthode prend en paramètre plusieurs cas. Voici, par exemple, une implémentation de eval qui utilise le pattern matching :

```scala
def eval(e: Expr): Int = e match { 
  case Number(n) => n
  case Sum(l, r) => eval(l) + eval(r)
}
```

Cet exemple utilise deux cas. Chacun d’eux associe un motif à une expression. Les motifs sont comparés avec les valeurs du sélecteur e. Le premier motif ici, Number(n) capture toutes les valeurs de la forme Number(v), où v est une valeur quelconque. En ce cas, la variable n du motif est liée à la valeur v. De même, le motif Sum(l, r) capture toutes les valeurs du sélecteur de la forme Sum(v1, v2) et lie les variables du motif l et r respectivement à v1 et à v2.
En règle générale, les motifs sont construits à partir de :
• Constructeurs de case classes, comme Number et Sum, dont les paramètres sont aussi des motifs.
• Variables, comme n, e1, e2.
• Motif "joker" _
• Littéraux, comme true ou "abc".
• Identificateurs de constantes, comme MAXINT ou EmptySet.


Les variables de motif commencent toujours par une minuscule afin de pouvoir être distinguées des identificateurs de constantes, qui commencent par une majuscule. Un nom de variable ne peut apparaître qu’une seule fois dans un même motif. Sum(x, x) est donc illégal car la variable x apparaît deux fois.
**Signification du pattern matching.** Une expression de pattern matching
```scala
e match {case p1 => e1 ... case pn => en }
```
compare les motifs p1, ..., pn à la valeur du sélecteur e dans l’ordre de leur apparition.
• Un motif constructeur C(p1, ..., pn) capture toutes les valeurs de type C (ou d’un sous-type de C)
qui ont été construites avec des paramètres correspondant aux motifs p1, ..., pn.
• Une variable de motif x capture n’importe quelle valeur et lie ce nom à la valeur.
• Le motif joker _ capture n’importe quelle valeur mais ne lie aucun nom à cette valeur.
• Un motif constant C capture une valeur égale (au sens de ==) à C.
L’expression pattern matching est réécrite par la partie droite du premier cas dont le motif correspond à la valeur du sélecteur. Les références aux variables de motif sont remplacées par les paramètres correspondants du constructeur. Si aucun motif n’a pu capturer de valeur, l’expression s’interrompt en produisant une exception MatchError.
Exemple : Notre modèle de sustitution de l'évaluation d’un programme s'étend assez naturellement au pattern matching. Voici, par exemple, comment est réécrit eval lorsqu’elle est appliquée à une expression simple :
```scala
eval(Sum(Number(1), Number(2)))
```
donne, par réécriture de l’application :
```scala
Sum(Number(1), Number(2)) match {
    case Number(n) => n
    case Sum(e1, e2) => eval(e1) + eval(e2)
}
```
Par réécriture du pattern matching, on obtient :
```scala
eval(Number(1)) + eval(Number(2))
```

En réécrivant le premier appel, on a :
```scala
Number(1) match {
  case Number(n) => n
  case Sum(e1, e2) => eval(e1) + eval(e2)
} + eval(Number(2))
```
Par réécriture du pattern matching, on obtient :
```scala
1 + eval(Number(2))
```
et, en continuant :
```scala
1 + 2 -> 3
```

**Pattern Matching et méthodes.** Dans l’exemple précédent, nous nous sommes servis du pattern matching dans une fonction qui était définie en dehors de la hiérarchie des classes qu’elle compare. Il est évidemment possible de définir une méthode de pattern matching dans la hiérarchie elle-même. Nous pourrions, par exemple, avoir défini evel comme une méthode de lal classe de base Expr et utiliser quand même le pattern matching dans son implémentation :
```scala
abstract class Expr {
    def eval: Int = this match {
      case Number(n) => n
      case Sum(e1, e2) => e1.eval + e2.eval }
}
```
Exercice : Considérez les définitions suivante d’une arborescence d’entiers. Elle peuvent être vues comme une représentation alternative de IntSet :
```scala
abstract class IntTree
case object EmptyTree extends IntTree
case class Node(elem: Int, left: IntTree, right: IntTree) extends IntTree
```

Complétez les implémentations suivantes des méthodes contains et insert pour les IntTree.

```scala
def contains(t: IntTree, v: Int): Boolean = t match { ... 
...
}
def insert(t: IntTree, v: Int): IntTree = t match { ...
...
}
```



**Fonctions anonymes de pattern matching.** Jusqu'à présent, les expressions case apparaissaient toujours au sein d’une opération match. Mais il est également possible d’utiliser les expressions case par elles-mêmes. Un bloc d’expressions case comme :
```scala
{case P1 => E1 ... case Pn => En }
```
est considéré comme une fonction qui compare ses paramètres aux motifs P1, ..., Pn et produit un résultat E1, ou ... ou En (si aucun motif ne correspond, la fonction lèvera une exception MatchError). En d’autres termes, l’expression ci-dessus est considérée comme une forme raccourcie de la fonction anonyme :
```scala
(x => x match { case P1 => E1 ... case Pn => En })
```
où x est une nouvelle variable qui n’est utilisée nulle part ailleurs dans l’expression.