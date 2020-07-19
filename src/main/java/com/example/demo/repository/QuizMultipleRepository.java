package com.example.demo.repository;

import com.example.demo.entity.Quiz_Multiple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizMultipleRepository extends JpaRepository<Quiz_Multiple, Long> {
}
