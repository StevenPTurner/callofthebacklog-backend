package com.steve.callofthebacklog.backlog;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.steve.callofthebacklog.media.Media;
import com.steve.callofthebacklog.media.MediaType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    public static class BacklogBuilder {
        private List<Media> mediaList;

        public BacklogBuilder media(int id, String title) {
            if (mediaList == null) {
                mediaList = new ArrayList<>();
            }
            mediaList.add(Media.builder()
                    .id(id)
                    .title(title)
                    .build()
            );
            return this;
        }
    }
}
