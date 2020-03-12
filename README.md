# Scrabble Solver Service 

This is a monolithic multi-tiered web application. It is built with 

* Java 8 
* Maven 3.3.9 
* Spring Boot 2.2.4.RELEASE 
* Spring Security
* Spring Data JPA
* JWT - Authentication Type
* Caffeine - Cache Provider
* H2 Memory (dev), PostgreSQL (prod) - SQL DB 
    
There are two environments for this application: development and product. In the development environment, H2 in-memory is used as the database so that there isn't needed for DB installation and configuration.

## Development

To build this application:

    mvn clean package
    
To run tests and coverage report:

    mvn clean verify    

Note: there is an error with the test coverage report. The error, however, isn't related to the task. Due to the time constrain, I leave it as what is for now.

The Scrabble Solver Service API related integration test class is WordResourceWordSearchIT.

To run checkstyle report:

    mvn checkstyle:checkstyle

To run in the development environment:

    mvn spring-boot:run

## Application Usage

Point your web browser to http://localhost:8080 as shown on the server console. On the home page of the application, there is an instruction of Scrabble Solver Service API access.

## Memory Usage And Time Complexity Of The Algorithm

The data structure Trie is used for word search functionality. There are two operations, insert and search, for this functionality. Both insert and search operations cost O(word_length) in time while the memory requirement of Trie is O(alphabet_size * word_length * number_of_words).
 
## Assumptions

Vernon
21:31 (8 minutes ago)
to me

* All words are formed with alphabetic characters and nothing else in a dictionary.
* There are two different interpretations of the word search for a given set of characters. One is to find all words containing only characters in the given character set and any single characters can appear in an output word multiple times. The other is to find all words containing only characters in the given character set and any single characters in a word can't exceed the number of the character in the given character set. I assume the task is the second case. 
