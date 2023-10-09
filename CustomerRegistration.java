import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CustomerRegistration extends JFrame {
    private JTextField nameField;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton submitButton;

    public CustomerRegistration() {
        setTitle("Customer Registration");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2));

        JLabel nameLabel = new JLabel("Name:");
        nameField = new JTextField();

        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField();

        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();

        submitButton = new JButton("Submit");

        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(new JLabel()); //Empty label
        panel.add(submitButton);

        add(panel);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String username = usernameField.getText();
                char[] passwordChars = passwordField.getPassword();
                String password = new String(passwordChars);

                String url = "jdbc:mysql://localhost:3306/ramdb";
                String dbUsername = "root";
                String dbPassword = "Pass@321";

                String query = "INSERT INTO cust_table (name, username, password) VALUES (?, ?, ?)";

                try (Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword);
                     PreparedStatement preparedStatement = connection.prepareStatement(query)) {

                    preparedStatement.setString(1, name);
                    preparedStatement.setString(2, username);
                    preparedStatement.setString(3, password);

                    int rowsInserted = preparedStatement.executeUpdate();

                    if (rowsInserted > 0) {
                        JOptionPane.showMessageDialog(CustomerRegistration.this,
                                "Registration successful! You can now log in.",
                                "Registration Success",
                                JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(CustomerRegistration.this,
                                "Registration failed. Please try again.",
                                "Registration Failed",
                                JOptionPane.ERROR_MESSAGE);
                    }

                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(CustomerRegistration.this,
                            "An error occurred while processing your request.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
                nameField.setText("");
                usernameField.setText("");
                passwordField.setText("");
            }
        });
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            CustomerRegistration registrationScreen = new CustomerRegistration();
            registrationScreen.setVisible(true);
        });
    }
}
