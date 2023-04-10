import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class Board extends JPanel implements ActionListener {
    int B_height = 400;
    int B_width = 400;
    int Max_Dots = 1600;
    int Dot_Size = 10;
    int [] x = new int[Max_Dots];
    int [] y = new int[Max_Dots];
    int x_apple;
    int y_apple;
    int Dots;
    Image body,head,apple;
    Timer timer;
    int Delay = 150;
    boolean leftDirection = true;
    boolean rightDirection = false;
    boolean upDirection = false;
    boolean downDirection = false;
    boolean inGame = true;


    Board(){

        TAdapter tAdapter = new TAdapter();
        addKeyListener(tAdapter);
        setFocusable(true);
        setPreferredSize(new Dimension(B_width,B_height));
        setBackground(Color.black);
        initgame();
        loadImages();
    }
    // Initializing
    public void initgame(){
        Dots = 3;
        x[0] = 250;
        y[0] = 250;
        for(int i=1;i<Dots;i++){
            x[i] = x[0] + Dot_Size*i;
            y[i] = y[0];
         }
        // initiate Apples position
        locateApple();

        timer = new Timer(Delay,this);
        timer.start();
    }
    // Load images
    public void loadImages(){  

        ImageIcon bodyIcon = new ImageIcon("src/resources/dot.png");
        body = bodyIcon.getImage();
        ImageIcon headIcon = new ImageIcon("src/resources/head.png");
        head = headIcon.getImage();
        ImageIcon appleIcon = new ImageIcon("src/resources/apple.png");
        apple = appleIcon.getImage();
    }

    // Draw images at snake and apple's position
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        doDrawing(g);
    }

    public void doDrawing(Graphics g){
        if (inGame) {
            g.drawImage(apple, x_apple,  y_apple, this); // draw apple
            for (int i = 0; i < Dots; i++) {
                if (i == 0) {
                    g.drawImage(head, x[i], y[i], this);
                } else
                    g.drawImage(body, x[i], y[i], this);
            }
        }
        else {
            gameOver(g);
            timer.stop();
        }
    }
    // Randomize Apples Position
    public void locateApple(){
        x_apple = ((int)(Math.random()*39))*Dot_Size;
        y_apple = ((int)(Math.random()*39))*Dot_Size;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent){
        if(inGame) {
            move();
            checkCollision();
            checkApple();
        }
        repaint();
    }

    public void move(){
        for(int i=Dots-1;i>0;i--){
                 x[i] = x[i-1];
                 y[i] = y[i-1];
             }
             if(leftDirection){
                 x[0]-=Dot_Size;
             }
             if(rightDirection){
                 x[0]+=Dot_Size;
             }
             if(upDirection){
                 y[0]-=Dot_Size;
             }
             if(downDirection){
                 y[0]+=Dot_Size;
             }
        }
    // Implement Controls
    public void checkApple(){
        if(x_apple == x[0] && y_apple == y[0]){
            Dots++;
            locateApple();
        }
    }
    public void checkCollision(){
        //body collision
        for(int i=1;i<Dots;i++){
            if(i>4 && x[i]==x[0]&&y[i]==y[0]){
                inGame = false;
                break;
            }
        }

        // collision with borders
        if(x[0]<0 || x[0]==B_width) inGame = false;
        if(y[0]<0 || y[0]==B_height) inGame = false;

    }
    public void gameOver(Graphics g){
        String msg = "Game Over";
        int score = (Dots-3)*100;
        String msg2 = "Score:"+Integer.toString(score);
        Font small = new Font("Helvetica",Font.BOLD,14);
        FontMetrics fontMetrics = getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg,(B_width-(fontMetrics.stringWidth(msg)))/2,B_height/4);
        g.drawString(msg2,(B_width-(fontMetrics.stringWidth(msg2)))/2,3*B_height/4);

    }
    private class TAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent keyEvent){
            int key = keyEvent.getKeyCode();
            if(key == keyEvent.VK_LEFT && !rightDirection){
                leftDirection = true;
                upDirection = false;
                downDirection = false;
            }
            if(key == keyEvent.VK_RIGHT && !leftDirection){
                rightDirection = true;
                upDirection = false;
                downDirection = false;
            }
            if(key == keyEvent.VK_UP && !downDirection){
                rightDirection = false;
                upDirection = true;
                leftDirection = false;
            }
            if(key == keyEvent.VK_DOWN && !upDirection){
                rightDirection = false;
                downDirection = true;
                leftDirection = false;
            }
        }
    }


}
