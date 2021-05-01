package com.cognizant.mortgagelender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MortageLenderTestCases {

    MortgageLender mortgageLender;
    @BeforeEach
    public void setUp(){
        mortgageLender=new MortgageLender();
    }
    //    When I check my available funds
//    Then I should see how much funds I currently have
    @Test
    public void mortageLenderWithZeroFundThenShowAvailableFundAsZero() throws NegativeAmountException {
        // Arrange
        mortgageLender.setAvailableFund(0.00);
        // Act
        double fundAmount=mortgageLender.getAvailableFund();

       // Assert
        assertEquals(fundAmount,0.0);
    }
    @Test
    public void mortageLenderWithNonZeroFundThenShowAvailableFundAsNonZero() throws NegativeAmountException {
        // Arrange
        mortgageLender.setAvailableFund(100000.00);
        // Act
        double fundAmount=mortgageLender.getAvailableFund();

        // Assert
        assertEquals(fundAmount,100000.0);
    }
//    Given I have <current_amount> available funds
//    When I add <deposit_amount>
//    Then my available funds should be <total>

    @Test
    public void mortageLenderDepositZeroAmountThenAvailableFundNotUpdated() throws NegativeAmountException {
        // Arrange
        mortgageLender.setAvailableFund(1000.00);

        // Act
        mortgageLender.depositFund(00.00);
        double totalFundAmount=mortgageLender.getAvailableFund();

        // Assert
        assertEquals(totalFundAmount,1000.00);
    }
    @Test
    public void mortageLenderDepositNonZeroAmountOneTimeThenAvailableFundUpdated() throws NegativeAmountException {
        // Arrange
        mortgageLender.setAvailableFund(100000.00);

        // Act
        mortgageLender.depositFund(200000.00);
        double totalFundAmount=mortgageLender.getAvailableFund();

        // Assert
        assertEquals(totalFundAmount,300000.00);
    }
    @Test
    public void mortageLenderDepositNonZeroAmountTwiceThenAvailableFundUpdated() throws NegativeAmountException {
        // Arrange
        mortgageLender.setAvailableFund(100000.00);

        // Act
        mortgageLender.depositFund(200000.00);
        mortgageLender.depositFund(300000.00);
        double totalFundAmount=mortgageLender.getAvailableFund();

        // Assert
        assertEquals(totalFundAmount,600000.00);
    }

    @Test
    public void mortageLenderDepositNegativeAmountThenNegativeNumberException() throws NegativeAmountException {
        // Arrange

        mortgageLender.setAvailableFund(100000.00);

        // Act and Assert
        assertThrows(NegativeAmountException.class, () -> {
            mortgageLender.depositFund(-200000.00);
        });
        double fundAmount=mortgageLender.getAvailableFund();
        assertEquals(fundAmount,100000.00);
    }

}
