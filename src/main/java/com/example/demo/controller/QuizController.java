package com.example.demo.controller;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.entity.Quiz;
import com.example.demo.repository.QuizRepository;
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
public class QuizController {
    @Autowired
    QuizRepository quizRepository;

    @GetMapping("/quizzes")
    public ResponseEntity<List<Quiz>> getAllQuiz()
            throws ResourceNotFoundException {
        List<Quiz> quizzes = quizRepository.findAll();
        if (quizzes.isEmpty()) {
            new ResourceNotFoundException("Don't have quiz");
        }
        return new ResponseEntity<>(quizzes, HttpStatus.OK);
    }

    @GetMapping("/quizzes/{id}")
    public ResponseEntity<Quiz> getQuizById(@PathVariable(value = "id") Long quizID)
            throws ResourceNotFoundException {

        Quiz quiz = quizRepository.findById(quizID)
                .orElseThrow(() -> new ResourceNotFoundException("Không tồn tại quiz này: " + quizID));

        return ResponseEntity.ok().body(quiz);
    }

    @PostMapping("/quizzes")
    public Quiz createQuiz(@Valid @RequestBody Quiz quiz) {
        return quizRepository.save(quiz);
    }

    @PutMapping("/quizzes/{id}")
    public ResponseEntity<Quiz> updateQuiz(@PathVariable(value = "id") Long quizID,
                                             @Valid @RequestBody Quiz quizDetails) throws ResourceNotFoundException {
        Quiz quiz = quizRepository.findById(quizID)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz này không tồn tại: " + quizID));
        quiz.setType(quizDetails.getType());

        final Quiz updatedQuiz = quizRepository.save(quiz);

        return ResponseEntity.ok(updatedQuiz);
    }

    @DeleteMapping("/quizzes/{id}")
    public Map<String, Boolean> deleteQuiz(@PathVariable(value = "id") Long quizId)
            throws ResourceNotFoundException {

        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz này không tồn tại: " + quizId));

        quizRepository.delete(quiz);
        Map<String, Boolean> response = new HashMap<>();
        response.put("Delete successfully!", Boolean.TRUE);

        return response;
    }
}
