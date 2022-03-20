package com.alhuelamo.databricks.jobmanager

import scala.util.control.NonFatal
import com.alhuelamo.databricks.jobmanager.DatabricksApi.ApiException
import com.alhuelamo.databricks.jobmanager.conf.{AppConf, DatabricksWorkspace}
import scala.util.Try

object Actions {

  def stopActiveRuns(jobId: Long)(using conf: AppConf): Unit = {
    val ws = conf.databricksWs
    println(s"Stopping job $jobId")

    try {
      val activeRuns = DatabricksApi.getActiveJobRuns(jobId, ws)
      if (activeRuns.isEmpty)
        println("  no active runs found for this job.")
      else
        activeRuns.foreach(runId => cancelRun(runId, ws))
    } catch {
      handleApiErrors("job")
    }
  }

  private def cancelRun(runId: Long, ws: DatabricksWorkspace)(using conf: AppConf): Unit = {
    println(s"  on run $runId")

    if (!conf.plan) {
      try {
        DatabricksApi.cancelJobRun(runId, ws)
      } catch {
        handleApiErrors("run")
      }
    }
  }

  def startRuns(jobId: Long)(using conf: AppConf): Unit = {
    println(s"Starting job $jobId")
    if (!conf.plan) {
      try {
        DatabricksApi.triggerJobRun(jobId, conf.databricksWs)
      } catch {
        handleApiErrors("job")
      }
    }
  }

  private def handleApiErrors(resource: String): PartialFunction[Throwable, Unit] = {
    case ApiException(_, 400) => println(s"  $resource not found!")
    case ex @ ApiException(_, 403) =>
      println("  authentication error!")
      throw ex
    case NonFatal(error) =>
      println(s"  problem querying the $resource!")
      throw error
  }

}
