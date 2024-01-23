package com.example.lib.patron;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.example.lib.patron.Patron;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

class PatronControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PatronService patronService;

    @InjectMocks
    private PatronController patronController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(patronController).build();
    }

    @Test
    void getAllPatrons_ShouldReturnPatrons() throws Exception {
        List<Patron> patrons = Arrays.asList(new Patron(), new Patron());
        given(patronService.findAllPatrons()).willReturn(patrons);

        mockMvc.perform(get("/api/patrons"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(patrons.size()));
    }

    @Test
    void getPatronById_ShouldReturnPatron() throws Exception {
        Long patronId = 1L;
        Patron patron = new Patron();
        given(patronService.findPatronById(patronId)).willReturn(patron);

        mockMvc.perform(get("/api/patrons/" + patronId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists());
    }

    @Test
    void addPatron_ShouldAddPatron() throws Exception {
        Patron patron = new Patron(); // Assuming constructor or setter methods set the necessary fields
        given(patronService.addPatron(any(Patron.class))).willReturn(patron);

        mockMvc.perform(post("/api/patrons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Moh\", \"contactInformation\":\"Moh@example.com\"}")) // Example JSON content
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("New patron added successfully"))
                .andExpect(jsonPath("$.data").exists());
    }

    @Test
    void updatePatron_ShouldUpdatePatron() throws Exception {
        Long patronId = 1L;
        Patron updatedPatron = new Patron();
        given(patronService.updatePatron(eq(patronId), any(Map.class))).willReturn(updatedPatron);

        mockMvc.perform(put("/api/patrons/" + patronId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Updated Name\"}")) // Example JSON content for updates
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("patron updated successfully"))
                .andExpect(jsonPath("$.data").exists());
    }

    @Test
    void deletePatron_ShouldDeletePatron() throws Exception {
        Long patronId = 1L;
        willDoNothing().given(patronService).deletePatron(patronId);

        mockMvc.perform(delete("/api/patrons/" + patronId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("patron deleted successfully"));
    }
}
