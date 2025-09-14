package com.steve.callofthebacklog.media;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.steve.callofthebacklog.backlog.Backlog;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
