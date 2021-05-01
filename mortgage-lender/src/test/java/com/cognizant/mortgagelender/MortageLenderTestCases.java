package com.cognizant.mortgagelender;

import com.cognizant.mortgagelender.candidate.Candidate;
import com.cognizant.mortgagelender.exception.NegativeAmountException;
import com.cognizant.mortgagelender.exception.UnqualifiedLoanException;
import com.cognizant.mortgagelender.loan.LoanApproval;
import com.cognizant.mortgagelender.loan.LoanOffer;
import com.cognizant.mortgagelender.loan.LoanRequest;
import com.cognizant.mortgagelender.loan.LoanResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MortageLenderTestCases {

    MortgageLender mortgageLender;

    @BeforeEach
    public void setUp() {
        mortgageLender = new MortgageLender();
    }

    //    When I check my available funds
//    Then I should see how much funds I currently have
    @Test
    public void mortageLenderWithZeroFundThenShowAvailableFundAsZero() throws NegativeAmountException {
        // Arrange
        mortgageLender.setAvailableFund(0.00);
        // Act
        double fundAmount = mortgageLender.getAvailableFund();

        // Assert
        assertEquals(fundAmount, 0.0);
    }

    @Test
    public void mortageLenderWithNonZeroFundThenShowAvailableFundAsNonZero() throws NegativeAmountException {
        // Arrange
        mortgageLender.setAvailableFund(100000.00);
        // Act
        double fundAmount = mortgageLender.getAvailableFund();

        // Assert
        assertEquals(fundAmount, 100000.0);
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
        double totalFundAmount = mortgageLender.getAvailableFund();

        // Assert
        assertEquals(totalFundAmount, 1000.00);
    }

    @Test
    public void mortageLenderDepositNonZeroAmountOneTimeThenAvailableFundUpdated() throws NegativeAmountException {
        // Arrange
        mortgageLender.setAvailableFund(100000.00);

        // Act
        mortgageLender.depositFund(200000.00);
        double totalFundAmount = mortgageLender.getAvailableFund();

        // Assert
        assertEquals(totalFundAmount, 300000.00);
    }

    @Test
    public void mortageLenderDepositNonZeroAmountTwiceThenAvailableFundUpdated() throws NegativeAmountException {
        // Arrange
        mortgageLender.setAvailableFund(100000.00);

        // Act
        mortgageLender.depositFund(200000.00);
        mortgageLender.depositFund(300000.00);
        double totalFundAmount = mortgageLender.getAvailableFund();

        // Assert
        assertEquals(totalFundAmount, 600000.00);
    }

    @Test
    public void mortageLenderDepositNegativeAmountThenNegativeNumberException() throws NegativeAmountException {
        // Arrange

        mortgageLender.setAvailableFund(100000.00);

        // Act and Assert
        assertThrows(NegativeAmountException.class, () -> {
            mortgageLender.depositFund(-200000.00);
        });
        double fundAmount = mortgageLender.getAvailableFund();
        assertEquals(fundAmount, 100000.00);
    }

    /* As a lender, I want to accept and qualify loan applications, so that I can ensure I get my money back.
Rule: To qualify for the full amount, candidates must have debt-to-income (DTI) less than 36%, credit score above 620
and savings worth 25% of requested loan amount.
Rule: To partially qualify, candidates must still meet the same dti and credit score thresholds.
The loan amount for partial qualified applications is four times the applicant's savings.
Given a loan applicant with <dti>, <credit_score>, and <savings>
When they apply for a loan with <requested_amount>
Then their qualification is <qualification>
And their loan amount is <loan_amount>
And their loan status is <status>
*/
    @Test
    public void testCandidateWithLowDtiHighCreditScoreHighSavingsThenLoanStatusIsQualifiedAndQualificationIsQualified() {
        // Arrange
        Candidate candidate = new Candidate("ID1", 21, 750, 100000);
        LoanRequest goodLoanRequest = new LoanRequest("CR1", candidate, 250000);
        // Act
        LoanResponse goodCandidateResponse = mortgageLender.acceptAndQualify(goodLoanRequest);
        // Assert
        assertEquals(goodCandidateResponse.getCandidate().getCandidateId(), "ID1");
        assertEquals(goodCandidateResponse.getQualification(), "qualified");
        assertEquals(goodCandidateResponse.getLoanResponseStatus(), "qualified");
        assertEquals(goodCandidateResponse.getLoanAmount(), 250000);
    }

    @Test
    public void testCandidateWithHighDtiLowCreditScoreLowSavingsThenLoanStatusIsDeniedAndQualificationIsNonQualified() {
        // Arrange
        Candidate candidate = new Candidate("ID2", 37, 600, 10000);
        LoanRequest poorLoanRequest = new LoanRequest("CR2", candidate, 250000);
        // Act
        LoanResponse poorCandidateResponse = mortgageLender.acceptAndQualify(poorLoanRequest);
        // Assert
        assertEquals(poorCandidateResponse.getCandidate().getCandidateId(), "ID2");
        assertEquals(poorCandidateResponse.getQualification(), "not qualified");
        assertEquals(poorCandidateResponse.getLoanResponseStatus(), "denied");
        assertEquals(poorCandidateResponse.getLoanAmount(), 0);
    }

    @Test
    public void testCandidateWithHighDtiHighCreditScoreHighSavingsThenLoanStatusIsDeniedAndQualificationIsNonQualified() {
        // Arrange
        Candidate candidate = new Candidate("ID3", 37, 700, 100000);
        LoanRequest poorLoanRequest = new LoanRequest("CR3", candidate, 250000);
        // Act
        LoanResponse poorCandidateResponse = mortgageLender.acceptAndQualify(poorLoanRequest);
        // Assert
        assertEquals(poorCandidateResponse.getCandidate().getCandidateId(), "ID3");
        assertEquals(poorCandidateResponse.getQualification(), "not qualified");
        assertEquals(poorCandidateResponse.getLoanResponseStatus(), "denied");
        assertEquals(poorCandidateResponse.getLoanAmount(), 0);
    }

    @Test
    public void testCandidateWithLowDtiLowCreditScoreHighSavingsThenLoanStatusIsDeniedAndQualificationIsNonQualified() {
        // Arrange
        Candidate candidate = new Candidate("ID4", 30, 500, 100000);
        LoanRequest poorLoanRequest = new LoanRequest("CR4", candidate, 250000);
        // Act
        LoanResponse poorCandidateResponse = mortgageLender.acceptAndQualify(poorLoanRequest);
        // Assert
        assertEquals(poorCandidateResponse.getCandidate().getCandidateId(), "ID4");
        assertEquals(poorCandidateResponse.getQualification(), "not qualified");
        assertEquals(poorCandidateResponse.getLoanResponseStatus(), "denied");
        assertEquals(poorCandidateResponse.getLoanAmount(), 0);
    }

    @Test
    public void testCandidateWithLowDtiHighCreditScoreLowSavingsThenLoanStatusIsDeniedAndQualificationIsNonQualified() {
        // Arrange
        Candidate candidate = new Candidate("ID5", 21, 700, 1000);
        LoanRequest poorLoanRequest = new LoanRequest("CR5", candidate, 250000);
        // Act
        LoanResponse poorCandidateResponse = mortgageLender.acceptAndQualify(poorLoanRequest);
        // Assert
        assertEquals(poorCandidateResponse.getCandidate().getCandidateId(), "ID5");
        assertEquals(poorCandidateResponse.getQualification(), "partially qualified");
        assertEquals(poorCandidateResponse.getLoanResponseStatus(), "qualified");
        assertEquals(poorCandidateResponse.getLoanAmount(), 4000);
    }

    // Test case TBD: if candidate has two loan request, where only one is approved.
//    @Test
//    public void testCandidateWithLowDtiHighCreditScoreHighSavingsForOneLoanApplicationWhenReqeustTwoLoansThenOneRequestApprovedOtherDenied() {
//        // Arrange
//        Candidate candidate = new Candidate("ID6", 21, 750,100000);
//        LoanRequest LoanRequest1 = new LoanRequest("CR6", candidate, 250000);
//        LoanRequest LoanRequest2 = new LoanRequest("CR7", candidate, 200000);
//        // Act
//        LoanResponse candidateResponse1 = mortgageLender.acceptAndQualify(LoanRequest1);
//        LoanResponse candidateResponse2 = mortgageLender.acceptAndQualify(LoanRequest2);
//        // Assert
//        assertEquals(candidateResponse1.getCandidate().getCandidateId(), "ID6");
//        assertEquals(candidateResponse1.getLoanRequest().getLoanRequestId(), "CR6");
//        assertEquals(candidateResponse1.getQualification(), "qualified");
//        assertEquals(candidateResponse1.getStatus(), "qualified");
//        assertEquals(candidateResponse1.getLoanAmount(), 250000);
//        assertEquals(candidateResponse2.getCandidate().getCandidateId(), "ID6");
//        assertEquals(candidateResponse1.getLoanRequest().getLoanRequestId(), "CR7");
//        assertEquals(candidateResponse2.getQualification(), "not qualified");
//        assertEquals(candidateResponse2.getStatus(), "denied");
//        assertEquals(candidateResponse2.getLoanAmount(), 0);
//    }


    // Test Case TBD : What if loan is requested but no available funds.
    @Test
    public void testWhenLenderHaveAvailableFundThenLoanStatusIsApprovedForQualifiedResponse() throws NegativeAmountException, UnqualifiedLoanException, CloneNotSupportedException {
        // Arrange
        mortgageLender.setAvailableFund(500000);
        Candidate candidate = new Candidate("ID5", 21, 700, 100000);
        LoanRequest goodLoanRequest = new LoanRequest("CR5", candidate, 250000);
        // Act
        LoanResponse goodCandidateResponse = mortgageLender.acceptAndQualify(goodLoanRequest);
        LoanApproval loanApproval = mortgageLender.approveLoan(goodCandidateResponse);
        // Assert

        assertEquals(loanApproval.getLoanApprovalStatus(), "approved");

    }

    @Test
    public void testWhenLenderHaveAvailableFundThenLoanStatusIsApprovedForPartiallyQualifiedResponse() throws NegativeAmountException, UnqualifiedLoanException, CloneNotSupportedException {
        // Arrange
        mortgageLender.setAvailableFund(500000);
        Candidate candidate = new Candidate("ID5", 21, 700, 1000);
        LoanRequest goodLoanRequest = new LoanRequest("CR5", candidate, 250000);
        // Act
        LoanResponse goodCandidateResponse = mortgageLender.acceptAndQualify(goodLoanRequest);
        LoanApproval loanApproval = mortgageLender.approveLoan(goodCandidateResponse);
        // Assert

        assertEquals(loanApproval.getLoanApprovalStatus(), "approved");

    }

    @Test
    public void testWhenLenderHaveInsufficientAvailableFundThenLoanStatusIsOnHoldForQualifiedResponse() throws NegativeAmountException, UnqualifiedLoanException, CloneNotSupportedException {
        // Arrange
        mortgageLender.setAvailableFund(200000);
        Candidate candidate = new Candidate("ID5", 21, 700, 100000);
        LoanRequest goodLoanRequest = new LoanRequest("CR5", candidate, 250000);
        // Act
        LoanResponse goodCandidateResponse = mortgageLender.acceptAndQualify(goodLoanRequest);
        LoanApproval loanApproval = mortgageLender.approveLoan(goodCandidateResponse);
        // Assert

        assertEquals(loanApproval.getLoanApprovalStatus(), "onhold");

    }

//    @Test
//    public void testWhenLenderHaveAvailableFundForOnlyOneRequestThenOneIsApprovedOtherIsOnHold(){}
//
//    // Assumption - we process loan request in the order we receive.
//
//    // I have - 25 -> 15 -> 10
//    // Request order - 10 (approved), 5 (approve), 20 (hold), 80 (hold), 25(hold)
//
//    @Test
//    public void testWhenLenderHaveAvailableFundForTwoRequestsThenTwoAreApprovedInOrderOthersAreOnHold(){}


//    As a lender, I want to keep pending loan amounts in a separate account, so I don't extend too many offers and bankrupt myself.
//
//    Given I have approved a loan
//    Then the requested loan amount is moved from available funds to pending funds
//    And I see the available and pending funds reflect the changes accordingly


    @Test
    public void whenLoanIsApprovedThenPendingFundIncreaseByLoanAmountAndAvailableFundDecreaseByLoanAmount() throws NegativeAmountException, UnqualifiedLoanException, CloneNotSupportedException {
        // Arrange
        mortgageLender.setAvailableFund(250000);
        mortgageLender.setPendingFund(10000);
        Candidate candidate = new Candidate("ID5", 21, 700, 100000);
        LoanRequest goodLoanRequest = new LoanRequest("CR5", candidate, 250000);

        // Act
        LoanResponse goodCandidateResponse = mortgageLender.acceptAndQualify(goodLoanRequest);
        LoanApproval loanApproval = mortgageLender.approveLoan(goodCandidateResponse);
        double availableFund = mortgageLender.getAvailableFund();
        double pendingFund = mortgageLender.getPendingFund();

        // Assert

        assertEquals(availableFund, (250000 - loanApproval.getLoanResponse().getLoanAmount()));
        assertEquals(pendingFund, (10000 + loanApproval.getLoanResponse().getLoanAmount()));

    }

    @Test
    public void whenLoanIsDeniedThenUnqualifiedExceptionThrownAndPendingFundNoChangeAndAvailableFundNoChange() throws NegativeAmountException, UnqualifiedLoanException {
        // Arrange
        mortgageLender.setAvailableFund(250000);
        mortgageLender.setPendingFund(10000);
        Candidate candidate = new Candidate("ID5", 51, 500, 100000);
        LoanRequest goodLoanRequest = new LoanRequest("CR5", candidate, 250000);
        LoanResponse goodCandidateResponse = mortgageLender.acceptAndQualify(goodLoanRequest);

        // Act & Assert

        assertThrows(UnqualifiedLoanException.class, () -> {
            mortgageLender.approveLoan(goodCandidateResponse);
        });

        double availableFund = mortgageLender.getAvailableFund();
        double pendingFund = mortgageLender.getPendingFund();

        assertEquals(availableFund, 250000);
        assertEquals(pendingFund, 10000);

    }


//    As a lender, I want to process response for approved loans, so that I can move forward with the loan.
//
//    Given I have an approved loan
//    When the applicant accepts my loan offer
//    Then the loan amount is removed from the pending funds
//    And the loan status is marked as accepted
//
//    Given I have an approved loan
//    When the applicant rejects my loan offer
//    Then the loan amount is moved from the pending funds back to available funds
//    And the loan status is marked as rejected

    @Test
    public void testWhenCustomerAcceptApprovedLoanThenUpdatePendingFundAndMarkApprovedLoanAsAccepted() throws NegativeAmountException, UnqualifiedLoanException, CloneNotSupportedException {
        // Arrange
        mortgageLender.setAvailableFund(250000);
        mortgageLender.setPendingFund(10000);
        Candidate candidate = new Candidate("ID5", 21, 700, 100000);
        LoanRequest goodLoanRequest = new LoanRequest("CR5", candidate, 250000);
        LoanResponse goodCandidateResponse = mortgageLender.acceptAndQualify(goodLoanRequest);
        LoanApproval approval = mortgageLender.approveLoan(goodCandidateResponse);

        // Act
        LoanOffer offer = mortgageLender.processOffer(approval, true);
        double pendingFund = mortgageLender.getPendingFund();

        // Assert
        assertEquals(pendingFund, 10000);
        assertEquals(offer.getLoanOfferStatus(), "accepted");
        assertEquals(offer.getLoanResponse().getLoanApprovalStatus(), "approved");
        assertEquals(offer.getLoanResponse().getLoanResponse().getLoanResponseStatus(), "qualified");
    }

    @Test
    public void testWhenCustomerRejectApprovedLoanThenUpdateAvailableFundAndMarkApprovedLoanAsRejected() throws NegativeAmountException, UnqualifiedLoanException, CloneNotSupportedException {
        // Arrange
        mortgageLender.setAvailableFund(250000);
        mortgageLender.setPendingFund(10000);
        Candidate candidate = new Candidate("ID5", 21, 700, 100000);
        LoanRequest goodLoanRequest = new LoanRequest("CR5", candidate, 250000);
        LoanResponse goodCandidateResponse = mortgageLender.acceptAndQualify(goodLoanRequest);
        LoanApproval approval = mortgageLender.approveLoan(goodCandidateResponse);

        // Act
        LoanOffer offer = mortgageLender.processOffer(approval, false);
        double pendingFund = mortgageLender.getPendingFund();
        double availableFund = mortgageLender.getAvailableFund();

        // Assert
        assertEquals(availableFund, 250000);
        assertEquals(pendingFund, 10000);
        assertEquals(offer.getLoanOfferStatus(), "rejected");
        assertEquals(offer.getLoanResponse().getLoanApprovalStatus(), "rejected");
        assertEquals(offer.getLoanResponse().getLoanResponse().getLoanResponseStatus(), "rejected");
    }

//    As a lender, I want to check if there are any undecided loans, so that I can manage my time and money wisely.
//
//    Rule: approved loans expired in 3 days
//
//    Given there is an approved loan offered more than 3 days ago
//    When I check for expired loans
//    Then the loan amount is move from the pending funds back to available funds
//    And the loan status is marked as expired


    @Test
    public void testWhenApprovedLoanIsOlderThenThreeDaysThenExpireLoanAndUpdateAvailableFund() throws NegativeAmountException, UnqualifiedLoanException, CloneNotSupportedException {

        // Arrange
        mortgageLender.setAvailableFund(250000);
        mortgageLender.setPendingFund(10000);
        Candidate candidate = new Candidate("ID5", 21, 700, 100000);
        LoanRequest goodLoanRequest = new LoanRequest("CR5", candidate, 250000);
        LoanResponse goodCandidateResponse = mortgageLender.acceptAndQualify(goodLoanRequest);
        LoanApproval approval = mortgageLender.approveLoan(goodCandidateResponse);
        // approval.setApprovalDate(LocalDate.now().minus(Period.ofDays(3)));;

        Optional<LoanApproval> found = mortgageLender.getListOfApprovals().stream().filter(obj -> obj.equals(approval)).findFirst();
        if (found.isPresent()) {
            found.get().setApprovalDate(LocalDate.now().minus(Period.ofDays(3)));
        }

        double pendingFundBeforeExpire = mortgageLender.getPendingFund();   // expect 0
        double availableFundBeforeExpire = mortgageLender.getAvailableFund(); //expect 251000


        // Act
        mortgageLender.checkExpiredApprovals();
        double pendingFundAfterExpire = mortgageLender.getPendingFund();
        double availableFundAfterExpire = mortgageLender.getAvailableFund();

        // Assert
        assertEquals(pendingFundBeforeExpire, 0);
        assertEquals(availableFundBeforeExpire, 251000);
        assertEquals(pendingFundAfterExpire, 10000);
        assertEquals(availableFundAfterExpire, 250000);
    }


    @Test
    public void testWhenApprovedLoanIsOlderFiveThreeDaysThenExpireLoanAndUpdateAvailableFund() {

    }

    @Test
    public void testWhenApprovedLoanIsOlderTwoDaysThenNoStatusChangeAndNoChangeInAvailableFund() {

    }
}