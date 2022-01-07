/*
rule = fix.NamedArgs
NamedArgs.typeList = []
NamedArgs.argRegexs = ["[0-9]", "[a-z].*"]
 */
package fix

object ArgRegexsExamples {
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
    f(10, upperBound = 1, "", true)(false, List(10), Nil)
}
