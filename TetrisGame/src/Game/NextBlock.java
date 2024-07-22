package Game;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class NextBlock extends JPanel {
    private TetrisBlock nextBlock;

    public NextBlock() {
        this.setBounds(550, 220, 140, 140);
        this.setPreferredSize(new Dimension(140, 140));
        this.setBorder(new LineBorder(Color.black));
        this.setBackground(Color.lightGray);

    }

    public void setNextBlock(TetrisBlock nextBlock) {
        this.nextBlock = nextBlock;
    }
    public TetrisBlock getNextBlock() {
        return nextBlock;
    }
    public void updateNextBlock(GameArea block) {
        this.nextBlock=block.getNextBlock();
          repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (nextBlock != null) {
            drawNextBlock(g);
        }
    }
    private void drawNextBlock(Graphics g) {
        int[][] shape = nextBlock.getShape();
        int size = 27;
        int blockWidth = shape[0].length * size;
        int blockHeight = shape.length * size;

        int startX = (this.getWidth() - blockWidth) / 2;
        int startY = (this.getHeight() - blockHeight) / 2;
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[i].length; j++) {
                if (shape[i][j] == 1) {
                    g.setColor(nextBlock.getColor());
                    g.fillRect(startX + j * size, startY + i * size, size, size);
                    g.setColor(Color.black);
                    g.drawRect(startX + j * size, startY + i * size, size, size);
                }
            }
        }
    }
}
