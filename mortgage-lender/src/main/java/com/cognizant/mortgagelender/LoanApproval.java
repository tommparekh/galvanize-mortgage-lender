package com.cognizant.mortgagelender;

public class LoanApproval {
    private LoanResponse loanResponse;
    private String loanApprovalStatus;

    public LoanApproval(LoanResponse response) {
        this.loanResponse = response;
    }

    public String getLoanApprovalStatus() {
        return this.loanApprovalStatus;

    }

    public void setLoanApprovalStatus(String loanApprovalStatus) {
        this.loanApprovalStatus = loanApprovalStatus;
    }

    public LoanResponse getLoanResponse() {
        return loanResponse;
    }

    public void setLoanResponse(LoanResponse loanResponse) {
        this.loanResponse = loanResponse;
    }
}
