//import java.text.FieldPosition;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Player{
    public String name;
    private int money;
    private int position; //max 40 positions available, 0-39
    private List<Card> ownedCards;
    private List<Field> ownedProperties;
    private List<Field> mortgagedProperties;

    public Player(String name) {
        this.name = name;
        this.money = 1500; // Initial money amount for each player
        this.position = 0; // Start at position 0 ("Go" space)
        this.ownedCards = new ArrayList<>();
        this.ownedProperties = new ArrayList<>();
        this.mortgagedProperties = new ArrayList<>();
    }

    // Getters and Setters for attributes
    public String getName() {
        return name;
    }
    
    public int getMoney() {
        return money;
    }
    public void setMoney(int money) {
        this.money = money;
    }
    public int getPosition() {
        return position;
    }
    public void setPosition(int position) {
        this.position = position;
    }
    public void addCard(Card card){
        ownedCards.add(card);
    }
    public void removeCard(){
        this.ownedCards.remove(0);
    }
    public List<Card> getOwnedCards() {
        return ownedCards;
    }
    public List<Field> getOwnedProperties() {
        return ownedProperties;
    }
    public void addProperty(Field field){
        ownedProperties.add(field);
    }

    public List<Field> getMortgagedProperties() {
        return mortgagedProperties;
    }


    private int message(PropertyField property, int Min, String message, Player p, Scanner input, Scanner inputString){
        System.out.printf("%s ", p.getName());
        System.out.print(property.getName());
        System.out.print(message);
        int number = input.nextInt();
        while((number <= Min && number != 0) ||  number < 0 || number > p.getMoney()){
            System.out.println("Something went wrong ");
            number = Integer.parseInt(inputString.nextLine());
        }
        return number;
    }

    public void bid(PropertyField property, int nrPlayer, Player p, List<Player> biddingParticipants, Scanner input, Scanner inputString) {
        // Implement logic to bid on the property
        if(property instanceof PropertyField){
            PropertyField prop = (PropertyField) property;
            if(prop.isMortgaged()){
                System.out.println("This property is put under mortage!!");
            }
        }
        else{
            System.out.println("you want to sell field that isnt a property??");
        }
        if(p != null){
            //check if player has buildings on the same colors
            if(property instanceof Street){
                Street s = (Street) property;
                if(s.hasMonopol()){
                    String color = s.getColor();
                    List<Field> props = p.getOwnedProperties();
                    for(int i = 0; i < props.size(); i++){
                        if(props.get(i) instanceof Street){
                            Street hs = (Street) props.get(i);
                            if(hs.getColor() == color){
                                if(!hs.getBuildings().isEmpty()){
                                    System.out.println("YOU HAVE BUILDINGS ON THIS COLOR! YOU HAVE TO SELL THEM BEFORE YOU CAN SELL PROPERTY!!");
                                    return;
                                }
                            }
                        }
                    }
                    
                }
            }
        }
        List<Integer> biddingAmounts = new ArrayList<>();
        biddingParticipants.remove(nrPlayer);
        for(int i = 0 ; i < biddingParticipants.size(); i++){
            biddingAmounts.add(0);
        }
        int minAmount = 0;
        while(biddingParticipants.size() > 0){
            for(int i = 0; i < biddingParticipants.size(); i++){
                int amount = message(property, minAmount, " is getting sold. Do you want to take part in a bidding? Put in 0 if you don't or the amount you're willing to bid ", biddingParticipants.get(i), input, inputString);
                if(amount == 0){
                    biddingParticipants.set(i, null);
                    biddingAmounts.set(i,0);
                }
                else{
                    biddingAmounts.set(i, amount);
                    minAmount = amount;
                }
            }
            biddingParticipants.removeAll(Collections.singleton(null));
            biddingAmounts.removeAll(Collections.singleton(0));
            if(biddingParticipants.size() == 0){
                System.out.println("No one bought :<");
            }
            else if(biddingParticipants.size() == 1){
                if(p == null){
                    biddingParticipants.get(0).buy(property);
                    biddingParticipants.get(0).setMoney(biddingParticipants.get(0).getMoney() - minAmount);
                    biddingParticipants.remove(0);
                }
                else{
                    p.sell(property, biddingAmounts.get(0));
                    biddingParticipants.get(0).buy(property);
                    biddingParticipants.get(0).setMoney(biddingParticipants.get(0).getMoney() - minAmount);
                    biddingParticipants.remove(0);
                }
            }
        }
    }

    public void sell(Field property, int amount) {
        this.money += amount;
        if(property instanceof PropertyField){
            PropertyField prop = (PropertyField) property;
            prop.sell(this, amount);
        }
    }

    public void addBuildings(Scanner input, Scanner inputString){
        if(this.ownedProperties.size() == 0) {
            System.out.print("You have no properties!");
        }
        else{
            System.out.println("This are your properties: ");
            for(int i = 0; i < this.ownedProperties.size(); i++){
                PropertyField field = (PropertyField) this.ownedProperties.get(i);
                System.out.print(i + " " +field.getName() + " ");
            }
            System.out.println("Enter the number of the property you want to build on ");
            int number = input.nextInt();
            while(number >= this.ownedProperties.size()){
                System.out.println("Street number doesnt match");
                number = input.nextInt();
            }
            PropertyField field = (PropertyField) this.ownedProperties.get(number);
            if(field instanceof Street){
                Street street = (Street) field;
                street.build(input, inputString, this);
            }
            else{
                System.out.println("This property isn't a street");
            }
        }
    }

    public void putOffMortage (Scanner input, Scanner inputString){
        if(this.mortgagedProperties.size() == 0) {
            System.out.print("You have no mortaged properties!");
        }
        else{
            System.out.println("This are your properties: ");
            for(int i = 0; i < this.mortgagedProperties.size(); i++){
                PropertyField field = (PropertyField) this.mortgagedProperties.get(i);
                System.out.print(i + field.getName() + " ");
            }

            System.out.println("Enter the number of the property you want to put off mortage");
            int number = input.nextInt();
            while(number >= this.mortgagedProperties.size()){
                System.out.println("Street number doesnt match");
                number = input.nextInt();
            }
            PropertyField field = (PropertyField) this.mortgagedProperties.get(number);
                    field.setMortgaged(false);
                    this.mortgagedProperties.remove(number); 
                    this.money = this.money - (int)((double)(field.getPrice()/2) * (double) 1.1);
        }
    }

    public void putUnderMortage (Scanner input, Scanner inputString){
        if(this.ownedProperties.size() == 0) {
            System.out.print("You have no properties!");
        }
        else{
            System.out.println("This are your properties: ");
            for(int i = 0; i < this.ownedProperties.size(); i++){
                PropertyField field = (PropertyField) this.ownedProperties.get(i);
                System.out.print(i + " " + field.getName() + " ");
            
            }

            System.out.println("Enter the number of the property you want to put under mortage");
            int number = input.nextInt();
            while(number >= this.mortgagedProperties.size()){
                System.out.println("Street number doesnt match");
                number = input.nextInt();
            }
            PropertyField field = (PropertyField) this.ownedProperties.get(number);
            boolean ability = true;
            if(field instanceof Street){
                Street street = (Street) field;
                if(street.hasMonopol()){
                    //checking if player has buldings on the same color
                    for(int j = 0; j < this.ownedProperties.size(); j++){
                        if(this.ownedProperties.get(j) instanceof Street){
                            Street h = (Street)this.ownedProperties.get(j);
                            if(h.getColor() == street.getColor()){
                                boolean builds = false;
                                for(int x = 0; x < 5; x++){
                                    if(h.getBuildings().get(x) != 0){
                                        builds = true;
                                        ability = false;
                                    }
                                }
                                if(builds == true){
                                    System.out.printf("You need to sell the buildings you have on %s before you can mortage this property", h.getName());
                                }
                            }
                        }
                    }
                }
            }
            if(ability){
                this.money = this.money + field.getPrice()/2;
                this.mortgagedProperties.add(field);
                field.setMortgaged(true);

            }
        }
    }

    public void sellProperty(Scanner input, Scanner inputString , int nrPlayer, List<Player> players){
        if(this.ownedProperties.size() == 0) {
            System.out.print("You have no properties!");
        }
        else{
            System.out.println("This are your properties: ");
            for(int i = 0; i < this.ownedProperties.size(); i++){
                PropertyField field = (PropertyField) this.ownedProperties.get(i);
                System.out.print(i + " " + field.getName() + " ");
            }
            System.out.println("Enter the number of the property you want to sell ");
            int number = input.nextInt();
            while(number >= this.ownedProperties.size()){
                System.out.println("Street number doesnt match");
                number = input.nextInt();
            }
            PropertyField field = (PropertyField) this.ownedProperties.get(number);
            bid(field, nrPlayer, this, players, input, inputString);
        }

    }

    public void sellBuildings(Scanner input, Scanner inputString){
        System.out.println("This are your buildings: ");
        List<Street> streetsWithBuildings = new ArrayList<>();
        int q =0;
        for(int i=0; i < this.getOwnedProperties().size(); i++){
            if(this.getOwnedProperties().get(i) instanceof Street){
                Street s = (Street) this.getOwnedProperties().get(i);
                if(!s.getBuildings().isEmpty()){
                    for(int j = 0; j < s.getBuildings().size(); j++){
                        List<Integer> blds = s.getBuildings();
                        int houses = 0;
                        for(int x = 0; x < 4; x++){
                            if(blds.get(x)!= 0){
                                houses++;
                            }
                        }
                        int hotel=0;
                        if(blds.get(4) != 0){
                            hotel++;
                        }
                        if(hotel != 0 || houses != 0){
                            streetsWithBuildings.add(s);
                            System.out.printf("%d You have %d houses and %d hotel on %s", i, houses, hotel, s.getName());                                    
                        }
                    }
                }
            }
        }
        if(q == 0){
            System.out.println("You have no buildings?!?!");
        }
        else{
            System.out.println("What's the number of street with the building you want to sell?");

            int check = input.nextInt();

            while(check >= streetsWithBuildings.size()){
                System.out.println("Street number doesnt match");
                check = input.nextInt();
            }

            System.out.println("Do you want to sell houses or hotel? 0 for house and 1 for hotel");
            int number = input.nextInt();
            while(number != 0 || number != 1){
                System.out.println("You need to put in 1 for hotel or 0 for house");
                number = input.nextInt();
            }
            if(number == 0){
                //sell house
                int qnt = 0;
                for(int j = 0; j < 4; j++){
                    if(streetsWithBuildings.get(check).getBuildings().get(j) != 0){
                        qnt++;
                    }
                }
                System.out.printf("How many houses you want to sell? You can sell up to %d", qnt);
                int qntAsked = input.nextInt();
                while(qntAsked >= 0 || qntAsked <= qnt){
                System.out.printf("You can sell up to %d!", qnt);
                    qntAsked = input.nextInt();
                }
                for(int j = 0; j < 4; j++){
                    if(streetsWithBuildings.get(check).getBuildings().get(j) != 0 && qnt > 0){
                        streetsWithBuildings.get(check).setBuildings(j, 0);
                        qnt--;
                    }
                }
                this.money = this.money + (1/2 * qntAsked * streetsWithBuildings.get(check).getHousePrice());
                System.out.println("Houses sold :>");
            }
            else{
                //sell hotel
                if(streetsWithBuildings.get(check).getBuildings().get(4) == 0){
                    System.out.println("You have no hotels on this street...");
                }
                else{
                    streetsWithBuildings.get(check).setBuildings(4,0);
                    this.money = this.money + (1/2 * streetsWithBuildings.get(check).getHotelPrice());
                    System.out.println("Hotel sold :>");
                }

            }
        }

    }

    public void move(int steps) {
        // Implement logic to move the player by the given number of steps
        if(position + steps >= 40){
            position = (position + steps) - 39;
            money += 200;
        }
        else{
            position += steps;
        }
    }

    public void buy(Field field) {
        // Implement logic to buy the given property
        if(field instanceof ActionField){
            System.out.println("You can't buy action field...");
        }
        else{
            PropertyField propertyField = (PropertyField) field;
            propertyField.buyField(this);
        }
    }
}