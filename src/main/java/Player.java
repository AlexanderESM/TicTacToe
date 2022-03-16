
public class Player {
    private int id;
    private String name;
    private String symbol;
    private int stepNumber;

        public Player() {}

    public Player(int id, String name, String symbol, int stepNumber) {
        this.id = id;
        this.name = name;
        this.symbol=symbol;
        this.stepNumber=stepNumber;


    }

    //Имя
    public void setName(String name) { this.name = name; }
    public String getName() { return name; }

    // id
    public void setId(int id) { this.id = id; }
    public int getId() { return id;}

    //символ
    public void setSymbol(String symbol) { this.symbol = symbol; }
    public String getSymbol() { return symbol;}

    //шаг
    public void setStepNumber(int stepNumber) { this.stepNumber = stepNumber; }
    public int getStepNumber() { return stepNumber;}
}
