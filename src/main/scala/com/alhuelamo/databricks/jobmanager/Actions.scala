package com.alhuelamo.databricks.jobmanager

import scala.util.control.NonFatal
import com.alhuelamo.databricks.jobmanager.DatabricksApi.ApiException
import com.alhuelamo.databricks.jobmanager.conf.{AppConf, DatabricksWorkspace}

object Actions {

  def stopActiveRuns(jobId: Long)(implicit conf: AppConf): Unit = {
    val ws = conf.databricksWs
    println(s"Stopping job $jobId")
    try {
      val activeRuns = DatabricksApi.getActiveJobRuns(jobId, ws)
      if (activeRuns.isEmpty)
        println("  no active runs found.")
      else
        activeRuns.foreach(runId => cancelRun(runId, ws))
    } catch {
      jobNotFound
    }
  }

  private def cancelRun(runId: Long, ws: DatabricksWorkspace)(implicit conf: AppConf): Unit = {
    println(s"  on run $runId")

    if (conf.plan) {
      try {
        DatabricksApi.cancelJobRun(runId, ws)
      } catch {
        runNotFound
      }
    }
  }

  def startRuns(jobId: Long)(implicit conf: AppConf): Unit = {
    println(s"Starting job $jobId")
    if (conf.plan) {
      try {
        DatabricksApi.triggerJobRun(jobId, conf.databricksWs)
      } catch {
        jobNotFound
      }
    }
  }

  private val jobNotFound: PartialFunction[Throwable, Unit] = {
    case ApiException(_, 400) => println("  job not found!")
    case NonFatal(error) =>
      println("  problem querying the job!")
      throw error
  }

  private val runNotFound: PartialFunction[Throwable, Unit] = {
    case ApiException(_, 400) => println(s"    run id not found!")
    case NonFatal(error) =>
      println("  problem cancelling the run!")
      throw error
  }

}
