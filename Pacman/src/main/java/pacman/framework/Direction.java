package pacman.framework;

public class Direction {
    private String direction;
    
    public Direction(String direction) {
        if (direction.equals("left") || direction.equals("down") || direction.equals("right") || direction.equals("up")) {
            this.direction = direction;
        } else {
            this.direction = "left";
        }
    }
    
    public String getDirection() {
        return this.direction;
    }
    
    public void setDirection(String direction) {
        if (direction.equals("left") || direction.equals("down") || direction.equals("right") || direction.equals("up")) {
            this.direction = direction;
        }
    }
    
    public boolean equals(String direction) {
        return this.direction.equals(direction);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        
        if (getClass() != obj.getClass()) {
            return false;
        }
        
        Direction other = (Direction) obj;
        
        if (!this.direction.equals(other.direction)) {
            return false;
        }
        
        return true;
    }
    
}
