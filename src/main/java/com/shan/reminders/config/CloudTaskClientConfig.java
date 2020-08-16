package com.shan.reminders.config;

import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.tasks.v2.CloudTasksClient;
import com.google.cloud.tasks.v2.CloudTasksSettings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;

@Slf4j
@Configuration
public class CloudTaskClientConfig {

    @Value("${cloud-task.credentials}")
    private String pathToCredentials;

    @Bean
    public CloudTasksClient createCloudTasksClient() throws IOException {

        GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(pathToCredentials));

        CloudTasksSettings cloudTasksSettings = CloudTasksSettings.newBuilder().setCredentialsProvider(
                FixedCredentialsProvider.create(credentials))
                .build();
        CloudTasksClient cloudTasksClient = CloudTasksClient.create(cloudTasksSettings);

        return cloudTasksClient;
    }
}
