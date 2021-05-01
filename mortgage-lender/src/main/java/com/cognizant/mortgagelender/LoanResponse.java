package com.cognizant.mortgagelender;

public class LoanResponse {
    private LoanRequest loanRequest;
    private String qualification;
    private String status;
    private double loanAmount;

    public LoanResponse(LoanRequest loanRequest) {
        this.loanRequest = loanRequest;
    }

    public Candidate getCandidate() {
        return loanRequest.getCandidate();
    }

    public String getQualification() {
        return this.qualification;

    }

    public String getStatus() {
        return this.status;
    }

    public double getLoanAmount() {
        return this.loanAmount;
    }

    public LoanRequest getLoanRequest() {
        return loanRequest;
    }

    public void setLoanRequest(LoanRequest loanRequest) {
        this.loanRequest = loanRequest;
    }
}
