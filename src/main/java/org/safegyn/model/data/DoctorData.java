package org.safegyn.model.data;

import lombok.Getter;
import lombok.Setter;
import org.safegyn.db.entity.Doctor;

@Getter
@Setter
public class DoctorData extends Doctor {

    private Double overallRating = 0.0D;

}
