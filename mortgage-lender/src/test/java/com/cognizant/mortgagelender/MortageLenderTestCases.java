package com.cognizant.mortgagelender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MortageLenderTestCases {

    MortgageLender mortgageLender;
    @BeforeEach
    public void setUp(){
        mortgageLender=new MortgageLender();
    }
    //    When I check my available funds
//    Then I should see how much funds I currently have
    @Test
    public void mortageLenderWithZeroFundThenShowAvailableFundAsZero(){
        // Arrange
        mortgageLender.setAvailableFund(0.00);
        // Act
        double fundAmount=mortgageLender.getAvailableFund();

       // Assert
        assertEquals(fundAmount,0.0);
    }
    @Test
    public void mortageLenderWithNonZeroFundThenShowAvailableFundAsNonZero(){
        // Arrange
        mortgageLender.setAvailableFund(100000.00);
        // Act
        double fundAmount=mortgageLender.getAvailableFund();

        // Assert
        assertEquals(fundAmount,100000.0);
    }



}
