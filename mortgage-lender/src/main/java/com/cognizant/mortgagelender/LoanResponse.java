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

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getLoanAmount() {
        return this.loanAmount;
    }

    public void setLoanAmount(double loanAmount) {
        this.loanAmount = loanAmount;
    }

    public LoanRequest getLoanRequest() {
        return loanRequest;
    }

    public void setLoanRequest(LoanRequest loanRequest) {
        this.loanRequest = loanRequest;
    }
}
