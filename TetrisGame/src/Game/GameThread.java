package Game;

import GameView.Tetris;

public class GameThread extends Thread {
   private GameArea gameArea;
   private int diem=0;
   private GameForm gf;
   private int level=1;
   private int speedLevel=1000;
   private NextBlock nextblock ;

   private boolean isRunning = true;

  public GameThread(GameArea gameArea,GameForm gf,NextBlock nextBlock) {
          this.gameArea = gameArea;
          this.gf=gf;
            this.nextblock=nextBlock;
            gf.updatePoint(diem);
               gf.updateLevel(level);

    }
    @Override
    public void run() {
        while (isRunning) {

            gameArea.taoBlock();
            nextblock.updateNextBlock(gameArea);
            while (gameArea.moveBlockDown()) {
                try {
                    Thread.sleep(speedLevel);
                } catch (InterruptedException e) {
                    return;
                }
            }
            if (gameArea.isGameOver()) {
                Tetris.gameOver(diem);
                break;
            }
            gameArea.addBlockToGrid();
            diem += gameArea.checkFullRow();
            gf.updatePoint(diem);
            if (diem >= level * 100) {
                level++;
                gf.updateLevel(level);
                speedLevel -= 200;
            }
        }
    }


}
