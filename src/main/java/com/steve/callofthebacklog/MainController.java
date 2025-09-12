package com.steve.callofthebacklog;

import com.steve.callofthebacklog.backlog.Backlog;
import com.steve.callofthebacklog.backlog.BacklogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/api")
public class MainController {
    @Autowired
    BacklogRepository backlogRepository;

    @GetMapping(path="/backlog")
    public ResponseEntity<Iterable<Backlog>> findAllBacklogs() {
        return ResponseEntity.ok(backlogRepository.findAll());
    }

    @GetMapping(path="/about")
    public ResponseEntity<String> aboutInfo() {
        return ResponseEntity.ok("Api is up");
    }

    @GetMapping(path="/steven")
    public ResponseEntity<String> aboutInfo() {
        return ResponseEntity.ok("I am the best");
    }
}
