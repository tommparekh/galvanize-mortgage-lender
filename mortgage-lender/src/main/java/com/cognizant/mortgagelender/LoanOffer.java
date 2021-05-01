package com.cognizant.mortgagelender;

public class LoanOffer {

    private final LoanApproval loanApproval;
    private String loanOfferStatus;

    public LoanOffer(LoanApproval loanApproval) {
        this.loanApproval = loanApproval;
    }

    public String getLoanOfferStatus() {
        return this.loanOfferStatus;
    }

    public void setLoanOfferStatus(String loanOfferStatus) {
        this.loanOfferStatus = loanOfferStatus;
    }

    public LoanApproval getLoanResponse() {
        return this.loanApproval;
    }

    public LoanApproval getLoanApproval() {
        return loanApproval;
    }
}
