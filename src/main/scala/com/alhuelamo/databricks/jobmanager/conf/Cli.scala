package com.alhuelamo.databricks.jobmanager.conf

import com.monovore.decline.*

object Cli {
  val opts = (action, profile, jobIds, plan)

  val action: Opts[String] = Opts.argument[String](metavar = "action")
    .validate("Must be 'start' or 'stop'!")(Set(AppConf.actionStart, AppConf.actionStop))

  val profile: Opts[String] =
    Opts.option[String]("profile", short = "p", metavar = "profile", help = "Profile section in ~/.databrickscfgProfile section in ~/.databrickscfg")

  val jobIds: Opts[Vector[Long]] = Opts
    .option[String]("job-ids", short = "s", help = "Comma-separated job IDs to manage")
    .map(s => s.split(",").map(_.strip.toLong).toVector)

  val plan: Opts[Boolean] = Opts.flag("plan", help = "Enable to just show affected jobs and runs.")
    .orFalse
}
