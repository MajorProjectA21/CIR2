package com.example.amma.cir2;

public class studentHiherSecondaryMarks {
    private String rollNo, percentage, DOP, board;
    public studentHiherSecondaryMarks(){}

    public studentHiherSecondaryMarks(String rollNo, String percentage, String DOP, String board) {
        this.rollNo = rollNo;
        this.percentage = percentage;
        this.DOP = DOP;
        this.board = board;
    }

    public String getRollNo() {
        return rollNo;
    }

    public void setRollNo(String rollNo) {
        this.rollNo = rollNo;
    }

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }

    public String getDOP() {
        return DOP;
    }

    public void setDOP(String DOP) {
        this.DOP = DOP;
    }

    public String getBoard() {
        return board;
    }

    public void setBoard(String board) {
        this.board = board;
    }
}
