package com.iancollington.blog.simplequartzwebapp;

import org.quartz.InterruptableJob;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.UnableToInterruptJobException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class SimpleJob extends QuartzJobBean implements InterruptableJob {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleJob.class);

    /**
     * Flag to indicate whether this job has been interrupted. When this flag becomes true the job should stop what it
     * is doing and finish.
     */
    private boolean hasBeenInterrupted = false;

    @Override
    protected void executeInternal(final JobExecutionContext context) throws JobExecutionException {
        while (!hasBeenInterrupted) {
            LOGGER.info("Long running job executing....");

            doSomeTask();
        }

        LOGGER.info("Long running job has been interrupted. Stopping.");
    }

    private void doSomeTask() {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            LOGGER.error("The thread sleep was interrupted.", e);
        }
    }

    public void interrupt() throws UnableToInterruptJobException {
        hasBeenInterrupted = true;
    }
}
