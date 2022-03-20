# dbjobs

> A Databricks Job Manager

[Databricks CLI](https://docs.databricks.com/dev-tools/cli/jobs-cli.html) is a very useful tool to deal with a Databricks workspace. From there we can start existing jobs [easily](https://docs.databricks.com/dev-tools/cli/jobs-cli.html#run-a-job). However, if we want to stop active executions for a particular job, we need query for their runs —their IDs— first, and then cancel them. This is the main motivation for this tiny CLI app.

It has basically two commands:
- Start an existing Databricks job given its job ID.
- Stop active runs for a particular job given its job ID.

## Installation

If you use [Coursier](https://get-coursier.io) you can install the application in your current working directory by running

```bash
coursier bootstrap com.alhuelamo:dbjobs_3:0.1.0 -o dbjobs
```

## Building from source

You will need [sbt](https://www.scala-sbt.org) as the main pre-requisite.

Go to the repository folder and run

```bash
sbt stage
```

to generate a launcher file for the app. Then you can go to the folder

```bash
cd ./target/universal/stage
```

and run

```bash
./bin/dbjobs --help
```

## Usage

The application requires the presence of a [Databricks CLI config file](https://docs.databricks.com/dev-tools/cli/index.html) in your home folder (`~/.databrickscfg`).

You need to define a profile for the Databricks workspace you want to point at.

```bash
vim ~/.databrickscfg
```

```ini
[myprofile]
host=https://mydatabricks.workspace.url.com
token=myapiaccesstoken
```

Once configured, you can use the app to start and stop existing jobs in that workspace.

Start jobs

```bash
dbjobs start \
  --profile myprofile \
  --job-ids 42,314,9
```

Stop active jobs:

```bash
dbjobs stop \
  --profile myprofile \
  --job-ids 42,314,9
```

### Planning

You can use the flag `--plan` to just show which are going to be the affected jobs and [runs](https://docs.databricks.com/dev-tools/api/latest/jobs.html#operation/JobsRunsList) without actually starting or stopping them.
