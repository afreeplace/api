package seyfa.afreeplace.utils.lifecycle.jobs.end;

import seyfa.afreeplace.Application;
import seyfa.afreeplace.utils.lifecycle.jobs.ApplicationLifecycleJob;

public class EndLoggerJob implements ApplicationLifecycleJob {

    @Override
    public void doAction() {
        System.err.println("Application ended");
    }
}
