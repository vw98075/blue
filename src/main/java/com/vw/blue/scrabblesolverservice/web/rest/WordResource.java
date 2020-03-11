package com.vw.blue.scrabblesolverservice.web.rest;

import com.vw.blue.scrabblesolverservice.domain.Word;
import com.vw.blue.scrabblesolverservice.service.WordService;
import com.vw.blue.scrabblesolverservice.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.vw.blue.scrabblesolverservice.domain.Word}.
 */
@RestController
@RequestMapping("/api")
public class WordResource {

    private final Logger log = LoggerFactory.getLogger(WordResource.class);

    private static final String ENTITY_NAME = "word";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WordService wordService;

    public WordResource(WordService wordService) {
        this.wordService = wordService;
    }

    /**
     * {@code POST  /words} : Create a new word.
     *
     * @param word the word to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new word, or with status {@code 400 (Bad Request)} if the word has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/words")
    public ResponseEntity<Word> createWord(@Valid @RequestBody Word word) throws URISyntaxException {
        log.debug("REST request to save Word : {}", word);
        if (word.getId() != null) {
            throw new BadRequestAlertException("A new word cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Word result = wordService.save(word);
        return ResponseEntity.created(new URI("/api/words/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /words} : Updates an existing word.
     *
     * @param word the word to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated word,
     * or with status {@code 400 (Bad Request)} if the word is not valid,
     * or with status {@code 500 (Internal Server Error)} if the word couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/words")
    public ResponseEntity<Word> updateWord(@Valid @RequestBody Word word) throws URISyntaxException {
        log.debug("REST request to update Word : {}", word);
        if (word.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Word result = wordService.save(word);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, word.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /words} : get all the words.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of words in body.
     */
    @GetMapping("/words")
    public ResponseEntity<List<Word>> getAllWords(Pageable pageable) {
        log.debug("REST request to get a page of Words");
        Page<Word> page = wordService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /words/:id} : get the "id" word.
     *
     * @param id the id of the word to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the word, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/words/{id}")
    public ResponseEntity<Word> getWord(@PathVariable Long id) {
        log.debug("REST request to get Word : {}", id);
        Optional<Word> word = wordService.findOne(id);
        return ResponseUtil.wrapOrNotFound(word);
    }

    /**
     * {@code DELETE  /words/:id} : delete the "id" word.
     *
     * @param id the id of the word to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/words/{id}")
    public ResponseEntity<Void> deleteWord(@PathVariable Long id) {
        log.debug("REST request to delete Word : {}", id);
        wordService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
    /**
     * {@code GET  /words/search/:string} : get all possible words with only characters in the "word".
     *
     * @param str the word to retrieve .
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of words in body.
     */
    @GetMapping("/words/search/{str}")
    public ResponseEntity<List<String>> searchAllWordsWithThoseCharacters(@PathVariable String str) {
        log.debug("REST request to get Words for : {}", str);
        List<String> l = wordService.findWordsOnTrie(str);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ""))
            .body(l);
    }
}
