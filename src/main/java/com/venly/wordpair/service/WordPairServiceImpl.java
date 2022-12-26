package com.venly.wordpair.service;

import com.venly.wordpair.model.RelationshipType;
import com.venly.wordpair.model.WordPair;
import com.venly.wordpair.repository.WordPairRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WordPairServiceImpl implements WordPairService {

    private WordPairRepository wordPairRepository;

    public WordPairServiceImpl(WordPairRepository wordPairRepository) {
        this.wordPairRepository = wordPairRepository;
    }

    @Override
    public WordPair addWordPair(WordPair wordPair) {
        return wordPairRepository.save(wordPair);
    }

    @Override
    public List<WordPair> filterWordPairs(RelationshipType relationshipType) {
        return wordPairRepository.findByRelationshipType(relationshipType);
    }

    @Override
    public List<WordPair> getAllWordPairs() {
        return wordPairRepository.findAll();
    }

    public void setWordPairRepository(WordPairRepository wordPairRepository) {
        this.wordPairRepository = wordPairRepository;
    }

    public WordPairRepository getWordPairRepository() {
        return wordPairRepository;
    }
}
