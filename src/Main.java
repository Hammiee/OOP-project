import java.util.Scanner;

public class Main{
    //main class to play the terminal version of the game
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Scanner inputString = new Scanner(System.in);
        Game game = new Game();
        game.setScanner(input);
        game.setScannerString(inputString);
        game.initialize_game();
        while(game.getRound() != -1){
            game.start_round();
        }
        inputString.close();
        input.close();
    }
}
