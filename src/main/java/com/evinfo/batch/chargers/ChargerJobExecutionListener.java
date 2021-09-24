package com.evinfo.batch.chargers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

@Slf4j(topic = "BATCH_FILE_LOGGER")
public class ChargerJobExecutionListener implements JobExecutionListener {
    @Override
    public void beforeJob(JobExecution jobExecution) {
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        long time = jobExecution.getEndTime().getTime() - jobExecution.getStartTime().getTime();

        log.info("{} 종료. 처리 시간 {} mills ", jobExecution.getJobInstance().getJobName(), time);
    }
}
