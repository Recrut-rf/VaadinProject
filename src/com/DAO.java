package com;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * Created by SOTRUDNIK on 14.06.2017.
 */
public class DAO {


    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("org.hsqldb.jdbc.JDBCDriver");
        return DriverManager.getConnection("jdbc:hsqldb:file:C:/Users/SOTRUDNIK/Desktop/DataBase/VAADINBase/database", "SA", "");
    }

    public static void closeConnection() throws ClassNotFoundException, SQLException {

        String sql = "SHUTDOWN";
        try (Statement statement = getConnection().createStatement()) {
            statement.execute(sql);
        }
    }

/////////////////////////////////////////////////
// for Customers ----------------------------------

    public static List<Customers> getCustomers() throws SQLException, ClassNotFoundException {
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement("SELECT * FROM customers");
             ResultSet resultSet = ps.executeQuery()) {
            ArrayList<Customers> customers = new ArrayList<>();

            while (resultSet.next()) {
                Integer id_cus = resultSet.getInt(1);
                String name = resultSet.getString(2);
                String surname = resultSet.getString(3);
                String patronymic = resultSet.getString(4);
                String phone = resultSet.getString(5);

                customers.add(new Customers(id_cus, name, surname, patronymic, phone));
            }
            return customers;
        }
    }


    public static void addCustomers(Customers customers) throws SQLException, ClassNotFoundException {
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement("INSERT INTO CUSTOMERS (NAME, SURNAME, PATRONYMIC, PHONE) VALUES (?, ?, ?, ?)")) {
            ps.setString(1, customers.getName());
            ps.setString(2, customers.getSurname());
            ps.setString(3, customers.getPatronymic());
            ps.setString(4, customers.getPhone());

            ps.executeUpdate();

            DAO.closeConnection();

        }
    }

    public static void deleteCustomers(Integer ID) throws SQLException, ClassNotFoundException {
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement("DELETE FROM CUSTOMERS WHERE ID_CUS = ?")) {
            ps.setInt(1, ID);
            ps.executeUpdate();
            DAO.closeConnection();
        }
    }

    public static List<Customers> getFilterCustomers(String str) throws SQLException, ClassNotFoundException {
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement("SELECT * FROM customers WHERE surname = " + "'" + str + "'");
             ResultSet resultSet = ps.executeQuery()) {
            ArrayList<Customers> customers = new ArrayList<>();

            while (resultSet.next()) {
                Integer id_cus = resultSet.getInt("ID_CUS");
                String name = resultSet.getString("NAME");
                String surname = resultSet.getString("SURNAME");
                String patronymic = resultSet.getString("PATRONYMIC");
                String phone = resultSet.getString("PHONE");

                customers.add(new Customers(id_cus, name, surname, patronymic, phone));
            }
            return customers;
        }
    }

/////////////////////////////////////////////////////////
    //  for Ordering -------------------------------------------------

    public static List<Ordering> getOrdering() throws SQLException, ClassNotFoundException {
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement("SELECT * FROM ordering");
             ResultSet resultSet = ps.executeQuery()) {
            ArrayList<Ordering> ordering = new ArrayList<>();

            while (resultSet.next()) {
                ordering.add(new Ordering(resultSet.getInt("ID_OR"),
                        resultSet.getString("DESCRIPTION"),
                        resultSet.getString("CUSTOMERS"),
                        resultSet.getDate("CREATEDDATE"),
                        resultSet.getDate("ENDDATE"),
                        resultSet.getString("COST"),
                        resultSet.getString("STATUS")));
            }
            return ordering;
        }
    }

    public static void addOrdering(Ordering ordering) throws SQLException, ClassNotFoundException {
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement("INSERT INTO ordering" +
                     " (description, customers, createddate, enddate, cost, status) VALUES (?, ?, ?, ?, ?, ?)")) {
            ps.setString(1, ordering.getDescription());
            ps.setString(2, ordering.getCustomerInt());
            ps.setDate(3, new Date(Calendar.getInstance().getTimeInMillis()));
            ps.setDate(4, new Date(Calendar.getInstance().getTimeInMillis()));
            ps.setString(5, ordering.getCost());
            ps.setString(6, ordering.getStatus());
            ps.executeUpdate();
            DAO.closeConnection();
        }
    }

    public static void deleteOrdering(Integer ID) throws SQLException, ClassNotFoundException {
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement("DELETE FROM ordering WHERE ID_OR = ?")) {
            ps.setInt(1, ID);
            ps.executeUpdate();
            DAO.closeConnection();
        }
    }

    public static List<Ordering> getFilterOrdering(String str) throws SQLException, ClassNotFoundException {
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement("SELECT * FROM ordering WHERE customers = " + "'" + str + "'");
             ResultSet resultSet = ps.executeQuery()) {
            ArrayList<Ordering> ordering = new ArrayList<>();

            while (resultSet.next()) {
                ordering.add(new Ordering(resultSet.getInt("ID_OR"),
                        resultSet.getString("DESCRIPTION"),
                        resultSet.getString("CUSTOMERS"),
                        resultSet.getDate("CREATEDDATE"),
                        resultSet.getDate("ENDDATE"),
                        resultSet.getString("COST"),
                        resultSet.getString("STATUS")));
            }
            return ordering;
        }
    }

}