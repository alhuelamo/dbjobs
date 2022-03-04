package com.alhuelamo.databricks.jobmanager.conf

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.ini4j.Wini

class DatabricksWorkspaceSpec extends AnyWordSpec with Matchers {

  private val validProfile = "me"
  private val validHost    = "myhost"
  private val validToken   = "mytoken"

  private val validIniObject = {
    val ini = new Wini()
    ini.put(validProfile, "host", validHost)
    ini.put(validProfile, "token", validToken)
    ini
  }

  "DatabricksWorkspace" should {

    "be loaded from a valid .databrickscfg file" in {
      val actual   = DatabricksWorkspace(validProfile, validIniObject)
      val expected = DatabricksWorkspace(validHost, validToken)
      actual shouldBe expected
    }

  }

}
