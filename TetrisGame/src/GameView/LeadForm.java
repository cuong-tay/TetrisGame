package GameView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class LeadForm extends JFrame implements ActionListener {
    private JButton startButton = new JButton();
    private JButton xephang = new JButton();
    private JButton exitButton = new JButton();

    public LeadForm() {
       this.setSize(500, 500);
        setBackground();
       initUI();


    }

    private void setBackground() {
        ImageIcon imageIcon = new ImageIcon(new ImageIcon("tetris-falling-blocks_800x450.jpg").getImage().getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH));
        JLabel backgroundLabel = new JLabel(imageIcon);
        this.setContentPane(backgroundLabel);
        this.setLayout(new BorderLayout());

    }

    private void initUI() {
        setTitle("Tetris");
        this.setIconImage(new ImageIcon("tetris.png").getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);//xuat hien chính giữa màn hình
        this.setResizable(false);
        this.setLayout(null);


        int x = 140;
        startButton.setBounds(x, 100, 200, 50);
        xephang.setBounds(x, 200, 200, 50);
        exitButton.setBounds(x, 300, 200, 50);
        startButton.setLayout(new BorderLayout());
        xephang.setLayout(new BorderLayout());
        exitButton.setLayout(new BorderLayout());
        startButton.setBackground(Color.white);
        xephang.setBackground(Color.white);
        exitButton.setBackground(Color.white);

        JLabel labelstart = new JLabel("START");
        labelstart.setHorizontalAlignment(JLabel.CENTER);// căn giữa theo chiều ngang

        JLabel labelxephang = new JLabel("TOP RANKING");
        labelxephang.setHorizontalAlignment(JLabel.CENTER);

        JLabel labelexit = new JLabel("EXIT");
        labelexit.setHorizontalAlignment(JLabel.CENTER);

        startButton.add(labelstart, BorderLayout.CENTER);
        xephang.add(labelxephang, BorderLayout.CENTER);
        exitButton.add(labelexit, BorderLayout.CENTER);

        Font font = null;
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, new File("modern.ttf")).deriveFont(20f);
            labelstart.setFont(font);
            labelxephang.setFont(font);
            labelexit.setFont(font);
        } catch (FontFormatException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.add(startButton);
        this.add(xephang);
        this.add(exitButton);
        startButton.addActionListener(this);
            xephang.addActionListener(this);
            exitButton.addActionListener(this);



    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startButton) {
            this.setVisible(false);
            Tetris.startGame();
        }
        if (e.getSource() == xephang) {
            this.setVisible(false);
            Tetris.xepHang();

        }
        if (e.getSource() == exitButton) {
            System.exit(0);
        }
    }

}
