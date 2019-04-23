package com.example.amma.cir2;

public class StudentGraduationMarks {
    String cgpa,dop;
    public StudentGraduationMarks(){ }

    public StudentGraduationMarks(String cgpa, String dop) {
        this.cgpa = cgpa;
        this.dop = dop;
    }

    public String getCgpa() {
        return cgpa;
    }

    public void setCgpa(String cgpa) {
        this.cgpa = cgpa;
    }

    public String getDop() {
        return dop;
    }

    public void setDop(String dop) {
        this.dop = dop;
    }
}
