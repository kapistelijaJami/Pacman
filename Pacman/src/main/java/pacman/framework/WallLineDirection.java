package pacman.framework;

/**
 * Enumia käytetään apuna seinien piirtämisessä, ja se tarjoaa seinille piirtosuunnat.
 */
public enum WallLineDirection {
    VERTICAL, HORIZONTAL, UP_LEFT, UP_RIGHT, DOWN_LEFT, DOWN_RIGHT;
    
    /**
     * Metodi muodostaa kahdesta suunnasta joko kulman, tai suoran.
     * 
     * @param first ensimmäinen suunta, jota käytetään viivan suunnan muodostamisessa
     * @param second toinen suunta, jota käytetään viivan suunnan muodostamisessa
     * 
     * @return palautetaan metodin muodostama suunta
     */
    public static WallLineDirection makeCorner(Direction first, Direction second) {
        if (first == null) {
            if (second == Direction.UP || second == Direction.DOWN) {
                return VERTICAL;
            } else {
                return HORIZONTAL;
            }
        } else if (second == null) {
            if (first == Direction.UP || first == Direction.DOWN) {
                return VERTICAL;
            } else {
                return HORIZONTAL;
            }
        }
        
        if (first == Direction.UP) {
            switch (second) {
                case UP:
                    return VERTICAL;
                    
                case LEFT:
                    return UP_LEFT;
                    
                case DOWN:
                    return VERTICAL;
                    
                case RIGHT:
                    return UP_RIGHT;
            }
        } else if (first == Direction.LEFT) {
            switch (second) {
                case UP:
                    return UP_LEFT;
                    
                case LEFT:
                    return HORIZONTAL;
                    
                case DOWN:
                    return DOWN_LEFT;
                    
                case RIGHT:
                    return HORIZONTAL;
            }
        } else if (first == Direction.DOWN) {
            switch (second) {
                case UP:
                    return VERTICAL;
                    
                case LEFT:
                    return DOWN_LEFT;
                    
                case DOWN:
                    return VERTICAL;
                    
                case RIGHT:
                    return DOWN_RIGHT;
            }
        } else if (first == Direction.RIGHT) {
            switch (second) {
                case UP:
                    return UP_RIGHT;
                    
                case LEFT:
                    return HORIZONTAL;
                    
                case DOWN:
                    return DOWN_RIGHT;
                    
                case RIGHT:
                    return HORIZONTAL;
            }
        }
        
        return VERTICAL;
    }
}
