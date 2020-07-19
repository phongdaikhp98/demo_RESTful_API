package com.example.demo.controller;

import com.example.demo.model.QuizWritingByLesson;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.entity.Quiz_Writing;
import com.example.demo.repository.QuizWritingRepository;
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
public class QuizWritingController {
    @Autowired
    QuizWritingRepository quizWritingRepository;

    @GetMapping("/quizzesWriting")
    public ResponseEntity<List<Quiz_Writing>> getAllQuizWriting()
            throws ResourceNotFoundException {
        List<Quiz_Writing> quizWritings = quizWritingRepository.findAll();
        if (quizWritings.isEmpty()) {
            new ResourceNotFoundException("Don't have quiz");
        }
        return new ResponseEntity<>(quizWritings, HttpStatus.OK);
    }

    @GetMapping("/quizzesWriting/{id}")
    public ResponseEntity<Quiz_Writing> getQuizWritingById(@PathVariable(value = "id") Long quizWritingID)
            throws ResourceNotFoundException {

        Quiz_Writing quizWriting = quizWritingRepository.findById(quizWritingID)
                .orElseThrow(() -> new ResourceNotFoundException("Không tồn tại quiz Writing này: " + quizWritingID));

        return ResponseEntity.ok().body(quizWriting);
    }

    @PostMapping("/quizzesWriting")
    public Quiz_Writing createQuizWriting(@Valid @RequestBody Quiz_Writing quizWriting) {
        return quizWritingRepository.save(quizWriting);
    }

    @PutMapping("/quizzesWriting/{id}")
    public ResponseEntity<Quiz_Writing> updateQuizWriting(@PathVariable(value = "id") Long quizWritingID,
                                            @Valid @RequestBody Quiz_Writing quizWritingDetails) throws ResourceNotFoundException {
        Quiz_Writing quizWriting = quizWritingRepository.findById(quizWritingID)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz Writing này không tồn tại: " + quizWritingID));
        quizWriting.setQuestion(quizWritingDetails.getQuestion());
        quizWriting.setAnswer(quizWritingDetails.getAnswer());

        final Quiz_Writing updatedQuizWriting = quizWritingRepository.save(quizWriting);

        return ResponseEntity.ok(updatedQuizWriting);
    }

    @DeleteMapping("/quizzesWriting/{id}")
    public Map<String, Boolean> deleteQuizWriting(@PathVariable(value = "id") Long quizWritingID)
            throws ResourceNotFoundException {

        Quiz_Writing quizWriting = quizWritingRepository.findById(quizWritingID)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz Writing này không tồn tại: " + quizWritingID));

        quizWritingRepository.delete(quizWriting);
        Map<String, Boolean> response = new HashMap<>();
        response.put("Delete successfully!", Boolean.TRUE);

        return response;
    }

    @GetMapping("lesson/{id}/quizzes")
    public ResponseEntity<List<QuizWritingByLesson>> getQuizWritingByLessonID(@PathVariable(value = "id") Long lessonID)
            throws ResourceNotFoundException {
        List<QuizWritingByLesson> kanjis =  quizWritingRepository.getQuizByLesson(lessonID);
        if (kanjis.isEmpty()) {
            new ResourceNotFoundException("Don't have vocab in this lesson: " + lessonID);
        }
        return new ResponseEntity<>(kanjis, HttpStatus.OK);
    }
}
