import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Game{
    private List<Player> players;
    private List<Card> cards;
    private int turn;
    private Board board;
    private int round;
    public Scanner input;
    public Scanner inputString;
    int playerRemoved = 0;


    public Game() {
        this.players = new ArrayList<>();
        this.cards = new ArrayList<>();
        this.turn = 0;
        this.board = new Board();
        this.round = 0;
        this.input = null;
    }

    public void setScanner(Scanner input){
        this.input = input;
    }

    public void setScannerString(Scanner string){
        this.inputString = string;
    }

    public List<Player> getPlayers(){
        return this.players;
    }

    public void removePlayer(int index){
        this.players.remove(index);
    }

    public List<Card> getCards(){
        return cards;
    }

    public Board getBoard(){
        return board;
    }

    public int getTurn(){
        return this.turn;
    }

    public int getRound(){
        return this.round;
    }

    public void nextTurn(){
        this.turn++;
    }

    public Card draw_card(){
        int lng = this.cards.size();
        if(lng == 0){
            shuffle();
        }
        int draw = random_number(lng - 1);
        Card h = this.cards.get(draw);
        this.cards.remove(draw);
        return h;
    }
    

    private int random_number(int range){
        return (int) (ThreadLocalRandom.current().nextInt(0, range + 1));
    }

    private void shuffle(){
        // Creating cards
        //1 - move player, remember to check somehow if he passed GO!...
        //2 - add money
        //3 - different
        Card card = new Card("Advance to Boardwalk", 1, 0);
        cards.add(card);
        card = new Card("Advance to Go (Collect $200)", 1, 0);
        cards.add(card);  
        card = new Card("Advance to Illinois Avenue. If you pass Go, collect $200", 1, 0);
        cards.add(card);
        card = new Card("Advance to St. Charles Place. If you pass Go, collect $200", 1, 0);
        cards.add(card);
        card = new Card("Advance to the nearest Railroad. If unowned, you may buy it from the Bank. If owned, pay owner twice the rental to which they are otherwise entitled", 3, 0);
        cards.add(card);
        card = new Card("Advance to the nearest Railroad. If unowned, you may buy it from the Bank. If owned, pay owner twice the rental to which they are otherwise entitled", 3, 0);
        cards.add(card);
        card = new Card("Advance to the nearest Railroad. If unowned, you may buy it from the Bank. If owned, pay owner twice the rental to which they are otherwise entitled", 3, 0);
        cards.add(card);
        card = new Card("Bank pays you dividend of $50", 2, 50);
        cards.add(card);
        card = new Card("Get Out of Jail Free", 3, 0);
        cards.add(card);
        card = new Card("Go Back 3 Spaces", 3, 0);
        cards.add(card);
        card = new Card("Go to Jail. Go directly to Jail, do not pass Go, do not collect $200", 3, 0);
        cards.add(card);
        card = new Card("Make general repairs on all your property. For each house, pay $25. For each hotel, pay $100", 3, 0);
        cards.add(card);
        card = new Card("Speeding fine $15", 2, -15);
        cards.add(card);
        card = new Card("Take a trip to nearest Railroad. If you pass Go, collect $200", 1, 0);
        cards.add(card);
        card = new Card("You have been elected Chairman of the Board. Pay each player $50", 3, 0);
        cards.add(card);
        card = new Card("Your building loan matures. Collect $150", 2, 150);
        cards.add(card);
        card = new Card("Advance to Go (Collect $200)", 1, 0);
        cards.add(card);
        card = new Card("Bank error in your favor. Collect $200", 2, 200);
        cards.add(card);
        card = new Card("Doctor's fee. Pay $50", 2, -50);
        cards.add(card);
        card = new Card("From sale of stock, you get $50", 2, 50);
        cards.add(card);
        card = new Card("Get Out of Jail Free", 3, 0);
        cards.add(card);
        card = new Card("Go to Jail. Go directly to jail, do not pass Go, do not collect $200", 3, 0);
        cards.add(card);
        card = new Card("Holiday fund matures. Receive $100", 2, 100);
        cards.add(card);
        card = new Card("Income tax refund. Collect $20", 2, 20);
        cards.add(card);
        card = new Card("It is your birthday. Collect $10 from every player", 3, 0);
        cards.add(card);
        card = new Card("Life insurance matures. Collect $100", 2, 100);
        cards.add(card);
        card = new Card("Pay hospital fees of $100", 2, -100);
        cards.add(card);
        card = new Card("Pay school fees of $50", 2, -50);
        cards.add(card);
        card = new Card("Receive $25 consultancy fee", 2, 25);
        cards.add(card);
        card = new Card("You are assessed for street repair. $40 per house. $115 per hotel", 3, 0);
        cards.add(card);
        card = new Card("You have won second prize in a beauty contest. Collect $10", 2, 10);
        cards.add(card);
        card = new Card("You inherit $100", 2, 100);
        cards.add(card);
    }


    public void initialize_game() {
        // Initialize the players, cards, and board for the game

        System.out.println("How many players will there be?");
        int number = input.nextInt();
        for(int i = 0; i < number; i++){
            System.out.printf("Please enter name for player number %d\n", i+1);
            String name = inputString.nextLine();
            Player player = new Player(name);
            this.players.add(player);
        }
       
        if(this.cards.size() == 0){
            shuffle();
        }
        else{
            System.out.println("ERROR: Already shuffled???");
        }
    }


    public void start_round(){
        this.round++;
        while (this.turn < this.players.size()){
            if(playerRemoved != 0){
                this.turn = this.turn - this.playerRemoved;
                this.playerRemoved = 0;
            }
            start_turn();
        }
        if(this.players.size() == 1){
            System.out.printf("%s! YOU WON!!! YOU HAVE IN YOUR BANK $%d", players.get(0).getName(), players.get(0).getMoney() );
            this.round = -1;
        }
        this.turn = 0;
    }

    public void afterMove(Field currentField, Player currentPlayer){
        if (currentField instanceof ActionField) {
                ActionField actionField = (ActionField) currentField;
                System.out.println("You're on: ");
                System.out.println(actionField.getInfo());
                performAction(currentPlayer, actionField);
            } else if (currentField instanceof PropertyField) {
                PropertyField propertyField = (PropertyField) currentField;
                System.out.println("You're on: ");
                System.out.println(propertyField.getName());
                if(propertyField.getOwner() == null){
                    System.out.printf("It's unowned. Do you want to buy this field for $%d? Write 1 if you do or 0 if you don't ", propertyField.getPrice());
                    int number = input.nextInt();
                    while(number != 1 && number != 0){
                        System.out.println("Input must be 0 or 1");
                        number = input.nextInt();
                    }
                    if(number == 1){
                        currentPlayer.buy(propertyField);

                    }
                    else{
                        List<Player> biddingParticipants = new ArrayList<>();
                        biddingParticipants.addAll(getPlayers());
                        currentPlayer.bid(propertyField, turn, null, biddingParticipants, input, inputString);
                    }
                }
                else{
                    if(propertyField.isMortgaged()){
                        System.out.print("Lucky! This property is put under moratage");
                    }
                    else{
                        propertyField.payRent(currentPlayer, 1);
                    }
                }
            }

    }

    public void start_turn() {
        int h = this.turn;
        while(h == this.turn){

            //get the player whose turn it is
            Player currentPlayer = players.get(turn);
            System.out.printf("Hello %s! ", players.get(turn).getName());

            //Rolling the dice and moving the player
            System.out.printf("You have left $%d ", players.get(turn).getMoney());
            int diceRoll = rollDice();
            currentPlayer.move(diceRoll);

            //Handling the action of the current field
            int currentPosition = currentPlayer.getPosition();
            Field currentField = this.board.getField(currentPosition);
            afterMove(currentField, currentPlayer);

            //ask player if he want to sell buildings
            System.out.printf("%s ", currentPlayer.getName());
            int ask = message("Do you want to sell some of your buildings? 0 for no or 1 for yes ");
            while(ask == 1){
                currentPlayer.sellBuildings(input, inputString);
                System.out.printf("%s ", currentPlayer.getName());
                ask = message("Do you want to sell more of your buildings? 0 for no or 1 for yes ");
            }
            //ask player if he wants to sell property
            System.out.printf("%s ", currentPlayer.getName());
            ask = message("Do you want to sell some of your property? 0 for no or 1 for yes ");
            while(ask == 1){
                currentPlayer.sellProperty(input, inputString, this.turn, this.players);
                System.out.printf("%s ", currentPlayer.getName());
                ask = message("Do you want to sell more of your property? 0 for no or 1 for yes ");
            }
            //ask player if he wants to mortgage property
            System.out.printf("%s ", currentPlayer.getName());
            ask = message("Do you want to mortgage some of your property? 0 for no or 1 for yes ");
            while(ask == 1){
                currentPlayer.putUnderMortage(input, inputString);
                //ask player if he wants to mortage smth, if yes check if he have buildings on it or diff color, if yes sell them
                System.out.printf("%s ", currentPlayer.getName());
                ask = message("Do you want to mortgage more of your property? 0 for no or 1 for yes ");
            }
            //ask player if he wants to unmortgage property
            System.out.printf("%s ", currentPlayer.getName());
            ask = message("Do you want to unmortgage some of your property? 0 for no or 1 for yes ");
            while(ask == 1){
                currentPlayer.putOffMortage(input, inputString);
                System.out.printf("%s ", currentPlayer.getName());
                ask = message("Do you want to unmortgage more of your property? 0 for no or 1 for yes ");
            }
            //aske player if he wants to build house/hotel
            System.out.printf("%s ", currentPlayer.getName());
            ask = message("Do you want to build on some of your property? 0 for no or 1 for yes ");
            while(ask == 1){
                currentPlayer.addBuildings(input, inputString);
                System.out.printf("%s ", currentPlayer.getName());
                ask = message("Do you want to build more on some of your property? 0 for no or 1 for yes ");
            }
            //ask player if he wants to surrender
            System.out.printf("%s ", currentPlayer.getName());
            ask = message("Do you want to surrender? 0 for no or 1 for yes ");
            if(ask == 1){
                surrender(this.turn, currentPlayer);
            }
            //check if player went bankrput
            if(currentPlayer.getMoney() < 0){
                System.out.println("You went bankrupt!!! You lost the game");
                this.playerRemoved++;
                surrender(this.turn, currentPlayer);
            }

            // Increment the turn to the next player
            this.turn = (this.turn + 1);
        }
    }


    public void surrender(int which, Player player) {
        // Implement logic to handle player surrendering the game
        //unmoratge properties
        for(int i = 0; i < player.getMortgagedProperties().size(); i++){
            if(player.getMortgagedProperties().get(i) instanceof PropertyField){
                PropertyField field = (PropertyField) player.getMortgagedProperties().get(i);
                field.setMortgaged(false);
            }
        }

        //clean properties
        for(int i = 0; i < player.getOwnedProperties().size(); i++){
            if(player.getOwnedProperties().get(i) instanceof PropertyField){
                PropertyField field = (PropertyField) player.getOwnedProperties().get(i);
                field.setOwner(null);
                if(field instanceof Street){
                    Street street = (Street) field;
                    for(int j = 0; j < 5; j++){
                        street.setBuildings(j, 0);
                    }
                }
                else if(field instanceof Railroad){
                    Railroad railroad = (Railroad) field;
                    railroad.setNumRailroads(0);
                    railroad.setRent(25);
                }
            }
        }
        this.players.remove(which);
    }

    public void performAction(Player player, ActionField field) {
        // Implement logic to perform the action associated with this action field on the given player
        switch(field.getInfo()){
            case "GO!":
                player.setMoney(player.getMoney()+200);
                break;
            case "Card":
                Card card = draw_card();
                System.out.println(card.getInfo());
                card.checkCard(player, input, inputString, this);
                break;
            case "Income Tax":
                System.out.println("You just payed $200 of Income tax");
                player.setMoney(player.getMoney() - 200);
                break;
            case "Visiting Jail":
                //nothing happens, player is visiting jail
                break;
            case "Free Parking":
                //nothing happens :> its parking
                break;
            case "Luxury Tax":
                System.out.println("You just payed $100 of Luxury tax");
                player.setMoney(player.getMoney() - 100);
                break;
            default:
                System.out.println("ERROR: PerformAction got action that doesnt exist?");
                break;
        }
    }

    public int message(String message){
        System.out.println(message);
        int number = input.nextInt();
        while(number != 0 && number != 1){
            System.out.println("Input must be 1 for yes or 0 for no ");
            number = input.nextInt();
        }
        return number;
    }

    private int rollDice() {
        return (int) (ThreadLocalRandom.current().nextInt(1, 12 + 1));
        // nextInt is normally exclusive of the top value, so add 1 to make it inclusive
        // Return the rolled dice value
    }
}