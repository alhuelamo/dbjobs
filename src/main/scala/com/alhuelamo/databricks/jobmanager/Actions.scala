package com.alhuelamo.databricks.jobmanager

import scala.util.control.NonFatal
import com.alhuelamo.databricks.jobmanager.DatabricksApi.ApiException
import com.alhuelamo.databricks.jobmanager.conf.{AppConf, DatabricksWorkspace}
import scala.util.Try

object Actions {

  def stopActiveRuns(jobId: Long)(using conf: AppConf): Unit = {
    val ws = conf.databricksWs
    println(s"Stopping job $jobId")

    Try {
      val activeRuns = DatabricksApi.getActiveJobRuns(jobId, ws)
      if (activeRuns.isEmpty)
        println("  no active runs found for this job.")
      else
        activeRuns.foreach(runId => cancelRun(runId, ws))
    } recover {
      jobNotFound
    }
  }

  private def cancelRun(runId: Long, ws: DatabricksWorkspace)(using conf: AppConf): Unit = {
    println(s"  on run $runId")

    if (!conf.plan) {
      Try {
        DatabricksApi.cancelJobRun(runId, ws)
      } recover {
        runNotFound
      }
    }
  }

  def startRuns(jobId: Long)(using conf: AppConf): Unit = {
    println(s"Starting job $jobId")
    if (!conf.plan) {
      Try {
        DatabricksApi.triggerJobRun(jobId, conf.databricksWs)
      } recover {
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
