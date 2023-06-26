import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Street extends PropertyField {
    private String color;
    private List <Integer> buildings;//max 4 houses or 1 hotel, at the beginning looks like this: 0 0 0 0 || 0
    private boolean monopol;
    private List <Integer> rentCostHouses;//[0]no houses [1]1 house ... [4]4 houses [5]1 hotel
    private int housePrice;
    private int hotelPrice;// to buy hotel you need to buy 4 houses!

    public Street(String name, String color, int price, int rent, int rh1, int rh2, int rh3, int rh4, int rhotel, int houseP, int hotelP) {
        //street constructor
        super("Street", name, price);
        this.color = color;
        this.buildings = new ArrayList<>();
        buildings.add(0);
        buildings.add(0);
        buildings.add(0);
        buildings.add(0);
        buildings.add(0);
        this.monopol = false;
        this.rentCostHouses = new ArrayList<>();
        this.rentCostHouses.add(rent);
        this.rentCostHouses.add(rh1);
        this.rentCostHouses.add(rh2);
        this.rentCostHouses.add(rh3);
        this.rentCostHouses.add(rh4);
        this.rentCostHouses.add(rhotel);
        this.housePrice = houseP;
        this.hotelPrice = hotelP;
    }

    public int getRent(){
        int rent = rentCostHouses.get(0);
        for(int i = 0; i < buildings.size(); i++){
            if(buildings.get(i) != 0){
                rent = rent + rentCostHouses.get(i+1);
            }
        }
        return rent;
    }

    public int getHousePrice(){
        return this.housePrice;
    }

    public int getHotelPrice(){
        return this.hotelPrice;
    }

    public String getColor() {
        return color;
    }

    public List<Integer> getBuildings() {
        return buildings;
    }

    public void setBuildings(int nr, int amount){
        buildings.set(nr, amount);
    }

    public boolean hasMonopol() {
        return monopol;
    }

    public void setMonopol(boolean monopol) {
        this.monopol = monopol;
    }

    public void build(Scanner input, Scanner inputString, Player player) {
        // Implement logic to add a building to this street
        boolean ability = true;
        for(int j = 0; j < player.getMortgagedProperties().size(); j++){
            if(player.getMortgagedProperties().get(j) instanceof Street){
                Street h = (Street) player.getMortgagedProperties().get(j);
                if(h.getColor() == this.color){
                    System.out.println("Property of this color is mortgaged");
                    ability = false;
                }
            }
        }
        if(!this.monopol){
            System.out.println("You don't have monopol for this color");
            ability = false;
        }
        if(ability){
            System.out.println("What do you wanna build? 1 for house and 0 for hotel");
            int number = input.nextInt();
            while(number != 0 && number != 1){
                System.out.println("Something went wrong ");
                number = Integer.parseInt(inputString.nextLine());
            }
            if(number == 1){
                int where = -1;
                for(int j = 0; j < 4; j++){
                    if(buildings.get(j)==0){
                        where = j;
                    }
                }
                if(where != -1){
                    player.setMoney(player.getMoney() - this.housePrice);
                    buildings.set(where, 1);
                    System.out.printf("You bought one house for $%d", this.housePrice);

                }
                else{
                    System.out.println("You dont have place for another house");
                }
            }
            else{
                if(this.buildings.get(4) != 0){
                    System.out.println("You already have a hotel on this street");
                }
                else{
                    System.out.println("Do you want to exchange your houses(1) or just buy the hotel with money(0)?");
                    number = input.nextInt();
                    while(number != 0 && number != 1){
                        System.out.println("Something went wrong ");
                        number = Integer.parseInt(inputString.nextLine());
                    }
                    if(number == 1){
                        int amount = 0;
                        for(int j = 0; j < 4; j++){
                            if(this.buildings.get(j) != 0){
                                amount++;
                                this.buildings.set(j, 0);
                            }
                        }
                        int cost = (this.housePrice * (4 -  amount ) + this.hotelPrice);
                        player.setMoney(player.getMoney() - cost);
                        this.buildings.set(4, 1);
                        System.out.printf("You bought one hotel for $%d", cost);
                    }
                    else{
                        int cost = (this.housePrice * 4 + this.hotelPrice);
                        player.setMoney(player.getMoney() - cost);
                        this.buildings.set(4, 1);
                        System.out.printf("You bought one hotel for $%d", cost);
                    }
                }
            }
        }

    }

}
