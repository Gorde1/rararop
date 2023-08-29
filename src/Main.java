import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public Main() {
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите выражение");
        String expression = scanner.nextLine();
        String regex = "(\\d+)\\s*([-+*/])\\s*(\\d+)|([IVXLCDM]+)\\s*([-+*/])\\s*([IVXLCDM]+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(expression);
        if (matcher.find()) {
            int firstNumber;
            int secondNumber;
            char operator;
            if (matcher.group(1) != null) {
                firstNumber = Integer.parseInt(matcher.group(1));
                operator = matcher.group(2).charAt(0);
                secondNumber = Integer.parseInt(matcher.group(3));
            } else {
                if (matcher.group(4) == null) {
                    System.out.println("Ошибка: Неверный формат выражения.");
                    return;
                }

                firstNumber = romanToArabic(matcher.group(4));
                operator = matcher.group(5).charAt(0);
                secondNumber = romanToArabic(matcher.group(6));
            }

            double result;
            if (Character.isDigit(expression.charAt(0))) {
                if (firstNumber < 1 || firstNumber > 10 || secondNumber < 1 || secondNumber > 10) {
                    System.out.println("Ошибка: числа должны быть в диапазоне от 1 до 10.");
                    return;
                }

                result = calculate((double)firstNumber, (double)secondNumber, operator);
                System.out.println("Результат: " + result);
            } else if (Character.isLetter(expression.charAt(0))) {
                result = calculate((double)firstNumber, (double)secondNumber, operator);
                String romanResult = arabicToRoman((int)result);
                System.out.println("Результат: " + romanResult);
            } else {
                System.out.println("Ошибка: некорректный выбор системы!");
            }
        }

    }

    public static double calculate(double num1, double num2, char operator) {
        double result = 0.0;
        switch (operator) {
            case '*':
                result = num1 * num2;
                break;
            case '+':
                result = num1 + num2;
                break;
            case ',':
            case '.':
            default:
                System.out.println("Ошибка: некорректный оператор!");
                System.exit(1);
                break;
            case '-':
                result = num1 - num2;
                break;
            case '/':
                if (num2 == 0.0) {
                    System.out.println("Ошибка: деление на ноль!");
                    System.exit(1);
                }

                result = num1 / num2;
        }

        return result;
    }

    public static int romanToArabic(String roman) {
        HashMap<Character, Integer> romanMap = new HashMap();
        romanMap.put('I', 1);
        romanMap.put('V', 5);
        romanMap.put('X', 10);
        romanMap.put('L', 50);
        romanMap.put('C', 100);
        romanMap.put('D', 500);
        romanMap.put('M', 1000);
        int result = 0;
        int prevValue = 0;

        for(int i = roman.length() - 1; i >= 0; --i) {
            int curValue = (Integer)romanMap.get(roman.charAt(i));
            if (curValue < prevValue) {
                result -= curValue;
            } else {
                result += curValue;
            }

            prevValue = curValue;
        }

        return result;
    }

    public static String arabicToRoman(int arabic) {
        String[] romanNumerals = new String[]{"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        int[] values = new int[]{1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        StringBuilder roman = new StringBuilder();

        for(int i = 0; i < values.length; ++i) {
            while(arabic >= values[i]) {
                arabic -= values[i];
                roman.append(romanNumerals[i]);
            }
        }

        return roman.toString();
    }
}
