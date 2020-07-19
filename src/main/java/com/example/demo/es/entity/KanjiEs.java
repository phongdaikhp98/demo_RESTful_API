package com.example.demo.es.entity;

import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Document(indexName = "demo_ezkanji", type ="kanji")
public class KanjiEs {
    @Id
    @GeneratedValue
    private String id;
    private String image;
    private String kanji;
    private String sinoVietnamese;
    private String kanjiMeaning;
    private String onyomi;
    private String onFurigana;
    private String kunyomi;
    private String kunFurigana;
    // a ơi type vs field trung nhau có sao k??
    //khong duoc viet _ no k hieu muon viet thi phai chi ro ten cho no
    //nen tach thanh 2 thang rieng sau nay nay log no de hơn khi nao inser update thi inser update no vao luon
    //Insert vs update e van de ben mysql xong dung cái logstash đồng bộ đc k a?? khong phai goi services no ra de inser vao oke a
}
