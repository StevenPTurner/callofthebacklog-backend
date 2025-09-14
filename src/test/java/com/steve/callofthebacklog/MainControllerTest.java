package com.steve.callofthebacklog;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.steve.callofthebacklog.backlog.Backlog;
import com.steve.callofthebacklog.backlog.BacklogDTO;
import com.steve.callofthebacklog.backlog.BacklogMapperImpl;
import com.steve.callofthebacklog.backlog.BacklogRepository;
import com.steve.callofthebacklog.media.Media;
import com.steve.callofthebacklog.media.MediaRepository;
import com.steve.callofthebacklog.media.MediaType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;

import java.util.List;
import java.util.Optional;

import static java.lang.String.format;
import static java.util.Collections.emptyList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    @MockitoBean
    MediaRepository mediaRepository;

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
    public void aboutInfo_returnsInfo() throws Exception {
        String response = mockMvc.perform(get("/api/about"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(response, containsString("API is awake"));
    }

    @Test
    public void createMediaForBacklog_invalidBacklogId_returnsBadRequest() throws Exception {
        Media responseBody = Media.builder()
                .title("Movie 1")
                .build();
        RequestBuilder request = post("/api/backlogs/badId/media")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(responseBody));

        mockMvc.perform(request).andExpect(status().isBadRequest());
    }

    @Test
    public void createMediaForBacklog_noBody_returnsBadRequest() throws Exception {
        RequestBuilder request = post("/api/backlogs/badId/media")
                .contentType(APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isBadRequest());
    }

    @Test
    public void createMediaForBacklog_backlogDoesNotExist_returnsNotFoundException() throws Exception {
        when(backlogRepository.findById(1)).thenReturn(Optional.empty());
        Media responseBody = Media.builder()
                .title("Movie 1")
                .build();
        RequestBuilder request = post("/api/backlogs/1/media")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(responseBody));

        String errorMessage = mockMvc.perform(request)
                .andExpect(status().isNotFound())
                .andReturn()
                .getResponse()
                .getErrorMessage();

        assertThat(errorMessage, is(format("Backlog not found with id: %d", 1)));
    }

    @Test
    public void createMediaForBacklog_notTitleProvided_returnsBadBodyException() throws Exception {
        Media responseBody = Media.builder().build();
        RequestBuilder request = post("/api/backlogs/1/media")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(responseBody));

        String errorMessage = mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getErrorMessage();

        assertThat(errorMessage, is("A valid title must be provided"));
    }

    @Test
    public void createMediaForBacklog_emptyTitleProvided_returnsBadBodyException() throws Exception {
        Media responseBody = Media.builder()
                .title("")
                .build();
        RequestBuilder request = post("/api/backlogs/1/media")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(responseBody));

        String errorMessage = mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getErrorMessage();

        assertThat(errorMessage, is("A valid title must be provided"));
    }

    @Test
    public void createMediaForBacklog_duplicateMediaTitleForBacklog_returnsBadBodyException() throws Exception {
        when(mediaRepository.existsByBacklogIdAndTitle(1, "Duplicate Movie")).thenReturn(true);
        Media responseBody = Media.builder()
                .title("Duplicate Movie")
                .build();
        RequestBuilder request = post("/api/backlogs/1/media")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(responseBody));

        String errorMessage = mockMvc.perform(request)
                .andExpect(status().isConflict())
                .andReturn()
                .getResponse()
                .getErrorMessage();

        assertThat(errorMessage, is(format("A media type for backlog %d already exists with title: %s", 1, "Duplicate Movie")));
    }

    @Test
    public void createMediaForBacklog_successfulSave_returnsSavedEntity() throws Exception {
        Backlog backlog = Backlog.builder()
                .id(10)
                .build();
        when(mediaRepository.existsByBacklogIdAndTitle(1, "Movie 1")).thenReturn(false);
        when(backlogRepository.findById(1)).thenReturn(Optional.of(backlog));
        when(mediaRepository.save(
                Media.builder()
                        .title("Movie 1")
                        .backlog(backlog)
                        .build()
        )).thenReturn(
                Media.builder()
                        .id(20)
                        .title("Movie 1")
                        .build()
        );
        RequestBuilder request = post("/api/backlogs/1/media")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(
                        Media.builder()
                                .title("Movie 1")
                                .build()
                ));

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andReturn();
        Media actualMedia = getEntityFromResult(result, Media.class);

        assertThat(actualMedia, is(
                Media.builder()
                        .id(20)
                        .title("Movie 1")
                        .build()
        ));
    }

    private <T> T getEntityFromResult(MvcResult result, Class<T> clazz) throws Exception {
        String json = result.getResponse().getContentAsString();
        JavaType type = objectMapper.getTypeFactory().constructType(clazz);
        return objectMapper.readValue(json, type);
    }

    private <T> List<T> getEntitiesFromResult(MvcResult result, Class<T> clazz) throws Exception {
        String json = result.getResponse().getContentAsString();
        JavaType type = objectMapper.getTypeFactory().constructCollectionType(List.class, clazz);
        return objectMapper.readValue(json, type);
    }
}
