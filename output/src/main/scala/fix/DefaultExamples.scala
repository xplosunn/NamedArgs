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
    f(lowerBound = 0, upperBound = 1, "", isTrue = true)(isFalse = false, List(1), list2 = Nil)
}
