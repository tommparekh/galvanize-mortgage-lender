package com.cognizant.mortgagelender;

public class LoanApproval {
    private LoanResponse loanResponse;
    private String status;

    public LoanApproval(LoanResponse response) {
        this.loanResponse=response;
    }

    public String getStatus() {
        return this.status;

    }

    public LoanResponse getLoanResponse() {
        return loanResponse;
    }

    public void setLoanResponse(LoanResponse loanResponse) {
        this.loanResponse = loanResponse;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
