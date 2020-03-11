package com.vw.blue.scrabblesolverservice.web.rest;

import com.vw.blue.scrabblesolverservice.BlueApp;
import com.vw.blue.scrabblesolverservice.service.WordService;
import com.vw.blue.scrabblesolverservice.web.rest.errors.ExceptionTranslator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.Validator;

import static com.vw.blue.scrabblesolverservice.web.rest.TestUtil.createFormattingConversionService;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasItems;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest(classes = BlueApp.class)
class WordResourceWordSearchIT {

    private static final String TRIE_DATA_ABC = "abc";
    private static final String TRIE_DATA_CBS = "cbs";

    @Autowired
    private WordService wordService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restWordMockMvc;

    @Autowired
    private Validator validator;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final WordResource wordResource = new WordResource(wordService);
        this.restWordMockMvc = MockMvcBuilders.standaloneSetup(wordResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();

        // Initialize the trie
        wordService.insertTextToTrie(TRIE_DATA_ABC);
        wordService.insertTextToTrie(TRIE_DATA_CBS);
    }

    @Test
    public void findWordsOnTrieAll() throws Exception {

        // Search words
        restWordMockMvc.perform(get("/api/words/search/{str}", "aebfcghs")
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").value(hasItems(TRIE_DATA_ABC, TRIE_DATA_CBS)));
    }

    @Test
    public void findWordsOnTrieSingle() throws Exception {

        // Search words
        restWordMockMvc.perform(get("/api/words/search/{str}", "bca")
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").value(hasItems(TRIE_DATA_ABC)));
    }

    @Test
    public void findWordsOnTrieNone() throws Exception {

        // Search words
        restWordMockMvc.perform(get("/api/words/search/{str}", "zzz")
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").value(empty()));
    }
}
