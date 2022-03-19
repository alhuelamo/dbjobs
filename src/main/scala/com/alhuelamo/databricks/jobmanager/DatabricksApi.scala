package com.alhuelamo.databricks.jobmanager

import com.alhuelamo.databricks.jobmanager.conf.DatabricksWorkspace

object DatabricksApi {

  def getActiveJobRuns(jobId: Long, ws: DatabricksWorkspace): List[Long] = {
    val response = requests.get(
      url = dbUrl("jobs/runs/list", ws),
      headers = defaultHeaders(ws),
      params = Map(
        "active_only" -> "true",
        "job_id"      -> jobId.toString
      ),
      check = false
    )

    val code = response.statusCode
    if (code != 200)
      throw ApiException("Cancellation attempt failed.", code)

    parseRunIds(response.text())
  }

  private[jobmanager] def parseRunIds(textResponse: String): List[Long] = {
    val runJson = ujson.read(textResponse)

    (for {
      rs     <- runJson.objOpt
      runs   <- rs.get("runs")
      runArr <- runs.arrOpt
    } yield {
      runArr
        .map(_.obj("run_id").num.toLong)
        .toList
    }).getOrElse(List.empty)
  }

  def cancelJobRun(runId: Long, ws: DatabricksWorkspace): Unit = {
    val response = requests.post(
      url = dbUrl("jobs/runs/cancel", ws),
      headers = defaultHeaders(ws),
      data = ujson.Obj("run_id" -> runId.toString),
      check = false
    )

    val code = response.statusCode
    if (code != 200)
      throw ApiException("Cancellation attempt failed.", code)
  }

  def triggerJobRun(jobId: Long, ws: DatabricksWorkspace): Unit = {
    val response = requests.post(
      url = dbUrl("jobs/run-now", ws),
      headers = defaultHeaders(ws),
      data = ujson.Obj("job_id" -> jobId.toString),
      check = false
    )

    val code = response.statusCode
    if (code != 200)
      throw ApiException("Running attempt failed.", code)
  }

  final case class ApiException(message: String, errorCode: Int) extends Exception(message)

  private def defaultHeaders(ws: DatabricksWorkspace): Map[String, String] = Map(
    "Authorization" -> s"Bearer ${ws.token}",
    "Content-Type"  -> "application/json"
  )

  private[jobmanager] def dbUrl(endpoint: String, ws: DatabricksWorkspace) =
    s"https://${ws.host}/api/2.1/$endpoint"

}
