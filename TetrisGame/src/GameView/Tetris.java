package GameView;

import Game.GameForm;
import javax.swing.*;


public class Tetris {
private static GameForm gf;
private static LeadForm lf;
private static ListBXH lbxh;
    public static void startGame(){
            gf = new GameForm();

        gf.startGame();
        gf.setVisible(true);//hiển thị form
    }
    public static void xepHang(){
        lbxh.setVisible(true);
    }
    public static void showMenu(){
        gf.setVisible(false);
        lf.setVisible(true);
    }
    public static void gameOver(int score){
        String namePlay= JOptionPane.showInputDialog("Game Over!\n Please enter your name:");
        if (namePlay==null || namePlay.trim().isEmpty()){
            gf.setVisible(false);
            lf.setVisible(true);
            return;
        } else {

                lbxh.addPlayer(namePlay,score);
        }
        
        gf.setVisible(false);
        lf.setVisible(true);
    }
    public static void main(String[] args) {

              lbxh = new ListBXH();
              gf = new GameForm();
              lf = new LeadForm();
              lf.setVisible(true);
            }

}
