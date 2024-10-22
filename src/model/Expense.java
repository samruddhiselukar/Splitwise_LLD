package model;

import java.util.List;

public class Expense {
    private final String paidBy;
    private final double amount;
    private final List<String> usersInvolved;
    private final String splitType;
    private final List<Double> splitValues;

    public Expense(String paidBy, double amount, List<String> usersInvolved, String splitType, List<Double> splitValues) {
        this.paidBy = paidBy;
        this.amount = amount;
        this.usersInvolved = usersInvolved;
        this.splitType = splitType;
        this.splitValues = splitValues;
    }

    public String getPaidBy() {
        return paidBy;
    }

    public double getAmount() {
        return amount;
    }

    public List<String> getUsersInvolved() {
        return usersInvolved;
    }

    public String getSplitType() {
        return splitType;
    }

    public List<Double> getSplitValues() {
        return splitValues;
    }

    @Override
    public String toString() {
        return "Expense{" +
                "paidBy='" + paidBy + '\'' +
                ", amount=" + amount +
                ", usersInvolved=" + usersInvolved +
                ", splitType='" + splitType + '\'' +
                ", splitValues=" + splitValues +
                '}';
    }
}
