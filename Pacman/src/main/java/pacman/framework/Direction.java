package pacman.framework;

public enum Direction {
    UP, LEFT, DOWN, RIGHT;
    
    public boolean isOpposite(Direction compare) {
        return compare == this.opposite();
    }
    
    public Direction opposite() {
        if (this == LEFT) {
            return RIGHT;
        } else if (this == DOWN) {
            return UP;
        } else if (this == RIGHT) {
            return LEFT;
        } else if (this == UP) {
            return DOWN;
        }
        
        return this;
    }
}