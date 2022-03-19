package seyfa.afreeplace.utils.lifecycle;

import seyfa.afreeplace.utils.lifecycle.jobs.ApplicationLifecycleJob;
import seyfa.afreeplace.utils.lifecycle.jobs.bootstrap.BoostrapLoggerJob;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Called at the construction of the application
 */
@Service
public class BootstrapActions extends ApplicationLifecycleActions {

    public BootstrapActions() {
        super();
        List<ApplicationLifecycleJob> jobs = new ArrayList<>();

        jobs.add(new BoostrapLoggerJob());

        this.jobs = jobs;
    }

}
