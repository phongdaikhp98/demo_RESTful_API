package com.example.demo.controller;

import com.example.demo.model.LessonByLevel;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.entity.Lesson;
import com.example.demo.repository.LessonRepository;
import com.example.demo.repository.LevelRepository;
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
public class

LessonController {
    @Autowired
    LessonRepository lessonRepository;

    @Autowired
    LevelRepository levelRepository;

    @GetMapping("/lessons")
    public ResponseEntity<List <Lesson>> getAllLesson()
            throws ResourceNotFoundException {
        List<Lesson> lessons = lessonRepository.findAll();
        if (lessons.isEmpty()) {
            new ResourceNotFoundException("Don't have lesson");
        }
        return new ResponseEntity<>(lessons, HttpStatus.OK);
    }

    @GetMapping("/lessons/{id}")
    public ResponseEntity<Lesson> getLessonById(@PathVariable(value = "id") Long lessonID)
            throws ResourceNotFoundException {

        Lesson lesson = lessonRepository.findById(lessonID)
                .orElseThrow(() -> new ResourceNotFoundException("Không tồn tại lesson này: " + lessonID));

        return ResponseEntity.ok().body(lesson);
    }

    @PostMapping("/lessons")
    public Lesson createLesson(@Valid @RequestBody Lesson lesson) {
        return lessonRepository.save(lesson);
    }

    @PutMapping("/lessons/{id}")
    public ResponseEntity<Lesson> updateLesson(@PathVariable(value = "id") Long lessonID,
                                             @Valid @RequestBody Lesson lessonDetails) throws ResourceNotFoundException {
        Lesson lesson = lessonRepository.findById(lessonID)
                .orElseThrow(() -> new ResourceNotFoundException("Lesson này không tồn tại: " + lessonID));
        lesson.setName(lessonDetails.getName());

        final Lesson updatedLesson  = lessonRepository.save(lesson);

        return ResponseEntity.ok(updatedLesson);
    }

    @DeleteMapping("/lessons/{id}")
    public Map<String, Boolean> deleteLesson(@PathVariable(value = "id") Long lessonId)
            throws ResourceNotFoundException {

        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new ResourceNotFoundException("Lesson này không tồn tại: " + lessonId));

        lessonRepository.delete(lesson);
        Map<String, Boolean> response = new HashMap<>();
        response.put("Delete successfully!", Boolean.TRUE);
        return response;
    }

    @GetMapping("level/{id}/lessons")
    public ResponseEntity<List<LessonByLevel>> getAllLessonByLevelID(@PathVariable(value = "id") Long levelId)
            throws ResourceNotFoundException {
        List<LessonByLevel> kanjis =  lessonRepository.findLesson(levelId);
        if (kanjis.isEmpty()) {
            new ResourceNotFoundException("Don't have lesson in this level: " + levelId);
        }
        return new ResponseEntity<>(kanjis, HttpStatus.OK);
    }
}
