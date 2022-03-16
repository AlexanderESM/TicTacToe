import java.util.Scanner;

public class ConsoleReader {

    //читает с клавиатуры строку
    public static String readString(){
        Scanner scanner = new Scanner(System.in );
        return scanner.nextLine();
    };

    String setName(){
        String str="";

        Scanner scanner = new Scanner(System.in);
        // строка недолжна быть пустой

        while (str.trim().length() == 0){
            System.out.print("Введите имя:");
            str = scanner.nextLine();
        }

        return str.trim();
    }
}
