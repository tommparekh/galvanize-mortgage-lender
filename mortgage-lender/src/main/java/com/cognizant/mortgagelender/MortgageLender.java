package com.cognizant.mortgagelender;

import com.cognizant.mortgagelender.candidate.Candidate;
import com.cognizant.mortgagelender.exception.NegativeAmountException;
import com.cognizant.mortgagelender.exception.UnqualifiedLoanException;
import com.cognizant.mortgagelender.loan.LoanApproval;
import com.cognizant.mortgagelender.loan.LoanOffer;
import com.cognizant.mortgagelender.loan.LoanRequest;
import com.cognizant.mortgagelender.loan.LoanResponse;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MortgageLender {
    private static final String QUALIFICATION_QUALIFIED = "qualified";
    private static final String QUALIFICATION_NOT_QUALIFIED = "not qualified";
    private static final String QUALIFICATION_PARTIALLY_QUALIFIED = "partially qualified";
    private static final String STATUS_DENIED = "denied";
    private static final String STATUS_QUALIFIED = "qualified";
    private static final String STATUS_APPROVED = "approved";
    private static final String STATUS_ONHOLD = "onhold";
    private static final String STATUS_ACCEPTED = "accepted";
    private static final String STATUS_REJECTED = "rejected";

    private List<LoanApproval> listOfApprovals = new ArrayList<>();

    private double pendingFund;
    private double availableFund;

    public double getPendingFund() {
        return pendingFund;
    }

    public void setPendingFund(double pendingFund) throws NegativeAmountException {
        if (pendingFund < 0) {
            throw new NegativeAmountException("Pending Fund is less then zero.");
        }
        this.pendingFund = pendingFund;
    }

    public List<LoanApproval> getListOfApprovals() {
        return listOfApprovals;
    }

    public void setListOfApprovals(List<LoanApproval> listOfApprovals) {
        this.listOfApprovals = listOfApprovals;
    }

    public double getAvailableFund() {
        return availableFund;
    }

    public void setAvailableFund(double availableFund) throws NegativeAmountException {
        if (availableFund < 0) {
            throw new NegativeAmountException("Available Fund is less then zero.");
        }
        this.availableFund = availableFund;
    }

    public void depositFund(double deposit) throws NegativeAmountException {
        if (deposit >= 0) {
            availableFund += deposit;
        } else
            throw new NegativeAmountException("Deposit amount is less then zero.");


    }

    public LoanResponse acceptAndQualify(LoanRequest loanRequest) {
        LoanResponse response = new LoanResponse(loanRequest);

        Candidate candidate = loanRequest.getCandidate();
        //     if(availableFund>=loanRequest.getRequestAmout()) {

        // base requirements
        if (candidate.getDti() < 36 && candidate.getCreditScore() > 620) {
            response.setLoanResponseStatus(MortgageLender.STATUS_QUALIFIED);

            // Either approved or partial
            if (loanRequest.getRequestAmount() / 4 <= candidate.getSavings()) {
                response.setQualification(MortgageLender.QUALIFICATION_QUALIFIED);
                response.setLoanAmount(loanRequest.getRequestAmount());
            } else {
                double partialAmt = candidate.getSavings() * 4;
                response.setQualification(MortgageLender.QUALIFICATION_PARTIALLY_QUALIFIED);
                response.setLoanAmount(partialAmt);
            }
        } else {
            // no good score or high dti
            response.setLoanResponseStatus(MortgageLender.STATUS_DENIED);
            response.setLoanAmount(0);
            response.setQualification(MortgageLender.QUALIFICATION_NOT_QUALIFIED);
        }
        //      }

        return response;
    }

    public LoanApproval approveLoan(LoanResponse response) throws UnqualifiedLoanException, NegativeAmountException, CloneNotSupportedException {
        if (response.getQualification().equals(MortgageLender.QUALIFICATION_QUALIFIED)
                || response.getQualification().equals(MortgageLender.QUALIFICATION_PARTIALLY_QUALIFIED)) {
            LoanApproval approval = new LoanApproval(response);
            if (availableFund < response.getLoanAmount()) {
                approval.setLoanApprovalStatus(MortgageLender.STATUS_ONHOLD);
            } else {
                approval.setLoanApprovalStatus(MortgageLender.STATUS_APPROVED);
                this.setAvailableFund((getAvailableFund() - approval.getLoanResponse().getLoanAmount()));
                this.setPendingFund((getPendingFund() + approval.getLoanResponse().getLoanAmount()));
                approval.setApprovalDate(LocalDate.now());

                listOfApprovals.add((LoanApproval) approval.clone());
            }

            return approval;
        } else throw new UnqualifiedLoanException();

    }

    public LoanOffer processOffer(LoanApproval approval, boolean isAccepted) {
        LoanOffer loanOffer = new LoanOffer(approval);
        if (isAccepted) {
            loanOffer.setLoanOfferStatus(MortgageLender.STATUS_ACCEPTED);
        } else {
            loanOffer.setLoanOfferStatus(MortgageLender.STATUS_REJECTED);
            loanOffer.getLoanApproval().setLoanApprovalStatus(MortgageLender.STATUS_REJECTED);
            loanOffer.getLoanApproval().getLoanResponse().setLoanResponseStatus(MortgageLender.STATUS_REJECTED);
            availableFund += approval.getLoanResponse().getLoanAmount();
        }
        pendingFund -= approval.getLoanResponse().getLoanAmount();
        return loanOffer;
    }

    public void checkExpiredApprovals() {
    }
}
