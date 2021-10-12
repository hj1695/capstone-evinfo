package com.evinfo.batch.charger.scheduler;

import com.evinfo.batch.charger.ChargerInitConfiguration;
import com.evinfo.batch.charger.ChargerUpdateConfiguration;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.explore.JobExplorer;
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
    private final JobExplorer jobExplorer;

    @Scheduled(cron = "0 0/2 * * * *")
    public void runUpdateJob() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        jobLauncher.run(
                chargerUpdateConfiguration.chargerUpdateJob(), new JobParametersBuilder(jobExplorer)
                        .getNextJobParameters(chargerUpdateConfiguration.chargerUpdateJob())
                        .toJobParameters()
        );
    }
}
