package com.example.demo.controller;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.entity.Quiz_Multiple;
import com.example.demo.repository.QuizMultipleRepository;
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
public class QuizMultipleController {
    @Autowired
    QuizMultipleRepository quizMultipleRepository;

    @GetMapping("/quizzesMultiple")
    public ResponseEntity<List<Quiz_Multiple>> getAllQuizMultiple()
            throws ResourceNotFoundException {
        List<Quiz_Multiple> quiz_multiples = quizMultipleRepository.findAll();
        if (quiz_multiples.isEmpty()) {
            new ResourceNotFoundException("Don't have quiz");
        }
        return new ResponseEntity<>(quiz_multiples, HttpStatus.OK);
    }

    @GetMapping("/quizzesMultiple/{id}")
    public ResponseEntity<Quiz_Multiple> getQuizMultiplelById(@PathVariable(value = "id") Long quizMultipleID)
            throws ResourceNotFoundException {

        Quiz_Multiple quiz_multiple = quizMultipleRepository.findById(quizMultipleID)
                .orElseThrow(() -> new ResourceNotFoundException("Không tồn tại quiz này: " + quizMultipleID));

        return ResponseEntity.ok().body(quiz_multiple);
    }

    @PostMapping("/quizzesMultiple")
    public Quiz_Multiple createQuizMultiple(@Valid @RequestBody Quiz_Multiple quizMultipleID) {
        return quizMultipleRepository.save(quizMultipleID);
    }

    @PutMapping("/quizzesMultiple/{id}")
    public ResponseEntity<Quiz_Multiple> updateQuizMultiple(@PathVariable(value = "id") Long quizMultipleID,
                                            @Valid @RequestBody Quiz_Multiple quizMultipleDetails) throws ResourceNotFoundException {
        Quiz_Multiple quiz_multiple = quizMultipleRepository.findById(quizMultipleID)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz này không tồn tại: " + quizMultipleID));
        quiz_multiple.setQuiz(quizMultipleDetails.getQuiz());
        quiz_multiple.setAnswerA(quizMultipleDetails.getAnswerA());
        quiz_multiple.setAnswerB(quizMultipleDetails.getAnswerB());
        quiz_multiple.setAnswerC(quizMultipleDetails.getAnswerC());
        quiz_multiple.setAnswerD(quizMultipleDetails.getAnswerD());
        quiz_multiple.setCorrectAnswer(quizMultipleDetails.getCorrectAnswer());

        final Quiz_Multiple updatedQuizMultiple = quizMultipleRepository.save(quiz_multiple);

        return ResponseEntity.ok(updatedQuizMultiple);
    }

    @DeleteMapping("/quizzesMultiple/{id}")
    public Map<String, Boolean> deleteQuizMultiple(@PathVariable(value = "id") Long quizMultipleID)
            throws ResourceNotFoundException {

        Quiz_Multiple quiz_multiple = quizMultipleRepository.findById(quizMultipleID)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz này không tồn tại: " + quizMultipleID));

        quizMultipleRepository.delete(quiz_multiple);
        Map<String, Boolean> response = new HashMap<>();
        response.put("Delete successfully!", Boolean.TRUE);

        return response;
    }
}
