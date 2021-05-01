package com.cognizant.mortgagelender;

public class LoanRequest {
    private String loanRequestId;
    private Candidate candidate;
    private double requestAmout;

    public LoanRequest(String loanRequestId, Candidate candidate, double requestAmout) {
        this.loanRequestId = loanRequestId;
        this.candidate = candidate;
        this.requestAmout = requestAmout;
    }

    public String getLoanRequestId() {
        return loanRequestId;
    }

    public void setLoanRequestId(String loanRequestId) {
        this.loanRequestId = loanRequestId;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }

    public double getRequestAmout() {
        return requestAmout;
    }

    public void setRequestAmout(double requestAmout) {
        this.requestAmout = requestAmout;
    }

    public Candidate getCandidate() {
        return this.candidate;
    }
}
