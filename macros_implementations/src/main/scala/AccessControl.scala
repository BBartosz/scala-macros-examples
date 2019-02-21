import scala.annotation.{StaticAnnotation, tailrec}
import scala.language.experimental.macros
import scala.reflect.macros.blackbox.Context

class AccessControl extends StaticAnnotation {
  def macroTransform(annottees: Any*) = macro AccessControl.impl
}

object AccessControl {
  def impl(c: Context)(annottees: c.Expr[Any]*): c.Expr[Any] = {
    import c.universe._

    val result = {
      annottees.map(_.tree).toList match {
        case q"$mods def $methodName[..$tparams](...$paramss): $returnType = { ..$body }" :: Nil => {
          annottees.map(_.tree).toList
          q"""$mods def $methodName[..$tparams](...$paramss): $returnType =  {
            val result = {..$body} + 1
            if(!context.role == "Admin" || context.customerId < 1000) {
              throw new Exception("Authorization Exception!")
            }
            println(result)
            result
          }"""
        }
        case _ => c.abort(c.enclosingPosition, "Annotation @AccessControl can be used only with methods")
      }
    }
    c.Expr[Any](result)
  }
}
//
//object MethodName {
//
//  implicit def methodName[A](extractor: (A) => Any): MethodName = macro MethodNamesMacro[A]
//
//  def methodNamesMacro[A: c.WeakTypeTag](c: Context)(extractor: c.Expr[(A) => Any]): c.Expr[MethodName] = {
//    import c.universe._
//
//    @tailrec
//    def resolveFunctionName(f: Function): String = {
//      f.body match {
//        // the function name
//        case t: Select => t.name.decoded
//
//        case t: Function =>
//          resolveFunctionName(t)
//
//        // an application of a function and extracting the name
//        case t: Apply if t.fun.isInstanceOf[Select] =>
//          t.fun.asInstanceOf[Select].name.decoded
//
//        // curried lambda
//        case t: Block if t.expr.isInstanceOf[Function] =>
//          val func = t.expr.asInstanceOf[Function]
//
//          resolveFunctionName(func)
//
//        case _ => {
//          throw new RuntimeException("Unable to resolve function name for expression: " + f.body)
//        }
//      }
//    }
//
//    val name = resolveFunctionName(extractor.tree.asInstanceOf[Function])
//
//    val literal = c.Expr[String](Literal(Constant(name)))
//
//    reify {
//      MethodName(literal.splice)
//    }
//  }
//}
