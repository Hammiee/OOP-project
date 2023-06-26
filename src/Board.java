import java.util.ArrayList;
import java.util.List;

public class Board{
    private List<Field> fields;

    public Board() {
        //board constructor that adds all the fields
        this.fields = new ArrayList<>();
        this.fields.add(new ActionField("Action", "GO!"));
        this.fields.add(new Street("Mediterranean Avenue", "Brown", 60, 2, 10, 30, 90, 160, 250, 50, 50));
        this.fields.add(new ActionField("Action", "Card"));
        this.fields.add(new Street("Baltic Avenue", "Brown", 60, 4, 20, 60, 180, 320, 450, 50, 50));
        this.fields.add(new ActionField("Action", "Income Tax"));
        this.fields.add(new Railroad("Reading Railroad", 200));
        this.fields.add(new Street("Oriental Avenue", "Light Blue", 100, 6, 30, 90, 270, 400, 550, 50, 50));
        this.fields.add(new ActionField("Action", "Card"));
        this.fields.add(new Street("Vermont Avenue", "Light Blue", 100, 6, 30, 90, 270, 400, 550, 50, 50));
        this.fields.add(new Street("Connecticut Avenue", "Light Blue", 120, 8, 40, 100, 300, 450, 600, 50, 50));
        this.fields.add(new ActionField("Action", "Visiting Jail"));
        this.fields.add(new Street("St. Charles Place", "Pink", 140, 10, 50, 150, 450, 625, 750, 100, 100));
        this.fields.add(new Railroad("King Cross Railroad", 200));
        this.fields.add(new Street("States Avenue", "Pink", 140, 10, 50, 150, 450, 625, 750, 100, 100));
        this.fields.add(new Street("Virginia Avenue", "Pink", 160, 12, 60, 180, 500, 700, 900, 100, 100));
        this.fields.add(new Railroad("Pennsylvania Railroad", 200));
        this.fields.add(new Street("St. James Place", "Orange", 180, 14, 70, 200, 550, 750, 950, 100, 100));
        this.fields.add(new ActionField("Action", "Card"));
        this.fields.add(new Street("Tennessee Avenue", "Orange", 180, 14, 70, 200, 550, 750, 950, 100, 100));
        this.fields.add(new Street("New York Avenue", "Orange", 200, 16, 80, 220, 600, 800, 1000, 100, 100));
        this.fields.add(new ActionField("Action", "Free Parking"));
        this.fields.add(new Street("Kentucky Avenue", "Red", 220, 18, 90, 250, 700, 875, 1050, 150, 150));
        this.fields.add(new ActionField("Action", "Card"));
        this.fields.add(new Street("Indiana Avenue", "Red", 220, 18, 90, 250, 700, 875, 1050, 150, 150));
        this.fields.add(new Street("Illinois Avenue", "Red", 240, 20, 100, 300, 750, 925, 1100, 150, 150));
        this.fields.add(new Railroad("B. & O. Railroad", 200));
        this.fields.add(new Street("Atlantic Avenue", "Yellow", 260, 22, 110, 330, 800, 975, 1150, 150, 150));
        this.fields.add(new Street("Ventnor Avenue", "Yellow", 260, 22, 110, 330, 800, 975, 1150, 150, 150));
        this.fields.add(new Railroad("Marylebone Railroad", 200));
        this.fields.add(new Street("Marvin Gardens", "Yellow", 280, 24, 120, 360, 850, 1025, 1200, 150, 150));
        this.fields.add(new ActionField("Action", "Go to Jail"));
        this.fields.add(new Street("Pacific Avenue", "Green", 300, 26, 130, 390, 900, 1100, 1275, 200, 200));
        this.fields.add(new Street("North Carolina Avenue", "Green", 300, 26, 130, 390, 900, 1100, 1275, 200, 200));
        this.fields.add(new ActionField("Action", "Card"));
        this.fields.add(new Street("Pennsylvania Avenue", "Green", 320, 28, 150, 450, 1000, 1200, 1400, 200, 200));
        this.fields.add(new Railroad("Short Line", 200));
        this.fields.add(new ActionField("Action", "Card"));
        this.fields.add(new Street("Park Place", "Dark Blue", 350, 35, 175, 500, 1100, 1300, 1500, 200, 200));
        this.fields.add(new ActionField("Action", "Luxury Tax"));
        this.fields.add(new Street("Boardwalk", "Dark Blue", 400, 50, 200, 600, 1400, 1700, 2000, 200, 200));
        // Initialize and populate the fields of the board
    }

    public Field getField(int position){
        return fields.get(position);
    }

    public void addField(Field field) {
        fields.add(field);
    }

    public int getPosition(Player player) {
        return player.getPosition();
    }
}
