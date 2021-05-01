package com.cognizant.mortgagelender;
public class MortgageLender {
    private double availableFund;
    public double getAvailableFund() {
        return availableFund;
    }

    public void setAvailableFund(double availableFund) throws NegativeAmountException {
        if(availableFund<0){
            throw new NegativeAmountException("Negative Amount");
        }
        this.availableFund=availableFund;
    }

    public void depositFund(double deposit) throws NegativeAmountException  {
        if(deposit>=0){
            availableFund+=deposit;
        }
        else
            throw new NegativeAmountException("Negative Amount");


    }
}
