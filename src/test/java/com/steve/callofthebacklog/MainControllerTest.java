package com.steve.callofthebacklog;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.steve.callofthebacklog.backlog.Backlog;
import com.steve.callofthebacklog.backlog.BacklogDTO;
import com.steve.callofthebacklog.backlog.BacklogMapperImpl;
import com.steve.callofthebacklog.backlog.BacklogRepository;
import com.steve.callofthebacklog.media.MediaType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static java.util.Collections.emptyList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MainController.class)
@Import(BacklogMapperImpl.class)
public class MainControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    BacklogRepository backlogRepository;

    @Test
    public void findAllBacklogs_noBacklogs_returnsNoEntities() throws Exception {
        when(backlogRepository.findAll()).thenReturn(emptyList());

        MvcResult result = mockMvc.perform(get("/api/backlogs"))
                .andExpect(status().isOk())
                .andReturn();

        List<BacklogDTO> actualDTOs = getEntitiesFromResult(result, BacklogDTO.class);
        assertThat(actualDTOs, empty());
    }

    @Test
    public void findAllBacklogs_oneBacklog_noType_returnsEntityWithoutType() throws Exception {
        when(backlogRepository.findAll()).thenReturn(List.of(
                Backlog.builder()
                        .id(1)
                        .media(20, "Movie 1")
                        .build()
        ));

        MvcResult result = mockMvc.perform(get("/api/backlogs"))
                .andExpect(status().isOk())
                .andReturn();
        List<BacklogDTO> actualDTOs = getEntitiesFromResult(result, BacklogDTO.class);

        List<BacklogDTO> expectedBacklogs = List.of(
                BacklogDTO.builder()
                        .id(1)
                        .media(20, "Movie 1")
                        .build()
        );
        assertThat(actualDTOs, contains(expectedBacklogs.toArray()));
    }

    @Test
    public void findAllBacklogs_oneBacklog_noMedia_returnsEntityWithNoMedia() throws Exception {
        when(backlogRepository.findAll()).thenReturn(List.of(
                Backlog.builder()
                        .id(1)
                        .type(MediaType.builder().id(10).name("Movie").build())
                        .build()
        ));

        MvcResult result = mockMvc.perform(get("/api/backlogs"))
                .andExpect(status().isOk())
                .andReturn();
        List<BacklogDTO> actualDTOs = getEntitiesFromResult(result, BacklogDTO.class);

        List<BacklogDTO> expectedBacklogs = List.of(
                BacklogDTO.builder()
                        .id(1)
                        .type("Movie")
                        .build()
        );
        assertThat(actualDTOs, contains(expectedBacklogs.toArray()));
    }

    @Test
    public void findAllBacklogs_oneBacklog_oneMedia_returnsEntityWithOneMedia() throws Exception {
        when(backlogRepository.findAll()).thenReturn(List.of(
                Backlog.builder()
                        .id(1)
                        .type(MediaType.builder().id(10).name("Movie").build())
                        .media(20, "Movie 1")
                        .build()
        ));

        MvcResult result = mockMvc.perform(get("/api/backlogs"))
                .andExpect(status().isOk())
                .andReturn();
        List<BacklogDTO> actualDTOs = getEntitiesFromResult(result, BacklogDTO.class);

        List<BacklogDTO> expectedBacklogs = List.of(
                BacklogDTO.builder()
                        .id(1)
                        .type("Movie")
                        .media(20, "Movie 1")
                        .build()
        );
        assertThat(actualDTOs, contains(expectedBacklogs.toArray()));
    }

    @Test
    public void findAllBacklogs_oneBacklog_multipleMedia_returnsEntityWithMultipleMedia() throws Exception {
        when(backlogRepository.findAll()).thenReturn(List.of(
                Backlog.builder()
                        .id(1)
                        .type(MediaType.builder().id(10).name("Movie").build())
                        .media(20, "Movie 1")
                        .media(21, "Movie 2")
                        .media(22, "Movie 3")
                        .build()
        ));

        MvcResult result = mockMvc.perform(get("/api/backlogs"))
                .andExpect(status().isOk())
                .andReturn();
        List<BacklogDTO> actualDTOs = getEntitiesFromResult(result, BacklogDTO.class);

        List<BacklogDTO> expectedBacklogs = List.of(
                BacklogDTO.builder()
                        .id(1)
                        .type("Movie")
                        .media(20, "Movie 1")
                        .media(21, "Movie 2")
                        .media(22, "Movie 3")
                        .build()
        );
        assertThat(actualDTOs, contains(expectedBacklogs.toArray()));
    }

    @Test
    public void findAllBacklogs_multipleBacklog_differentTypesAndMedia_returnsExpectedEntities() throws Exception {
        when(backlogRepository.findAll()).thenReturn(List.of(
                Backlog.builder()
                        .id(1)
                        .type(MediaType.builder().id(10).name("Movie").build())
                        .media(20, "Movie 1")
                        .media(21, "Movie 2")
                        .media(22, "Movie 3")
                        .build(),
                Backlog.builder()
                        .id(2)
                        .type(MediaType.builder().id(11).name("Television").build())
                        .build(),
                Backlog.builder()
                        .id(3)
                        .type(MediaType.builder().id(12).name("Anime").build())
                        .media(23, "Anime 1")
                        .build(),
                Backlog.builder()
                        .id(4)
                        .media(24, "Media 1")
                        .media(25, "Media 2")
                        .media(26, "Media 3")
                        .media(27, "Media 4")
                        .media(28, "Media 6")
                        .build(),
                Backlog.builder()
                        .id(5)
                        .build()
        ));

        MvcResult result = mockMvc.perform(get("/api/backlogs"))
                .andExpect(status().isOk())
                .andReturn();
        List<BacklogDTO> actualDTOs = getEntitiesFromResult(result, BacklogDTO.class);

        List<BacklogDTO> expectedBacklogs = List.of(
                BacklogDTO.builder()
                        .id(1)
                        .type("Movie")
                        .media(20, "Movie 1")
                        .media(21, "Movie 2")
                        .media(22, "Movie 3")
                        .build(),
                BacklogDTO.builder()
                        .id(2)
                        .type("Television")
                        .build(),
                BacklogDTO.builder()
                        .id(3)
                        .type("Anime")
                        .media(23, "Anime 1")
                        .build(),
                BacklogDTO.builder()
                        .id(4)
                        .media(24, "Media 1")
                        .media(25, "Media 2")
                        .media(26, "Media 3")
                        .media(27, "Media 4")
                        .media(28, "Media 6")
                        .build(),
                BacklogDTO.builder()
                        .id(5)
                        .build()
        );
        assertThat(actualDTOs, contains(expectedBacklogs.toArray()));
    }

    @Test
    public void aboutInfo_returnsInfo() throws Exception{
        String response =  mockMvc.perform(get("/api/about"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(response, containsString("API is awake"));
    }

    private <T> List<T> getEntitiesFromResult(MvcResult result, Class<T> clazz) throws Exception {
        String json = result.getResponse().getContentAsString();
        JavaType type = objectMapper.getTypeFactory().constructCollectionType(List.class, clazz);
        return objectMapper.readValue(json, type);
    }
}
