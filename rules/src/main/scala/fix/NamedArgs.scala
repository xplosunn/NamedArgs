package fix

import metaconfig.Configured
import scalafix.v1._

import scala.annotation.tailrec
import scala.meta._

class NamedArgs(config: NamedArgsConfig) extends SemanticRule("fix.NamedArgs") {

  def this() = this(NamedArgsConfig())

  private val regexPatterns: List[java.util.regex.Pattern] =
    config.argRegexs.map(r => java.util.regex.Pattern.compile(r))

  override def withConfiguration(config: Configuration): Configured[Rule] =
    config.conf
      .getOrElse("NamedArgs")(this.config)
      .map(new NamedArgs(_))

  override def fix(implicit doc: SemanticDocument): Patch =
    doc.tree
      .collect {
        case Init(_, name, argsLists) if !hasPlaceholder(argsLists.flatten) =>
          methodSignature(name.symbol) match {
            case Some(methodSig) =>
              val patchGens: List[(Term, Int) => Patch] =
                methodSig.parameterLists.zipWithIndex.map { case (_, idx) =>
                  patchForArgLists(methodSig, idx)
                }
              argsLists
                .zip(patchGens)
                .flatMap { case (argsInBlock, patchGen) =>
                  argsInBlock.zipWithIndex.map { case (t, idx) => patchGen(t, idx) }
                }
            case None => List.empty
          }
        case Term.Apply(fun, args) if !hasPlaceholder(args) =>
          val fname = functionTerm(fun)
          val methodSignatureOpt =
            methodSignature(fname.symbol).orElse(companionObjectApply(fname))
          methodSignatureOpt match {
            case Some(methodSig) if methodSig.parameterLists.nonEmpty =>
              val patchGen: (Term, Int) => Patch =
                patchForArgLists(methodSig, parametersListsIndex(fname))
              args.zipWithIndex.map { case (t, idx) => patchGen(t, idx) }
            case _ => List.empty
          }
      }
      .flatten
      .asPatch

  private def hasPlaceholder(args: List[Term]): Boolean =
    args.exists {
      case Term.Placeholder() => true
      case _ => false
    }

  private def functionTerm(term: Term): Term =
    term match {
      case name: Term.Name => name
      case apply: Term.Apply => apply
      case Term.ApplyType(term, _) => term
      case s: Term.Select => s.name
    }

  private def patchForArgLists(
    methodSignature: MethodSignature,
    paramBlockIndex: Int,
  )(implicit doc: SemanticDocument): (Term, Int) => Patch = {
    val thisParamBlock =
      methodSignature.parameterLists(paramBlockIndex)

    if (config.minLength.exists(_ <= thisParamBlock.length)) { (term: Term, index: Int) =>
      term match {
        case _: Term.Assign =>
          Patch.empty
        case term =>
          thisParamBlock.lift(index) match {
            case Some(symInfo) =>
              val paramName = Term.Name(symInfo.displayName).toString
              Patch.addLeft(term, s"$paramName = ")
            case None =>
              Patch.empty
          }
      }
    } else { (term: Term, index: Int) =>
      term match {
        case _: Term.Assign =>
          Patch.empty
        case term =>
          thisParamBlock.lift(index) match {
            case Some(symInfo) =>
              val parameterType = symInfo.signature match {
                case ValueSignature(tpe) =>
                  tpe.toString()
              }
              if (
                config.typeList.contains(parameterType) || regexPatterns.exists(
                  _.matcher(term.toString()).matches()
                )
              ) {
                val paramName = Term.Name(symInfo.displayName).toString
                Patch.addLeft(term, s"$paramName = ")
              } else Patch.empty
            case None =>
              Patch.empty
          }
      }
    }
  }

  private def methodSignature(
    functionSymbol: Symbol
  )(implicit doc: SemanticDocument): Option[MethodSignature] =
    functionSymbol.info match {
      case Some(info) if info.isScala =>
        info.signature match {
          case methodSignature: MethodSignature => Some(methodSignature)
          case _ => None
        }
      case _ => None
    }

  private def companionObjectApply(
    functionTerm: Term
  )(implicit doc: SemanticDocument): Option[MethodSignature] =
    functionTerm.synthetics
      .flatMap(_.symbol)
      .flatMap(_.info)
      .map(_.signature)
      .collectFirst { case methodSignature: MethodSignature =>
        methodSignature
      }

  @tailrec
  private def parametersListsIndex(curFuncTerm: Term, index: Int = 0): Int =
    curFuncTerm match {
      case Term.Apply(innerFuncTerm, _) => parametersListsIndex(innerFuncTerm, index + 1)
      case _ => index
    }

}
