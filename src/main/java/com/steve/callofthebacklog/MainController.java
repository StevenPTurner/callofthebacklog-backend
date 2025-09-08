package com.steve.callofthebacklog;

import com.steve.callofthebacklog.backlog.Backlog;
import com.steve.callofthebacklog.backlog.BacklogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import static java.util.Collections.emptyList;

@Controller
@RequestMapping(path = "/api")
public class MainController {
    @Autowired
    BacklogRepository backlogRepository;

    @GetMapping(path="/backlog")
    public ResponseEntity<Iterable<Backlog>> findAllBacklogs() {
        return ResponseEntity.ok(backlogRepository.findAll());
    }
}
