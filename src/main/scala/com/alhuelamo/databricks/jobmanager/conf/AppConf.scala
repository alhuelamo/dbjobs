package com.alhuelamo.databricks.jobmanager.conf

final case class AppConf(
    action: String,
    databricksWs: DatabricksWorkspace,
    jobIds: Vector[Long],
    plan: Boolean = false
)

object AppConf {
  val actionStart = "start"
  val actionStop  = "stop"

  def apply(action: String, profile: String, jobIds: Vector[Long], plan: Boolean): AppConf =
    AppConf(
      action,
      DatabricksWorkspace(profile),
      jobIds,
      plan
    )
}
