import NpmCliBase._

val textLintAll = taskKey[Unit]("lint text, html")
val textTestAll = taskKey[Unit]("test scala, links")

ThisBuild / onChangedBuildSource := ReloadOnSourceChanges

name := "textbook"

scalaVersion := "2.13.14"

crossScalaVersions += "3.4.1"

enablePlugins(MdocPlugin)

mdocIn := srcDir

mdocOut := compiledSrcDir

cleanFiles += compiledSrcDir

libraryDependencies ++= Seq(
  // TODO sbtがScala 2.13対応したら再び有効化
  // "org.scala-sbt" % "sbt" % sbtVersion.value,
  "org.mockito" % "mockito-core" % "5.12.0",
  "org.scalacheck" %% "scalacheck" % "1.18.0",
  "org.scalatest" %% "scalatest-flatspec" % "3.2.18", // mdocで使うので、テストライブラリだが、わざとcompileスコープ
  "org.scalatest" %% "scalatest-diagrams" % "3.2.18"
)

Honkit.settings

TextLint.settings

LinkTest.settings

textLintAll := Def.sequential(LinkTest.textEslint, TextLint.textLint.toTask("")).value

textTestAll := Def.sequential(Test / compile, LinkTest.textLinkTest).value

aggregateProjects(
  RootProject(file("src/example_projects/trait-example")),
  RootProject(file("src/example_projects/trait-refactored-example"))
)
