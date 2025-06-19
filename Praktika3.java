import java.util.Scanner;

public class Task28 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String text = scanner.nextLine();
        int shift = scanner.nextInt();
        String direction = scanner.next();

        if (direction.equalsIgnoreCase("лево")) {
            shift = -shift;
        }

        StringBuilder result = new StringBuilder();
        for (char c : text.toCharArray()) {
            if (Character.isLetter(c)) {
                char base = Character.isUpperCase(c) ? 'A' : 'a';
                c = (char) ((c - base + shift) % 26);
                if (c < 0) c += 26;
                c += base;
            }
            result.append(c);
        }
        System.out.println(result.toString());
    }
}


import java.util.Scanner;

public class Task29 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int k = (n - 1) / 2;
        int first = k * k;
        int second = (k + 1) * (k + 1);
        System.out.println(second - first);
    }
}


import java.util.Scanner;

public class Task30 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int number = scanner.nextInt();

        String s = String.valueOf(number);
        for (int w = 1; w <= s.length(); w++) {
            int sum = 0;
            for (char c : s.toCharArray()) {
                int digit = c - '0';
                sum += Math.pow(digit, w);
            }
            if (sum == number) {
                System.out.println("YES");
                return;
            }
        }
        System.out.println("NO");
    }
}


import java.util.*;

public class Task31 {
    public static void main(String[] args) {
        int[] distances = {50, 55, 57, 58, 60};
        int maxSum = 0;
        for (int i = 0; i < distances.length; i++) {
            for (int j = i + 1; j < distances.length; j++) {
                for (int k = j + 1; k < distances.length; k++) {
                    int sum = distances[i] + distances[j] + distances[k];
                    if (sum > maxSum && sum <= 175) {
                        maxSum = sum;
                    }
                }
            }
        }
        System.out.println(maxSum);
    }
}


import java.math.BigInteger;

public class Task32 {
    public static void main(String[] args) {
        int i = 10;
        BigInteger fib = fibonacci(i);

        String s = fib.toString();
        Map<Character, Integer> counts = new HashMap<>();
        for (char c = '0'; c <= '9'; c++) {
            counts.put(c, 0);
        }
        for (char c : s.toCharArray()) {
            counts.put(c, counts.get(c) + 1);
        }
        int maxCount = Collections.max(counts.values());
        List<Integer> candidates = new ArrayList<>();
        for (Map.Entry<Character, Integer> entry : counts.entrySet()) {
            if (entry.getValue() == maxCount) {
                candidates.add(entry.getKey() - '0');
            }
        }
        System.out.println("Цифра: " + Collections.min(candidates));
        System.out.println("Вхождения: " + maxCount);
    }

    public static BigInteger fibonacci(int n) {
        BigInteger a = BigInteger.ZERO;
        BigInteger b = BigInteger.ONE;
        for (int i = 2; i <= n; i++) {
            BigInteger temp = a.add(b);
            a = b;
            b = temp;
        }
        return b;
    }
}


public class Task33 {
    public static String game(int n) {
        if (n % 2 == 0) {
            return "Да";
        } else {
            return "Нет";
        }
    }
}


public class Task34 {
    public static long tanyaRemaining(long n) {
        boolean tanyaTurn = true;
        while (n > 1) {
            if (n % 2 == 0) {
                n /= 2;
            } else {
                n -= 1;
            }
            tanyaTurn = !tanyaTurn;
        }
        return tanyaTurn ? 0 : n;
    }
}


import java.util.*;

public class Task35 {
    public static void main(String[] args) {
        String input = "56 65 74 100 99 68 86 180 90";
        String[] arr = input.split(" ");
        Arrays.sort(arr, Comparator.comparingInt(s -> sumDigits(s)));
        System.out.println(String.join(" ", arr));
    }

    public static int sumDigits(String s) {
        int sum = 0;
        for (char c : s.toCharArray()) {
            sum += c - '0';
        }
        return sum;
    }
}


public class Task36 {
    public static void main(String[] args) {
        System.out.println("Реализуйте по образцу перебора сдвигов");
    }
}


import java.util.*;

public class Task37 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int attempts = 0;
        int maxAttempts = 20;
        int[] code = {1, 2, 3, 4};
        Random rand = new Random();

        while (attempts < maxAttempts) {
            System.out.println("Введите 4 числа:");
            int[] guess = new int[4];
            for (int i = 0; i < 4; i++) {
                guess[i] = scanner.nextInt();
            }

            int matches = 0;
            for (int g : guess) {
                for (int c : code) {
                    if (g == c) {
                        matches++;
                    }
                }
            }
            System.out.println("Совпадений: " + matches);
            if (matches == 4) {
                System.out.println("Угадали!");
                break;
            }
            attempts++;
        }
        if (attempts == maxAttempts) {
            System.out.println("Не удалось угадать за 20 попыток");
        }
    }
}


import java.util.*;

public class Task38 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int playersCount = scanner.nextInt();
        int startPlayer = scanner.nextInt();

        List<Integer> players = new ArrayList<>();
        for (int i = 1; i <= playersCount; i++) {
            players.add(i);
        }

        List<Integer> result = new ArrayList<>();
        int index = startPlayer - 1;
        while (players.size() > 1) {
            index = (index + 2) % players.size();
            result.add(players.remove(index));
        }
        result.add(players.get(0));

        System.out.println(result);
    }
}


import java.util.*;

public class Task39 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String sentence = scanner.nextLine();
        String[] words = sentence.split(" ");
        for (int i = 0; i < words.length; i++) {
            String w = words[i];
            if (w.length() > 0) {
                words[i] = w.substring(1) + w.charAt(0) + "ауч";
            }
        }
        System.out.println(String.join(" ", words));
    }
