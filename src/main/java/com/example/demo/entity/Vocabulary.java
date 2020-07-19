package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "vocabulary")
public class Vocabulary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(insertable = true, updatable = true)
    private String kanji_vocab;

    @Column(insertable = true, updatable = true)
    private String hiragana;

    @Column(insertable = true, updatable = true)
    private String vocab_meaning;

//    @ManyToOne
//    @JoinColumn(referencedColumnName = "id", name = "kanji_id")
//    private Kanji kanji;

    @ManyToOne
    @JoinColumn(name="kanji_id")
    @JsonIgnore
    private Kanji kanji;
}
