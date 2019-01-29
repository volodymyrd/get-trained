package online.gettrained.frontend.web.common;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/common/job")
public class JobRunnerController {
    @Autowired
    private ApplicationContext appContext;

    @Autowired
    JobLauncher jobLauncher;

    @RequestMapping("/start")
    public void handle(@RequestParam("name") String name) {
        try {
            Job job = (Job) appContext.getBean(name);
            if (job != null)
                jobLauncher.run(job, new JobParameters());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
