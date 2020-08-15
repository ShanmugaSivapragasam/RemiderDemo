package com.shan.reminders.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReminderController {


    @GetMapping
    public ResponseEntity<String> greetings(){
        return  new ResponseEntity<>("Default" , HttpStatus.OK);
    }
}
