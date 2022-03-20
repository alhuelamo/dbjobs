package com.alhuelamo.databricks.jobmanager.conf

import java.io.File;
import org.ini4j.Ini

final case class DatabricksWorkspace(
    host: String,
    token: String
)

object DatabricksWorkspace {
  def apply(profile: String): DatabricksWorkspace = {
    val ini = loadDefaultDatabricksConf()
    apply(profile, ini)
  }

  def apply(profile: String, ini: Ini): DatabricksWorkspace = {
    val section = Option(ini.get(profile))
      .getOrElse(throw new IllegalStateException(s"Profile '$profile' not found!"))

    DatabricksWorkspace(
      host = section.get("host"),
      token = section.get("token")
    )
  }

  private def loadDefaultDatabricksConf() = {
    val homeDir    = System.getProperty("user.home")
    val dbConfPath = s"$homeDir/.databrickscfg"
    new Ini(new File(dbConfPath))
  }

}
