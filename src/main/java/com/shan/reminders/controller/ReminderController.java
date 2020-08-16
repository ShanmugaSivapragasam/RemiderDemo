package com.shan.reminders.controller;


import com.shan.reminders.service.CloudTaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
public class ReminderController {

    @Value("${reminder-url}")
    private String reminderUrl;
    @Value("${project_id}")
    private String projectId;
    @Value("${queue-name}")
    private String queue;
    @Value("${location}")
    private String location;

    @Autowired
    CloudTaskService cloudTaskService;

    @GetMapping
    public ResponseEntity<String> greetings(){
        return  new ResponseEntity<>("Default" , HttpStatus.OK);
    }

    @PostMapping("/v1/reminders")
    public  ResponseEntity<Map> createReminder(@RequestBody String reminderPayload){
        log.info("create a reminder with payload  " + reminderPayload);
        Map<String, String> response = new HashMap();
        response.put("reminder", reminderPayload);
        String initializedTask = cloudTaskService.createTask(reminderPayload,reminderUrl, projectId, queue, location );
        response.put("hasIntialized", initializedTask);
        return  new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("v1/actions")
    public  ResponseEntity<Map> actiions(@RequestBody String actionPayload ){
        log.info("create a reminder with payload  " + actionPayload);
        Map<String, String> response = new HashMap<>();
        response.put("action", actionPayload);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);

    }

}
