package jogo;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try {
            new Jogo();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}