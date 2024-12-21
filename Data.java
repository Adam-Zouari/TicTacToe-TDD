public class Data {
    private int turn;
    private int x;
    private int y;
    private char player;

    public Data(int turn, int x, int y, char player) {
        this.turn = turn;
        this.x = x;
        this.y = y;
        this.player = player;
    }

    public int getTurn() {
        return turn;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public char getPlayer() {
        return player;
    }

    @Override
    public String toString() {
        return String.format("Turn: %d; X: %d; Y: %d; Player: %s", turn, x, y, player);
    }
}