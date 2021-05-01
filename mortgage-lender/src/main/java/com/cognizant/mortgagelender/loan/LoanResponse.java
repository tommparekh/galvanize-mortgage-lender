package com.cognizant.mortgagelender.loan;

import com.cognizant.mortgagelender.candidate.Candidate;

public class LoanResponse {
    private LoanRequest loanRequest;
    private String qualification;
    private String loanResponseStatus;
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

    public String getLoanResponseStatus() {
        return this.loanResponseStatus;
    }

    public void setLoanResponseStatus(String loanResponseStatus) {
        this.loanResponseStatus = loanResponseStatus;
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
