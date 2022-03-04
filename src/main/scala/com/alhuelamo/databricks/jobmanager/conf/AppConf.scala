package com.alhuelamo.databricks.jobmanager.conf

final case class AppConf(
    action: String,
    databricksWs: DatabricksWorkspace,
    jobIds: List[Long],
    plan: Boolean = false
)

object AppConf {
  def apply(command: Command): AppConf =
    AppConf(
      command.name,
      DatabricksWorkspace(command.profile),
      parseJobIds(command.jobIds),
      command.plan
    )

  private def parseJobIds(jobIdsStr: String) =
    jobIdsStr
      .split(",")
      .map(_.strip().toLong)
      .toList
}

sealed trait Command {
  def profile: String
  def jobIds: String
  def plan: Boolean

  def name: String
}

final case class StartCommand(
    profile: String,
    jobIds: String,
    plan: Boolean
) extends Command {
  override def name: String = "start"
}

final case class StopCommand(
    profile: String,
    jobIds: String,
    plan: Boolean
) extends Command {
  override def name: String = "stop"
}
