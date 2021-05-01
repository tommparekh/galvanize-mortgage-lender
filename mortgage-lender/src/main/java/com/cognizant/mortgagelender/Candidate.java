package com.cognizant.mortgagelender;

public class Candidate {
    private String candidateId;
    private double dti;
    private double creditScore;
    private double savings;

    public Candidate(String id1, double dti, double
            credit_score, double saving) {
        this.candidateId = id1;
        this.dti = dti;
        this.creditScore = credit_score;
        this.savings = saving;
    }

    public String getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(String id1) {
        this.candidateId = id1;
    }

    public double getDti() {
        return dti;
    }

    public void setDti(double dti) {
        this.dti = dti;
    }

    public double getCreditScore() {
        return creditScore;
    }

    public void setCreditScore(double creditScore) {
        this.creditScore = creditScore;
    }

    public double getSavings() {
        return savings;
    }

    public void setSavings(double savings) {
        this.savings = savings;
    }
}
