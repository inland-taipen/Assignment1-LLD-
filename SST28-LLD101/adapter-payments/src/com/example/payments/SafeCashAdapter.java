package com.example.payments;

public class SafeCashAdapter implements PaymentGateway {
    private final SafeCashClient client;
    public SafeCashAdapter(SafeCashClient client) {
        this.client = client;
    }
    @Override
    public String charge(String customerId, int amountCents) {
        SafeCashPayment p = client.createPayment(amountCents, customerId);
        return p.confirm();
    }
}