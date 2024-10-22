import model.BalanceManager;
import model.Expense;
import model.User;
import service.ExpenseService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//SHOW
//SHOW u1
//EXPENSE u1 1000 4 u1 u2 u3 u4 EQUAL
//SHOW u4
//SHOW u1
//EXPENSE u1 1250 2 u2 u3 EXACT 370 880
//SHOW
//EXPENSE u4 1200 4 u1 u2 u3 u4 PERCENT 40 20 20 20
//SHOW u1
//SHOW

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Creating users
        User user1 = new User("u1", "User1", "user1@example.com", "1111111111");
        User user2 = new User("u2", "User2", "user2@example.com", "2222222222");
        User user3 = new User("u3", "User3", "user3@example.com", "3333333333");
        User user4 = new User("u4", "User4", "user4@example.com", "4444444444");

        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        users.add(user3);
        users.add(user4);

        BalanceManager balanceManager = new BalanceManager();
        ExpenseService expenseService = new ExpenseService(balanceManager);

        while (true) {
            String input = scanner.nextLine();
            String[] parts = input.split(" ");
            String command = parts[0];

            if (command.equals("SHOW")) {
                if (parts.length == 1) {
                    balanceManager.showBalances();
                } else {
                    balanceManager.showBalanceForUser(parts[1]);
                }
            } else if (command.equals("EXPENSE")) {
                String paidBy = parts[1];
                double amount = Double.parseDouble(parts[2]);
                int numberOfUsers = Integer.parseInt(parts[3]);

                List<String> involvedUsers = new ArrayList<>();
                for (int i = 0; i < numberOfUsers; i++) {
                    involvedUsers.add(parts[4 + i]);
                }

                String type = parts[4 + numberOfUsers];
                List<Double> values = new ArrayList<>();

                if (type.equals("EXACT") || type.equals("PERCENT")) {
                    for (int i = 0; i < numberOfUsers; i++) {
                        values.add(Double.parseDouble(parts[5 + numberOfUsers + i]));
                    }
                }

                // Create expense object
                Expense expense = new Expense(paidBy, amount, involvedUsers, type, values);

                // Add expense
                expenseService.addExpense(expense);
            }
        }
    }
}
