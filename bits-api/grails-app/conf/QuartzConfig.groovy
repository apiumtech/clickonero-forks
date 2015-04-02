def jobsAutoStartupValue =  System.properties['bits.jobs.autoStartup']
def jobsAutoStartup = jobsAutoStartupValue == 'true'
if (jobsAutoStartup) {
  def bannerText = 'jobs enabled'
  def bannerLength = 40
  def banner = new StringBuilder()
  bannerLength.times { banner.append('#') }
  bannerText = bannerText.toUpperCase().center(bannerLength - 2)
  banner.append("\n#${bannerText}#\n")
  bannerLength.times { banner.append('#') }
  print banner.toString()
}

quartz {
  autoStartup = true
  jdbcStore = false
  waitForJobsToCompleteOnShutdown = true
  exposeSchedulerInRepository = false
  interruptJobsOnShutdown = false

  props {
    scheduler.skipUpdateCheck = true
  }
}

environments {
  test {
    quartz {
      autoStartup = false
    }
  }
  ci {
    quartz {
      autoStartup = false
    }
  }
  qa {
    quartz {
      autoStartup = jobsAutoStartup
    }
  }
  staging {
    quartz {
      autoStartup = jobsAutoStartup
    }
  }
  production {
    quartz {
      autoStartup = jobsAutoStartup
    }
  }
}
