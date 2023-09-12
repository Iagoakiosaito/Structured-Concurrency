package org.example;

public class PagSeguroService implements PaymentService {
    public String getPaymentService() throws InterruptedException {
        Thread.sleep((long)(Math.random() * 1000));
        return "PagSeguro";
    }
}
