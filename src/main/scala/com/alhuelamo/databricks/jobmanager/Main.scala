package com.alhuelamo.databricks.jobmanager

import cats.implicits.*
import com.monovore.decline.*
import com.alhuelamo.databricks.jobmanager.conf.*

object Main
    extends CommandApp(
      name = "dbjobs",
      header = "Databricks Job Manager",
      main = {
        Cli.opts.mapN { (action, profile, jobIds, plan) =>
          given conf: AppConf = AppConf(action, profile, jobIds, plan)

          if (conf.plan)
            println("WARNING! 'plan' flag is enabled! No actions will be done.")

          import ActionKey.*
          conf.action match {
            case Start => conf.jobIds.foreach(Actions.startRuns)
            case Stop  => conf.jobIds.foreach(Actions.stopActiveRuns)
          }
        }
      }
    )

object Cli {
  def opts = (action, profile, jobIds, plan)

  val action: Opts[ActionKey] = Opts
    .argument[String](metavar = "action")
    .map(ActionKey.apply)

  val profile: Opts[String] =
    Opts.option[String]("profile", short = "p", metavar = "profile", help = "Profile section in ~/.databrickscfgProfile section in ~/.databrickscfg")

  val jobIds: Opts[Vector[Long]] = Opts
    .option[String]("job-ids", short = "s", help = "Comma-separated job IDs to manage")
    .map(s => s.split(",").map(_.trim.toLong).toVector)

  val plan: Opts[Boolean] = Opts.flag("plan", help = "Enable to just show affected jobs and runs.").orFalse
}
