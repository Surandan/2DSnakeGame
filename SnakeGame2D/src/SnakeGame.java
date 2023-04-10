import javax.swing.*;

public class SnakeGame extends JFrame {
    Board board;
    SnakeGame(){
        board = new Board();
        add(board);
        pack();
        setVisible(true);
        setResizable(false);
    }
    public static void main(String[] args) {

        SnakeGame snakeGame = new SnakeGame();
    }
}