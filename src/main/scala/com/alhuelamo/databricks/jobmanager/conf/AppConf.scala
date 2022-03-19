package com.alhuelamo.databricks.jobmanager.conf

import cats.syntax.validated

final case class AppConf(
    action: ActionKey,
    databricksWs: DatabricksWorkspace,
    jobIds: Vector[Long],
    plan: Boolean = false
)

object AppConf {
  def apply(action: ActionKey, profile: String, jobIds: Vector[Long], plan: Boolean): AppConf =
    AppConf(
      action,
      DatabricksWorkspace(profile),
      jobIds,
      plan
    )
}

enum ActionKey(val value: String) {
  case Start extends ActionKey("start")
  case Stop extends ActionKey("stop")
}

object ActionKey {
  import ActionKey.*

  def apply(string: String): ActionKey = string match {
    case Start.`value` => ActionKey.Start
    case Stop.`value`  => ActionKey.Stop
    case invalid       => throw new IllegalArgumentException(s"Invalid '$invalid' argument for <action>.")
  }
}
