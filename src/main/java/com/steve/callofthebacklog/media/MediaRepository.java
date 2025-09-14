package com.steve.callofthebacklog.media;

import org.springframework.data.repository.ListCrudRepository;

public interface MediaRepository extends ListCrudRepository<Media, Integer> {
    boolean existsByBacklogIdAndTitle(Integer backlogId, String title);

}
