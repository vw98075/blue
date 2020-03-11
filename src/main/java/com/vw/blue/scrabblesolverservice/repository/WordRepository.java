package com.vw.blue.scrabblesolverservice.repository;

import com.vw.blue.scrabblesolverservice.domain.Word;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Word entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WordRepository extends JpaRepository<Word, Long> {

}
