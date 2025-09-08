package com.steve.callofthebacklog.backlog;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Backlog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String type;

    @JsonManagedReference
    @OneToMany(mappedBy = "backlog", cascade = CascadeType.ALL)
    private List<Media> mediaList;
}
