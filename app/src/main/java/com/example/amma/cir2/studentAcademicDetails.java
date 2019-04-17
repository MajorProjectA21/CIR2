package com.example.amma.cir2;

public class studentAcademicDetails {
    String percentage, hsPercentage, DOP, hsDOP, board, hsBoard;

    public studentAcademicDetails() {
    }

    public studentAcademicDetails(String percentage, String DOP, String board, String hsPercentage, String hsDOP, String hsBoard) {
        this.percentage = percentage;
        this.hsPercentage = hsPercentage;
        this.DOP = DOP;
        this.hsDOP = hsDOP;
        this.board = board;
        this.hsBoard = hsBoard;
    }

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }

    public String getHsPercentage() {
        return hsPercentage;
    }

    public void setHsPercentage(String hsPercentage) {
        this.hsPercentage = hsPercentage;
    }

    public String getDOP() {
        return DOP;
    }

    public void setDOP(String DOP) {
        this.DOP = DOP;
    }

    public String getHsDOP() {
        return hsDOP;
    }

    public void setHsDOP(String hsDOP) {
        this.hsDOP = hsDOP;
    }

    public String getBoard() {
        return board;
    }

    public void setBoard(String board) {
        board = board;
    }

    public String getHsBoard() {
        return hsBoard;
    }

    public void setHsBoard(String hsBoard) {
        this.hsBoard = hsBoard;
    }
}
