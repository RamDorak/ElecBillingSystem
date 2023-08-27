import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
// import java.sql.Connection;
// import java.sql.DriverManager;
// import java.sql.PreparedStatement;
// import java.sql.ResultSet;
// import java.sql.SQLException;

public class AdminDashboard extends JFrame {
    public AdminDashboard() {
        setTitle("Admin Dashboard");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));

        JButton viewCustomerDataButton = new JButton("View Customer Data");
        JButton setMonthlyConsumptionButton = new JButton("Set Monthly Consumption");
        JButton viewComplaintsButton = new JButton("View Complaints");

        panel.add(viewCustomerDataButton);
        panel.add(setMonthlyConsumptionButton);
        panel.add(viewComplaintsButton);

        add(panel);

        viewCustomerDataButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Transition to view customer data screen
                // For example: new ViewCustomerDataScreen().setVisible(true);
            }
        });

        setMonthlyConsumptionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Transition to set monthly consumption screen
                // For example: new SetMonthlyConsumptionScreen().setVisible(true);
            }
        });

        viewComplaintsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Transition to view complaints screen
                // For example: new ViewComplaintsScreen().setVisible(true);
            }
        });
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            AdminDashboard adminDashboard = new AdminDashboard();
            adminDashboard.setVisible(true);
        });
    }
}
