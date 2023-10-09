
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
// import java.sql.Connection;
// import java.sql.DriverManager;
// import java.sql.PreparedStatement;
// import java.sql.ResultSet;
// import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;

public class AdminDashboard extends JFrame {
    private JButton homeButton;
    public AdminDashboard() {
        
        setTitle("Admin Dashboard");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        
        panel.setLayout(new GridLayout(3, 1));
        

        
        JButton homeButton = new JButton("Home");
        JButton viewCustomerDataButton = new JButton("View Customer Data");
        JButton setMonthlyConsumptionButton = new JButton("Set Monthly Consumption");
        JButton viewComplaintsButton = new JButton("View Complaints");
       
        panel.add(homeButton);
        panel.add(viewCustomerDataButton);
        panel.add(setMonthlyConsumptionButton);
        panel.add(viewComplaintsButton);
        

        add(panel);

        viewCustomerDataButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Transition to view customer data screen
                viewCustomerData();
            }

        });


        setMonthlyConsumptionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openSetMonthlyConsumptionDialog();
            }
        });

        viewComplaintsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Transition to view complaints screen
                openViewComplaintsDialog();
            }
        });
        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Redirect to the home page
                openHomeScreen();
        }
        });
    }

        private void viewCustomerData() {
        JFrame customerDataFrame = new JFrame("Customer Data");
        customerDataFrame.setSize(600, 400);
        customerDataFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel customerDataPanel = new JPanel();
        customerDataPanel.setLayout(new GridLayout(1, 1));

        JTextArea customerDataTextArea = new JTextArea();
        customerDataTextArea.setEditable(false);

        // Fetch customer data from the database and populate the text area
        try {
            String url = "jdbc:mysql://localhost:3306/ramdb";
            String dbUsername = "root";
            String dbPassword = "Pass@321";

            Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword);

            String selectQuery = "SELECT username, name,consumption,id FROM cust_table";
            

            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);

            ResultSet resultSet = preparedStatement.executeQuery();

            StringBuilder customerData = new StringBuilder();
                            
            int i=1;    
            while (resultSet.next()) {
                String username = resultSet.getString("username");
                String name = resultSet.getString("name");
                String consumption = resultSet.getString("consumption");
                String id = resultSet.getString("id");
               
                customerData.append(" \n");
                customerData.append("-------------------------------").append("\n ");
                customerData.append("Details of Customer "+i+" :\n");
                customerData.append("-------------------------------").append("\n ");
                customerData.append("Username: ").append(username).append("\n");
                customerData.append("........").append("\n ");
                customerData.append("Name: ").append(name).append("\n");
                customerData.append("........").append("\n ");
                customerData.append("Consumption: ").append(consumption).append("\n ");
                customerData.append("........").append("\n ");
                customerData.append("Meter no.: ").append(id).append("\n ");
                customerData.append("-------------------------------").append("\n ");
                i++;
                
                
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();

            customerDataTextArea.setText(customerData.toString());
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(
                    AdminDashboard.this,
                    "An error occurred while retrieving customer data.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }

        customerDataPanel.add(new JScrollPane(customerDataTextArea));
        customerDataFrame.add(customerDataPanel);
        customerDataFrame.setVisible(true);
    }

    // ... (existing code)

    
        

    private void openViewComplaintsDialog() {
        try {
            String url = "jdbc:mysql://localhost:3306/ramdb";
            String dbUsername = "root";
            String dbPassword = "Pass@321";

            Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword);

            String selectQuery = "SELECT cust_name, complaints FROM complaints";

            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);

            ResultSet resultSet = preparedStatement.executeQuery();

            ArrayList<String> complaintsList = new ArrayList<>();

            while (resultSet.next()) {
                String username = resultSet.getString("cust_name");
                String complaintText = resultSet.getString("complaints");

                String complaintInfo = "Username: " + username +
                        "\nComplaint: " + complaintText;

                complaintsList.add(complaintInfo);
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();

            if (!complaintsList.isEmpty()) {
                String complaintsText = String.join("\n\n", complaintsList);
                JTextArea complaintsTextArea = new JTextArea(complaintsText);
                complaintsTextArea.setEditable(false);

                JOptionPane.showMessageDialog(
                        AdminDashboard.this,
                        new JScrollPane(complaintsTextArea),
                        "Customer Complaints",
                        JOptionPane.PLAIN_MESSAGE
                );
            } else {
                JOptionPane.showMessageDialog(
                        AdminDashboard.this,
                        "No complaints found.",
                        "Customer Complaints",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(
                    AdminDashboard.this,
                    "An error occurred while retrieving complaints.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void openSetMonthlyConsumptionDialog() {
        String customerUsername = JOptionPane.showInputDialog(
                AdminDashboard.this,
                "Enter customer username:",
                "Set Monthly Consumption",
                JOptionPane.QUESTION_MESSAGE
        );

        if (customerUsername != null && !customerUsername.isEmpty()) {
            String newConsumptionStr = JOptionPane.showInputDialog(
                    AdminDashboard.this,
                    "Enter new monthly consumption value for " + customerUsername + ":",
                    "Set Monthly Consumption",
                    JOptionPane.QUESTION_MESSAGE
            );

            if (newConsumptionStr != null && !newConsumptionStr.isEmpty()) {
                try {
                    double newConsumption = Double.parseDouble(newConsumptionStr);
                    updateConsumptionInDatabase(customerUsername, newConsumption);
                    JOptionPane.showMessageDialog(
                            AdminDashboard.this,
                            "Monthly consumption updated successfully.",
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(
                            AdminDashboard.this,
                            "Invalid consumption value. Please enter a valid number.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        }
    }
    private void updateConsumptionInDatabase(String username, double consumption) {
        String updateQuery = "UPDATE cust_table SET consumption = ? WHERE username = ?";
        String url = "jdbc:mysql://localhost:3306/ramdb";
        String dbUsername = "root";
        String dbPassword = "Pass@321";
        try (Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword);
            PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
            preparedStatement.setDouble(1, consumption);
            preparedStatement.setString(2, username);
            int rowsUpdated = preparedStatement.executeUpdate();
            System.out.println(preparedStatement);
            if (rowsUpdated > 0) {
                System.out.println("Consumption updated in the database.");
            } else {
                System.out.println("Failed to update consumption in the database.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("An error occurred while updating consumption in the database.");
        }
    }
    
    private void openHomeScreen() {
        HomeScreen homeScreen = new HomeScreen();
        homeScreen.setVisible(true);
        dispose(); 
    }


    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            AdminDashboard adminDashboard = new AdminDashboard();
            adminDashboard.setVisible(true);
        });
    }
}
