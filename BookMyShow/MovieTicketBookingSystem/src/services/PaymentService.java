package services;

public interface PaymentService {
    boolean processPayment(double amount, String userId);
}
