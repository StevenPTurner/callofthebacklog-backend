package com.steve.callofthebacklog.backlog;

import com.steve.callofthebacklog.media.Media;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class BacklogDTO {
    private Integer id;
    private String type;
    private List<Media> mediaList;
}
