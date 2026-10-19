package com.scc.dao;

import com.scc.model.ScenarioEntity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ScenarioDao {

    public void insertRecord(String customerName, String drinkItem, double price, String status) {
        String sql = "INSERT INTO orders (customer_name, drink_item, price, status) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, customerName);
            pstmt.setString(2, drinkItem);
            pstmt.setDouble(3, price);
            pstmt.setString(4, status);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error inserting record: " + e.getMessage());
        }
    }

    public List<ScenarioEntity> getAllRecords() {
        List<ScenarioEntity> orders = new ArrayList<>();
        String sql = "SELECT id, customer_name, drink_item, price, status FROM orders";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                orders.add(new ScenarioEntity(
                        rs.getInt("id"),
                        rs.getString("customer_name"),
                        rs.getString("drink_item"),
                        rs.getDouble("price"),
                        rs.getString("status")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching records: " + e.getMessage());
        }
        return orders;
    }
}