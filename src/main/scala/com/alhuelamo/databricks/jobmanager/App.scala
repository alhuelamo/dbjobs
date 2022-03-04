package com.alhuelamo.databricks.jobmanager

import caseapp._
import com.alhuelamo.databricks.jobmanager.conf._

object App extends CommandApp[Command] {

  def run(command: Command, args: RemainingArgs): Unit = {
    implicit val conf = AppConf(command)

    conf.action match {
      case "start" => conf.jobIds.foreach(Actions.startRuns)
      case "stop"  => conf.jobIds.foreach(Actions.stopActiveRuns)
      case invalid => throw new IllegalArgumentException(s"$invalid is not a valid flow (valid ones: start, stop)")
    }
  }

}
