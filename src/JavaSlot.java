import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class JavaSlot {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int bet;
        int payout;
        String[] row;
        String playAgain;

        System.out.println("*************************");
        System.out.println("  Welcome to Java Slots  ");
        System.out.println("Symbols: 🍒 🍉 🍋 🔔 ⭐ ");
        System.out.println("Payouts:");
        System.out.println("3 of a kind: 🍒 x3, 🍉 x4, 🍋 x5, 🔔 x10, ⭐ x20");
        System.out.println("2 of a kind: 🍒 x2, 🍉 x3, 🍋 x4, 🔔 x5, ⭐ x10");
        System.out.println("*************************");

        // --- Starting Balance Validation ---
        int balance = 0;
        while (true) {
            System.out.print("Please enter your starting amount: ");
            try {
                balance = input.nextInt();
                input.nextLine(); // consume newline
                if (balance <= 0) {
                    System.out.println("Balance must be greater than 0. Try again.");
                } else {
                    break; // valid balance
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                input.nextLine(); // clear invalid input
            }
        }

        Random random = new Random();

        while(balance > 0){
            System.out.println("Current balance: $" + balance );

            while(true){
                System.out.print("Enter your bet: ");
                try {
                    bet = input.nextInt();
                    input.nextLine(); // consume newline

                    if(bet > balance) System.out.println("Insufficient funds. Try again.");
                    else if(bet <= 0) System.out.println("Bets must be greater than zero.");
                    else break; // valid bet

                } catch(InputMismatchException e){
                    System.out.println("Invalid input. Please enter a number.");
                    input.nextLine(); // clear invalid input
                }
            }

            balance -= bet;
            System.out.println("Spinning...");
            row = spinRow();
            printRow(row);

            //Small jackpot chance
            if(random.nextInt(1000) == 0){ // 0.1% chance
                payout = (balance + bet) * 10;
                System.out.println("JACKPOT!!! You won $" + payout);
                balance += payout;
            } else {
                payout = getPayout(row, bet);
                if(payout > 0) {
                    System.out.println("You won $" + payout);
                    balance += payout;
                } else {
                    System.out.println("Sorry, you lost this round");
                }
            }

            System.out.print("Do you want to play again? (Y/N): ");
            playAgain = input.nextLine().toUpperCase();

            if(!playAgain.equals("Y")) {
                break;
            }
        }

        System.out.println("Game Over! Your final balance is $" + balance);
        input.close();
    }


    static String[] spinRow(){
        String[] symbols = {"🍒", "🍉", "🍋", "🔔", "⭐"};
        String[] row = new String[3];
        Random random = new Random();
        for(int i = 0; i < 3; i++){
            row[i] = symbols[random.nextInt(symbols.length)];
        }
        return row;
    }

    static void printRow(String[] row) {
        System.out.println("**************");
        System.out.println(" "+ String.join(" | ", row));
        System.out.println("**************");
    }

    static int getPayout(String[] row, int bet){
        if(row[0].equals(row[1]) && row[1].equals(row[2])){
            return switch(row[0]) {
                case "🍒" -> bet * 3;
                case "🍉" -> bet * 4;
                case "🍋" -> bet * 5;
                case "🔔" -> bet * 10;
                case "⭐" -> bet * 20;
                default -> 0;
            };
        }
        else if(row[0].equals(row[1])){
            return switch(row[0]) {
                case "🍒" -> bet * 2;
                case "🍉" -> bet * 3;
                case "🍋" -> bet * 4;
                case "🔔" -> bet * 5;
                case "⭐" -> bet * 10;
                default -> 0;
            };
        }
        else if(row[1].equals(row[2])){
            return switch(row[1]) {
                case "🍒" -> bet * 2;
                case "🍉" -> bet * 3;
                case "🍋" -> bet * 4;
                case "🔔" -> bet * 5;
                case "⭐" -> bet * 10;
                default -> 0;
            };
        }
        return 0;
    }
}
