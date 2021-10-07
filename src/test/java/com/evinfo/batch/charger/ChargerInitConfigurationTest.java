package com.evinfo.batch.charger;

import com.evinfo.TestConfiguration;
import com.evinfo.api.charger.repository.ChargerRepository;
import com.evinfo.api.charger.service.ChargerClient;
import com.evinfo.global.config.PropertiesConfig;
import com.evinfo.utils.ChargerGenerator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import static org.mockito.Mockito.when;

@ActiveProfiles(profiles = "test")
@SpringBatchTest
@SpringBootTest(classes = {TestConfiguration.class, ChargerInitConfiguration.class, PropertiesConfig.class})
class ChargerInitConfigurationTest {
    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private ChargerRepository chargerRepository;

    @MockBean
    private ChargerClient chargerClient;

    @AfterEach
    public void tearDown() throws Exception {
        chargerRepository.deleteAll();
    }

    @DisplayName("ChargerInitJob 실행시 올바르게 수행된다.")
    @Test
    void chargerInitJobTest() throws Exception {
        //given
        when(chargerClient.fetchChargers()).thenReturn(ChargerGenerator.getClientChargers());
        JobParameters jobParameters = jobLauncherTestUtils.getUniqueJobParameters();

        //when
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);

        // then
        Assertions.assertThat(jobExecution.getStepExecutions().stream()
                .mapToInt(StepExecution::getWriteCount)
                .sum())
                .isEqualTo(chargerRepository.count())
                .isEqualTo(2);
    }
}


