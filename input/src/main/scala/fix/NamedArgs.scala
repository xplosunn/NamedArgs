/*
rule = NamedArgs
*/
package fix

object NamedArgs {
  def f(lowerBound: Int, upperBound: Int, obj: Object): Unit = {}

  def g() =
    f(0, 1, "")
}
