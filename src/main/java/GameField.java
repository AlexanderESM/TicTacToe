import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GameField {

    int [] canvas = {0,0,0,
                     0,0,0,
                     0,0,0};

//прорисовываем поле
     void drawCanvas(){
         int cellNumber=1;
         System.out.println("     |     |     ");
        for (int i = 0; i < canvas.length; i++) {
            if (i!=0){
                if (i%3==0) {
                    System.out.println();
                    System.out.println("_____|_____|_____");
                    System.out.println("     |     |     ");
                }
                else
                    System.out.print("|");
            }

            if (canvas[i]==0) System.out.print("  " + cellNumber + "  ");
            cellNumber++;
            if (canvas[i]==1) System.out.print("  X  ");
            if (canvas[i]==2) System.out.print("  O  ");
        }
        System.out.println();
        System.out.println("     |     |     ");
    }

    //пользователь выбирет номер ячейки (свободной)
    int getNumber(){
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (true){
            try {
                int n = Integer.parseInt(reader.readLine());
                n--;
                if (n >= 0 && n < canvas.length && canvas[n]==0){
                    return n;
                }
                System.out.println("Укажите (введите) номер свободной ячейки");
            } catch (NumberFormatException e) {
                System.out.println("Укажите (введите) номер ячейки");
            } catch (IOException e) {
            }
        }
    }

    //Проверка победителя
    boolean isGameOver(int n){
        // 1 2 3
        // 4 5 6
        // 7 8 9
        //поиск совпадений по горизонтали
        int row = n-n%3; //номер строки - проверяем только её
        if (canvas[row]==canvas[row+1] &&
                canvas[row]==canvas[row+2]) return true;
        //поиск совпадений по вертикали
        int column = n%3; //номер столбца - проверяем только его
        if (canvas[column]==canvas[column+3])
            if (canvas[column]==canvas[column+6]) return true;
        //мы здесь, значит, первый поиск не положительного результата
        //если значение n находится на одной из граней - возвращаем false
        if (n%2!=0) return false;
        //проверяем принадлежит ли к левой диагонали значение
        if (n%4==0){
            //проверяем есть ли совпадения на левой диагонали
            if (canvas[0] == canvas[4] &&
                    canvas[0] == canvas[8]) return true;
            if (n!=4) return false;
        }
        return canvas[2] == canvas[4] &&
                canvas[2] == canvas[6];
    }


    //прверка что поле нарисовано
    boolean isDraw() {
        for (int n : canvas) if (n==0) return false;
        return true;
    }
    //игра
    void game() throws IOException {
        int stepNum=0;
        boolean bool;
        boolean isCurrentX = false;//!!!

        //добавим в наименование файла дату и время для уникальности имени файла
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.mm.yyyy HH_mm_ss");// установить формат даты
        String myFileName = "Game_"+dateFormat.format(new Date())+"_.xml";

         //для ввода имени игрока
        ConsoleReader scann = new ConsoleReader();

/*
//для теста
         Player playerOne = new Player(1, "Филип Джей Фрай","X",0 );
         Player playerTwo = new Player(2, "Бе́ндер Сгибальщик Родри́гес","O",0);
*/
        //создаём игроков

        Player playerOne = new Player(1, scann.setName(),"X",0 );
        Player playerTwo = new Player(2, scann.setName(),"O",0);

        //создали документ
        Document doc = new Document();

        //создаём корневой элемент с пространством имён
        doc.setRootElement(new Element("Gameplay"));


        Element player = new Element("Player");
        player.setAttribute("id", String.valueOf( playerOne.getId() ) );
        player.setAttribute("name",playerOne.getName()  );
        player.setAttribute("symbol",playerOne.getSymbol() );


        Element player2 = new Element("Player");
        player2.setAttribute("id", String.valueOf( playerTwo.getId() ) );
        player2.setAttribute("name",playerTwo.getName()  );
        player2.setAttribute("symbol",playerTwo.getSymbol() );


        //Element game = new Element("Game");


         doc.getRootElement().addContent(player);
         doc.getRootElement().addContent(player2);


        do {
            isCurrentX = !isCurrentX;
            drawCanvas();

            //System.out.print("Ход игрока "+ +"(" + (isCurrentX ? "X" : "O")+") :"  );

            if (!isCurrentX) {
                System.out.println("Ход игрок "+playerTwo.getName() +" символ "+(isCurrentX ? "X" : "O"));
            }
            else
                System.out.println("Ход игрок "+playerOne.getName()+" символ "+(isCurrentX ? "X" : "O")  );

            System.out.print("Укажите (введите) номер свободной ячейки:");

            //Номер шага общий для всех
            stepNum++;

            //игрок вводит номер ячейки
            int n = getNumber();

            canvas[n] = isCurrentX ? 1 : 2;

            //если это 1й игро (х)
            if (isCurrentX){

                //номер шана и выбранное значение записываем как есть без красоты т.е. начинаем с 0 (ноля)
                playerOne.setStepNumber(stepNum);

                //добавляем в xml
                Element stepNumber = new Element("Stepnum");
                stepNumber.setAttribute("Stepnum",String.valueOf(stepNum)  );
                stepNumber.setAttribute("playerId",String.valueOf(playerOne.getId())  );
                stepNumber.setAttribute("cell",String.valueOf(n)  );
                doc.getRootElement().addContent(stepNumber);

            }
            //если это 2й игрок (О)
            else{
              //  System.out.println(">"+isCurrentX);

                //номер шана и выбранное значение записываем как есть без красоты т.е. начинаем с 0 (ноля)
                playerTwo.setStepNumber(stepNum);

                //добавляем в xml
                Element stepNumber = new Element("Stepnum");
                stepNumber.setAttribute("Stepnum",String.valueOf(stepNum)  );
                stepNumber.setAttribute("playerId",String.valueOf(playerTwo.getId())  );
                stepNumber.setAttribute("cell",String.valueOf(n)  );
                doc.getRootElement().addContent(stepNumber);

            }

           //проверка на результат
            bool = !isGameOver(n);
            if (isDraw()){
                System.out.println("Ничья");

                return;
            }
        } while (bool);
        drawCanvas();
        System.out.println();

        if (!isCurrentX) {
            System.out.println("Победил игрок "+playerTwo.getName());
        }
        else
            System.out.println("Победил игрок "+playerOne.getName());
       // System.out.println("Победил игрок " + (isCurrentX ? "X" : "O") + "!");




       // doc.getRootElement().addContent(game);

        /*
        // вывод на экран
        XMLOutputter xmlOutputter = new XMLOutputter();
        xmlOutputter.output(doc, System.out);
        */

        // Документ JDOM сформирован и готов к записи в файл
        XMLOutputter xmlWriter = new XMLOutputter(Format.getPrettyFormat());

        // сохнаряем в файл
        xmlWriter.output(doc, new FileOutputStream(myFileName));


    }

}
