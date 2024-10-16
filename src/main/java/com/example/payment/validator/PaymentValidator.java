package com.example.payment.validator;

import com.example.payment.model.Payment;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class PaymentValidator {

    public List<String> validate(Payment payment) {
        List<String> errors = new ArrayList<>();

        if (payment.getUserId() == null) {
            errors.add("User ID is required");
        }

        if (payment.getAmount() == null) {
            errors.add("Amount is required");
        } else if (payment.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            errors.add("Amount must be positive");
        }

        if (payment.getSourceOfFunds() == null || payment.getSourceOfFunds().trim().isEmpty()) {
            errors.add("Source of funds is required");
        } else if (!isValidSourceOfFunds(payment.getSourceOfFunds())) {
            errors.add("Invalid source of funds");
        }

        return errors;
    }

    private boolean isValidSourceOfFunds(String sourceOfFunds) {
        // This is a placeholder implementation. In a real application, you would
        // check against a list of valid sources or use an enum.
        return List.of("CREDIT_CARD", "DEBIT_CARD", "BANK_TRANSFER").contains(sourceOfFunds.toUpperCase());
    }
}