package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "level")
public class Level {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(insertable = true, updatable = true, nullable = false)
    private String name;

    @Column(insertable = true, updatable = true, nullable = false)
    private String image;

//    @OneToMany(mappedBy = "lesson", fetch = FetchType.EAGER)
//    private Collection<Lesson> lesson;

    @OneToMany(mappedBy = "level", cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    private List<Lesson> lessons;
}
