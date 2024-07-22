package Game;

import Game.Block.*;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

import java.util.Random;


public class GameArea extends JPanel {
    public static int gridColumns;
    public static   int gridRows;
    public static int gridSize;
   private final Color[][] grid; // tao ra 1 ma trận màu để vẽ các block
  private TetrisBlock block;
   private TetrisBlock[] blocks;
   private TetrisBlock nextBlock;
    int width = gridColumns * gridSize;
    int height = gridRows * gridSize;
   public GameArea() {

        this.gridSize = 30;
        gridColumns = 12;
        this.gridRows = 20;
       this.setBounds(170, 2, width, height);
        this.setPreferredSize(new Dimension(width, height));//kích thước của panel trùng với kích thước của lưới chơi
        this.setBorder(new LineBorder(Color.black));
       grid = new Color[gridRows][gridColumns];
        blocks = new TetrisBlock[]{new IShape(), new LShape(), new OShape(), new TShape(), new ZShape(),new SShape(),new JShape()};
         this.setBackground(Color.black);
    }



//    private int blockCount = 0; // Biến đếm số lượng khối đã rơi xuống

    public void taoBlock(){
        Random r = new Random();
            int randomIndex = r.nextInt(blocks.length);
            block = blocks[randomIndex];
        block.roiXuong(gridColumns);
        nextBlock = block;
    }

    public TetrisBlock getNextBlock() {
        return nextBlock;
    }


    public void moveBlockRight(){
        if (block == null) {
            return;
        }
        if (!checkRight() ) return;

        block.moveRight();

        repaint();
    }
    public void moveBlockLeft(){
        if (block == null) {
            return;
        }
        if ( !checkLeft()) return;
        block.moveLeft();


        repaint();
    }
    public void rotateBlock(){
        if (block==null) return;
        block.rotate();
        // Kiểm tra xem sau khi xoay khối có chạm vào biên trái hoặc phải không
        if (block.getLeft() < 0) {
            block.setX(0);
        } else if (block.getRight() > gridColumns) {
            block.setX(gridColumns - block.getWidth());
        }
        // Kiểm tra xem sau khi xoay khối có chạm vào biên dưới không
        if (block.getBottom() >= gridRows) {
            block.setY(gridRows - block.getHeight());
        }
        // Kiểm tra xem sau khi xoay khối có chạm vào khối khác không
        if (checkchamKhoiKhac()) return;


        repaint();
    }

    private boolean checkchamKhoiKhac() {
        int[][] shape = block.getShape();
        for (int i = 0; i < block.getHeight(); i++) {
            for (int j = 0; j < block.getWidth(); j++) {
                if (shape[i][j] == 1) {
                    int x = block.getX() + j;//tọa độ x của block
                    int y = block.getY() + i;//tọa độ y của block
                    if (y >= 0 && grid[y][x] != null) {
                        block.rotate();
                        block.rotate();
                        block.rotate();
                        return true;
                    }
                }
            }
        }
        return false;
    }


    public void dropBlock(){ // mũi tên xuống là rơi ngay
         while(checkBottom()){
             block.moveDown();
         }
         repaint();

    }
    //kiểm tra xem game đã kết thúc chưa
    public boolean isGameOver(){
        if(!checkBottom() && block.getY()<0){
            block=null;
            return true;
        }
        return false;
    }

    public boolean moveBlockDown(){
        if (!checkBottom() || block == null) {
            return false;
        }
        block.moveDown();
        repaint();//gọi lại hàm paintComponent để vẽ lại block
        return true;
    }
    //kiểm tra xem block đã chạm đáy chưa
    private boolean checkBottom() {
        if ( block.getBottom() == gridRows) {
            return false;
        }
       int[][] shape = block.getShape();
        for(int col=0;col<block.getWidth();col++) {
            //duyệt từ dưới lên
            for (int row = block.getHeight() - 1; row >= 0; row--) {
                if(shape[row][col]!=0) {
                   int x=block.getX()+col;
                     int y=block.getY()+row+1;
                     if(y<0) break;
                    if (grid[y][x] != null) {
                        return false;
                    }
                    break;
                }
            }
        }
        return true;
    }

