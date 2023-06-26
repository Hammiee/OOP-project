public class Railroad extends PropertyField {
    private int numRailroads;
    private int rent; //dubled for every owned one

    public Railroad(String name, int price) {
        super("Railroad", name, price);
        this.numRailroads = 0;
        this.rent = 25;
    }

    public int getRent(){
        return this.rent;
    }

    public void setRent(int number){
        this.rent = number;
    }

    public int getNumRailroads() {
        return numRailroads;
    }

    public void setNumRailroads(int numRailroads) {
        this.numRailroads = numRailroads;
    }
}