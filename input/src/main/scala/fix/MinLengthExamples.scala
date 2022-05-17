/*
rule = fix.NamedArgs
NamedArgs.typeList = []
NamedArgs.argRegexs = []
NamedArgs.minLength = 5
 */
package fix

object MinLengthExamples {

  def f(
    lowerBound: Int,
    upperBound: Int,
    obj: Object,
    isTrue: Boolean,
    text: String,
    anotherText: String,
  )(
    isFalse: Boolean,
    list1: List[Int],
    list2: List[Int],
  ): Unit = {}

  def g(
    isFalse: Boolean,
    list1: List[Int],
    list2: List[Int],
  )(
    lowerBound: Int,
    upperBound: Int,
    obj: Object,
    isTrue: Boolean,
    text: String,
    anotherText: String,
  ): Unit = {}

  def h(
    lowerBound: Int,
    upperBound: Int,
    obj: Object,
    isTrue: Boolean,
    text: String,
  ): Unit = {}

  val a = f(
    1,
    2,
    "",
    false,
    "",
    anotherText = "",
  )(false, Nil, List.empty)
  val b = g(
    false,
    Nil,
    List.empty,
  )(1, 2, "", false, "", anotherText = "")
  val c = h(1, 2, "", false, "")

}
