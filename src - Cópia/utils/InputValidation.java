package utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class InputValidation {
    // Valida um número inteiro num intervalo (min, max)
    public static int validateIntBetween(Scanner sc, String message, int min, int max) {
        int value;

        while (true) {
            System.out.print(message);
            try{
                value = sc.nextInt();
                sc.nextLine();

                // Verifica se o valor está dentro do intervalo
                if (value >= min && value <= max) {
                    return value;
                }

                // Se o valor não estiver dentro do intervalo, solicita um novo número
                System.out.println("Introduza um número inteiro entre " + min + " e " + max);

            }catch (InputMismatchException e){
                // Caso o valor não seja um inteiro válido
                System.out.println("Introduza um número inteiro entre " + min + " e " + max);
                sc.nextLine();
            }
        }
    }

    // Valida um número inteiro maior que 0
    public static int validateIntGT0(Scanner sc, String message) {
        int value;

        while (true) {
            System.out.print(message);
            try{
                value = sc.nextInt();
                sc.nextLine();

                // Verifica se o valor é maior que 0
                if (value > 0) {
                    return value;
                }
                System.out.print("Introduza um número inteiro maior que 0! ");
            }catch (InputMismatchException e){
                // Caso o valor não seja um inteiro válido
                System.out.print("Introduza um número inteiro maior que 0! ");
                sc.nextLine();
            }
        }
    }

    // Valida um número inteiro genérico
    public static int validateInt(Scanner sc, String message) {
        int value;

        while (true) {
            System.out.print(message);
            try{
                value = sc.nextInt();
                sc.nextLine();
                return value; // Retorna o valor se for um número inteiro válido
            }catch (InputMismatchException e){
                // Caso o valor não seja um inteiro válido
                System.out.print("Introduza um número inteiro: ");
                sc.nextLine();
            }
        }
    }

    // Valida um número inteiro opcional (pode ser deixado em branco)
    public static Integer validateOptionalInt(Scanner sc, String message) {
        System.out.print(message);
        String input = sc.nextLine().trim();

        // Retorna null se o input for vazio
        if (input.isEmpty()) return null;

        try {
            return Integer.parseInt(input); // Tenta converter para inteiro
        } catch (NumberFormatException e) {
            // Se não for um número válido
            System.out.println("Erro: Introduza um número inteiro válido.");
            return validateOptionalInt(sc, message); // recursivo até estar correto ou ENTER
        }
    }

    // Valida um número decimal maior ou igual a 0
    public static double validateDoubleGE0(Scanner sc, String message) {
        double value;
        while (true) {
            try{
                System.out.print(message);
                value = sc.nextDouble();
                sc.nextLine();

                // Verifica se o valor é maior ou igual a 0
                if (value >= 0) {
                    return value;
                }
                System.out.println("Introduza um número maior ou igual a 0");
            }catch (Exception e){
                // Se ocorrer qualquer erro de formato, solicita novamente
                System.out.println("Introduza um número maior ou igual a 0");
                sc.nextLine();
            }
        }
    }

    // Valida um número decimal opcional (pode ser deixado em branco)
    public static Double validateOptionalDouble(Scanner sc, String message) {
        System.out.print(message);
        String input = sc.nextLine().trim();

        // Retorna null se o input for vazio
        if (input.isEmpty()) return null;

        try {
            return Double.parseDouble(input); // Tenta converter para double
        } catch (NumberFormatException e) {
            // Se não for um número válido
            System.out.println("Erro: Introduza um número decimal válido.");
            return validateOptionalDouble(sc, message);
        }
    }

    // Valida uma entrada de texto não vazia
    public static String validateString(Scanner sc, String prompt) {
        String input;
        do {
            System.out.print(prompt);
            input = sc.nextLine().trim(); // Remover espaços antes e depois

            if (input.isEmpty()) {
                System.out.println("Entrada de texto não pode ser vazia! Insira um valor válido.");
            }
        } while (input.isEmpty());  // Repete até o utilizador inserir algo válido

        return input;  // Retorna o valor inserido
    }

    // Valida uma entrada de texto opcional (pode ser deixada em branco)
    public static String validateOptionalString(Scanner sc, String message) {
        System.out.print(message);
        String input = sc.nextLine().trim();
        return input.isEmpty() ? null : input;
    }

    // Valida um número de telefone com exatamente 9 dígitos
    public static int validateNineDigitInteger(Scanner sc, String message) {
        int number;

        while (true) {
            System.out.print(message);
            String input = sc.nextLine().trim();

            // Verifica se o número tem exatamente 9 dígitos
            if (input.matches("\\d{9}")) {
                try {
                    number = Integer.parseInt(input); // Converte para inteiro
                    return number;
                } catch (NumberFormatException e) {
                    System.out.println("Número inválido. Tente novamente.");
                }
            } else {
                System.out.println("Erro: O número deve ter exatamente 9 dígitos.");
            }
        }
    }

    // Valida um número de telefone opcional (pode ser deixado em branco)
    public static Integer validateOptionalNineDigitInteger(Scanner sc, String message) {
        System.out.print(message);
        String input = sc.nextLine().trim();

        // Retorna null se o input for vazio
        if (input.isEmpty()) return null;
        // Verifica se o número tem exatamente 9 dígitos
        if (input.matches("\\d{9}")) {
            return Integer.parseInt(input);
        } else {
            System.out.println("Erro: Introduza um número com exatamente 9 dígitos.");
            return validateOptionalNineDigitInteger(sc, message);
        }
    }

    // Valida um endereço de e-mail
    public static String validateEmail(Scanner sc, String message) {
        while (true) {
            System.out.print(message);
            String input = sc.nextLine().trim();
            // Verifica se o e-mail contém "@" e "."
            if (input.contains("@") && input.contains(".")) {
                return input;
            } else {
                System.out.println("Erro: Introduza um email válido (deve conter '@' e '.').");
            }
        }
    }

    // Valida um e-mail opcional (pode ser deixado em branco)
    public static String validateOptionalEmail(Scanner sc, String message) {
        System.out.print(message);
        String input = sc.nextLine().trim();

        // Retorna null se o input for vazio
        if (input.isEmpty()) return null;

        // Verifica se o e-mail contém "@" e "."
        if (input.contains("@") && input.contains(".")) {
            return input;
        } else {
            System.out.println("Erro: Introduza um email válido (ou deixe em branco).");
            return validateOptionalEmail(sc, message);
        }
    }

    // Valida uma data no formato "dd/MM/yyyy"
    public static LocalDate validateDate(Scanner sc, String message) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        while (true) {
            System.out.print(message);
            String input = sc.nextLine().trim();

            try {
                return LocalDate.parse(input, formatter); // Tenta converter a entrada de texto para uma data
            } catch (DateTimeParseException e) {
                System.out.println("Erro: Introduza a data no formato dd/MM/yyyy.");
            }
        }
    }

    // Valida uma data opcional (pode ser deixada em branco)
    public static LocalDate validateOptionalDate(Scanner sc, String message) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        System.out.print(message);
        String input = sc.nextLine().trim();

        // Retorna null se o input for vazio
        if (input.isEmpty()) return null;

        try {
            return LocalDate.parse(input, formatter);
        } catch (DateTimeParseException e) {
            System.out.println("Erro: Introduza a data no formato dd/MM/yyyy (ou deixe em branco).");
            return validateOptionalDate(sc, message);
        }
    }

}
