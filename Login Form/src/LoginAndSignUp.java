import javax.swing.*;
import java.sql.*;

public class LoginAndSignUp {

    public static void main(String[] args) {
        new LoginAndSignUp();
    }

    public LoginAndSignUp() {
        createFrame("Login and SignUp", 400, 400, createLoginPanel());
    }

    private JPanel createLoginPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel userLabel = createLabel("Username:", 100, 150, 80, 25);
        panel.add(userLabel);

        JTextField userTextField = createTextField(180, 150, 165, 25);
        panel.add(userTextField);

        JLabel passwordLabel = createLabel("Password:", 100, 180, 80, 25);
        panel.add(passwordLabel);

        JPasswordField passwordField = createPasswordField(180, 180, 165, 25);
        panel.add(passwordField);

        JButton loginButton = createButton("Login", 100, 210, 80, 25);
        loginButton.addActionListener(e -> login(userTextField.getText(), String.valueOf(passwordField.getPassword())));
        panel.add(loginButton);

        JButton signupButton = createButton("Sign Up", 200, 210, 80, 25);
        signupButton.addActionListener(e -> signUp());
        panel.add(signupButton);

        return panel;
    }

    private void createFrame(String title, int width, int height, JPanel panel) {
        JFrame frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width, height);
        frame.add(panel);
        frame.setVisible(true);
    }

    private JLabel createLabel(String text, int x, int y, int width, int height) {
        JLabel label = new JLabel(text);
        label.setBounds(x, y, width, height);
        return label;
    }

    private JTextField createTextField(int x, int y, int width, int height) {
        JTextField textField = new JTextField(20);
        textField.setBounds(x, y, width, height);
        return textField;
    }

    private JPasswordField createPasswordField(int x, int y, int width, int height) {
        JPasswordField passwordField = new JPasswordField(20);
        passwordField.setBounds(x, y, width, height);
        return passwordField;
    }

    private JButton createButton(String text, int x, int y, int width, int height) {
        JButton button = new JButton(text);
        button.setBounds(x, y, width, height);
        return button;
    }

    private void login(String username, String password) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/SYIT", "root", "NAYAK");
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM loginandsignup WHERE username=? AND password=?")) {
            statement.setString(1, username);
            statement.setString(2, password);
            try (ResultSet resultSet = statement.executeQuery()) {
                JOptionPane.showMessageDialog(null, resultSet.next() ? "Login Successful" : "Invalid username or password");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void signUp() {
        createFrame("Sign Up", 400, 400, createSignUpPanel());
    }

    private JPanel createSignUpPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel userLabel = createLabel("Username:", 100, 150, 80, 25);
        panel.add(userLabel);

        JTextField userTextField = createTextField(180, 150, 165, 25);
        panel.add(userTextField);

        JLabel passwordLabel = createLabel("Password:", 100, 180, 80, 25);
        panel.add(passwordLabel);

        JPasswordField passwordField = createPasswordField(180, 180, 165, 25);
        panel.add(passwordField);

        JButton signupButton = createButton("Sign Up", 100, 210, 80, 25);
        signupButton.addActionListener(e -> signUpAction(userTextField.getText(), String.valueOf(passwordField.getPassword())));
        panel.add(signupButton);

        return panel;
    }

    private void signUpAction(String username, String password) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/SYIT", "root", "NAYAK");
             PreparedStatement statement = connection.prepareStatement("INSERT INTO loginandsignup (username, password) VALUES (?, ?)")) {
            statement.setString(1, username);
            statement.setString(2, password);
            statement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Sign Up Successful");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
}
