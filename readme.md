# NamedArgs

Warning: Not released yet.

[Scalafix](https://github.com/scalacenter/scalafix) rule to force named arguments.

| Condition                            | How to override it in `.scalafix.conf` (example is default value) |
|--------------------------------------|-------------------------------------------------------------------|
| Parameter type                       | NamedArgs.typeList = ["Int", "String"]                            |
| Regex over the argument              | NamedArgs.argRegexs = ["Nil", "None"]                             |
| Min arguments (per parameter list)   | NamedArgs.minLength = 5                                           |
