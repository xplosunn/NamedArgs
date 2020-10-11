package fix

import scalafix.v1._
import scala.meta._

class NamedArgs extends SemanticRule("NamedArgs") {

  private val typeList = List("Int")

  override def fix(implicit doc: SemanticDocument): Patch = {
    doc.tree.collect {
      case Term.Apply(fun, args) =>
        args.zipWithIndex.collect {
          case (t, i) =>
            fun.symbol.info match {
              case Some(info) =>
                info.signature match {
                  case method: MethodSignature if method.parameterLists.nonEmpty =>
                    val parameter = method.parameterLists.head(i)
                    val parameterType = parameter.signature match {
                      case ValueSignature(tpe) =>
                        tpe.toString()
                    }
                    if (typeList.contains(parameterType)) {
                      val parameterName = parameter.displayName
                      Patch.addLeft(t, s"$parameterName = ")
                    } else {
                      Patch.empty
                    }
                  case _ =>
                    Patch.empty
                }
              case None =>
                Patch.empty
            }
        }
    }.flatten.asPatch
  }

}
