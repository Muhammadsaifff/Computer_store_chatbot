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
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashSet;
import java.util.Set;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

public class Chatbot extends JFrame {
    private JTextArea chatArea;
    private JTextArea inputField;
    private JScrollPane scrollPane;
    private JButton sendButton;
    private String username;
    private UserAuthenticationGUI userAuthGUI;
    private String currentInput;
    private Set<String> bookedSchedules;
    private static final Color BACKGROUND_COLOR = new Color(00,00,00);
    private static final Color TEXT_COLOR = new Color(250, 180, 80);
    private static final Color in_BACKGROUND_COLOR = new Color(255, 240, 225);
    private static final Color in_TEXT_COLOR = new Color(153, 51, 0);

    public Chatbot(String username) {
        this.username = username;
        this.bookedSchedules = new HashSet<>();

        setTitle("Chatbot");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        chatArea = new JTextArea(20, 50);
        chatArea.setEditable(false);
        chatArea.setLineWrap(true);
        chatArea.setBackground(BACKGROUND_COLOR);
        chatArea.setForeground(TEXT_COLOR);

        scrollPane = new JScrollPane(chatArea);

        inputField = new JTextArea(2, 40);
        inputField.setBackground(in_BACKGROUND_COLOR);
        inputField.setForeground(in_TEXT_COLOR);

        ImageIcon symbolIcon = new ImageIcon("paper-plane.png");
        Image image = symbolIcon.getImage();

        // Scale the image to fit the desired size
        Image scaledImage = image.getScaledInstance(32, 32, Image.SCALE_SMOOTH);

        // Create a new ImageIcon with the scaled image
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        sendButton = new JButton();
        sendButton.setPreferredSize(new Dimension(32, 32));
        sendButton.setIcon(scaledIcon);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout());
        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.LINE_END);

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(inputPanel, BorderLayout.SOUTH);

        add(panel);

        pack();
        setLocationRelativeTo(null); // Center the frame on the screen

        sendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                processInput();
            }
        });

        inputField.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "processInput");
        inputField.getActionMap().put("processInput", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                processInput();
            }
        });

        displayIntro();
    }

    private void displayIntro() {
        String introMessage = "Welcome, " + username + "!\n\n";
        introMessage += "I am Techy Bot of XYZ Store.\n";
        introMessage += "How may I assist you today?\n\n";
        introMessage += "Menu:\n";
        introMessage += "0 - Sign Out\n";
        introMessage += "1 - Repair Schedule\n";
        introMessage += "2 - PC Parts Prices\n";
        introMessage += "3 - PreBuilds\n\n";
        introMessage += "Please enter a menu option or type your message.";

        chatArea.append(introMessage);
    }

    private void processInput() {
        String input = inputField.getText();
        inputField.setText("");

        chatArea.append("\n" + username + ": " + input + "\n");

        // Process the input here and generate appropriate responses
        String response = generateResponse(input);

        chatArea.append("\nTechy: " + response);
        chatArea.setCaretPosition(chatArea.getDocument().getLength());

        // Additional logic for sign out
        if (response.equalsIgnoreCase("You have been signed out. Goodbye!")) {
            userAuthGUI.setVisible(true);
            dispose();
        }
    }
