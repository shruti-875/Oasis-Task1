package org.example.onlinereservation;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.*;

public class HelloController {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField trainNumberField;
    @FXML
    private TextField trainNameField;
    @FXML
    private TextField classTypeField;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField fromField;
    @FXML
    private TextField toField;
    @FXML
    private TextField pnrField;
    @FXML
    private TabPane tabPane;

    private Connection connect() {
        String url = "jdbc:mysql://localhost:3306/reservation_system";
        String user = "root";
        String password = "root";

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    @FXML
    protected void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        if (authenticate(username, password)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Login Successful!");
            alert.show();
            tabPane.getSelectionModel().selectNext();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Invalid Username or Password");
            alert.show();
        }
    }

    @FXML
    protected void handleReservation() {
        String name = nameField.getText();
        String trainNumber = trainNumberField.getText();
        String trainName = trainNameField.getText();
        String classType = classTypeField.getText();
        String dateOfJourney = datePicker.getValue().toString();
        String from = fromField.getText();
        String to = toField.getText();

        String sql = "INSERT INTO reservations(name, train_number, train_name, class_type, date_of_journey, from_place, to_place) VALUES(?,?,?,?,?,?,?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, trainNumber);
            pstmt.setString(3, trainName);
            pstmt.setString(4, classType);
            pstmt.setDate(5, Date.valueOf(dateOfJourney));
            pstmt.setString(6, from);
            pstmt.setString(7, to);
            pstmt.executeUpdate();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Reservation Successful!");
            alert.show();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    protected void handleCancellation() {
        String pnr = pnrField.getText();

        String sql = "INSERT INTO cancellations(reservation_id, pnr_number) VALUES(?,?)";

        // Assuming PNR number maps directly to reservation_id for simplicity
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, Integer.parseInt(pnr)); // Assuming PNR is reservation_id
            pstmt.setString(2, pnr);
            pstmt.executeUpdate();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Cancellation Successful!");
            alert.show();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private boolean authenticate(String username, String password) {
        String sql = "SELECT username, password FROM users WHERE username = ? AND password = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}