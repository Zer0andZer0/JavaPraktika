import java.util.Scanner;

public class Task1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String str1 = scanner.nextLine();
        String str2 = scanner.nextLine();
        System.out.println(str1.endsWith(str2));
    }
}


import java.util.Scanner;

public class Task2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();
        String properName = name.substring(0,1).toUpperCase() + name.substring(1).toLowerCase();
        System.out.println("Привет, " + properName + "!");
    }
}


import java.util.Scanner;

public class Task3 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        String result = input.replaceAll("(?i)[aeiouаеёиоуыэюя]", "");
        System.out.println(result);
    }
}


import java.util.Scanner;

public class Task4 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int start = scanner.nextInt();
        int end = scanner.nextInt();
        int step = (end >= start) ? 1 : -1;
        int current = start;
        while ((step > 0 && current <= end) || (step < 0 && current >= end)) {
            System.out.print((int)Math.pow(current, 2) + " ");
            current += step;
        }
        System.out.println();
    }
}


import java.util.Scanner;

public class Task5 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int start = scanner.nextInt();
        int step = scanner.nextInt();
        int count = scanner.nextInt();
        for (int i = 0; i < count; i++) {
            System.out.print((start + i * step) + " ");
        }
        System.out.println();
    }
}


import java.util.Scanner;

public class Task6 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[] cell = new int[3];
        boolean[] filled = new boolean[3];

        for (int i = 0; i < 3; i++) {
            cell[i] = Integer.MIN_VALUE;
        }

        while (scanner.hasNextInt()) {
            int num = scanner.nextInt();

            int emptyIndex = -1;
            for (int i = 0; i < 3; i++) {
                if (!filled[i]) {
                    emptyIndex = i;
                    break;
                }
            }

            if (emptyIndex != -1) {
                cell[emptyIndex] = num;
                filled[emptyIndex] = true;
            } else {
                int minIndex = 0;
                for (int i = 1; i < 3; i++) {
                    if (cell[i] < cell[minIndex]) {
                        minIndex = i;
                    }
                }
                cell[minIndex] = num;
            }
        }

        for (int i = 0; i < 3; i++) {
            System.out.print(cell[i] + " ");
        }
        System.out.println();
    }
}


import java.util.*;

public class Task7 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        String[] words = input.split(" ");
        LinkedHashSet<String> uniqueWords = new LinkedHashSet<>(Arrays.asList(words));
        System.out.println(String.join(" ", uniqueWords));
    }
}


import java.util.Scanner;

public class Task8 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        int upperCount = 0, lowerCount = 0;
        for (char c : input.toCharArray()) {
            if (Character.isUpperCase(c))
                upperCount++;
            else if (Character.isLowerCase(c))
                lowerCount++;
        }

        if (upperCount > lowerCount) {
            System.out.println(input.toUpperCase());
        } else if (lowerCount > upperCount) {
            System.out.println(input.toLowerCase());
        } else {
            System.out.println(input.toLowerCase());
        }
    }
}