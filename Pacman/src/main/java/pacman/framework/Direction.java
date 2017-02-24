package pacman.framework;

/**
 * Enum tarjoaa liikkumissuunnat pelin objekteille.
 */
public enum Direction {
    UP, LEFT, DOWN, RIGHT;
    
    /**
     * Metodi kertoo onko parametrina annettava suunta vastakkainen tämän suunnan kanssa.
     * 
     * @param compare Verrattava suunta
     * 
     * @return totuusarvo onko verrattava vastakkainen
     */
    public boolean isOpposite(Direction compare) {
        return compare == this.opposite();
    }
    
    /**
     * Metodi palauttaa vastakkaisen suunnan.
     * 
     * @return vastakkainen suunta
     */
    public Direction opposite() {
        if (this == LEFT) {
            return RIGHT;
        } else if (this == DOWN) {
            return UP;
        } else if (this == RIGHT) {
            return LEFT;
        } else {
            return DOWN;
        }
    }
    
    /**
     * Metodi palauttaa suuntaa vastaavan numeron. (Ylös = 0, Vasen = 1, Alas = 2, Oikea = 3).
     * 
     * @return suuntaa vastaava numero
     */
    public int getCorrespondingNumber() {
        if (this == UP) {
            return 0;
        } else if (this == LEFT) {
            return 1;
        } else if (this == DOWN) {
            return 2;
        } else {
            return 3;
        }
    }
    
    /**
     * Metodi palauttaa numeroa vastaavan suunnan. (Ylös = 0, Vasen = 1, Alas = 2, Oikea = 3).
     * 
     * @param number numero, jota kysytään
     * @return numeroa vastaava suunta
     */
    public static Direction getCorrespondingDirection(int number) {
        if (number == 0) {
            return UP;
        } else if (number == 1) {
            return LEFT;
        } else if (number == 2) {
            return DOWN;
        } else {
            return RIGHT;
        }
    }
}