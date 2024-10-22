package model;

import java.util.HashMap;
import java.util.Map;

public class BalanceManager {
    private Map<String, Map<String, Double>> balances;

    public BalanceManager() {
        balances = new HashMap<>();
    }

    public void addExpense(String paidBy, Map<String, Double> splits) {
        for (String user : splits.keySet()) {
            double amount = splits.get(user);
            if (!user.equals(paidBy)) {
                updateBalance(paidBy, user, amount);
            }
        }
    }

    private void updateBalance(String paidBy, String owesTo, double amount) {
        balances.putIfAbsent(owesTo, new HashMap<>());
        balances.putIfAbsent(paidBy, new HashMap<>());

        balances.get(owesTo).put(paidBy, balances.get(owesTo).getOrDefault(paidBy, 0.0) + amount);
        balances.get(paidBy).put(owesTo, balances.get(paidBy).getOrDefault(owesTo, 0.0) - amount);
    }

    public void showBalances() {
        boolean noBalance = true;
        for (String user : balances.keySet()) {
            for (String owesTo : balances.get(user).keySet()) {
                double balance = balances.get(user).get(owesTo);
                if (balance != 0) {
                    noBalance = false;
                    if (balance > 0) {
                        System.out.println(user + " owes " + owesTo + ": " + String.format("%.2f", balance));
                    }
                }
            }
        }
        if (noBalance) {
            System.out.println("No balances");
        }
    }

    public void showBalanceForUser(String userId) {
        if (!balances.containsKey(userId)) {
            System.out.println("No balances");
            return;
        }
        boolean noBalance = true;
        for (String owesTo : balances.get(userId).keySet()) {
            double balance = balances.get(userId).get(owesTo);
            if (balance != 0) {
                noBalance = false;
                if (balance > 0) {
                    System.out.println(userId + " owes " + owesTo + ": " + String.format("%.2f", balance));
                }
            }
        }
        if (noBalance) {
            System.out.println("No balances");
        }
    }
}

