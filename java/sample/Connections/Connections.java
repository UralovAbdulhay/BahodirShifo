package sample.Connections;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Connections {


    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    protected Connection connect() {
        // SQLite connection string
        Connection conn = null;
        try {
            String dataBase = "baza/baza.db";
            conn = DriverManager.getConnection("jdbc:sqlite:" + dataBase);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public LocalDate parseToLocalDate(String value) {
        if (value == null) {
            value = "0001-01-01";
        }
        String[] s = value.split(" ");
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try {
            long l = Long.parseLong(s[0]);
            Date date = new Date();
            date.setTime(l);
            return LocalDate.parse(format.format(date), dateFormatter);
        } catch (NumberFormatException e) {
            return LocalDate.parse(s[0], dateFormatter);
        }
    }

    protected LocalDateTime parseToLocalDateTime(String value) {
        if (value == null) {
            value = "0001-01-01 00:00:00";
        }
        value = value.trim();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        try {
            long l = Long.parseLong(value);
            Date date = new Date();
            date.setTime(l);
            return LocalDateTime.parse(format.format(date), dateFormatter);
        } catch (NumberFormatException e) {
            return LocalDateTime.parse(value, dateFormatter);
        }
    }

    protected String localDateTimeParseToString(LocalDateTime dateTime) {
        System.out.println("connection localDateTimeParseToString dateTime = " + dateTime);
        return dateTime.format(dateTimeFormatter);
    }

    protected String localDateParseToString(LocalDate date) {
        return date.format(dateFormatter);
    }

}
