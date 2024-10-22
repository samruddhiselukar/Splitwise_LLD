package service;

import model.BalanceManager;
import model.Expense;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExpenseService {
    private final BalanceManager balanceManager;

    public ExpenseService(BalanceManager balanceManager) {
        this.balanceManager = balanceManager;
    }

    public void addExpense(Expense expense) {
        Map<String, Double> splits = new HashMap<>();
        switch (expense.getSplitType()) {
            case "EQUAL":
                double equalSplit = Math.round((expense.getAmount() / expense.getUsersInvolved().size()) * 100.0) / 100.0;
                for (String user : expense.getUsersInvolved()) {
                    splits.put(user, equalSplit);
                }
                adjustForRounding(expense.getAmount(), splits, expense.getUsersInvolved());
                break;
            case "EXACT":
                List<Double> exactSplits = expense.getSplitValues();
                for (int i = 0; i < expense.getUsersInvolved().size(); i++) {
                    splits.put(expense.getUsersInvolved().get(i), exactSplits.get(i));
                }
                if (!validateExactSplits(splits, expense.getAmount())) {
                    System.out.println("Invalid exact split amounts.");
                    return;
                }
                break;
            case "PERCENT":
                List<Double> percentSplits = expense.getSplitValues();
                for (int i = 0; i < expense.getUsersInvolved().size(); i++) {
                    splits.put(expense.getUsersInvolved().get(i), Math.round((percentSplits.get(i) * expense.getAmount() / 100.0) * 100.0) / 100.0);
                }
                if (!validatePercentSplits(percentSplits)) {
                    System.out.println("Invalid percentage splits.");
                    return;
                }
                break;
        }
        balanceManager.addExpense(expense.getPaidBy(), splits);
    }

    private boolean validateExactSplits(Map<String, Double> splits, double totalAmount) {
        double sum = 0;
        for (double val : splits.values()) {
            sum += val;
        }
        return Math.abs(sum - totalAmount) < 0.01;
    }

    private boolean validatePercentSplits(List<Double> percents) {
        double sum = 0;
        for (double percent : percents) {
            sum += percent;
        }
        return Math.abs(sum - 100.0) < 0.01;
    }

    private void adjustForRounding(double totalAmount, Map<String, Double> splits, List<String> users) {
        double sum = 0;
        for (double val : splits.values()) {
            sum += val;
        }
        double roundingDiff = Math.round((totalAmount - sum) * 100.0) / 100.0;
        splits.put(users.get(0), splits.get(users.get(0)) + roundingDiff);
    }
}

