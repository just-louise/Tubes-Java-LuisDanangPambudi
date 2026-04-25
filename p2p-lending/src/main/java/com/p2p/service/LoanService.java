package com.p2p.service;

import com.p2p.domain.*;
import java.math.BigDecimal;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoanService {

    private static final Logger logger = LogManager.getLogger(LoanService.class);

    public Loan createLoan(Borrower borrower, BigDecimal amount) {
        logger.info("createLoan dipanggil - verified: {}, creditScore: {}, amount: {}",
                borrower.isVerified(), borrower.getCreditScore(), amount);

        validateBorrower(borrower);
        validateAmount(amount);

        Loan loan = new Loan();

        if (borrower.getCreditScore() >= 600) {
            loan.approve();
            logger.info("Loan APPROVED - credit score: {}", borrower.getCreditScore());
        } else {
            loan.reject();
            logger.warn("Loan REJECTED - credit score terlalu rendah: {}", borrower.getCreditScore());
        }

        return loan;
    }

    private void validateBorrower(Borrower borrower) {
        if (!borrower.canApplyLoan()) {
            logger.error("Borrower tidak terverifikasi (KYC gagal)");
            throw new IllegalArgumentException("Borrower not verified");
        }
    }

    private void validateAmount(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            logger.error("Amount tidak valid: {}", amount);
            throw new IllegalArgumentException("Amount must be greater than 0");
        }
    }
}