package com.example.demo.controller;

import com.example.demo.model.KanjiAndSinoOnly;
import com.example.demo.es.entity.KanjiEs;
import com.example.demo.es.repository.KanjiEsRepository;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.entity.Kanji;
import com.example.demo.model.KanjiSearchModel;
import com.example.demo.repository.KanjiRepository;
import com.example.demo.repository.LessonRepository;
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
public class KanjiController {
    @Autowired
    KanjiRepository kanjiRepository;

    @Autowired
    LessonRepository lessonRepository;

    @Autowired
    KanjiEsRepository kanjiEsRepository;

    @GetMapping("kanjis")
    public ResponseEntity<List<Kanji>> getAllKanji()
            throws ResourceNotFoundException {
        List<Kanji> kanjis = kanjiRepository.findAll();
        if (kanjis.isEmpty()) {
            new ResourceNotFoundException("Don't have kanji");
        }
        return new ResponseEntity<>(kanjis, HttpStatus.OK);
    }

    @GetMapping("/kanjis/{id}")
    public ResponseEntity<Kanji> getKanjiById(@PathVariable(value = "id") Long kanjiID)
            throws ResourceNotFoundException {

        Kanji kanji = kanjiRepository.findById(kanjiID)
                .orElseThrow(() -> new ResourceNotFoundException("Không tồn tại kanji này: " + kanjiID));

        return ResponseEntity.ok().body(kanji);
    }

    @PostMapping("/kanjis")
    public Kanji createLesson(@Valid @RequestBody Kanji kanji) {
        return kanjiRepository.save(kanji);
    }

    @PutMapping("/kanjis/{id}")
    public ResponseEntity<Kanji> updateKanji(@PathVariable(value = "id") Long kanjiID,
                                               @Valid @RequestBody Kanji kanjiDetails) throws ResourceNotFoundException {
        Kanji kanji = kanjiRepository.findById(kanjiID)
                .orElseThrow(() -> new ResourceNotFoundException("Kanji này không tồn tại: " + kanjiID));
        kanji.setImage(kanjiDetails.getImage());
        kanji.setSino_vietnamese(kanjiDetails.getSino_vietnamese());
        kanji.setKanji_meaning(kanjiDetails.getKanji_meaning());
        kanji.setOnyomi(kanjiDetails.getOnyomi());
        kanji.setKunyomi(kanjiDetails.getKunyomi());

        final Kanji updatedKanji  = kanjiRepository.save(kanji);

        return ResponseEntity.ok(updatedKanji);
    }

    @DeleteMapping("/kanjis/{id}")
    public Map<String, Boolean> deleteLKanji(@PathVariable(value = "id") Long kanjiId)
            throws ResourceNotFoundException {

        Kanji kanji = kanjiRepository.findById(kanjiId)
                .orElseThrow(() -> new ResourceNotFoundException("Kanji này không tồn tại: " + kanjiId));

        kanjiRepository.delete(kanji);
        Map<String, Boolean> response = new HashMap<>();
        response.put("Delete successfully! ", Boolean.TRUE);
        return response;
    }


    @GetMapping("lesson/{id}/kanjis")
    public ResponseEntity<List<KanjiAndSinoOnly>> getAllKanjiByLessonID(@PathVariable(value = "id") Long lessonID)
            throws ResourceNotFoundException {
        List<KanjiAndSinoOnly> kanjis =  kanjiRepository.findKanji(lessonID);
        if (kanjis.isEmpty()) {
            new ResourceNotFoundException("Don't have kanji in lesson: " + lessonID);
        }
        return new ResponseEntity<>(kanjis, HttpStatus.OK);
    }

    @GetMapping("level/{id}/kanjis")
    public ResponseEntity<List<KanjiAndSinoOnly>> getAllKanjiByLevelID(@PathVariable(value = "id") Long levelID)
            throws ResourceNotFoundException {
        List<KanjiAndSinoOnly> kanjis =  kanjiRepository.findKanjiByLevel(levelID);
        if (kanjis.isEmpty()) {
            new ResourceNotFoundException("Don't have kanji in lesson: " + levelID);
        }
        return new ResponseEntity<>(kanjis, HttpStatus.OK);
    }

//    @PostMapping("/search")
//    public ResponseEntity<List <KanjiEs>> searchKanji(@Valid @RequestBody KanjiSearchModel kanjiSearchModel) {
//        List<KanjiEs> kanjis = kanjiEsRepository.search(kanjiSearchModel.getKeyword());
//
//        if (kanjis.isEmpty()) {
//            new ResourceNotFoundException("Don't have kanji");
//        }
//        return new ResponseEntity<>(kanjis, HttpStatus.OK);
//    }

    @GetMapping("/search")
    public ResponseEntity<List <KanjiEs>> searchAllKanji() {
        List<KanjiEs> kanjis = kanjiEsRepository.findAll();

        if (kanjis.isEmpty()) {
            new ResourceNotFoundException("Don't have kanji");
        }
        return new ResponseEntity<>(kanjis, HttpStatus.OK);
    }
}
