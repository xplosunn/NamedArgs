lazy val V = _root_.scalafix.sbt.BuildInfo

inThisBuild(
  List(
    version := "0.0.1",
    organization := "com.github.xplosunn",
    homepage := Some(url("https://github.com/xplosunn/NamedArgs")),
    licenses := List("Apache-2.0" -> url("https://www.apache.org/licenses/LICENSE-2.0")),
    scalaVersion := V.scala212,
    crossScalaVersions := List(V.scala212, V.scala213),
    addCompilerPlugin(scalafixSemanticdb),
    scalacOptions ++= List(
      "-Yrangepos",
      "-P:semanticdb:synthetics:on",
      "-deprecation"
    ),
    publish / skip := true,
    scmInfo := Some(
      ScmInfo(
        url("https://github.com/xplosunn/NamedArgs"),
        "scm:git@github.com:xplosunn/NamedArgs.git"
      )
    ),
    pomIncludeRepository := { _ => false },
    publishTo := Some("releases" at "https://oss.sonatype.org/service/local/staging/deploy/maven2"),
    publishMavenStyle := true,
    credentials += Credentials(Path.userHome / ".sbt" / "sonatype_credentials"),
    developers := List(
      Developer(
        "xplosunn",
        "Hugo Sousa",
        "gi.ciberon@gmail.com",
        url("https://github.com/xplosunn")
      )
    ),
  )
)

lazy val rules = project.settings(
  moduleName := "NamedArgs",
  libraryDependencies += "ch.epfl.scala" %% "scalafix-core" % V.scalafixVersion,
  publish / skip := false,
)

lazy val input = project.settings(
  skip in publish := true
)

lazy val output = project.settings(
  skip in publish := true
)

lazy val tests = project
  .settings(
    skip in publish := true,
    libraryDependencies += "ch.epfl.scala" % "scalafix-testkit" % V.scalafixVersion % Test cross CrossVersion.full,
    compile.in(Compile) :=
      compile.in(Compile).dependsOn(compile.in(input, Compile)).value,
    scalafixTestkitOutputSourceDirectories :=
      sourceDirectories.in(output, Compile).value,
    scalafixTestkitInputSourceDirectories :=
      sourceDirectories.in(input, Compile).value,
    scalafixTestkitInputClasspath :=
      fullClasspath.in(input, Compile).value
  )
  .dependsOn(rules)
  .enablePlugins(ScalafixTestkitPlugin)
