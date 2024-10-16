package com.example.payment.service;

import com.example.payment.model.Payment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AsyncService {

    @Async
    public void enrichPaymentData(Payment payment) {
        // Call external APIs to enrich data
        enrichDemographicData(payment);
        enrichBankData(payment);
    }

    private void enrichDemographicData(Payment payment) {
        // Implement API call for demographic data
    }

    private void enrichBankData(Payment payment) {
        // Implement API call for bank data
    }
}