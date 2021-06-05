package org.safegyn.db.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(indexes = {
        @Index(name = "review_submission_index", columnList = "submissionId"),
        @Index(name = "review_submission_question", columnList = "questionId")})
public class Review extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long submissionId;

    @Column(nullable = false)
    private Long questionId;

    @Column(columnDefinition = "TEXT")
    private String answer;

    private Double score;

}
