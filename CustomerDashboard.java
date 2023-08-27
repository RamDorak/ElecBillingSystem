import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;

public class CustomerDashboard extends JFrame {
    private String customerName;
    private double consumption;

    public CustomerDashboard(String customerName, double consumption) {
        this.customerName = customerName;
        this.consumption = consumption;

        setTitle("Customer Dashboard");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1));

        JLabel nameLabel = new JLabel("Welcome, " + customerName + "!");
        JLabel consumptionLabel = new JLabel("Your monthly consumption: " + consumption + " kWh");
        
        double bill = calculateBill(consumption);
        JLabel billLabel = new JLabel("Your bill: $" + formatDecimal(bill));

        JButton raiseComplaintButton = new JButton("Raise Complaint");

        panel.add(nameLabel);
        panel.add(consumptionLabel);
        panel.add(billLabel);
        panel.add(raiseComplaintButton);

        add(panel);

        raiseComplaintButton.addActionListener(e -> {
            // Open the complaint form or dialog here
            // For example: new ComplaintForm(customerName).setVisible(true);
        });
    }

    private double calculateBill(double consumption) {
        // Calculate bill based on consumption and other factors
        // Replace this with your actual billing logic
        return consumption * 0.15; // Example calculation
    }

    private String formatDecimal(double value) {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        return decimalFormat.format(value);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            CustomerDashboard dashboard = new CustomerDashboard("John Doe", 200.0);
            dashboard.setVisible(true);
        });
    }
}
