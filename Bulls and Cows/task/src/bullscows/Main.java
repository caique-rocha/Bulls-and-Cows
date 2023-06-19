package bullscows;

import java.util.Scanner;
import java.util.Random;

public class Main {

    private static final int SECRET = 9305;
    private static final String ERROR_MSG = """
    Error: can't generate a secret number with a length of 11 because there aren't enough unique digits.
    """;

    private static final String SECRET_NUMBER = """
    The random secret number is %d.
    """;

    private static final String letter = "abcdefghijklmnopqrstuvwxyz";
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Input the length of the secret code:");
        String input = scanner.nextLine();
        int length;

        try {

            length = Integer.parseInt(input);

            if (length > 36 || length == 0) {
                System.out.println(ERROR_MSG);
            } else {

                System.out.println("Input the number of possible symbols in the code:");
                int numberSymbols = scanner.nextInt();
                if (numberSymbols > 36) {
                    System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
                } else {
                    StringBuilder rangeChars = new StringBuilder();
                    if (numberSymbols < length) {
                        System.out.printf("Error: it's not possible to generate a code " +
                                "with a length of %d with %d unique symbols.\n", numberSymbols, length);

                    } else {
                        if (numberSymbols > 11) {
                            rangeChars.append(", a-");
                            rangeChars.append(letter.charAt(numberSymbols - 11));
                        }

                        String secret = generateSecret(length, numberSymbols);
                        System.out.printf("The secret is prepared: %s (0-9%s).\n", "*".repeat(Math.max(0, length)), rangeChars);
                        System.out.println("Okay, let's start a game!");
                        checkNumber(secret);
                    }

                    System.out.println("Congratulations! You guessed the secret code.");
                }
            }

        } catch (Exception e) {
            System.out.printf("Error: \"%s\" isn't a valid number.", input);
        }

    }

    private static void checkNumber(String secret) {

        Scanner scanner = new Scanner(System.in);
        int turn = 1;
        int bulls = 0;
        int cows = 0;

        String[] secretArray = String.valueOf(secret).split("");

        while (bulls < secretArray.length) {

            bulls = 0;
            cows = 0;
            System.out.printf("Turn %d:\n", turn);
            String tryGuess = scanner.nextLine();
            String[] tryGuessArray = String.valueOf(tryGuess).split("");
            for (int i = 0; i < secretArray.length; i++) {

                if (String.valueOf(secret).contains(tryGuessArray[i]) &&
                        !secretArray[i].equals(tryGuessArray[i])) {
                    cows++;
                }

                if (secretArray[i].equals(tryGuessArray[i])) {
                    bulls++;
                }
            }
            turn++;
            printCheck(bulls, cows);
            System.out.println();
        }

    }

    private static void printCheck(int bulls, int cows) {

        String message = "Grade: ";

        if (bulls == 0 && cows == 0) {
             message += "None.";
        } else if (bulls == 0) {
            message += cows + " cow(s).";
        } else if (cows == 0) {
            message += bulls + " bull(s).";
        }  else {
            message += bulls + " bull(s) and " + cows + " cow(s).";
        }

        System.out.print(message);
    }

    /**
     * Gera um número aleatório com base nos nano segundos.
     *
     * @param digits Quantidade de digitos que número devera ter
     * @return Número pseudo aleatório
     */
    private static int generateNumber(int digits) {

        long pseudoRandomNumber = System.nanoTime();

        Random random = new Random();
        int min = (int) Math.pow(10, digits - 1);
        int max = (int) Math.pow(10, digits) - 1;
        String randomNumber = String.valueOf(random.nextInt(max - min + 1) + min);
        if (randomNumber.charAt(0) == 0) { // verifica se o primeiro digíto não é zero
            return generateNumber(digits);
        }

        int i = 0;

        while (i < digits - 1) { // itera pelo número e verifica se não há repetições de digitos
            for (int j = i + 1; j < digits; j++) {
                if (randomNumber.charAt(i) == randomNumber.charAt(j)) {
                    return generateNumber(digits);
                }
            }
            i++;
        }

        return Integer.parseInt(randomNumber);
    }

    private static String generateSecret(int digits, int chars) {


        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < digits; i++) {
            if (Math.random() > 0.5 && chars > 10) {
                sb.append(letter.charAt(random.nextInt(chars - 10)));
            } else {
                sb.append(random.nextInt(10));
            }
        }

        int i = 0;

        while (i < digits - 1) { // itera pelo número e verifica se não há repetições de digitos
            for (int j = i + 1; j < digits; j++) {
                if (sb.charAt(i) == sb.charAt(j)) {
                    return generateSecret(digits, chars);
                }
            }
            i++;
        }

        return sb.toString();
    }

}


