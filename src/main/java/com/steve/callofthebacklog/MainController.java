package com.steve.callofthebacklog;

import com.steve.callofthebacklog.backlog.Backlog;
import com.steve.callofthebacklog.backlog.BacklogDTO;
import com.steve.callofthebacklog.backlog.BacklogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(path = "/api")
public class MainController {
    @Autowired
    BacklogRepository backlogRepository;

    @GetMapping(path="/backlog")
    public ResponseEntity<Iterable<BacklogDTO>> findAllBacklogs() {
        List<BacklogDTO> backlogDTOList = backlogRepository.findAll()
                .stream()
                .map(backlog -> {
                    return BacklogDTO.builder()
                            .id(backlog.getId())
                            .type(backlog.getType().getName())
                            .mediaList(backlog.getMediaList())
                            .build();
                })
                .toList();
        return ResponseEntity.ok(backlogDTOList);
    }

    @GetMapping(path="/about")
    public ResponseEntity<String> aboutInfo() {
        return ResponseEntity.ok("Api is up");
    }
}
