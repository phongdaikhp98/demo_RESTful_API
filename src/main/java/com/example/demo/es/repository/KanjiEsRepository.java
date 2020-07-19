package com.example.demo.es.repository;

import com.example.demo.es.entity.KanjiEs;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KanjiEsRepository extends ElasticsearchRepository<KanjiEs, String> {
    @Query("{\"match\": {\"kun_furigana\": {\"query\": \"?0\"}}}")
    List<KanjiEs> search(String keyword);

    List<KanjiEs> findAll();
    // nó found by type k phải by field hả a?? e thấy trên doc custom đc query
}


//auto wier thăng nay lai ma seach ra
