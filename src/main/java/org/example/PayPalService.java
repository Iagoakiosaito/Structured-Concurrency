package org.example;

import java.util.concurrent.ExecutionException;

public class PayPalService implements PaymentService {
    public String getPaymentService() throws InterruptedException {
        Thread.sleep((long)(Math.random() * 1000));
        return "PayPal";
    }

    public String forcedException() throws ExecutionException, InterruptedException {
        Thread.sleep((long)(Math.random() * 1000));
        throw new ExecutionException("Forced exception", new RuntimeException("Forced exception"));
    }
}
