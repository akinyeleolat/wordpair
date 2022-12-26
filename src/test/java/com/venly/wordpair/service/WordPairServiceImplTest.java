package com.venly.wordpair.service;

import com.venly.wordpair.model.RelationshipType;
import com.venly.wordpair.model.WordPair;
import com.venly.wordpair.repository.WordPairRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class WordPairServiceImplTest {

    @Mock
    private WordPairRepository wordPairRepository;

    @InjectMocks
    private WordPairServiceImpl wordPairService;


    @Test
    public void testAddWordPair() {
        WordPair wordPair = new WordPair("happy", "sad", RelationshipType.ANTONYM);
        when(wordPairRepository.save(wordPair)).thenReturn(wordPair);

        WordPair result = wordPairService.addWordPair(wordPair);
        assertEquals(wordPair, result);
    }

    @Test
    public void testFilter() {
        // Create a list of word pairs to test
        List<WordPair> wordPairs = Arrays.asList(
                new WordPair("happy", "sad", RelationshipType.ANTONYM),
                new WordPair("hot", "cold", RelationshipType.ANTONYM),
                new WordPair("big", "small", RelationshipType.ANTONYM),
                new WordPair("fast", "slow", RelationshipType.ANTONYM)
        );

        // Initialize a mock repository and set it as the repository used by the service
        WordPairRepository mockRepository = mock(WordPairRepository.class);
        when(mockRepository.findAll()).thenReturn(wordPairs);
        wordPairService.setWordPairRepository(mockRepository);

        // Test filtering by "antonym" relationship type
        List<WordPair> filteredWordPairs = wordPairService.filterWordPairs(RelationshipType.valueOf("ANTONYM"));
        assertEquals(4, filteredWordPairs.size());
        for (WordPair wp : filteredWordPairs) {
            assertEquals(RelationshipType.ANTONYM, wp.getRelationshipType());
        }

        // Test filtering by "synonym" relationship type
        filteredWordPairs = wordPairService.filterWordPairs(RelationshipType.valueOf("SYNONYM"));
        assertEquals(0, filteredWordPairs.size());
    }

    @Test
    void getAllWordPairs() {
        List<WordPair> wordPairs = Arrays.asList(
                new WordPair("happy", "sad", RelationshipType.ANTONYM),
                new WordPair("hot", "cold", RelationshipType.ANTONYM),
                new WordPair("big", "small", RelationshipType.ANTONYM),
                new WordPair("fast", "slow", RelationshipType.ANTONYM)
        );

        List<WordPair> expectedOutput = Arrays.asList(
                new WordPair("happy", "sad", RelationshipType.ANTONYM),
                new WordPair("hot", "cold", RelationshipType.ANTONYM),
                new WordPair("big", "small", RelationshipType.ANTONYM),
                new WordPair("fast", "slow", RelationshipType.ANTONYM)
        );

        when(wordPairRepository.findAll()).thenReturn(wordPairs);

        List<WordPair> result = wordPairService.getAllWordPairs();

        assertEquals(expectedOutput, result);
    }
}