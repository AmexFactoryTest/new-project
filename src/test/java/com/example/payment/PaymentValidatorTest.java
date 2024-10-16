package com.example.payment.validator;

import com.example.payment.model.Payment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PaymentValidatorTest {

    private PaymentValidator validator;

    @BeforeEach
    void setUp() {
        validator = new PaymentValidator();
    }

    @Test
    void testValidPayment() {
        Payment payment = new Payment();
        payment.setUserId(1L);
        payment.setAmount(new BigDecimal("100.00"));
        payment.setSourceOfFunds("CREDIT_CARD");

        List<String> errors = validator.validate(payment);

        assertTrue(errors.isEmpty());
    }

    @Test
    void testMissingUserId() {
        Payment payment = new Payment();
        payment.setAmount(new BigDecimal("100.00"));
        payment.setSourceOfFunds("CREDIT_CARD");

        List<String> errors = validator.validate(payment);

        assertEquals(1, errors.size());
        assertTrue(errors.contains("User ID is required"));
    }

    @Test
    void testNegativeAmount() {
        Payment payment = new Payment();
        payment.setUserId(1L);
        payment.setAmount(new BigDecimal("-100.00"));
        payment.setSourceOfFunds("CREDIT_CARD");

        List<String> errors = validator.validate(payment);

        assertEquals(1, errors.size());
        assertTrue(errors.contains("Amount must be positive"));
    }

    @Test
    void testMissingAmount() {
        Payment payment = new Payment();
        payment.setUserId(1L);
        payment.setSourceOfFunds("CREDIT_CARD");

        List<String> errors = validator.validate(payment);

        assertEquals(1, errors.size());
        assertTrue(errors.contains("Amount is required"));
    }

    @Test
    void testInvalidSourceOfFunds() {
        Payment payment = new Payment();
        payment.setUserId(1L);
        payment.setAmount(new BigDecimal("100.00"));
        payment.setSourceOfFunds("INVALID_SOURCE");

        List<String> errors = validator.validate(payment);

        assertEquals(1, errors.size());
        assertTrue(errors.contains("Invalid source of funds"));
    }

    @Test
    void testMissingSourceOfFunds() {
        Payment payment = new Payment();
        payment.setUserId(1L);
        payment.setAmount(new BigDecimal("100.00"));

        List<String> errors = validator.validate(payment);

        assertEquals(1, errors.size());
        assertTrue(errors.contains("Source of funds is required"));
    }

    @Test
    void testMultipleErrors() {
        Payment payment = new Payment();
        // All fields are missing

        List<String> errors = validator.validate(payment);

        assertEquals(3, errors.size());
        assertTrue(errors.contains("User ID is required"));
        assertTrue(errors.contains("Amount is required"));
        assertTrue(errors.contains("Source of funds is required"));
    }
}