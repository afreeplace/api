package seyfa.afreeplace.utils.lifecycle.jobs.bootstrap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import seyfa.afreeplace.Application;
import seyfa.afreeplace.utils.lifecycle.jobs.ApplicationLifecycleJob;

public class BoostrapLoggerJob implements ApplicationLifecycleJob {

    Logger logger = LoggerFactory.getLogger(BoostrapLoggerJob.class);

    @Override
    public void doAction() {
        logger.info("Application Started");
    }
}
