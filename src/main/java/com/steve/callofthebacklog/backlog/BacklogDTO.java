package com.steve.callofthebacklog.backlog;

import com.steve.callofthebacklog.media.Media;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class BacklogDTO {
    private Integer id;
    private String type;
    private List<Media> mediaList;

    public static class BacklogDTOBuilder {
        private ArrayList<Media> mediaList;

        public BacklogDTOBuilder media(int id, String title) {
            if (mediaList == null) {
                mediaList = new ArrayList<>();
            }
            mediaList.add(Media.builder()
                    .id(id)
                    .title(title)
                    .build());
            return this;
        }
    }
}
