import java.io.*;
import java.util.*;

public class Calculator {
    private static final String HISTORY_FILE = "history.txt";

    public static void main(String[] args) throws IOException {
        loadHistory();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Введите уравнение или 'история' для просмотра, 'выход' для завершения:");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("выход")) {
                break;
            } else if (input.equalsIgnoreCase("история")) {
                printHistory();
            } else if (!input.isEmpty()) {
                try {
                    double result = evaluateExpression(input);
                    System.out.println("Результат: " + result);
                    saveToHistory(input + " = " + result);
                } catch (Exception e) {
                    System.out.println("Ошибка при вычислении: " + e.getMessage());
                }
            }
        }
    }

    // Загрузка истории из файла
    private static void loadHistory() {
        File file = new File(HISTORY_FILE);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.err.println("Ошибка при создании файла истории.");
            }
        }
    }

    // Вывод истории
    private static void printHistory() {
        try (BufferedReader br = new BufferedReader(new FileReader(HISTORY_FILE))) {
            String line;
            System.out.println("История вычислений:");
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Ошибка при чтении истории.");
        }
    }

    // Сохранение в файл
    private static void saveToHistory(String entry) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(HISTORY_FILE, true))) {
            bw.write(entry);
            bw.newLine();
        } catch (IOException e) {
            System.err.println("Ошибка при сохранении в историю.");
        }
    }

    // Основной метод оценки выражения
    private static double evaluateExpression(String expr) throws Exception {
        // Удаляем пробелы
        String cleanedExpr = expr.replaceAll("\\s+", "");
        // Проверяем правильность скобочной структуры
        if (!isValidParentheses(cleanedExpr))
            throw new Exception("Некорректная структура скобок");
        // Разбиваем на токены
        List<String> tokens = tokenize(cleanedExpr);
        // Обрабатываем токены через рекурсивный парсер
        return parseExpression(tokens, new int[]{0});
    }

    // Проверка сбалансированности скобок
    private static boolean isValidParentheses(String s) {
        int count = 0;
        for (char c : s.toCharArray()) {
            if (c == '(') count++;
            else if (c == ')') count--;
            if (count < 0) return false;
        }
        return count == 0;
    }

    // Токенизация строки
    private static List<String> tokenize(String expr) {
        List<String> tokens = new ArrayList<>();
        Pattern pattern = Pattern.compile(
            "(\\d+\\.\\d+|\\d+|\\|)|([+\\-*/%^()])|(//)|(?<=\\|)(.*?)(?=\\|)");
        Matcher matcher = pattern.matcher(expr);
        int index = 0;
        while (index < expr.length()) {
            if (matcher.find(index) && matcher.start() == index) {
                String match = matcher.group();
                if (match.equals("|")) {
                    tokens.add("|");
                } else if (match.equals("//")) {
                    tokens.add("//");
                } else if (match.matches("[+\\-*/%^()]|")) {
                    tokens.add(match);
                } else if (match.matches("\\d+\\.\\d+|\\d+")) {
                    tokens.add(match);
                } else {
                    // Внутри |...|, добавляем содержимое
                    int endIndex = matcher.end();
                    String inside = expr.substring(index + 1, endIndex - 1);
                    tokens.add("|" + inside + "|");
                    index = endIndex;
                    continue;
                }
                index = matcher.end();
            } else {
                // Не совпадает - пропускаем
                index++;
            }
        }
        return tokens;
    }

    // Парсинг выражения
    private static double parseExpression(List<String> tokens, int[] pos) throws Exception {
        double value = parseAddSubtract(tokens, pos);
        return value;
    }

    // Обработка + и -
    private static double parseAddSubtract(List<String> tokens, int[] pos) throws Exception {
        double value = parseMulDivModulo(tokens, pos);
        while (pos[0] < tokens.size()) {
            String token = tokens.get(pos[0]);
            if (token.equals("+")) {
                pos[0]++;
                value += parseMulDivModulo(tokens, pos);
            } else if (token.equals("-")) {
                pos[0]++;
                value -= parseMulDivModulo(tokens, pos);
            } else {
                break;
            }
        }
        return value;
    }

    // Обработка *, /, %, //, ^
    private static double parseMulDivModulo(List<String> tokens, int[] pos) throws Exception {
        double value = parseUnary(tokens, pos);
        while (pos[0] < tokens.size()) {
            String token = tokens.get(pos[0]);
            if (token.equals("*")) {
                pos[0]++;
                value *= parseUnary(tokens, pos);
            } else if (token.equals("/")) {
                pos[0]++;
                double divisor = parseUnary(tokens, pos);
                if (divisor == 0)
                    throw new Exception("Деление на ноль");
                value /= divisor;
            } else if (token.equals("%")) {
                pos[0]++;
                int a = (int) value;
                int b = (int) parseUnary(tokens, pos);
                if (b == 0)
                    throw new Exception("Деление по модулю на ноль");
                value = a % b;
            } else if (token.equals("//")) {
                pos[0]++;
                int a = (int) value;
                int b = (int) parseUnary(tokens, pos);
                if (b == 0)
                    throw new Exception("Деление без остатка на ноль");
                value = a / b;
            } else if (token.equals("^")) {
                pos[0]++;
                double exponent = parseUnary(tokens, pos);
                value = Math.pow(value, exponent);
            } else {
                break;
            }
        }
        return value;
    }

    // Обработка унарных операторов и скобок
    private static double parseUnary(List<String> tokens, int[] pos) throws Exception {
        if (pos[0] >= tokens.size())
            throw new Exception("Некорректное выражение");
        String token = tokens.get(pos[0]);

        if (token.equals("(")) {
            pos[0]++;
            double val = parseAddSubtract(tokens, pos);
            if (pos[0] >= tokens.size() || !tokens.get(pos[0]).equals(")"))
                throw new Exception("Отсутствует закрывающая скобка");
            pos[0]++;
            return val;
        } else if (token.equals("|")) {
            // Модуль
            pos[0]++;
            double num = parseAddSubtract(tokens, pos);
            if (pos[0] >= tokens.size() || !tokens.get(pos[0]).equals("|"))
                throw new Exception("Некорректный модуль");
            pos[0]++;
            return Math.abs(num);
        } else if (token.matches("-?\\d+(\\.\\d+)?")) {
            pos[0]++;
            return Double.parseDouble(token);
        } else if (token.equals("-")) {
            // Унарный минус
            pos[0]++;
            return -parseUnary(tokens, pos);
        } else {
            throw new Exception("Неизвестный токен: " + token);
        }
    }
}