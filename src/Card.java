
import java.util.List;
import java.util.Scanner;

public class Card{
    private String info;
    private int action;
    private int amount;

    public Card(String info, int action, int amount) {
        this.info = info;//description of the card aka message to the player
        this.action = action;//number of action style
        //1 - move player
        //2 - add money
        //3 - different
        this.amount = amount;//if action has numbers, if not 0
    }

    public String getInfo() {
        return info;
    }

    private int findRailroad(int pos){
            if( pos < 5 && pos >= 35){
                return 5;
            }
            else if( pos < 12){
                return 12;
            }
            else if( pos < 15){
                return 15;
            }
            else if( pos < 25){
                return 25;
            }
            else if( pos < 28){
                return 28;
            }
            else if( pos < 35){
                return 35;
            }
            else{
                return 0;  //ERROR: where are the railroads!?!?
            }

    }
    private void payForBuildings(Player player,int hotelP, int houseP){
        List<Field> properties = player.getOwnedProperties();
        if(properties.isEmpty()){
            System.out.println("You're poor! Nothing happens!");
        }
        else{
            int houses = 0;
            int hotels = 0;
            for(int i = 0; i < properties.size(); i++){
                if(properties.get(i) instanceof Street){
                    Street street = (Street) properties.get(i);
                    List<Integer> builds = street.getBuildings();
                    for(int j = 0; j < 5; j++){
                        if(builds.get(j) != 0){
                            if(j == 4){
                                hotels++;
                            }
                            else houses++;
                        }
                    }
                }
            }
            player.setMoney(player.getMoney() - houses * houseP - hotels * hotelP);
        }
    }
    //helper functions for checkCard

    public void checkCard(Player player, Scanner input, Scanner inputString, Game game){
        switch(this.action){
            case 2:
                if(this.amount == 0){
                    System.out.println("ERROR: i want to add 0 money?");
                }
                else{
                    player.setMoney(player.getMoney() + this.amount);
                }
                break;
            case 1:
                switch(this.info){
                    case "Advance to Boardwalk":
                        player.setPosition(39);
                        game.afterMove(game.getBoard().getField(39), player);
                        break;
                    case "Advance to Go (Collect $200)":
                        if(player.getPosition() > 0){
                            player.setMoney(player.getMoney() + 200);
                        }
                        player.setPosition(0);
                        break;
                    case "Advance to Illinois Avenue. If you pass Go, collect $200":
                        if(player.getPosition() > 24){
                            player.setMoney(player.getMoney() + 200);
                        }
                        player.setPosition(24);
                        game.afterMove(game.getBoard().getField(24), player);
                        break;
                    case "Advance to St. Charles Place. If you pass Go, collect $200":
                        if(player.getPosition() > 11){
                            player.setMoney(player.getMoney() + 200);
                        }
                        player.setPosition(11);
                        game.afterMove(game.getBoard().getField(11), player);
                        break;

                    case "Take a trip to nearest Railroad. If you pass Go, collect $200":
                        int newPos = findRailroad(player.getPosition());
                        if(player.getPosition() >= 35){
                            player.setMoney(player.getMoney() + 200);
                        }
                        if(newPos == 0){
                            System.out.println("ERROR: where are the railroads!?!?");
                        }
                        else{
                            player.setPosition(newPos);
                            game.afterMove(game.getBoard().getField(newPos), player);
                        }
                }
                break;
            case 3:
                switch(this.info){
                    case "Advance to the nearest Railroad. If unowned, you may buy it from the Bank. If owned, pay owner twice the rental to which they are otherwise entitled":
                        int pos = findRailroad(player.getPosition());
                        player.setPosition(pos);
                        PropertyField field = (PropertyField) game.getBoard().getField(pos);

                        Player owner = field.getOwner();
                        if(owner == null){
                            System.out.println("Do you want to buy this Railroad? Press 1 if yes or 0 if no");
                            int number = input.nextInt();
                            while(number != 1 && number != 0){
                                System.out.println("Please! Press 1 if you want to buy this Railroad or 0 if you dont");
                                number = input.nextInt();
                            }
                            if (number == 1){
                                player.buy(field);
                            }
                            else{
                                //nothing happens, game proceeds
                            }
                        }
                        else{
                            //pay
                            if(field.isMortgaged()){
                                System.out.print("Lucky! This property is put under moratage");
                            }
                            else{
                                field.payRent(player, 2);
                            }

                        }
                        break;
                    case "Get Out of Jail Free":
                        player.addCard(this);
                        break;
                    case "Go Back 3 Spaces":
                        int p = player.getPosition() - 3;
                        if(p < 0){
                            player.setPosition(40 + p);
                            game.afterMove(game.getBoard().getField(40 + p), player);
                        }
                        else{
                            player.setPosition(player.getPosition() - 3);
                            game.afterMove(game.getBoard().getField(player.getPosition() - 3), player);
                        }
                        break;
                    case "Go to Jail. Go directly to Jail, do not pass Go, do not collect $200":
                        if(player.getOwnedCards().isEmpty()){//player does not have free of jail card
                            player.setPosition(10);
                            game.nextTurn();//end rund!!!!

                        }
                        player.removeCard();
                        System.out.println("You have 'Get out of Jail Free' card!! Nothing happens!");
                        break;
                    case "Make general repairs on all your property. For each house, pay $25. For each hotel, pay $100":
                        payForBuildings(player, 100, 25);
                        break;
                    case "You have been elected Chairman of the Board. Pay each player $50":
                        List<Player> players = game.getPlayers();
                        player.setMoney(player.getMoney() - (players.size() - 1));
                        for(int i = 0; i < players.size(); i++){
                            if(players.get(i) != player){
                                players.get(i).setMoney(players.get(i).getMoney() + 50);
                            }
                        }
                    case "It is your birthday. Collect $10 from every player":
                        List<Player> plyrs = game.getPlayers();
                        player.setMoney(player.getMoney() + 10*(plyrs.size() - 1));
                        for(int i = 0; i < plyrs.size(); i++){
                            if(plyrs.get(i) != player){
                                plyrs.get(i).setMoney(plyrs.get(i).getMoney() - 10);
                            }
                        }
                        break;
                    case "You are assessed for street repair. $40 per house. $115 per hotel":
                        payForBuildings(player, 115, 40);
                        break;
                }
                break;
            default:
                System.out.println("ERROR: this card shouldnt exist?");
                break;
            }
    }
}
