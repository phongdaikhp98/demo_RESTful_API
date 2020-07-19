package com.example.demo.repository;

import com.example.demo.model.QuizWritingByLesson;
import com.example.demo.entity.Quiz_Writing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizWritingRepository extends JpaRepository<Quiz_Writing, Long> {
    @Query(value ="SELECT answer,question from quiz_writing where lesson_id = ?1" , nativeQuery = true)
    List<QuizWritingByLesson> getQuizByLesson(@Param("id")Long lessonID);
}
