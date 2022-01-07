package fix

import metaconfig.{ ConfDecoder, ConfEncoder }
import metaconfig.generic.{ deriveDecoder, deriveEncoder, deriveSurface, Surface }

case class NamedArgsConfig(
  typeList: List[String] = List(
    "Int",
    "Boolean",
  ),
  argRegexs: List[String] = List(
    "Nil",
    "None",
  ),
)

object NamedArgsConfig {
  val default: NamedArgsConfig = NamedArgsConfig()

  implicit val surface: Surface[NamedArgsConfig] = deriveSurface
  implicit val encoder: ConfEncoder[NamedArgsConfig] = deriveEncoder
  implicit val decoder: ConfDecoder[NamedArgsConfig] = deriveDecoder(default)
}
