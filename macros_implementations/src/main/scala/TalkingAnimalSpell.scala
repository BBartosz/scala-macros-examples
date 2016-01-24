import scala.annotation.StaticAnnotation
import scala.language.experimental.macros
import scala.reflect.macros.blackbox.Context

class TalkingAnimalSpell extends StaticAnnotation {
  def macroTransform(annottees: Any*) = macro TalkingAnimalSpell.impl
}

object TalkingAnimalSpell {
  def impl(c: Context)(annottees: c.Expr[Any]*): c.Expr[Any] = {
    import c.universe._

    val result = {
      annottees.map(_.tree).toList match {
        case q"$mods class $tpname[..$tparams] $ctorMods(...$paramss) extends Animal with ..$parents { $self => ..$stats }" :: Nil => {
          val animalType = tpname.toString()
          q"""$mods class $tpname[..$tparams] $ctorMods(...$paramss) extends Animal with ..$parents{
            def sayHello: Unit = {
              println("Hello I'm " + $animalType + " and my name is " + name)
            }
          }"""
        }
        case _ => c.abort(c.enclosingPosition, "Annotation @TalkingAnimal can be used only with case classes which extends Animal trait")
      }
    }
    c.Expr[Any](result)
  }
}
