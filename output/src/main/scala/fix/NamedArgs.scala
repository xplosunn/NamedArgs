package fix

object NamedArgs {
  def f(lowerBound: Int, upperBound: Int, obj: Object): Unit = {}

  def g() =
    f(lowerBound = 0, upperBound = 1, "")
}
