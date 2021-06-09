package org.safegyn.model.data;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScoreDistribution {

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

}
