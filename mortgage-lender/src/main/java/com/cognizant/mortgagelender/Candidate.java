package com.cognizant.mortgagelender;

public class Candidate {
    private String candidateId;
    private double dti;
    private double credit_score;
    private double saving;

    public Candidate(String id1, double dti, double
            credit_score, double saving) {
        this.candidateId = id1;
        this.dti = dti;
        this.credit_score = credit_score;
        this.saving = saving;
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

    public double getCredit_score() {
        return credit_score;
    }

    public void setCredit_score(double credit_score) {
        this.credit_score = credit_score;
    }

    public double getSaving() {
        return saving;
    }

    public void setSaving(double saving) {
        this.saving = saving;
    }
}
