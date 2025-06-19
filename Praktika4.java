import java.util.*;

public class Task45 {
    public static String toRoman(int num) {
        String[] thousands = {"", "M", "MM", "MMM", "MMMM"};
        String[] hundreds = {"", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"};
        String[] tens = {"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"};
        String[] ones = {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"};

        return thousands[num / 1000] +
               hundreds[(num % 1000) / 100] +
               tens[(num % 100) / 10] +
               ones[num % 10];
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        System.out.println(toRoman(n));
    }
}


import java.util.*;

public class Task46 {
    public static int fromRoman(String s) {
        Map<Character, Integer> romanMap = new HashMap<>();
        romanMap.put('I', 1);
        romanMap.put('V', 5);
        romanMap.put('X', 10);
        romanMap.put('L', 50);
        romanMap.put('C', 100);
        romanMap.put('D', 500);
        romanMap.put('M', 1000);

        int total = 0;
        for (int i = 0; i < s.length(); i++) {
            int value = romanMap.get(s.charAt(i));
            if (i + 1 < s.length() && romanMap.get(s.charAt(i + 1)) > value) {
                total -= value;
            } else {
                total += value;
            }
        }
        return total;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String roman = scanner.nextLine().toUpperCase();
        System.out.println(fromRoman(roman));
    }
}


import java.util.*;

public class Task47 {
    public static List<Integer> snail(int[][] array) {
        List<Integer> result = new ArrayList<>();
        int n = array.length;
        int m = array[0].length;
        int top = 0, bottom = n - 1;
        int left = 0, right = m - 1;

        while (top <= bottom && left <= right) {
            for (int i = left; i <= right; i++)
                result.add(array[top][i]);
            top++;
            for (int i = top; i <= bottom; i++)
                result.add(array[i][right]);
            right--;
            if (top <= bottom) {
                for (int i = right; i >= left; i--)
                    result.add(array[bottom][i]);
                bottom--;
            }
            if (left <= right) {
                for (int i = bottom; i >= top; i--)
                    result.add(array[i][left]);
                left++;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        int[][] array = {{1,2,3},{4,5,6},{7,8,9}};
        System.out.println(snail(array));
        int[][] array2 = {{1,2,3},{8,9,4},{7,6,5}};
        System.out.println(snail(array2));
    }
}


import java.util.*;

public class Task48 {
    public static List<Integer> generateU(int limit) {
        Set<Integer> set = new HashSet<>();
        List<Integer> result = new ArrayList<>();
        int x = 0;
        int y = 2 * x + 1;
        int z = 3 * x + 1;

        for (int i = 0; i < limit; i++) {
            set.add(y);
            set.add(z);
            x++;
            y = 2 * x + 1;
            z = 3 * x + 1;
        }
        result.addAll(set);
        return result;
    }

    public static void main(String[] args) {
        System.out.println(generateU(20));
    }
}


public class Task49 {
    public static long fib(int n) {
        long a = 1, b = 1;
        if (n == 0 || n == 1) return 1;
        for (int i = 2; i <= n; i++) {
            long temp = a + b;
            a = b;
            b = temp;
        }
        return b;
    }

    public static Map<Integer, Integer> countDigitOccurrences(long number) {
        Map<Integer, Integer> counts = new HashMap<>();
        for (int i = 0; i <= 9; i++) counts.put(i, 0);
        String s = String.valueOf(number);
        for (char c : s.toCharArray()) {
            counts.put(c - '0', counts.get(c - '0') + 1);
        }
        return counts;
    }

    public static void main(String[] args) {
        int n = 10; // пример
        long fibNumber = fib(n);
        System.out.println("Фибоначчи номер " + n + ": " + fibNumber);
        Map<Integer, Integer> counts = countDigitOccurrences(fibNumber);
        int maxCount = Collections.max(counts.values());
        List<Integer> maxDigits = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : counts.entrySet()) {
            if (entry.getValue() == maxCount) {
                maxDigits.add(entry.getKey());
            }
        }
        System.out.println("Цифра с максимальным количеством вхождений: " + Collections.min(maxDigits));
        System.out.println("Количество вхождений: " + maxCount);
    }
}