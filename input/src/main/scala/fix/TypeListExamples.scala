/*
rule = fix.NamedArgs
NamedArgs.typeList = ["Int", "String"]
NamedArgs.argRegexs = []
 */
package fix

object TypeListExamples {
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
    f(0, 1, "", true)(false, List(1), Nil)
}
