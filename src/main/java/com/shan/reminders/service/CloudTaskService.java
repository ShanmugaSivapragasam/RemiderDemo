package com.shan.reminders.service;


import com.google.cloud.tasks.v2.*;
import com.google.protobuf.ByteString;
import com.google.protobuf.Timestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.time.Instant;
import java.util.Locale;

import static java.lang.System.currentTimeMillis;

@Service
public class CloudTaskService {

    @Autowired
    CloudTasksClient cloudTasksClient;



    public  String createTask(String payload, String url, String projectId, String queueId, String locationId){

        // Construct the fully qualified queue name.
        String queuePath = QueueName.of(projectId, locationId, queueId).toString();

        Instant time = Instant.now();
        Timestamp timestamp = Timestamp.newBuilder().setSeconds(time.getEpochSecond()+60L)
                .setNanos(time.getNano()).build();

        // Construct the task body.
        Task.Builder taskBuilder =
                Task.newBuilder()
                        .setScheduleTime(timestamp)
                        .setHttpRequest(
                                HttpRequest.newBuilder()
                                        .setBody(ByteString.copyFrom(payload, Charset.defaultCharset()))
                                        .setUrl(url)
                                        .setHttpMethod(HttpMethod.POST)
                                        .build());

        // Send create task request.
        Task task = cloudTasksClient.createTask(queuePath, taskBuilder.build());
        return task.getName() + " - "+ task.isInitialized();

    }
}
