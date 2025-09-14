package com.steve.callofthebacklog.backlog;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BacklogMapper {

    @Mapping(target = "type", source = "type.name")
    BacklogDTO backlogEntityToDTO(Backlog backlog);
}
