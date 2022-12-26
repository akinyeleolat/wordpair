package com.venly.wordpair.service;

import com.venly.wordpair.model.RelationshipType;
import com.venly.wordpair.model.WordPair;

import java.util.List;

public interface WordPairService {
    WordPair addWordPair(WordPair wordPair);
    List<WordPair> filterWordPairs(RelationshipType relationshipType);
    List<WordPair> getAllWordPairs();
}
