package com.steve.callofthebacklog.backlog;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Media {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "backlog_id")
    private Backlog backlog;
}
