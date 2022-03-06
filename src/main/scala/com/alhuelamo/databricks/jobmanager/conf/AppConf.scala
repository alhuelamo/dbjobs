package com.alhuelamo.databricks.jobmanager.conf

import caseapp._

final case class AppConf(
    action: String,
    databricksWs: DatabricksWorkspace,
    jobIds: List[Long],
    plan: Boolean = false
)

object AppConf {
  val actionStart = "start"
  val actionStop  = "stop"

  def apply(command: DjmCommand): AppConf = command match {
    case Start(CommonOptions(profile, jobIds, plan)) =>
      AppConf(
        actionStart,
        DatabricksWorkspace(profile),
        parseJobIds(jobIds),
        plan
      )
    case Stop(CommonOptions(profile, jobIds, plan)) =>
      AppConf(
        actionStop,
        DatabricksWorkspace(profile),
        parseJobIds(jobIds),
        plan
      )
  }

  private[conf] def parseJobIds(jobIdsStr: String) =
    jobIdsStr
      .split(",")
      .map(_.strip().toLong)
      .toList
}
