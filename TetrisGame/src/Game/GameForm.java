package Game;

import GameView.Tetris;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;


public class GameForm extends JFrame {
    private static  int widthFrame = 720;
    private static int heightFrame = 650;
    private  GameArea gameArea = new GameArea();
    private  JLabel point,level;
    private JButton Menu;
   private NextBlock nextBlock = new NextBlock();
  private GameThread gt;

    public GameForm() {
        this.add(nextBlock);
        initUI();
        this.add(gameArea);
        initControl();
//        setBackground();

    }

    //xử lý sự kiện từ bàn phím
    private void initControl(){
        this.addKeyListener((new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                switch (e.getKeyCode()){
                    case KeyEvent.VK_LEFT:
                        gameArea.moveBlockLeft();
                        break;
                    case KeyEvent.VK_RIGHT:
                        gameArea.moveBlockRight();
                        break;
                    case KeyEvent.VK_DOWN:
                        gameArea.moveBlockDown();
                        break;
                    case KeyEvent.VK_UP:
                        gameArea.rotateBlock();
                        break;
                    case KeyEvent.VK_SPACE:
                        gameArea.dropBlock();
                        break;
                }
            }
        }));
        this.setFocusable(true);//để có thể nhận sự kiện từ bàn phím


    }

    private void initUI() {
        setTitle("Tetris");
        this.setIconImage(new ImageIcon("tetris.png").getImage());
        setSize(widthFrame, heightFrame);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);//xuat hien chính giữa màn hình
        this.setResizable(false);
        this.setLayout(null);

        PointLevel();
        MainMenu();


    }
    private void PointLevel(){
         point = new JLabel("Point: 0");
        point.setBounds(600, 10, 100, 50);
        point.setFont(point.getFont().deriveFont(15.0f));

        this.add(point);
         level = new JLabel("Level: 1");
        level.setBounds(600, 45, 100, 50);
        level.setFont(level.getFont().deriveFont(15.0f));
        this.add(level);
        //đặt font chữ
        Font font = null;
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, new File("modern.ttf")).deriveFont(12f);
            point.setFont(font);
            level.setFont(font);
        } catch (FontFormatException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public void MainMenu(){
        Menu = new JButton();
        Menu.setBounds(20, 20, 100, 30);
       Menu.setBackground(Color.white);
       Menu.setLayout(new BorderLayout());
        Menu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gt.interrupt();// dừng thread game khi chuyển sang menu chính để tránh lỗi
                GameForm.this.setVisible(false);
               Tetris.showMenu();


            }
        });

        this.add(Menu);
        JLabel label = new JLabel("Main Menu");
        label.setHorizontalAlignment(JLabel.CENTER);// căn giữa theo chiều ngang
        Menu.add(label, BorderLayout.CENTER);
        //đặt font chữ
        Font font = null;
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, new File("modern.ttf")).deriveFont(10f);
            label.setFont(font);
        } catch (FontFormatException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }



    public void updatePoint(int point){
        this.point.setText("Point: "+point);
    }
    public void updateLevel(int level){
       this.level.setText("Level: "+level);
    }
    public void startGame(){
       gt= new GameThread(gameArea,this,nextBlock );
         gt.start();

    }

//    // Phương thức để đặt hình nền cho frame
//    private void setBackground() {
//        ImageIcon backgroundIcon = new ImageIcon("black1.jpg");
//        JLabel backgroundLabel = new JLabel(backgroundIcon);
//        backgroundLabel.setBounds(0, 0, widthFrame, heightFrame);
//        this.add(backgroundLabel,-1);
//
//    }


}
