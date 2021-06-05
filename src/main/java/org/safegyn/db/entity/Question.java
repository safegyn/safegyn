package org.safegyn.db.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(indexes = {@Index(name = "question_index", columnList = "title")})
public class Question extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Category category;

    public enum Category {
        INFO, TESTIMONIAL,
        ANONYMITY, INCLUSIVITY, OBJECTIVITY, PROFESSIONALISM, RESPECTFULNESS
    }

}
