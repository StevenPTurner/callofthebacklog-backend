package com.steve.callofthebacklog.backlog;

import org.springframework.data.repository.ListCrudRepository;

public interface BacklogRepository extends ListCrudRepository<Backlog, Integer> {
}
