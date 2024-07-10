/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chatbotty;

/**
 *
 * @author saif
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class UserAuthenticationGUI extends JFrame {
    private static final int USERS = 10000; // Maximum number of users
    private static String[] usernames = new String[USERS];
    private static String[] passwords = new String[USERS];
    private static String[] emails = new String[USERS];
    private static int userCount = 0;
    private static final String FILE_PATH = "user_data.txt";

    private JTextField usernameEmailField;
    private JPasswordField passwordField;
    private JButton signUpButton;
    private JButton signInButton;

    public UserAuthenticationGUI() {
        loadUserData(); // Load user data from file

        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // Change label color to yellow
        Color customColor = new Color(242, 170, 76);
        UIManager.put("Label.foreground", customColor);

        // Create components
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2, 10, 10));

        JLabel titleLabel = new JLabel("Sign In");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));

        JLabel usernameEmailLabel = new JLabel("Username/Email:");
        usernameEmailLabel.setHorizontalAlignment(JLabel.RIGHT);
        usernameEmailField = new JTextField(20);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setHorizontalAlignment(JLabel.RIGHT);
        passwordField = new JPasswordField(20);

        signUpButton = new JButton("Sign Up");
        signUpButton.setBackground(customColor);
        signUpButton.setForeground(Color.BLACK);
        signUpButton.setFocusPainted(false);

        signInButton = new JButton("Sign In");
        signInButton.setBackground(customColor);
        signInButton.setForeground(Color.BLACK);
        signInButton.setFocusPainted(false); // Remove the default focus border

        panel.add(titleLabel);
        panel.add(new JLabel());
        panel.add(usernameEmailLabel);
        panel.add(usernameEmailField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(signUpButton);
        panel.add(signInButton);

        // Set panel background color to black
        panel.setBackground(Color.BLACK);

        add(panel);

        signUpButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showSignUpDialog();
            }
        });

        signInButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                signIn();
            }
        });

        pack();
        setLocationRelativeTo(null); // Center the frame on the screen
        setVisible(true);
    }

    private void showSignUpDialog() {
        // Modify the sign-up dialog theme
        Color customColor = new Color(242, 170, 76);
        UIManager.put("OptionPane.background", Color.DARK_GRAY);
        UIManager.put("Panel.background", Color.DARK_GRAY);
        UIManager.put("Button.background", customColor);
        UIManager.put("Button.foreground", Color.BLACK);

        JDialog signUpDialog = new JDialog(this, "Sign Up", true);
        signUpDialog.setLayout(new GridLayout(8, 2, 10, 10));

        JLabel titleLabel = new JLabel("Sign Up");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));

        JLabel firstNameLabel = new JLabel("First Name:");
        firstNameLabel.setHorizontalAlignment(JLabel.RIGHT);
        JTextField firstNameField = new JTextField(20);

        JLabel lastNameLabel = new JLabel("Last Name:");
        lastNameLabel.setHorizontalAlignment(JLabel.RIGHT);
        JTextField lastNameField = new JTextField(20);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setHorizontalAlignment(JLabel.RIGHT);
        JTextField emailField = new JTextField(20);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setHorizontalAlignment(JLabel.RIGHT);
        JTextField usernameField = new JTextField(20);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setHorizontalAlignment(JLabel.RIGHT);
        JPasswordField passwordField = new JPasswordField(20);

        JButton signUpButton = new JButton("Sign Up");
        signUpButton.setBackground(customColor);
        signUpButton.setForeground(Color.BLACK);

        signUpDialog.add(titleLabel);
        signUpDialog.add(new JLabel());
        signUpDialog.add(firstNameLabel);
        signUpDialog.add(firstNameField);
        signUpDialog.add(lastNameLabel);
        signUpDialog.add(lastNameField);
        signUpDialog.add(emailLabel);
        signUpDialog.add(emailField);
        signUpDialog.add(usernameLabel);
        signUpDialog.add(usernameField);
        signUpDialog.add(passwordLabel);
        signUpDialog.add(passwordField);
        signUpDialog.add(new JLabel());
        signUpDialog.add(signUpButton);

        signUpButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String firstName = firstNameField.getText();
                String lastName = lastNameField.getText();
                String email = emailField.getText();
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                if (isUsernameTaken(username)) {
                    JOptionPane.showMessageDialog(signUpDialog, "Username is already taken. Please choose a different username.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    usernames[userCount] = username;
                    passwords[userCount] = password;
                    emails[userCount] = email;
                    userCount++;

                    saveUserData(); // Save user data to file
                    UIManager.put("OptionPane.background", Color.DARK_GRAY);
                    UIManager.put("OptionPane.messageForeground", customColor);
                    UIManager.put("Panel.background", Color.DARK_GRAY);
                    UIManager.put("Button.background", customColor);
                    UIManager.put("Button.foreground", Color.BLACK);
                    JOptionPane.showMessageDialog(signUpDialog, "Sign up successful. You can now sign in.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    signUpDialog.dispose();
                }
            }
        });

        signUpDialog.pack();
        signUpDialog.setLocationRelativeTo(this);
        signUpDialog.setVisible(true);
    }

    private boolean isUsernameTaken(String username) {
        for (int i = 0; i < userCount; i++) {
            if (usernames[i].equalsIgnoreCase(username)) {
                return true;
            }
        }
        return false;
    }

    private void signIn() {
        String usernameEmail = usernameEmailField.getText();
        String password = new String(passwordField.getPassword());

        int index = getUserIndex(usernameEmail);
        if (index != -1 && passwords[index].equals(password)) {
            String firstName = getUsernameFromIndex(index);
            // Set the UI theme for the success dialog
            Color customColor = new Color(242, 170, 76);
            UIManager.put("OptionPane.background", Color.BLACK);
            UIManager.put("OptionPane.messageForeground", customColor);
            UIManager.put("Panel.background", Color.BLACK);
            UIManager.put("Button.background", customColor);
            UIManager.put("Button.foreground", Color.BLACK);

            JOptionPane.showMessageDialog(this, "Welcome, " + firstName + "!\nYou have successfully signed in.", "Success", JOptionPane.INFORMATION_MESSAGE);
            clearFields();
            dispose();
            openChatbot(getUsernameFromIndex(index)); // Call the openChatbot method to open the Chatbot window
        } else {
            JOptionPane.showMessageDialog(this, "Invalid username/email or password. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private int getUserIndex(String usernameEmail) {
        for (int i = 0; i < userCount; i++) {
            if (usernames[i].equalsIgnoreCase(usernameEmail) || emails[i].equalsIgnoreCase(usernameEmail)) {
                return i;
            }
        }
        return -1;
    }

    private String getUsernameFromIndex(int index) {
        String[] nameParts = usernames[index].split(" ");
        return nameParts[0];
    }

    private void clearFields() {
        usernameEmailField.setText("");
        passwordField.setText("");
    }

    private void loadUserData() {
        try {
            File file = new File(FILE_PATH);
            if (!file.exists()) {
                return;
            }

            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            int i = 0;
            while ((line = reader.readLine()) != null && i < USERS) {
                String[] parts = line.split(",");
                usernames[i] = parts[0];
                passwords[i] = parts[1];
                emails[i] = parts[2];
                i++;
            }
            userCount = i;
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveUserData() {
        try {
            FileWriter writer = new FileWriter(FILE_PATH);
            for (int i = 0; i < userCount; i++) {
                writer.write(usernames[i] + "," + passwords[i] + "," + emails[i] + "\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openChatbot(String username) {
        // Code to open the chatbot window
        new Chatbot(username).setVisible(true);
    }

}
