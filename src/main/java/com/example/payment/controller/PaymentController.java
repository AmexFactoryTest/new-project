package com.example.payment.controller;

import com.example.payment.model.Payment;
import com.example.payment.service.PaymentService;
import com.example.payment.validator.PaymentValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private PaymentValidator paymentValidator;

    @PostMapping
    public ResponseEntity<?> createPayment(@Valid @RequestBody Payment payment,
                                           @RequestHeader("Idempotency-Key") String idempotencyKey) {
        List<String> validationErrors = paymentValidator.validate(payment);
        if (!validationErrors.isEmpty()) {
            return ResponseEntity.badRequest().body(validationErrors);
        }

        try {
            Payment createdPayment = paymentService.createPayment(payment, idempotencyKey);
            return ResponseEntity.ok(createdPayment);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing the payment");
        }
    }
}