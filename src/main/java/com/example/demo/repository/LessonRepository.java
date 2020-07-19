package com.example.demo.repository;

import com.example.demo.entity.Lesson;
import com.example.demo.model.LessonByLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {
    @Query(value ="Select id,name from lesson where lesson.level_id = ?1" , nativeQuery = true)
    List<LessonByLevel> findLesson(@Param("id")Long levelID);

}
