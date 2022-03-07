package com.alhuelamo.databricks.jobmanager

import caseapp._
import com.alhuelamo.databricks.jobmanager.conf._
import shapeless.BuildInfo

object Main extends CommandApp[DjmCommand] {
  override def appName: String = "Databricks Job Manager"
  override def appVersion: String = BuildInfo.version
  override def progName: String = "dbjobs"

  def run(command: DjmCommand, args: RemainingArgs): Unit = {
    implicit val conf = AppConf(command)

    conf.action match {
      case AppConf.`actionStart` => conf.jobIds.foreach(Actions.startRuns)
      case AppConf.`actionStop`  => conf.jobIds.foreach(Actions.stopActiveRuns)
      case invalid => throw new IllegalArgumentException(s"$invalid is not a valid flow (valid ones: start, stop)")
    }
  }

}
