package com.venly.wordpair.repository;

import com.venly.wordpair.model.RelationshipType;
import com.venly.wordpair.model.WordPair;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WordPairRepository extends JpaRepository<WordPair, Long> {
    List<WordPair> findByRelationshipType(RelationshipType relationshipType);
    // Optional custom methods
}

