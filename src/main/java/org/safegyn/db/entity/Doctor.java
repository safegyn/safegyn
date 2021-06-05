package org.safegyn.db.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "city"})},
        indexes = {
                @Index(name = "doctor_index_name", columnList = "name"),
                @Index(name = "doctor_index_city", columnList = "city")})
public class Doctor extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String city;

    private String gender;

    private String ageRange;

    private String officeAddress;

    private String telNo;

    private String consultationFees;

    private String paymentForms;

    private String officeHours;

    private String languagesSpoken;

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
