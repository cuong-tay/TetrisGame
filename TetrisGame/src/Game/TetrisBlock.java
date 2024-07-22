package Game;

import java.awt.*;
import java.util.Random;

public class TetrisBlock  {
     private int[][] shape;
     private Color color;
        private int x,y;
        private int[][][] shapes;
        private int currentRotation;
        private Color[] colors = {Color.cyan, Color.blue, Color.orange, Color.yellow, Color.green, Color.pink, Color.red};


    public TetrisBlock(int[][] shape) {
       this.shape = shape;
        initShapes();
    }


    public void roiXuong(int gridWidth) {
        Random r=new Random();
         currentRotation=r.nextInt(shapes.length);
         shape=shapes[currentRotation];
     y=-getHeight();
     x=r.nextInt(gridWidth-getWidth());
     color=colors[r.nextInt(colors.length)];
    }

    private void initShapes() {
      shapes = new int[4][][];
      for (int i = 0; i < 4; i++) {
          int r=shape[0].length;//số hàng
            int c=shape.length;//số cột
            shapes[i] = new int[r][c];//tạo mảng 2 chiều
            for (int j = 0; j < r; j++) {
                for (int k = 0; k < c; k++) {//duyệt mảng 2 chiều
                    shapes[i][j][k] = shape[c - 1 - k][j];//xoay 90 độ
                }
            }
            shape= shapes[i];
      }
    }

    public int[][] getShape() {
        return shape;
    }

    public void setShape(int[][] shape) {
        this.shape = shape;
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }

    public void moveDown() {
        y++;
    }

    public void moveLeft() {
        x--;
    }

    public void moveRight() {
        x++;
    }

    public void rotate() {
          currentRotation++;
            if (currentRotation >3) {
                currentRotation = 0;
            }
            shape = shapes[currentRotation];
    }


    public Color getColor() {
        return color;
    }

    public int getHeight() {
        return shape.length;
    }

    public int getWidth() {
        return shape[0].length;
    }

    public int getBottom() {
        return y + getHeight();
    }
    public int getLeft() {
        return x;
    }
    public int getRight() {
        return x + getWidth();
    }


}
