package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "kanji")
public class Kanji {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(insertable = true, updatable = true)
    private String image;

    @Column(insertable = true, updatable = true)
    private String kanji;

    @Column(insertable = true, updatable = true)
    private String sino_vietnamese;

    @Column(insertable = true, updatable = true)
    private String kanji_meaning;

    @Column(insertable = true, updatable = true)
    private String onyomi;

    @Column(insertable = true, updatable = true)
    private String on_furigana;

    @Column(insertable = true, updatable = true)
    private String kunyomi;

    @Column(insertable = true, updatable = true)
    private String kun_furigana;

    private boolean flag;

    @ManyToOne
    @JoinColumn(name="lesson_id")
    @JsonIgnore
    private Lesson lesson;

    @OneToMany(mappedBy = "kanji", cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    private List<Vocabulary> vocabularies;

}
