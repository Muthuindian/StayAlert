package com.tech42labs.stayalert.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by mari on 3/17/17.
 */

public class Reports extends RealmObject {

    @PrimaryKey
    private String empId;


    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }
}
