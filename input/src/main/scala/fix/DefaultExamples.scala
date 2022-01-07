/*
rule = fix.NamedArgs
 */
package fix

object DefaultExamples {
  def f(
    lowerBound: Int,
    upperBound: Int,
    obj: Object,
    isTrue: Boolean,
  )(
    isFalse: Boolean,
    list1: List[Int],
    list2: List[Int],
  ): Unit = {}

  def g() = // named args
    f(0, upperBound = 1, "", true)(false, List(1), Nil)
}
