package org.example;

public class MercadoPagoService implements PaymentService {
    public String getPaymentService() throws InterruptedException {
        Thread.sleep((long)(Math.random() * 1000));
        Thread.sleep(5 * 1000);
        return "MercadoPago";
    }
}