private String generateResponse(String input) {
    String response;
    String ignorecase= input.toLowerCase();
    try{
    if (input.equalsIgnoreCase("hello") || input.equalsIgnoreCase("hi")) {
        response = "Hello! How can I help you?\n\n";
    } else if (input.equalsIgnoreCase("ok") ||input.equalsIgnoreCase("okay")) {
        response = "Okay!";
    } else if (ignorecase.contains("how are you")) {
        response = "I'm an AI, so I don't have feelings, but thank you for asking!";
    } else if (ignorecase.contains("weather")) {
        response = "I'm sorry, I don't have access to real-time weather information.";
    } else if (ignorecase.contains("bye")) {
        response = "Goodbye! Have a great day!";
    } else if (ignorecase.contains("your developer") || ignorecase.contains("Who developed you")){
        response = "I have been developed by a group of guys. \nTheir names are :\n Furqan Ahmed\n Muhammad Saif Shakil\nMuhammad Shahzaib\nMuhammad Romaan\n";
    } else if (input.equalsIgnoreCase("how are you?")) {
        response = "I'm doing great, thank you for asking!\n\n";
    } else if (input.equalsIgnoreCase("1") || input.equalsIgnoreCase("Repair Schedule")) {
        response = "Enter the date of this month: ";
        currentInput = "date";
    } else if (input.equalsIgnoreCase("2") || input.equalsIgnoreCase("PC Parts Prices")) {
        response = "Here are the prices of PC parts:\n\n";
        response += "1. CPUs:\n";
        response += "   - Intel Core i5 9600K: $200\n";
        response += "   - AMD Ryzen 7 5800X: $250\n";
        response += "   - Intel Core i7 10700K: $300\n";
        response += "2. GPUs:\n";
        response += "   - NVIDIA GeForce GTX 1660: $400\n";
        response += "   - AMD Radeon RX 580: $350\n";
        response += "   - NVIDIA GeForce RTX 3060: $500\n";
        response += "3. RAM:\n";
        response += "   - 8GB DDR4: $100\n";
        response += "   - 16GB DDR4: $150\n";
        response += "   - 32GB DDR4: $250\n";
        response += "4. Storage:\n";
        response += "   - 500GB SSD: $150\n";
        response += "   - 1TB SSD: $200\n";
        response += "   - 2TB HDD: $100\n";
        response += "5. Power Supply Units (PSUs):\n";
        response += "   - 450W: $80\n";
        response += "   - 650W: $120\n";
        response += "   - 850W: $200\n\n";
    } else if (input.equalsIgnoreCase("3") || input.equalsIgnoreCase("PreBuilds")) {
        response = "We offer the following pre-built PCs:\n\n";
        response += "1. Basic PC:\n";
        response += "   - Price: $500\n";
        response += "   - Specs:\n";
        response += "     - CPU: Intel Core i5 9400F\n";
        response += "     - GPU: AMD Radeon RX 5500XT\n";
        response += "     - PSU: 450W 80+ Bronze\n";                        
        response += "     - RAM: 8GB DDR4\n";
        response += "     - Storage: 500GB HDD\n\n";
        response += "2. Gaming PC:\n";
        response += "   - Price: $1000\n";
        response += "   - Specs:\n";
        response += "     - CPU: AMD Ryzen 5 3600\n";
        response += "     - GPU: NVIDIA GeForce RTX 3060\n";
        response += "     - PSU: 650W 80+ Gold\n";                        
        response += "     - RAM: 16GB DDR4\n";
        response += "     - Storage: 1TB SSD\n\n";
        response += "3. Workstation PC:\n";
        response += "   - Price: $1500\n";
        response += "   - Specs:\n";
        response += "     - CPU: Intel Core i7 11700k\n";
        response += "     - GPU: NVIDIA RTX 4070\n";
        response += "     - PSU: 850W 80+ Platinum\n";                        
        response += "     - RAM: 32GB DDR4\n";
        response += "     - Storage: 2TB HDD\n\n";
    } else if (input.equalsIgnoreCase("0") || input.equalsIgnoreCase("Sign Out")){
    
        response = "You have been signed out. Goodbye!";
        dispose();
        UserAuthenticationGUI userAuthGUI = new UserAuthenticationGUI();
        userAuthGUI.setVisible(true);
    
    } else if (currentInput.equals("date")) {
        response = validateDate(input);
        if (response.isEmpty()) {
            currentInput = "time";
            response = "Enter the time from 10AM-12PM in HH:MM format with intervals of 30 minutes:\n";
        }
    } else if (currentInput.equals("time")) {
        response = validateTime(input);
        if (response.isEmpty()) {
            response = bookRepairSchedule(currentInput, input);
            currentInput = ""; // Reset currentInput after booking the schedule
        }
    } else {
        response = "I'm sorry, I didn't understand that. Please enter a valid menu option or type your message.\n\n";
    }

    return response;
}
    catch (Exception e){
        response = "I'm sorry, I didn't understand that. Please enter a valid menu option or type your message.\n\n";
        return response; }
        }
  //private String currentInput = ""; // Add a variable to track the current input mode
    private String selectedDate = ""; // Add a new variable to store the selected date

