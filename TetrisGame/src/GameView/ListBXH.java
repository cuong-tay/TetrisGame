package GameView;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import java.awt.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;


public class ListBXH extends JFrame  {

    private JTable table1;
    private JButton button1;
    private DefaultTableModel model;


        public ListBXH() {

           UIBXH();
           initData();
        }
        private void UIBXH(){
            setTitle("Bảng xếp hạng");
            this.setIconImage(new ImageIcon("tetris.png").getImage());
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.setSize(700,600);
            this.setLocationRelativeTo(null);
            this.setResizable(false);
            this.setLayout(null);

           creatButton();
           creatTable();
              setBackground();

        }
    private void setBackground() {
        // Create a JLabel with an ImageIcon
        ImageIcon imageIcon = new ImageIcon(new ImageIcon("tetris_wallpaper.jpg").getImage().getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH));
        JLabel backgroundLabel = new JLabel(imageIcon);

        backgroundLabel.setBounds(0, 0, this.getWidth(), this.getHeight());
        // Add the JLabel to the JFrame
        this.add(backgroundLabel);

    }
    private void creatButton() {
            button1 = new JButton();
            button1.setBounds(10, 10, 100, 30);
            button1.setBackground(Color.white);
            button1.setLayout(new BorderLayout());

            JLabel label = new JLabel("Main Menu");
            label.setHorizontalAlignment(JLabel.CENTER);
            button1.add(label, BorderLayout.CENTER);

        Font font = null;
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, new File("modern.ttf")).deriveFont(10f);
            label.setFont(font);
        } catch (FontFormatException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
            this.add(button1);

            button1.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    Tetris.showMenu();
                    dispose();
                }
            });
    }

    class Player {
        String name;
        int score;

        Player(String name, int score) {
            this.name = name;
            this.score = score;
        }
    }

    private void initData(){
        model = (DefaultTableModel) table1.getModel();
        model.setRowCount(0);
        try {
            java.io.File file = new java.io.File("BXH.txt");
            java.util.Scanner input = new java.util.Scanner(file);
            ArrayList<Player> players = new ArrayList<>();
            while (input.hasNext()) {
                String[] arr = input.nextLine().split(":");
                players.add(new Player(arr[0], Integer.parseInt(arr[1])));
            }
            input.close();


            Collections.sort(players, new Comparator<Player>() {
                public int compare(Player p1, Player p2) {
                    return Integer.compare(p2.score, p1.score);
                }
            });


            for (Player player : players) {
                model.addRow(new Object[]{player.name, player.score});

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public DefaultTableModel getModel() {
        return model;
    }

    private void creatTable() {
        model = new DefaultTableModel(new Object[][]{}, new String[]{"TOP Player", "Score"});

        table1 = new JTable(model) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Không cho phép sửa dữ liệu
            }
        };
        table1.setBackground(Color.PINK);
        JScrollPane sp = new JScrollPane(table1);
        sp.setBounds(20, 50, 650, 500);

        this.add(sp);
        // Căn giữa dữ liệu trong bảng
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        table1.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        table1.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);

    }

    public void addPlayer(String name, int score) {
        // Kiểm tra xem tên người chơi đã tồn tại trong danh sách hay chưa
        boolean ok=false;
        for (int i = 0; i < model.getRowCount(); i++) {
            if (name.equals(model.getValueAt(i, 0))) {
                ok=true;
                // Nếu tên người chơi đã tồn tại và điểm mới cao hơn điểm hiện tại, cập nhật điểm
                if (score > Integer.parseInt(model.getValueAt(i, 1).toString())) {
                    model.setValueAt(score, i, 1);
                }
                break;
            }


        }
        if (!ok) {
            model.addRow(new Object[]{name, score});
        }

        // Lưu dữ liệu vào file txt
        try (FileWriter writer = new FileWriter("BXH.txt",true)) {
            writer.write(name + ":" + score + "\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
     initData();
    }




}
