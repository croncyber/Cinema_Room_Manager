package cinema;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Cinema {

    private static final int HIGH_PRICE = 10;
    private static final int LOW_PRICE = 8;

    private int rows;
    private int seats;
    private int total;
    private Seat seat;
    private char[][] cinemaSeats;
    private final List<Seat> seatList = new ArrayList<>();
    private boolean isInit = false;

    public Cinema() {
    }

//    public Cinema(int rows, int seats) {
//        this.rows = rows;
//        this.seats = seats;
//    }

    public void cinemaService() {
        if (!isInit) {
            initCinemaSeats();
            isInit = true;
        }

        boolean check = true;

        while (check) {
            System.out.println("\n1. Show the seats\n" +
                    "2. Buy a ticket\n" +
                    "3. Statistics\n" +
                    "0. Exit");
            Scanner scanner = new Scanner(System.in);
            int num = scanner.nextInt();

            switch (num) {
                case 1: {
                    showSeats(cinemaSeats);
                    break;
                }
                case 2: {
                    buyTicket();
                    break;
                }
                case 3: {
                    statistics();
                    break;
                }
                case 0: {
                    check = false;
                }
            }
        }
    }

    private void statistics() {
        calcTotal();
        long curIncome = seatList.stream().map(Seat::getPrice).reduce(0, Integer::sum);
        int allTickets = getRows()*getSeats();

        BigDecimal per = new BigDecimal((seatList.size()*100.00) / allTickets).setScale(2, RoundingMode.HALF_UP);
        System.out.println("Number of purchased tickets: " + seatList.size() + "\n" +
                "Percentage: " + per + "%\n" +
                "Current income: $" + curIncome + "\n" +
                "Total income: $" + total);
    }


    public void setCinemaSeat() {

        if (cinemaSeats[seat.getRow() - 1][seat.getSeatInRow() - 1] == 'B') {
            System.out.println("That ticket has already been purchased!");
            buyTicket();
        } else {
            cinemaSeats[seat.getRow() - 1][seat.getSeatInRow() - 1] = 'B';
            seatList.add(seat);
        }
    }

//    public void printTotalIncome() {
//        Scanner scanner = new Scanner(System.in);
//
//        System.out.println("Enter the number of rows:");
//        setRows(Math.min(scanner.nextInt(), 9));
//
//        System.out.println("Enter the number of seats in each row:");
//        setSeats(Math.min(scanner.nextInt(), 9));
//
//        System.out.printf("Total income:\n$%d", calcTotal());
//    }

    private void calcTotal() {
        int totalSeats = getSeats() * getRows();
        int firstHalfRows = getRows() / 2;
        int secondHalfRows = getRows() - firstHalfRows;

        if (totalSeats <= 60) {
            setTotal(totalSeats * 10);
        } else {
            setTotal((firstHalfRows * getSeats() * HIGH_PRICE) + (secondHalfRows * getSeats() * LOW_PRICE));
        }
    }

    public void buyTicket() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\nEnter a row number:");
        int row = scanner.nextInt();

        System.out.println("Enter a seat number in that row:");
        int seatInRow = scanner.nextInt();

        if (row>getRows() || seatInRow>getSeats()) {
            System.out.println("Wrong input!");
            cinemaService();
        }

        seat = new Seat(row, seatInRow);
        seat.setPrice(calcSeatPrice(seat));

        setCinemaSeat();
        System.out.printf("Ticket price: $%d \n", calcSeatPrice(seat));
    }

    private int calcSeatPrice(Seat seat) {
        int seatPrice;
        int totalSeats = getSeats() * getRows();
        int firstHalfRows = getRows() / 2;

        if (totalSeats <= 60) {
            seatPrice = HIGH_PRICE;
        } else {
            seatPrice = (seat.getRow() <= firstHalfRows ? HIGH_PRICE : LOW_PRICE);
        }

        return seatPrice;
    }

    public void showSeats(char[][] cinemaSeats) {
        System.out.println("Cinema:");
        int n = 0;

        while (getSeats() >= n) {
            System.out.print(n != 0 ? n + " " : "  ");
            ++n;
        }

        System.out.println();
        for (int i = 0; i < getRows(); i++) {
            System.out.print(i != 0 ? i + 1 + " " : 1 + " ");
            for (int j = 0; j < getSeats(); j++) {
                System.out.print(cinemaSeats[i][j] + " ");
            }
            System.out.println();
        }
    }

    private void initCinemaSeats() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the number of rows:");
        setRows(Math.min(scanner.nextInt(), 9));

        System.out.println("Enter the number of seats in each row:");
        setSeats(Math.min(scanner.nextInt(), 9));

        cinemaSeats = new char[getRows()][getSeats()];
        for (int i = 0; i < getRows(); i++) {
            for (int j = 0; j < getSeats(); j++) {
                cinemaSeats[i][j] = 'S';
            }
        }
    }

    private void setRows(int rows) {
        this.rows = rows;
    }

    private void setSeats(int seats) {
        this.seats = seats;
    }

    private void setTotal(int total) {
        this.total = total;
    }

    public int getRows() {
        return rows;
    }

    public int getSeats() {
        return seats;
    }


    public static void main(String[] args) {
        Cinema cinema = new Cinema();
        cinema.cinemaService();


    }

}
