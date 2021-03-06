package org.safegyn.model.data;

import lombok.Getter;
import lombok.Setter;
import org.safegyn.db.entity.Question;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ReviewData {

    private Long submissionId;

    private Double averageRating;

    private Long doctorId;

    private double professionalismScore = 0.0D;
    private int professionalismScoreCount = 0;

    private double objectivityScore = 0.0D;
    private int objectivityScoreCount = 0;

    private double respectfulnessScore = 0.0D;
    private int respectfulnessScoreCount = 0;

    private double anonymityScore = 0.0D;
    private int anonymityScoreCount = 0;

    private double inclusivityScore = 0.0D;
    private int inclusivityScoreCount = 0;

    List<ReviewSubmissionEntry> answers = new ArrayList<>();

    @Getter
    @Setter
    public static class ReviewSubmissionEntry {

        private String title;

        private String answer;

        private Question.Category category;

    }

}
