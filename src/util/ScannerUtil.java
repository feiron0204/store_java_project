package util;

// 우리가 입력을 처리할 때,
// 도움이 되는 메소드를 모아둔 클래스
import java.util.Scanner;

public class ScannerUtil {
    // 1. 입력 시 메세지의 출력방법을 담당하는
    // printMessage(String)
    private static void printMessage(String message) {
        System.out.println(message);
        System.out.print("> ");
    }

    // 2. 기본형 데이터타입 입력에 사용할
    // nextInt(Scanner, String)
    public static int nextInt(Scanner scanner, String message) {
//      printMessage(message);
//      
//      int temp=scanner.nextInt();
        String temp = nextLine(scanner, message);
        while (!validateNumber(temp)) {
            message="숫자를 입력해주세요.";
            temp=nextLine(scanner,message);
        }

        return Integer.parseInt(temp);
//      return temp;
    }
    public static Object nextBasetype(Scanner scanner, String message) {
//      printMessage(message);
//      
//      int temp=scanner.nextInt();
        String temp = nextLine(scanner, message);
        if (!validateNumber(temp)) {
            return temp.charAt(0);
        }

        return Integer.parseInt(temp);
//      return temp;
    }

    // +실수 입력에 사용할 nextDouble(Scanner,String)
    public static double nextDouble(Scanner scanner, String message) {
        printMessage(message);

        double temp = scanner.nextDouble();
        return temp;
    }

    public static double nextDouble(Scanner scanner, String message, final int MIN, final int MAX) {
        double temp = nextDouble(scanner, message);

        while (temp < MIN || temp > MAX) {
            System.out.println("잘못 입력하셨습니다.");
            temp = nextDouble(scanner, message);
        }

        return temp;
    }

    // 3. 범위의 정수 입력에 사용할
    // nextInt(Scanner, String, int, int)
    public static int nextInt(Scanner scanner, String message, final int MIN, final int MAX) {
        
        int temp = (Integer)nextInt(scanner, message);

        while (temp < MIN || temp > MAX) {
            System.out.println("잘못 입력하셨습니다.");
            temp = (Integer)nextInt(scanner, message);
        }

        return temp;
    }

    // 4. 스트링 입력에 사용할
    // nextLine(Scanner, String)
    public static String nextLine(Scanner scanner, String message) {
        printMessage(message);

        String temp = scanner.nextLine();

        if (temp.isEmpty()) {
            temp = scanner.nextLine();
        }
        return temp;
    }

    // 숫자를넣으랬더니 ....
    private static boolean validateNumber(String s) {
        String regEx = new String("^\\d+$");
        // 숫자로만 이루어져있는지 체크
        if (s.matches(regEx)) {
            return true;
        }
        // -숫자로만 이루어져있는지 체크
        regEx = new String("^-\\d+$");
        if (s.matches(regEx)) {
            return true;
        }

        return false;
    }

}
