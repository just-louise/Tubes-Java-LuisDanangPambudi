package com.p2p;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;
import com.p2p.domain.*;
import com.p2p.service.*;

public class LoanServiceTest {

    // TC-01
    @Test
    void shouldRejectLoanWhenBorrowerNotVerified() {
        Borrower borrower = new Borrower(false, 700);
        LoanService loanService = new LoanService();
        BigDecimal amount = BigDecimal.valueOf(1000);

        assertThrows(IllegalArgumentException.class, () -> {
            loanService.createLoan(borrower, amount);
        });
    }
    // TC-02
@Test
void shouldRejectLoanWhenAmountIsZeroOrNegative() {
    Borrower borrower = new Borrower(true, 700);
    LoanService loanService = new LoanService();
    BigDecimal amount = BigDecimal.valueOf(0);

    assertThrows(IllegalArgumentException.class, () -> {
        loanService.createLoan(borrower, amount);
    });
}

// TC-03
@Test
void shouldApproveLoanWhenCreditScoreHigh() {
    Borrower borrower = new Borrower(true, 700);
    LoanService loanService = new LoanService();
    BigDecimal amount = BigDecimal.valueOf(1000);

    Loan loan = loanService.createLoan(borrower, amount);

    assertEquals(Loan.Status.APPROVED, loan.getStatus());
}

// TC-04
@Test
void shouldRejectLoanWhenCreditScoreLow() {
    Borrower borrower = new Borrower(true, 500);
    LoanService loanService = new LoanService();
    BigDecimal amount = BigDecimal.valueOf(1000);

    Loan loan = loanService.createLoan(borrower, amount);

    assertEquals(Loan.Status.REJECTED, loan.getStatus());
}
}