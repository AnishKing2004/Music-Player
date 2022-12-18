import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;

/**
 * !!! Put in description of your program here !!!
 * 
 * @author !!! your name here !!!
 *
 */
public class Composer extends JPanel implements KeyListener {

    //This string will describe the song that the user should play
    String song = "";
    
    //Font used for the song letters
    Font letterFont = new Font("Sans", Font.PLAIN, 20);

    /**
     * paint
     *
     * Draws the music in the window
     *
     * @param  canvas   the Graphics object for the window
     */
    public void paint(Graphics canvas) {
        super.paint(canvas);  // this must be the first line
        bracketsBorder(canvas); //outline of the brackets 
        bracketsVerticle(canvas); //verticle rectangles 
        bracketHorozontal(canvas); //horzontal rectangles
        numbersSymbol(canvas);
        setBackground(Color.white);

        //These characters may be helpful to you
        //   If the chars below don't work, use this instead:
        //     String musicChars = "\u2669 \ud834\udd3d \ud834\udd5e \ud834\udd1e";
        String musicChars = "♩ 𝄽 𝅗𝅥 𝄞";


        //Draw the all letters at the bottom for reference
        canvas.setColor(Color.black);
        canvas.setFont(letterFont);
        canvas.drawString(song, 50, WINDOW_HEIGHT - 150);
        
        
    }//paint
    public void bracketsBorder(Graphics canvas){ //the big square
        canvas.setColor(Color.BLACK);
        canvas.drawRect(80,280,800,120);
        
    }
    
    public void bracketsVerticle(Graphics canvas){ //verticle rectangles 
        canvas.setColor(Color.BLACK);
        int i;
        int x = 0;
        for(i = 0; i<4; i++){
            x = x+200;
            canvas.drawRect(80,280,x,120);
            
        }
   
        
    }
     public void bracketHorozontal(Graphics canvas){ //horizontal rectangles 
         canvas.setColor(Color.BLACK);
         int j;
         int y= 0;
         for(j = 0; j < 3; j++){
             y = y + 30;
             canvas.drawRect(80,280,800,y);
         }
         
         
    }
    
    public void numbersSymbol(Graphics canvas){
        Font symbol = new Font("Serif", Font.ITALIC, 160);//font, italics, size
        canvas.setFont(symbol);
        canvas.setColor(Color.BLACK);
        canvas.drawString("\ud834\udd1e", 80,400);
        
        Font four = new Font("Serif", Font.BOLD, 80);//font, italics, size
        canvas.setFont(four);
        canvas.setColor(Color.BLACK);
        canvas.drawString("4", 200,335);
        
        canvas.setFont(four);
        canvas.setColor(Color.BLACK);
        canvas.drawString("4", 200,400);
        
    
        
    }
    


    
    /**
     * play
     *
     * plays the entire song entered so far
     */
    public void play() {
        //Replace this comment with your code
    }//play

    
    /*======================================================================
     *      >>    >>    >>    ATTENTION STUDENTS    <<    <<     <<
     *
     * None of the code below this line should be modified.  You are welcome
     * to review this code if you wish to help you understand how the program
     * works.
     *----------------------------------------------------------------------
     */

    //the size of the window (do not modify this)
    public static final int WINDOW_WIDTH = 1000;
    public static final int WINDOW_HEIGHT = 800;

    //these variables are used to track how long the user holds down a key
    public char lastKey = '\0';
    public long pressTime = -1;

    /**
     * keyPressed
     *
     * is called each time any key is pressed.
     */
    public void keyPressed(KeyEvent keyEvent) {
        //We have to check for a backspace separately since it's
        //a special key code
        if (keyEvent.getExtendedKeyCode() == KeyEvent.VK_BACK_SPACE) {
            //remove the last note (if there is one)
            int len = song.length();
            if (len > 0) {
                song = song.substring(0, len - 1);
            }
            repaint();
            return;
        }
        
        //Ignore invalid keys
        String validKeys = "abcdefgABCDEFG pqr";
        char key = keyEvent.getKeyChar();
        if (validKeys.indexOf(key) == -1) {
            return;
        }

        //Handle the keypress
        switch(key) {
            case 'q':  //quit
                System.exit(0);  
            case 'p':
                play();
                break;
            case 'r':
                song = "";
                repaint();
                break;
            default:  //must be a note!
                if (key != lastKey) {
                    pressTime = System.currentTimeMillis();
                    lastKey = key;
                }
                break;
        }

    }//keyPressed

    /**
     * keyReleased
     *
     * is called each time a key is released.
     */
    public void keyReleased(KeyEvent keyEvent) {
        //Ignore invalid keys
        char key = keyEvent.getKeyChar();
        if (key != lastKey) {
            return;
        }

        //Calculate duration
        long releaseTime = System.currentTimeMillis();
        int totalTime = (int)(releaseTime - pressTime);

        //Report the note played
        //375+ ms is a half note (quarter note is default)
        if (totalTime >= 375) {
            key = Character.toUpperCase(key);
        }
        song += key;  //Add the note to the song


        //Reset for next keypress
        lastKey = '\0';
        pressTime = -1;
        
        //draw the updated song
        repaint();
    }//keyReleased

    
    //not used
    public void keyTyped(KeyEvent keyEvent) { /** do nothing */ }
    

    
    /**
     * main
     *
     * This method starts the application. 
     */
    public static void main(String[] args) {
        //Create the window
        JFrame frame = new JFrame();
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Gather input from the user
        Composer music = new Composer();
        frame.add(music);

        //Listen for keystrokes
        frame.addKeyListener(music);

        //Display the window
        frame.setVisible(true);
        frame.repaint();
    }
    
}//class Composer
