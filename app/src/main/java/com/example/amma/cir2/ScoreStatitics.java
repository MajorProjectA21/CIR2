package com.example.amma.cir2;

public class ScoreStatitics {
    String correct, incorrect, total, date;

    public String getCorrect() {
        return correct;
    }

    public void setCorrect(String correct) {
        this.correct = correct;
    }

    public String getIncorrect() {
        return incorrect;
    }

    public void setIncorrect(String incorrect) {
        this.incorrect = incorrect;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ScoreStatitics() {
    }

    public ScoreStatitics(String correct, String incorrect, String total, String date) {
        this.correct = correct;
        this.incorrect = incorrect;
        this.total = total;
        this.date = date;
    }
}
