import java.util.List;

public abstract class PropertyField extends Field {
    private String name;
    private Player owner;
    private int price;
    private boolean mortgaged;

    public PropertyField(String type, String name, int price) {
        super(type);
        this.name = name;
        this.price = price;
        this.owner = null; // Unowned by default
        this.mortgaged = false;
    }

    public String getName() {
        return this.name;
    }

    public Player getOwner() {
        return this.owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public int getPrice() {
        return price;
    }

    public boolean isMortgaged() {
        return mortgaged;
    }

    public void setMortgaged(boolean mortgaged) {
        this.mortgaged = mortgaged;
    }

    public void payRent(Player player, int multiplier) {
        int rent;
        if(this instanceof Railroad){
            Railroad rail = (Railroad) this;
            rent = rail.getRent();
        }
        else if(this instanceof Street){
            Street street = (Street) this;
            rent = street.getRent();
        }
        else{
            rent = 0;
            System.out.println("ERROR: field that can be payed rent on is neither railroad or street???!?!");
        }
        this.owner.setMoney(this.owner.getMoney() + rent);
        player.setMoney(player.getMoney() - rent);
    }


    public void sell(Player player, int amount){
        this.owner = null;
        if(this instanceof Street){
            Street s = (Street) this;
            s.setMonopol(false);
            setMonopol(s.getColor(), player.getOwnedProperties(), false);
        }
        else if(this instanceof Railroad){
            Railroad r = (Railroad) this;
            r.setRent(25);
            r.setNumRailroads(0);
            changeRailroads(player.getOwnedProperties(), -1, 1/2);
        }
    }

    private void setMonopol(String color, List<Field> properties, Boolean mon){
        for(int i = 0; i < properties.size(); i++){
            if(properties.get(i) instanceof Street){
                Street hs = (Street) properties.get(i);
                if(hs.getColor() == color){
                    hs.setMonopol(mon);
                }
            }
        }
    }

    private int changeRailroads(List<Field> properties, int number, int mult){
        int nr = 1;
        for(int i = 0; i < properties.size(); i++){
            if(properties.get(i) instanceof Railroad){
                Railroad hr = (Railroad) properties.get(i);
                hr.setNumRailroads(hr.getNumRailroads() + number);
                hr.setRent(hr.getRent() * mult);
                nr ++;
            }
        }
        return nr;
    }

    public void buyField(Player player){
        if(this instanceof Street){
            Street street = (Street) this;
            String color = street.getColor();
            int mon = 1;
            for(int i = 0; i < player.getOwnedProperties().size(); i++){
                if(player.getOwnedProperties().get(i) instanceof Street){
                    Street hs = (Street) player.getOwnedProperties().get(i);
                    if(hs.getColor() == color){
                        if(color == "Brown" || color == "Dark Blue"){
                            hs.setMonopol(true);
                            street.setMonopol(true);
                            break;
                        }
                        else{
                            mon++;
                        }
                    }
                    
                }
            }
            player.addProperty(street);
            this.setOwner(player);
            if(mon == 3 ){
                setMonopol(color, player.getOwnedProperties(), true);

            }
            player.setMoney(player.getMoney() - this.price);
        }
        else{
            Railroad railroad = (Railroad) this;
            int nr = changeRailroads(player.getOwnedProperties(), 1, 2);
            player.addProperty(railroad);
            this.owner = player;
            player.setMoney(player.getMoney() - this.price);
            railroad.setNumRailroads(nr);
            int rent = 25 * (int) Math.pow(2, nr);
            railroad.setRent(rent);
        }

    }

}