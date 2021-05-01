package com.cognizant.mortgagelender.loan;

import com.cognizant.mortgagelender.candidate.Candidate;

public class LoanRequest {
    private String loanRequestId;
    private Candidate candidate;
    private double requestAmount;

    public LoanRequest(String loanRequestId, Candidate candidate, double requestAmout) {
        this.loanRequestId = loanRequestId;
        this.candidate = candidate;
        this.requestAmount = requestAmout;
    }

    public String getLoanRequestId() {
        return loanRequestId;
    }

    public void setLoanRequestId(String loanRequestId) {
        this.loanRequestId = loanRequestId;
    }

    public double getRequestAmount() {
        return requestAmount;
    }

    public void setRequestAmount(double requestAmount) {
        this.requestAmount = requestAmount;
    }

    public Candidate getCandidate() {
        return this.candidate;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }
}
