package Game.Block;

import Game.TetrisBlock;

public class LShape extends TetrisBlock {
    public LShape() {
        super(new int[][]{
                {1, 0},
                {1, 0},
                {1, 1}
        });
    }
}
