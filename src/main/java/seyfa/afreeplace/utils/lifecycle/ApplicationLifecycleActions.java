package seyfa.afreeplace.utils.lifecycle;

import seyfa.afreeplace.utils.lifecycle.jobs.ApplicationLifecycleJob;

import java.util.List;

/**
 * Classes that serves to be called to perform
 * some bootstrap or end actions on the Application Lifecycle
 */
public abstract class ApplicationLifecycleActions {

    protected List<ApplicationLifecycleJob> jobs;

    public void executeActions() {
        jobs.forEach(job -> job.doAction());
    }

}
