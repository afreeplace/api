package seyfa.afreeplace.utils.lifecycle;

import seyfa.afreeplace.utils.lifecycle.jobs.ApplicationLifecycleJob;
import seyfa.afreeplace.utils.lifecycle.jobs.end.EndLoggerJob;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EndActions extends ApplicationLifecycleActions {

    public EndActions() {
        super();
        List<ApplicationLifecycleJob> jobs = new ArrayList<>();

        jobs.add(new EndLoggerJob());

        this.jobs = jobs;
    }


}
