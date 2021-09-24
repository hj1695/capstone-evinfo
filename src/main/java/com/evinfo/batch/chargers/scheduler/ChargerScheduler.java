package com.evinfo.batch.chargers.scheduler;

import com.evinfo.batch.chargers.ChargerInitConfiguration;
import com.evinfo.batch.chargers.ChargerUpdateConfiguration;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChargerScheduler {
    private final ChargerInitConfiguration chargerInitConfiguration;
    private final ChargerUpdateConfiguration chargerUpdateConfiguration;
    private final JobLauncher jobLauncher;

    @Scheduled(cron = "0 0/10 * * * *")
    public void runUpdateJob() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        jobLauncher.run(chargerUpdateConfiguration.chargerUpdateJob(), new JobParameters());
    }

    @Scheduled(cron = "0 0 3 * * *")
    public void runInitJob() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        jobLauncher.run(chargerInitConfiguration.chargerInitJob(), new JobParameters());
    }
}
