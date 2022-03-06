package com.alhuelamo.databricks.jobmanager.conf

import caseapp._

sealed trait DjmCommand

final case class CommonOptions(
  @Name("p")
  @HelpMessage("Profile section in ~/.databrickscfg")
  profile: String,
  
  @Name("j")
  @HelpMessage("Comma-separated job IDs to manage")
  jobIds: String,
  
  @HelpMessage("Enable to just plan")
  plan: Boolean = false,
)

final case class Start(
    @Recurse common: CommonOptions,
) extends DjmCommand

final case class Stop(
    @Recurse common: CommonOptions,
) extends DjmCommand
