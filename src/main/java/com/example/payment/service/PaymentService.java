package com.example.payment.service;

import com.example.payment.model.Payment;

public interface PaymentService {
    Payment createPayment(Payment payment, String idempotencyKey);
}

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private AsyncService asyncService;

    @Override
    @Transactional
    public Payment createPayment(Payment payment, String idempotencyKey) {
        validatePayment(payment);
        checkIdempotency(idempotencyKey);
        
        Payment savedPayment = paymentRepository.save(payment);
        
        // Trigger async enrichment
        asyncService.enrichPaymentData(savedPayment);
        
        return savedPayment;
    }

    private void validatePayment(Payment payment) {
        if (payment.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        if (!isValidSourceOfFunds(payment.getSourceOfFunds())) {
            throw new IllegalArgumentException("Invalid source of funds");
        }
    }

    private boolean isValidSourceOfFunds(String sourceOfFunds) {
        // Implement your validation logic here
        return true; // Placeholder
    }

    private void checkIdempotency(String idempotencyKey) {
        // Implement idempotency check logic here
    }
}