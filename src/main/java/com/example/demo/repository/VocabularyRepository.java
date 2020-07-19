package com.example.demo.repository;

import com.example.demo.model.VocabByKanji;
import com.example.demo.entity.Vocabulary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VocabularyRepository extends JpaRepository<Vocabulary, Long> {
    @Query(value ="Select kanji_vocab, hiragana, vocab_meaning from vocabulary where vocabulary.kanji_id = ?1" , nativeQuery = true)
    List<VocabByKanji> findVocab(@Param("id")Long kanjiID);

    @Query(value ="SELECT hiragana,kanji_vocab,vocab_meaning FROM ((vocabulary inner join kanji on vocabulary.kanji_id = kanji.id ) inner join lesson on kanji.lesson_id = lesson.id) where lesson_id = ?1" , nativeQuery = true)
    List<VocabByKanji> findVocabByLesson(@Param("id")Long lessonID);

}
