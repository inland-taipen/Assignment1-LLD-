package services;

public class CreditCardPaymentService implements PaymentService {
    @Override
    public boolean processPayment(double amount, String userId) {
        System.out.println("--> Contacting Bank API...");
        System.out.println("--> Processing Credit Card payment of Rs" + amount + " for User ID: " + userId);
        // Under a proper microservice architecture, this simulates REST calls to Stripe/RazorPay
        System.out.println("--> Payment Successful! Transaction verified.");
        return true; 
    }
}
