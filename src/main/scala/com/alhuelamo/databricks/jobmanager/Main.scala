package com.alhuelamo.databricks.jobmanager

import cats.implicits.*
import com.monovore.decline.*
import com.alhuelamo.databricks.jobmanager.conf.*

object Main extends CommandApp(
  name = "dbjobs",
  header = "Databricks Job Manager",
  main = {
    Cli.opts.mapN { (action, profile, jobIds, plan) =>
      implicit val conf = AppConf(action, profile, jobIds, plan)

      conf.action match {
        case AppConf.`actionStart` => conf.jobIds.foreach(Actions.startRuns)
        case AppConf.`actionStop`  => conf.jobIds.foreach(Actions.stopActiveRuns)
        case invalid => throw new IllegalArgumentException(s"$invalid is not a valid flow (valid ones: start, stop)")
      }
    }
  }
)