private String validateDate(String dateInput) {
    String response = "";

    try {
        int day = Integer.parseInt(dateInput);
        LocalDate currentDate = LocalDate.now();

        if (day < 1 || day > currentDate.lengthOfMonth()) {
            // Check if the entered date has already passed
            if (currentDate.getDayOfMonth() > day) {
                // Add 1 month to the current date to get the next month
                LocalDate nextMonth = currentDate.plusMonths(1);
                selectedDate = String.format("%02d-%02d", day, nextMonth.getMonthValue()); // Store the selected date for next month
                response = "Enter the time in HH:MM format from 10AM to 12PM:\n";
                currentInput = "time"; // Set the currentInput to "time" to prompt for time input
            } else {
                response = "Please enter a valid date within the current month.\n";
            }
        } else {
            selectedDate = String.format("%02d-%02d", day, currentDate.getMonthValue()); // Store the selected date for the current month
            response = "Enter the time in HH:MM format from 10AM to 12PM:\n";
            currentInput = "time"; // Set the currentInput to "time" to prompt for time input
        }
    } catch (NumberFormatException e) {
        response = "Please enter a valid date in the format DD.\n";
    }

    return response;
}

private String validateTime(String timeInput) {
    String response = "";

    try {
        LocalTime time = LocalTime.parse(timeInput, DateTimeFormatter.ofPattern("HH:mm"));
        LocalDate currentDate = LocalDate.now();
        
        if (time.isBefore(LocalTime.parse("10:00")) || time.isAfter(LocalTime.parse("23:30"))) {
            response = "Please enter a valid time between 10AM and 12PM.\n";
        } else if (selectedDate.equals(currentDate.format(DateTimeFormatter.ofPattern("dd-MM"))) && time.isBefore(LocalTime.now())) {
            response = "This time has already passed. Please select a future time.\n";
        } else {
            response = bookRepairSchedule(selectedDate, timeInput);
            currentInput = ""; // Reset currentInput after booking the schedule
            selectedDate = ""; // Reset selectedDate after booking the schedule
        }
    } catch (DateTimeParseException e) {
        response = "Please enter a valid time in the format HH:MM.\n";
    }

    return response;
}
private HashSet<String> bookedTimeSlots = new HashSet<>();
private String bookRepairSchedule(String date, String time) {
    String response;

    String bookingInfo = time + ", " + date;
    String bookingLine = bookingInfo + System.lineSeparator();

    if (bookedTimeSlots.contains(bookingInfo)) {
        response = "I'm sorry, the time slot " + time + " on " + date + " has already been booked.\n";
    } else {
        try {
            FileWriter writer = new FileWriter("bookings.txt", true);

            // Split the date into day and month parts
            String[] dateParts = date.split("-");
            int day = Integer.parseInt(dateParts[0]);
            int month = Integer.parseInt(dateParts[1]);

            // Get the current date
            LocalDate currentDate = LocalDate.now();

            // Check if the entered date has already passed
            if (currentDate.getDayOfMonth() > day) {
                // Add 1 month to the current date to get the next month
                LocalDate nextMonth = currentDate.plusMonths(1);
                int nextMonthValue = nextMonth.getMonthValue();

                // Get the full display name of the next month in English
                String nextMonthName = nextMonth.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH);

                writer.write(time + ", " + String.format("%02d-%02d", day, nextMonthValue) + System.lineSeparator());
                writer.close();
                bookedTimeSlots.add(bookingInfo);

                response = "Your time has been booked for " + time + " on " + day + getOrdinalSuffix(day) +
                        " of " + nextMonthName + ", " + nextMonth.getYear() + ".\n";
            } else {
                writer.write(bookingLine);
                writer.close();
                bookedTimeSlots.add(bookingInfo);

                String currentMonthName = currentDate.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
                String currentYear = String.valueOf(currentDate.getYear());

                response = "Your time has been booked for " + time + " on " + day + getOrdinalSuffix(day) +
                        " of " + currentMonthName + ", " + currentYear + ".\n";
            }
        } catch (IOException e) {
            response = "An error occurred while storing the booking information.\n";
            return response;
        }
    }

    return response;
}
    private String getOrdinalSuffix(int day) {
        if (day >= 11 && day <= 13) {
            return "th";
        } else {
            switch (day % 10) {
                case 1:
                    return "st";
                case 2:
                    return "nd";
                case 3:
                    return "rd";
                default:
                    return "th";
            }
        }
    }
    
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }
    
}