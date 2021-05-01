package com.cognizant.mortgagelender.loan;

import java.time.LocalDate;
import java.util.Objects;

public class LoanApproval implements Cloneable {
    private LoanResponse loanResponse;
    private String loanApprovalStatus;
    private LocalDate approvalDate;

    public LoanApproval(LoanResponse response) {
        this.loanResponse = response;
    }

    public String getLoanApprovalStatus() {
        return this.loanApprovalStatus;
    }

    public void setLoanApprovalStatus(String loanApprovalStatus) {
        this.loanApprovalStatus = loanApprovalStatus;
    }

    public LocalDate getApprovalDate() {
        return approvalDate;
    }

    public void setApprovalDate(LocalDate date) {
        this.approvalDate = date;
    }

    public LoanResponse getLoanResponse() {
        return loanResponse;
    }

    public void setLoanResponse(LoanResponse loanResponse) {
        this.loanResponse = loanResponse;
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoanApproval that = (LoanApproval) o;
        return loanResponse.equals(that.loanResponse) && loanApprovalStatus.equals(that.loanApprovalStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(loanResponse, loanApprovalStatus);
    }
}
