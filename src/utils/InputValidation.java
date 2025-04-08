package utils;

import java.util.InputMismatchException;
import java.util.Scanner;

public class InputValidation {
    public static int validateIntBetween(Scanner sc, String message, int min, int max) {
        int value = 0;

        while (true) {
            System.out.print(message);
            try{
                value = sc.nextInt();
                sc.nextLine();

                if (value >= min && value <= max) {
                    return value;
                }

                System.out.println("Introduza um número inteiro entre " + min + " e " + max);

            }catch (InputMismatchException e){
                System.out.println("Introduza um número inteiro entre " + min + " e " + max);
                sc.nextLine();
            }
        }
    }

    public static double validateDouble(Scanner sc, String message) {
        double value;

        while (true) {
            System.out.print(message);
            try{
                value = sc.nextDouble();
                sc.nextLine();
                return value;

            }catch (InputMismatchException e){
                System.out.println("Introduza um número válido. ");
                sc.nextLine();
            }
        }
    }

    public static int validateIntGT0(Scanner sc, String message) {
        int value;

        while (true) {
            System.out.print(message);
            try{
                value = sc.nextInt();
                sc.nextLine();

                if (value > 0) {
                    return value;
                }
                System.out.print("Introduza um número inteiro maior que 0: ");
            }catch (InputMismatchException e){
                System.out.print("Introduza um número inteiro maior que 0: ");
                sc.nextLine();
            }
        }
    }

    public static int validateInt(Scanner sc, String message) {
        int value;

        while (true) {
            System.out.print(message);
            try{
                value = sc.nextInt();
                sc.nextLine();

                return value;

            }catch (InputMismatchException e){
                System.out.print("Introduza um número inteiro: ");
                sc.nextLine();
            }
        }
    }

    public static double validateDoubleGE0(Scanner sc, String message) {
        double value;
        while (true) {
            try{
                System.out.print(message);
                value = sc.nextDouble();
                sc.nextLine();

                if (value >= 0) {
                    return value;
                }

                System.out.println("Introduza um número maior ou igual a 0");
            }catch (Exception e){
                System.out.println("Introduza um número maior ou igual a 0");
                sc.nextLine();
            }
        }
    }

    public static int validateNineDigitInteger(Scanner sc, String message) {
        int number;

        while (true) {
            System.out.print(message);
            String input = sc.nextLine().trim();

            if (input.matches("\\d{9}")) {
                try {
                    number = Integer.parseInt(input);
                    return number;
                } catch (NumberFormatException e) {
                    System.out.println("Número inválido. Tente novamente.");
                }
            } else {
                System.out.println("Erro: O número deve ter exatamente 9 dígitos.");
            }
        }
    }

    public static Integer validateOptionalInt(Scanner sc, String message) {
        System.out.print(message);
        String input = sc.nextLine().trim();

        if (input.isEmpty()) return null;

        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Erro: Introduza um número inteiro válido.");
            return validateOptionalInt(sc, message); // recursivo até estar correto ou ENTER
        }
    }

    public static Double validateOptionalDouble(Scanner sc, String message) {
        System.out.print(message);
        String input = sc.nextLine().trim();

        if (input.isEmpty()) return null;

        try {
            return Double.parseDouble(input);
        } catch (NumberFormatException e) {
            System.out.println("Erro: Introduza um número decimal válido.");
            return validateOptionalDouble(sc, message);
        }
    }

    public static String validateOptionalString(Scanner sc, String message) {
        System.out.print(message);
        String input = sc.nextLine().trim();
        return input.isEmpty() ? null : input;
    }

    public static Integer validateOptionalNineDigitInteger(Scanner sc, String message) {
        System.out.print(message);
        String input = sc.nextLine().trim();

        if (input.isEmpty()) return null;

        if (input.matches("\\d{9}")) {
            return Integer.parseInt(input);
        } else {
            System.out.println("Erro: Introduza um número com exatamente 9 dígitos.");
            return validateOptionalNineDigitInteger(sc, message);
        }
    }
}
