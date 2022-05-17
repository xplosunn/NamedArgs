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
    lowerBound = 1,
    upperBound = 2,
    obj = "",
    isTrue = false,
    text = "",
    anotherText = "",
  )(false, Nil, List.empty)
  val b = g(
    false,
    Nil,
    List.empty,
  )(lowerBound = 1, upperBound = 2, obj = "", isTrue = false, text = "", anotherText = "")
  val c = h(lowerBound = 1, upperBound = 2, obj = "", isTrue = false, text = "")

}
