package com.pluralsight.calcengine;

import java.time.LocalDate;
import java.util.Scanner;

/**
 * CalcEngine is a calculator which performs the four basic arithmetic operations
 * on any two floating-point values (addition, subtraction, multiplication, division).
 *
 * @author Canaan Matias
 * @version 1.0
 * @since 2020-06-20
 */
public class Main {

    public static void main(String[] args) {

        double[] leftVals = {100.0, 25.0, 225.0, 11.0};
        double[] rightVals = {50.0, 92.0, 17.0, 11.0};
        /* Valid opCodes include:
         * 'a' = add
         * 's' = subtract
         * 'm' = multiply
         * 'd' = divide
         */
        char[] opCodes = {'d', 'a', 's', 'm'};
        double[] results = new double[opCodes.length];

        if (args.length == 0) {
            for (int i = 0; i < opCodes.length; i++) {
                results[i] = execute(opCodes[i], leftVals[i], rightVals[i]);
            }

            for (double curResult : results) {
                System.out.println(curResult);
            }
        } else if (args.length == 1 && args[0].equalsIgnoreCase("interactive")) {
            interactUser();
        }
        else if (args.length == 3) {
            handleCommandLine(args);
        } else {
            System.out.println("Please provide an operation code and 2 numeric values");
        }
        System.out.println("*** END OF PROGRAM ***");
    }

    static void interactUser() {
        System.out.println("Enter an operation and two numbers:");
        Scanner scanner = new Scanner(System.in);
        String userInput = scanner.nextLine();
        String[] parts = userInput.toLowerCase().split(" ");
        performOperation(parts);
    }

    private static void performOperation(String[] parts) {
        char opCode = opCodeFromString(parts[0]);

        if (opCode == 'w') {
            handleWhen(parts);
        } else {
            double leftVal = valueFromWord(parts[1]);
            double rightVal = valueFromWord(parts[2]);
            double result = execute(opCode, leftVal, rightVal);
            displayResult(opCode, leftVal, rightVal, result);
        }
    }

    private static void handleWhen(String[] parts) {
        LocalDate startDate = LocalDate.parse(parts[1]);
        long daysToAdd = Long.parseLong(parts[2]);
        LocalDate newDate = startDate.plusDays(daysToAdd);
        String output = String.format("%s plus %d days is %s", startDate, daysToAdd, newDate);
        System.out.println(output);
    }

    private static void displayResult(char opCode, double leftVal, double rightVal, double result) {
        char symbol = symbolFromOpCode(opCode);
//        StringBuilder sb = new StringBuilder(20);
//
//        sb.append(leftVal);
//        sb.append(" ");
//        sb.append(symbol);
//        sb.append(" ");
//        sb.append(rightVal);
//        sb.append(" = ");
//        sb.append(result);
//
//        String equation = sb.toString();
        String equation = String.format("%.3f %c %.3f = %.3f", leftVal, symbol, rightVal, result);
        System.out.println(equation);
    }

    static char symbolFromOpCode(char opCode) {
        char symbol = ' ';
        char[] opCodes = {'a', 's', 'd', 'm'};
        char[] symbols = {'+', '-', '/', '*'};

        for (int i = 0; i < opCodes.length; i++) {
            if (opCode == opCodes[i]) {
                symbol = symbols[i];
                break;
            }
        }
        return symbol;
    }

    /**
     * Performs a calculation using the arguments provided in the command line
     * @param args an array of Strings holding the command line arguments (must be length 3)
     */
    private static void handleCommandLine(String[] args) {
        char opCode = args[0].charAt(0);
        double leftVal = Double.parseDouble(args[1]);
        double rightVal = Double.parseDouble(args[2]);
        double result = execute(opCode, leftVal, rightVal);
        System.out.println(result);
    }

    /**
     * Performs a calculation using two numbers specified by opCode
     * @param leftVal an integer value to be placed on the left side of a mathematical expression
     * @param rightVal an integer value to be placed on the right side of a mathematical expression
     * @param opCode the operation to be performed
     * @return the result of the calculation as a double
     */
    static double execute(char opCode, double leftVal, double rightVal) {
        double result;

        switch (opCode) {
            case 'a' -> result = leftVal + rightVal;
            case 's' -> result = leftVal - rightVal;
            case 'm' -> result = leftVal * rightVal;
            case 'd' -> result = rightVal != 0 ? leftVal / rightVal : 0.0;
            default -> {
                System.out.println("Invalid opCode: " + opCode);
                result = 0.0;
            }
        }
        return result;
    }

    static char opCodeFromString(String operationName) {
        return operationName.toLowerCase().charAt(0);
    }

    static double valueFromWord(String word) {
        String[] numberWords = {
                "zero", "one", "two", "three", "four",
                "five", "six", "seven", "eight", "nine", "ten"
        };
        double value = 0.0;
        for (int index = 0; index < numberWords.length; index++) {
            if (word.toLowerCase().equals(numberWords[index])) {
                value = index;
                break;
            }
        }
        return value;
    }
}
