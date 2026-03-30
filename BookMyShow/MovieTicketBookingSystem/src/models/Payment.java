package models;

public class Payment {
    private double basePay;
    private SeatType seatType;

    public Payment(double basePay, SeatType seatType) {
        this.basePay = basePay;
        this.seatType = seatType;
    }

    public double calc() {
        return basePay * seatType.getPriceMultiplier();
    }
}
