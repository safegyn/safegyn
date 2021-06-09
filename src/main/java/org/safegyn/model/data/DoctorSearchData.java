package org.safegyn.model.data;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class DoctorSearchData {

    private String state;
    private String city;
    private int total;

    List<DoctorData> reviews = new ArrayList<>();

}
