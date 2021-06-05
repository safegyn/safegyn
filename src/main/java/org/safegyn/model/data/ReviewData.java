package org.safegyn.model.data;

import lombok.Getter;
import lombok.Setter;
import org.safegyn.model.form.ReviewForm;

@Getter
@Setter
public class ReviewData extends ReviewForm {

    private Long submissionId;

    private Long doctorId;

}
