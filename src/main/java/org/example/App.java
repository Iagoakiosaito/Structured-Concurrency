package org.example;

import jdk.incubator.concurrent.StructuredTaskScope;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class App 
{
    public static void main( String[] args ) {
        record Response(String mercadoPago, String paypal, String pagSeguro) {}

        // Structured concurrency
        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
            Future<String> mercadoPago = scope.fork(() -> new MercadoPagoService().getPaymentService()); // Here we have a 5 seconds delay
            Future<String> paypal = scope.fork(() -> new PayPalService().forcedException()); // a forced ExecutionException
            Future<String> pagSeguro = scope.fork(() -> new PagSeguroService().getPaymentService());
            System.out.println("Waiting for all tasks to finish in structured concurrency...");
            scope.join();
            Response response = new Response(mercadoPago.resultNow(), paypal.resultNow(), pagSeguro.resultNow());
            System.out.println(response);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // Without structured concurrency
        try (ExecutorService executorService = Executors.newFixedThreadPool(6)) {
            Future<String> mercadoPago = executorService.submit(new MercadoPagoService()::getPaymentService); // Here we have a 5 seconds delay
            Future<String> paypal = executorService.submit(new PayPalService()::forcedException); // a forced ExecutionException
            Future<String> pagSeguro = executorService.submit(new PagSeguroService()::getPaymentService);
            System.out.println("Waiting for all tasks to finish...");
            Response response = new Response(mercadoPago.get(), paypal.get(), pagSeguro.get());
            System.out.println(response);
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
