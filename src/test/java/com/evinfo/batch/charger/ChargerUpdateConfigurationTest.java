package com.evinfo.batch;

import com.evinfo.TestConfiguration;
import com.evinfo.api.charger.repository.ChargerRepository;
import com.evinfo.api.charger.service.ChargerClient;
import com.evinfo.batch.charger.ChargerUpdateConfiguration;
import com.evinfo.utils.ChargerGenerator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ActiveProfiles(profiles = "test")
@SpringBatchTest
@SpringBootTest(classes = {TestConfiguration.class, ChargerUpdateConfiguration.class})
class ChargerUpdateConfigurationTest {
    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @MockBean
    private ChargerRepository chargerRepository;

    @MockBean
    private ChargerClient chargerClient;

    @AfterEach
    public void tearDown() throws Exception {
        chargerRepository.deleteAll();
    }

    // TODO: 2021/09/24 테스트가 수행 여부만 확인하고 종합적으로 바뀌는지 확인 못한다. 테스트추후 수정
    @DisplayName("ChargerInitJob 실행시 올바르게 수행된다.")
    @Test
    void chargerInitJobTest() throws Exception {
        //given
        when(chargerClient.fetchChargers()).thenReturn(ChargerGenerator.getClientChargers());
        when(chargerRepository.findById(any())).thenReturn(Optional.of(ChargerGenerator.getCharger()));
        JobParameters jobParameters = jobLauncherTestUtils.getUniqueJobParameters();

        //when
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);

        // then
        Assertions.assertThat(jobExecution.getExitStatus().getExitCode()).isEqualTo("COMPLETED");
    }
}


