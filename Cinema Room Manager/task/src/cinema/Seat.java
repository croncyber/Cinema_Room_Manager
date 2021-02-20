package cinema;

public class Seat {
    private int row;
    private int seatInRow;
    private int price;

    public Seat(int row, int seatInRow) {
        this.row = row;
        this.seatInRow = seatInRow;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getSeatInRow() {
        return seatInRow;
    }

    public void setSeatInRow(int seatInRow) {
        this.seatInRow = seatInRow;
    }

}
