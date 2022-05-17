# NamedArgs

[Scalafix](https://github.com/scalacenter/scalafix) rule to force named arguments.

## Usage

Add to `build.sbt`:

`ThisBuild / scalafixDependencies += "com.github.xplosunn" %% "NamedArgs" % "0.0.1"`

Add to `.scalafix.conf`:

`fix.NamedArgs`

## Configuration

| Condition                            | How to override it in `.scalafix.conf` (example is default value) |
|--------------------------------------|-------------------------------------------------------------------|
| Parameter type                       | NamedArgs.typeList = ["Int", "Boolean"]                           |
| Regex over the argument              | NamedArgs.argRegexs = ["Nil", "None"]                             |
| Min arguments (per parameter list)   | NamedArgs.minLength = 5                                           |
