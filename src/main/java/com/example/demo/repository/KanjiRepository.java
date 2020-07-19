package com.example.demo.repository;

import com.example.demo.entity.Kanji;
import com.example.demo.model.KanjiAndSinoOnly;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KanjiRepository extends JpaRepository<Kanji, Long> {

//    @Query(value = "SELECT * from kanji where kanji.lesson_id = ?1", nativeQuery = true)
//    List<Kanji> findDetailKanjiByID(@Param("lesson_id")Long lessonID);
//
//    @Query(value = "SELECT id,kanji,sino_vietnamese from kanji where kanji.lesson_id = ?1", nativeQuery = true)
//    List<Kanji> getList(@Param("lesson_id")Long lessonID);

    @Query(value ="Select id,kanji,sino_vietnamese from kanji where kanji.lesson_id = ?1" , nativeQuery = true)
    List<KanjiAndSinoOnly> findKanji(@Param("id")Long lessonID);

    @Query(value ="SELECT kanji,sino_vietnamese FROM ((kanji inner join lesson on kanji.lesson_id = lesson.id ) inner join level on lesson.level_id  = level.id) where level_id = ?1" , nativeQuery = true)
    List<KanjiAndSinoOnly> findKanjiByLevel(@Param("id")Long levelID);

//    List<Kanji> getIdAndKanjiAndSino_vietnameseByLesson_Id(@Param("lesson_id")Long lessonID);
//    List<Kanji> findByLessonIdOrderByDescriptionAsc(Optional<Lesson> lessonID);
}
