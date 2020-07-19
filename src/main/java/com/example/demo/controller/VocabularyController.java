package com.example.demo.controller;

import com.example.demo.model.VocabByKanji;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.entity.Vocabulary;
import com.example.demo.repository.VocabularyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class VocabularyController {
    @Autowired
    VocabularyRepository vocabularyRepository;

    @GetMapping("vocabs")
    public ResponseEntity<List<Vocabulary>> getAllVocabulary()
            throws ResourceNotFoundException {
        List<Vocabulary> vocabularies = vocabularyRepository.findAll();
        if (vocabularies.isEmpty()) {
            new ResourceNotFoundException("Don't have level");
        }
        return new ResponseEntity<>(vocabularies, HttpStatus.OK);
    }

    @GetMapping("/vocabs/{id}")
    public ResponseEntity<Vocabulary> getVocabularyById(@PathVariable(value = "id") Long vocabID)
            throws ResourceNotFoundException {

        Vocabulary vocabulary = vocabularyRepository.findById(vocabID)
                .orElseThrow(() -> new ResourceNotFoundException("Không tồn tại lesson này: " + vocabID));

        return ResponseEntity.ok().body(vocabulary);
    }

    @PostMapping("/vocabs")
    public Vocabulary createVocabulary(@Valid @RequestBody Vocabulary vocabulary) {
        return vocabularyRepository.save(vocabulary);
    }

    @PutMapping("/vocabs/{id}")
    public ResponseEntity<Vocabulary> updateVocabulary(@PathVariable(value = "id") Long vocabularyID,
                                              @Valid @RequestBody Vocabulary vocabDetails) throws ResourceNotFoundException {
        Vocabulary vocabulary = vocabularyRepository.findById(vocabularyID)
                .orElseThrow(() -> new ResourceNotFoundException("Level này không tồn tại: " + vocabularyID));
        vocabulary.setKanji_vocab(vocabDetails.getKanji_vocab());
        vocabulary.setHiragana(vocabDetails.getHiragana());
        vocabulary.setVocab_meaning(vocabDetails.getVocab_meaning());

        final Vocabulary updatedVocab  = vocabularyRepository.save(vocabulary);

        return ResponseEntity.ok(updatedVocab);
    }

    @DeleteMapping("/vocabs/{id}")
    public Map<String, Boolean> deleteLVocabulary(@PathVariable(value = "id") Long vocabId)
            throws ResourceNotFoundException {

        Vocabulary vocabulary = vocabularyRepository.findById(vocabId)
                .orElseThrow(() -> new ResourceNotFoundException("Kanji này không tồn tại: " + vocabId));

        vocabularyRepository.delete(vocabulary);
        Map<String, Boolean> response = new HashMap<>();
        response.put("Delete successfully! ", Boolean.TRUE);
        return response;
    }


    @GetMapping("kanji/{id}/vocabs")
    public ResponseEntity<List<VocabByKanji>> getAllVocabByKanjiID(@PathVariable(value = "id") Long kanjiID)
            throws ResourceNotFoundException {
        List<VocabByKanji> kanjis =  vocabularyRepository.findVocab(kanjiID);
        if (kanjis.isEmpty()) {
            new ResourceNotFoundException("Don't have vocab in this kanji: " + kanjiID);
        }
        return new ResponseEntity<>(kanjis, HttpStatus.OK);
    }

    @GetMapping("lesson/{id}/vocabs")
    public ResponseEntity<List<VocabByKanji>> getAllVocabByLessonID(@PathVariable(value = "id") Long lessonID)
            throws ResourceNotFoundException {
        List<VocabByKanji> kanjis =  vocabularyRepository.findVocabByLesson(lessonID);
        if (kanjis.isEmpty()) {
            new ResourceNotFoundException("Don't have vocab in this lesson: " + lessonID);
        }
        return new ResponseEntity<>(kanjis, HttpStatus.OK);
    }
}
