/*
 * Copyright (C) 2009-2021 Lightbend Inc. <https://www.lightbend.com>
 */

package akka

import sbt._
import sbt.Keys._
import java.io.File
import sbtwhitesource.WhiteSourcePlugin.autoImport.whitesourceIgnore
import com.lightbend.sbt.publishrsync.PublishRsyncPlugin.autoImport.publishRsyncHost

object Publish extends AutoPlugin {

  val defaultPublishTo = settingKey[File]("Default publish directory")

  override def trigger = allRequirements

  override lazy val projectSettings = Seq(
    publishTo := artifactoryRepo(version.value),
    publishRsyncHost := "akkarepo@gustav.akka.io",
    credentials += Credentials(Path.userHome / ".ivy2" / ".credentials"),
    organizationName := "DASGIP",
    organizationHomepage := Some(url("https://www.dasgip.de")),
    startYear := Some(2009),
    developers := List(
        Developer(
          "akka-contributors",
          "Akka Contributors",
          "akka.official@gmail.com",
          url("https://github.com/akka/akka/graphs/contributors"))),
    publishMavenStyle := true,
    pomIncludeRepository := { x =>
      false
    },
    defaultPublishTo := target.value / "repository")

  private def artifactoryRepo(version: String): Option[Resolver] = {
    if (version.endsWith("-SNAPSHOT")) {
      None
    } else {
      Some(
        Resolver.url(
          "Artifactory third party library releases",
          new URL(s"http://artifactory.zentrale.local/ext-release-local")
        )(Resolver.mavenStylePatterns)
      )
    }
  }
}

/**
 * For projects that are not to be published.
 */
object NoPublish extends AutoPlugin {
  override def requires = plugins.JvmPlugin

  override def projectSettings =
    Seq(skip in publish := true, sources in (Compile, doc) := Seq.empty, whitesourceIgnore := true)
}
