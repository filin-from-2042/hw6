package com.github.javarar.poke;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class PokeServiceApplicationTests {

    @Autowired
    private MockMvc mvc;

    @Test
    void should_returnPokemonsSync() throws Exception {
        List<String> names = List.of("bulbasaur", "ivysaur", "venusaur");
        ObjectMapper mapper = new ObjectMapper();

        mvc.perform(post("/api/syncAll")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(names)))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name", is("bulbasaur")))
                .andExpect(jsonPath("$[1].name", is("ivysaur")))
                .andExpect(jsonPath("$[2].name", is("venusaur"))
                );
    }

    @Test
    void should_returnOnePokemonSync() throws Exception {
        List<String> names = List.of("bulbasaur", "ivysaur", "venusaur");
        ObjectMapper mapper = new ObjectMapper();

        mvc.perform(post("/api/syncOne")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(names)))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", anyOf(equalTo("bulbasaur"), equalTo("ivysaur"), equalTo("venusaur")))
                );
    }

    @Test
    void should_returnPokemonsAsync() throws Exception {
        List<String> names = List.of("bulbasaur", "ivysaur", "venusaur");
        ObjectMapper mapper = new ObjectMapper();

        mvc.perform(post("/api/asyncAll")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(names)))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name", is("bulbasaur")))
                .andExpect(jsonPath("$[1].name", is("ivysaur")))
                .andExpect(jsonPath("$[2].name", is("venusaur"))
                );
    }

    @Test
    void should_returnOnePokemonAsync() throws Exception {
        List<String> names = List.of("bulbasaur", "ivysaur", "venusaur");
        ObjectMapper mapper = new ObjectMapper();

        mvc.perform(post("/api/asyncOne")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(names)))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", anyOf(equalTo("bulbasaur"), equalTo("ivysaur"), equalTo("venusaur")))
                );
    }

}
