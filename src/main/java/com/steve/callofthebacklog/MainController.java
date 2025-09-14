package com.steve.callofthebacklog;

import com.steve.callofthebacklog.backlog.Backlog;
import com.steve.callofthebacklog.backlog.BacklogDTO;
import com.steve.callofthebacklog.backlog.BacklogMapper;
import com.steve.callofthebacklog.backlog.BacklogRepository;
import com.steve.callofthebacklog.exceptions.BadBodyException;
import com.steve.callofthebacklog.exceptions.DuplicateEntityException;
import com.steve.callofthebacklog.exceptions.NotFoundException;
import com.steve.callofthebacklog.media.Media;
import com.steve.callofthebacklog.media.MediaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import static java.lang.String.format;
import static org.springframework.util.ObjectUtils.isEmpty;

@Controller
@RequestMapping(path = "/api")
public class MainController {
    @Autowired
    BacklogRepository backlogRepository;
    @Autowired
    MediaRepository mediaRepository;
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

    @PostMapping("/backlogs/{id}/media")
    public ResponseEntity<Media> createMediaForBacklog(@PathVariable Integer id, @RequestBody Media body) {
        if (isEmpty(body.getTitle())) {
            throw new BadBodyException("A valid title must be provided");
        }
        if (mediaRepository.existsByBacklogIdAndTitle(id, body.getTitle())) {
            String error = format("A media type for backlog %d already exists with title: %s", 1, body.getTitle());
            throw new DuplicateEntityException(error);
        }
        Backlog backlog = backlogRepository.findById(id).orElseThrow(() ->
                new NotFoundException(format("Backlog not found with id: %d", id))
        );
        Media savedEntity = mediaRepository.save(
                Media.builder()
                        .title(body.getTitle())
                        .backlog(backlog)
                        .build());
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedEntity.getId())
                .toUri();
        return ResponseEntity.created(location).body(savedEntity);
    }

    @GetMapping(path = "/about")
    public ResponseEntity<String> aboutInfo() {
        return ResponseEntity.ok("API is awake");
    }
}
