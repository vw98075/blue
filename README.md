# Scrabble Solver Service 

This is a monolithic multi-tiered web application. It is built with Java 8 and Maven 3.5.4. There are two environments for this application: development and product. In the development environment, H2 in-memory is used as the database so that there isn't needed for DB installation and configuration.

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

Point your web browser to http://localhost:8080. On the home page, there is an application sign in instruction and the Scrabble Solver Service API access information.

## Memory Usage And Time Complexity Of The Algorithm

The data structure Trie is used for word search functionality. There are two operations, insert and search, for this functionality. Both insert and search operations cost O(word_length) in time while the memory requirement of Trie is O(alphabet_size * word_length * number_of_words).
 
### Assumption Dictionary  

All words are formed with alphabetic characters and nothing else in a dictionary.
