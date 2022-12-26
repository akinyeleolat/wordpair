package com.venly.wordpair.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.venly.wordpair.model.RelationshipType;
import com.venly.wordpair.model.WordPair;
import com.venly.wordpair.repository.WordPairRepository;
import com.venly.wordpair.service.WordPairService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;



@SpringBootTest
class WordPairControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private WordPairRepository wordPairRepository;

    @Autowired
    private WordPairService wordPairService;

    @Autowired
    private WordPairController wordPairController;



    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        WordPairService mockService = Mockito.mock(WordPairService.class);
        ReflectionTestUtils.setField(wordPairController, "wordPairService", mockService);
    }

    @Test
    public void testAddWordPair() throws Exception {
        WordPair wordPair = new WordPair("happy", "sad", RelationshipType.ANTONYM);

        mockMvc.perform(post("/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(wordPair)))
                .andExpect(status().isOk())
                .andExpect(content().string("Word pair added successfully"));
    }

    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



    @Test
    public void testFilterWordPairs() throws Exception {
        WordPair wordPair1 = new WordPair("happy", "sad", RelationshipType.ANTONYM);
        WordPair wordPair2 = new WordPair("hot", "cold", RelationshipType.ANTONYM);
        WordPair wordPair3 = new WordPair("big", "small", RelationshipType.ANTONYM);
        WordPair wordPair4 = new WordPair("fast", "slow", RelationshipType.SYNONYM);

        List<WordPair> antonyms = Arrays.asList(wordPair1, wordPair2, wordPair3);
        List<WordPair> synonyms = Collections.singletonList(wordPair4);

        when(wordPairService.filterWordPairs(RelationshipType.ANTONYM)).thenReturn(antonyms);
        when(wordPairService.filterWordPairs(RelationshipType.SYNONYM)).thenReturn(synonyms);

        mockMvc.perform(get("/filter")
                        .param("relationshipType", "ANTONYM"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect((ResultMatcher) jsonPath("$[0].word1", is("happy")))
                .andExpect((ResultMatcher) jsonPath("$[1].word1", is("hot")))
                .andExpect((ResultMatcher) jsonPath("$[2].word1", is("big")));

        mockMvc.perform(get("/filter")
                        .param("relationshipType", "SYNONYM"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect((ResultMatcher) jsonPath("$[0].word1", is("fast")));
    }


    @Test
    public void testGetAllWordPairs() throws Exception {
        WordPair wordPair1 = new WordPair("happy", "sad", RelationshipType.ANTONYM);
        WordPair wordPair2 = new WordPair("hot", "cold", RelationshipType.ANTONYM);
        WordPair wordPair3 = new WordPair("big", "small", RelationshipType.ANTONYM);
        WordPair wordPair4 = new WordPair("fast", "slow", RelationshipType.SYNONYM);
        List<WordPair> allWordPairs = Arrays.asList(wordPair1, wordPair2, wordPair3, wordPair4);

        Mockito.when(wordPairService.getAllWordPairs()).thenReturn(allWordPairs);

        mockMvc.perform(get("/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect((ResultMatcher) jsonPath("$[0].word1", is("happy")))
                .andExpect((ResultMatcher) jsonPath("$[0].word2", is("sad")))
                .andExpect((ResultMatcher) jsonPath("$[0].relationshipType", is("ANTONYM")))
                .andExpect((ResultMatcher) jsonPath("$[1].word1", is("hot")))
                .andExpect((ResultMatcher) jsonPath("$[1].word2", is("cold")))
                .andExpect((ResultMatcher) jsonPath("$[1].relationshipType", is("ANTONYM")))
                .andExpect((ResultMatcher) jsonPath("$[2].word1", is("big")))
                .andExpect((ResultMatcher) jsonPath("$[2].word2", is("small")))
                .andExpect((ResultMatcher) jsonPath("$[2].relationshipType", is("ANTONYM")))
                .andExpect((ResultMatcher) jsonPath("$[3].word1", is("fast")))
                .andExpect((ResultMatcher) jsonPath("$[3].word2", is("slow")))
                .andExpect((ResultMatcher) jsonPath("$[3].relationshipType", is("SYNONYM")));
    }}