    //kiểm tra xem block có chạm cạnh trái không
    private boolean checkLeft() {
       if(block.getLeft()==0) return false;
        int[][] shape = block.getShape();
        for (int row = 0; row < block.getHeight(); row++){
            //duyệt từ trái sang phải
            for(int col=0;col<block.getWidth();col++) {
                if(shape[row][col]!=0) {
                    int x=block.getX()+col-1;
                    int y=block.getY()+row;
                    if(y<0) break;
                    if (grid[y][x] != null) {
                        return false;
                    }
                    break;
                }

        }}
         return true;
    }


    //kiểm tra xem block có chạm cạnh phải không
    private  boolean checkRight(){
        if(block.getRight()==gridColumns) return false;

        int[][] shape = block.getShape();
        for (int row = 0; row <block.getHeight(); row++){//duyệt từ trên xuống
            for(int col=block.getWidth()-1;col>=0;col--) {//duyệt từ phải sang trái
                if(shape[row][col]!=0) {
                    int x=block.getX()+col+1;
                    int y=block.getY()+row;
                    if(y<0) break;
                    if (grid[y][x] != null) {
                        return false;
                    }
                    break;
                }

            }}
        return true;
    }
    public int checkFullRow(){//kiểm tra hàng đầy
        int point=0;
      for (int i = gridRows-1; i >= 0; i--) {
            boolean full = true;
            for (int j = 0; j < gridColumns; j++) {
                if (grid[i][j] == null ) {
                    full = false;
                    break;
                }

            }
            if (full) {
                point+=10;
                clearLine(i);// xóa hàng đầy đó đi và dịch các hàng trên nó xuống dưới
                shiftLine(i);// dịch các hàng trên nó xuống dưới
                 clearLine(0);
                i++;
                repaint();
            }
        }
      return point;

    }
    // xóa hàng đầy
    private void clearLine(int row){
      for(int i=0;i<gridColumns;i++){
          grid[row][i]=null;
      }
    }
    // dịch các hàng trên nó xuống dưới
    private void shiftLine(int row){
        for(int i=row;i>0;i--){
            for(int j=0;j<gridColumns;j++){
                grid[i][j]=grid[i-1][j];
            }
        }
    }
    //vẽ block
    private void drawBlock(Graphics g) {
        for (int i = 0; i < block.getHeight(); i++) {
            for (int j = 0; j < block.getWidth(); j++) {
                if (block.getShape()[i][j] == 1)
                {   int x = (block.getX() + j)*gridSize;
                    int y = (block.getY() + i)*gridSize;
                    g.setColor(block.getColor());
                  g.fillRect(x, y, gridSize, gridSize);
                    g.setColor(Color.black);
                  g.drawRect(x, y, gridSize, gridSize);
                }
            }
        }
    }
     public void addBlockToGrid() {//thêm block vào grid
         for (int r = 0; r < block.getHeight(); r++) {
             for (int c = 0; c < block.getWidth(); c++) {
                 if (block.getShape()[r][c] == 1) {
                     grid[block.getY() + r][block.getX() + c] = block.getColor();
                 }
             }
         }
     }
    private void drawBackground(Graphics g) {
      Color color;
        for (int i = 0; i < gridRows; i++) {
            for (int j = 0; j < gridColumns; j++) {
                color = grid[i][j];
                if (grid[i][j] != null) {//nếu ô đó đã được vẽ màu thì vẽ lại

                    int x = j*gridSize;
                    int y = i*gridSize;
                    g.setColor(color);
                    g.fillRect(x, y, gridSize, gridSize);
                    g.setColor(Color.black);
                    g.drawRect(x, y, gridSize, gridSize);
                }
            }
        }
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBackground(g);
        drawBlock(g);
    }



}