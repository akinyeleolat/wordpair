package com.venly.wordpair.controller;

import com.venly.wordpair.model.RelationshipType;
import com.venly.wordpair.model.WordPair;
import com.venly.wordpair.service.WordPairService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class WordPairController {

    private final WordPairService wordPairService;

    public WordPairController(WordPairService wordPairService) {
        this.wordPairService = wordPairService;
    }

    @PostMapping("/add")
    public ResponseEntity<String> addWordPair(@RequestBody WordPair wordPair) {
        try {
            wordPairService.addWordPair(wordPair);
            return ResponseEntity.ok("Word pair added successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error adding word pair");
        }
    }

    @GetMapping("/filter")
    public List<WordPair> filterWordPairs(@RequestParam RelationshipType relationshipType) {
        return wordPairService.filterWordPairs(relationshipType);
    }

    @GetMapping("/all")
    public List<WordPair> getAllWordPairs() {
        return wordPairService.getAllWordPairs();
    }
}

