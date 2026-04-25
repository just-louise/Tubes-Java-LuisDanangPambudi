package com.p2p;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;
import com.p2p.domain.*;
import com.p2p.service.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoanServiceTest {

    private static final Logger logger = LogManager.getLogger(LoanServiceTest.class);

    // TC-01
    @Test
    void shouldRejectLoanWhenBorrowerNotVerified() {
        logger.info("TC-01: Menguji penolakan loan jika borrower tidak terverifikasi");
        Borrower borrower = new Borrower(false, 700);
        LoanService loanService = new LoanService();
        BigDecimal amount = BigDecimal.valueOf(1000);

        assertThrows(IllegalArgumentException.class, () -> {
            loanService.createLoan(borrower, amount);
        });
        logger.info("TC-01: PASSED - Exception berhasil dilempar");
    }

    // TC-02
    @Test
    void shouldRejectLoanWhenAmountIsZeroOrNegative() {
        logger.info("TC-02: Menguji penolakan loan jika amount <= 0");
        Borrower borrower = new Borrower(true, 700);
        LoanService loanService = new LoanService();
        BigDecimal amount = BigDecimal.valueOf(0);

        assertThrows(IllegalArgumentException.class, () -> {
            loanService.createLoan(borrower, amount);
        });
        logger.info("TC-02: PASSED - Exception berhasil dilempar untuk amount 0");
    }

    // TC-03
    @Test
    void shouldApproveLoanWhenCreditScoreHigh() {
        logger.info("TC-03: Menguji persetujuan loan jika credit score tinggi");
        Borrower borrower = new Borrower(true, 700);
        LoanService loanService = new LoanService();
        BigDecimal amount = BigDecimal.valueOf(1000);

        Loan loan = loanService.createLoan(borrower, amount);

        assertEquals(Loan.Status.APPROVED, loan.getStatus());
        logger.info("TC-03: PASSED - Status loan: {}", loan.getStatus());
    }

    // TC-04
    @Test
    void shouldRejectLoanWhenCreditScoreLow() {
        logger.info("TC-04: Menguji penolakan loan jika credit score rendah");
        Borrower borrower = new Borrower(true, 500);
        LoanService loanService = new LoanService();
        BigDecimal amount = BigDecimal.valueOf(1000);

        Loan loan = loanService.createLoan(borrower, amount);

        assertEquals(Loan.Status.REJECTED, loan.getStatus());
        logger.info("TC-04: PASSED - Status loan: {}", loan.getStatus());
    }
}