package com.steve.callofthebacklog;

import com.steve.callofthebacklog.backlog.BacklogDTO;
import com.steve.callofthebacklog.backlog.BacklogMapper;
import com.steve.callofthebacklog.backlog.BacklogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/api")
public class MainController {
    @Autowired
    BacklogRepository backlogRepository;
    @Autowired
    BacklogMapper backlogMapper;

    @GetMapping(path = "/backlogs")
    public ResponseEntity<Iterable<BacklogDTO>> findAllBacklogs() {
        return ResponseEntity.ok(backlogRepository.findAll()
                .stream()
                .map(backlogMapper::backlogEntityToDTO)
                .toList()
        );
    }

    @GetMapping(path = "/about")
    public ResponseEntity<String> aboutInfo() {
        return ResponseEntity.ok("Api is up");
    }
}
