package com.cognizant.mortgagelender;

public class MortgageLender {
    private static final String QUALIFICATION_QUALIFIED = "qualified";
    private static final String QUALIFICATION_NOT_QUALIFIED = "not qualified";
    private static final String QUALIFICATION_PARTIALLY_QUALIFIED = "partially qualified";
    private static final String STATUS_DENIED = "denied";
    private static final String STATUS_QUALIFIED = "qualified";
    private static final String STATUS_APPROVED = "approved";
    private static final String STATUS_ONHOLD = "onhold";
    private double availableFund;

    public double getAvailableFund() {
        return availableFund;
    }

    public void setAvailableFund(double availableFund) throws NegativeAmountException {
        if (availableFund < 0) {
            throw new NegativeAmountException("Negative Amount");
        }
        this.availableFund = availableFund;
    }

    public void depositFund(double deposit) throws NegativeAmountException {
        if (deposit >= 0) {
            availableFund += deposit;
        } else
            throw new NegativeAmountException("Negative Amount");


    }

    public LoanResponse acceptAndQualify(LoanRequest loanRequest) {
        LoanResponse response = new LoanResponse(loanRequest);

        Candidate candidate = loanRequest.getCandidate();
        if(availableFund>=loanRequest.getRequestAmout()) {

            // base requirements
            if (candidate.getDti() < 36 && candidate.getCreditScore() > 620) {
                response.setStatus(MortgageLender.STATUS_QUALIFIED);

                // Either approved or partial
                if (loanRequest.getRequestAmout() / 4 <= candidate.getSavings()) {
                    response.setQualification(MortgageLender.QUALIFICATION_QUALIFIED);
                    response.setLoanAmount(loanRequest.getRequestAmout());
                } else {
                    double partialAmt = candidate.getSavings() * 4;
                    response.setQualification(MortgageLender.QUALIFICATION_PARTIALLY_QUALIFIED);
                    response.setLoanAmount(partialAmt);
                }
            } else {
                // no good score or high dti
                response.setStatus(MortgageLender.STATUS_DENIED);
                response.setLoanAmount(0);
                response.setQualification(MortgageLender.QUALIFICATION_NOT_QUALIFIED);
            }
        }

        return response;
    }
    public LoanApproval approveLoan(LoanResponse response) throws UnqualifiedLoanException{
        if(response.equals(MortgageLender.STATUS_QUALIFIED) || response.equals(MortgageLender.QUALIFICATION_PARTIALLY_QUALIFIED))
        {
            LoanApproval approval=new LoanApproval(response);
            if(availableFund<response.getLoanAmount()){
                approval.setStatus(MortgageLender.STATUS_ONHOLD);
            }
            else{
                approval.setStatus(MortgageLender.STATUS_APPROVED);
            }
            return  approval;


        }
        else throw new UnqualifiedLoanException();

    }
}
