package com.alhuelamo.databricks.jobmanager

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class DatabricksApiSpec extends AnyWordSpec with Matchers {

  "Databricks API module" should {

    "retrieve a single run_id correctly" in {
      val plainResponse =
        """
          |{
          |    "runs": [
          |        {
          |            "job_id": 19064954,
          |            "run_id": 21072094,
          |            "number_in_job": 21072094,
          |            "original_attempt_run_id": 21072094,
          |            "state": {
          |                "life_cycle_state": "RUNNING",
          |                "state_message": "In run",
          |                "user_cancelled_or_timedout": false
          |            },
          |            "start_time": 1643293536010,
          |            "setup_duration": 91000,
          |            "execution_duration": 0,
          |            "cleanup_duration": 0,
          |            "end_time": 0,
          |            "trigger": "ONE_TIME",
          |            "creator_user_name": "myuser",
          |            "run_name": "dping-stream-ppro-ecommerceproductdetail-prd",
          |            "run_page_url": "https://mydbworkspace.com/?o=82178415198056#job/19064954/run/21072094",
          |            "run_type": "JOB_RUN",
          |            "format": "MULTI_TASK"
          |        }
          |    ],
          |    "has_more": false
          |}
          |""".stripMargin

      val expectedRunIds = List(21072094)

      val actualRunIds = DatabricksApi.parseRunIds(plainResponse)

      actualRunIds should contain theSameElementsAs expectedRunIds
    }

    "retrieve multiple run_ids correctly" in {
      val plainResponse =
        """
          |{
          |    "runs": [
          |        {
          |            "job_id": 19064954,
          |            "run_id": 21072094,
          |            "number_in_job": 21072094,
          |            "original_attempt_run_id": 21072094,
          |            "state": {
          |                "life_cycle_state": "RUNNING",
          |                "state_message": "In run",
          |                "user_cancelled_or_timedout": false
          |            },
          |            "start_time": 1643293536010,
          |            "setup_duration": 91000,
          |            "execution_duration": 0,
          |            "cleanup_duration": 0,
          |            "end_time": 0,
          |            "trigger": "ONE_TIME",
          |            "creator_user_name": "myuser",
          |            "run_name": "myJob1_01",
          |            "run_page_url": "https://mydbworkspace.com.azuredatabricks.net/?o=82178415198056#job/19064954/run/21072094",
          |            "run_type": "JOB_RUN",
          |            "format": "MULTI_TASK"
          |        },
          |        {
          |            "job_id": 19064954,
          |            "run_id": 21072095,
          |            "number_in_job": 21072094,
          |            "original_attempt_run_id": 21072094,
          |            "state": {
          |                "life_cycle_state": "RUNNING",
          |                "state_message": "In run",
          |                "user_cancelled_or_timedout": false
          |            },
          |            "start_time": 1643293536010,
          |            "setup_duration": 91000,
          |            "execution_duration": 0,
          |            "cleanup_duration": 0,
          |            "end_time": 0,
          |            "trigger": "ONE_TIME",
          |            "creator_user_name": "myuser",
          |            "run_name": "myJob1_02",
          |            "run_page_url": "https://mydbworkspace.com.azuredatabricks.net/?o=82178415198056#job/19064954/run/21072094",
          |            "run_type": "JOB_RUN",
          |            "format": "MULTI_TASK"
          |        }
          |    ],
          |    "has_more": false
          |}
          |""".stripMargin

      val expectedRunIds = List(21072094, 21072095)

      val actualRunIds = DatabricksApi.parseRunIds(plainResponse)

      actualRunIds should contain theSameElementsAs expectedRunIds
    }

    "return empty list on no active runs" in {
      val plainResponse =
        """
          |{
          |    "has_more": false
          |}
          |""".stripMargin

      val expectedRunIds = List()

      val actualRunIds = DatabricksApi.parseRunIds(plainResponse)

      actualRunIds should contain theSameElementsAs expectedRunIds
    }
  }

}
