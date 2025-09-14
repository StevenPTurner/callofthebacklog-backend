package com.steve.callofthebacklog.backlog;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.steve.callofthebacklog.media.Media;
import com.steve.callofthebacklog.media.MediaType;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Backlog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "media_type_id")
    private MediaType type;

    @JsonManagedReference
    @OneToMany(mappedBy = "backlog", cascade = CascadeType.ALL)
    private List<Media> mediaList;
}
