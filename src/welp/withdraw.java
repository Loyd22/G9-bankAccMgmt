package welp;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate; 
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;

public class Withdraw1 extends JFrame implements ActionListener {

    private JButton onek, fivek, tenk, fiftink, withdrawbutton, backbutton;
    private JTextField textfield2;
    private JPanel rightpanel;
    private JLabel label1, label2, dateLabel; 
    private int balance; 
    
    private Connection c;

    Withdraw1() {
        setTitle("IT Bank");
        setSize(700, 640);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        onek = new JButton("1000");
        onek.setBounds(50, 100, 190, 50);
        onek.setBackground(Color.LIGHT_GRAY);
        onek.setFont(new Font("Georgia", Font.PLAIN, 30));
        onek.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        onek.addActionListener(this);
        add(onek);
        
        fivek = new JButton("5000");
        fivek.setBounds(50, 200, 190, 50);
        fivek.setBackground(Color.LIGHT_GRAY);
        fivek.setFont(new Font("Georgia", Font.PLAIN, 30));
        fivek.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        fivek.addActionListener(this);
        add(fivek);
        
        tenk = new JButton("10,000");
        tenk.setBounds(50, 300, 190, 50);
        tenk.setBackground(Color.LIGHT_GRAY);
        tenk.setFont(new Font("Georgia", Font.PLAIN, 30));
        tenk.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        tenk.addActionListener(this);
        add(tenk);
        
        fiftink = new JButton("20,000");
        fiftink.setBounds(50, 400, 190, 50);
        fiftink.setBackground(Color.LIGHT_GRAY);
        fiftink.setFont(new Font("Georgia", Font.PLAIN, 30));
        fiftink.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        fiftink.addActionListener(this);
        add(fiftink);
        
        backbutton = new JButton("BACK");
        backbutton.setBounds(50, 500, 100, 50);
        backbutton.setBackground(Color.LIGHT_GRAY);
        backbutton.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        backbutton.addActionListener(this);
        add(backbutton);

        textfield2 = new JTextField();
        textfield2.setBounds(380, 200, 230, 50);
        textfield2.setBackground(Color.LIGHT_GRAY);
        textfield2.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        textfield2.setFont(new Font(textfield2.getFont().getName(), Font.PLAIN, 25));
        add(textfield2);

        withdrawbutton = new JButton("WITHDRAW");
        withdrawbutton.setBounds(480, 500, 130, 50);
        withdrawbutton.setBackground(Color.GREEN);
        withdrawbutton.setFont(new Font("Georgia", Font.PLAIN, 15));
        withdrawbutton.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        withdrawbutton.addActionListener(this);
        add(withdrawbutton);

        label1 = new JLabel("SELECT AMOUNT");
        label1.setBounds(60, 30, 200, 30);
        label1.setFont(new Font(label1.getFont().getName(), Font.PLAIN, 20));
        label1.setForeground(Color.WHITE);
        add(label1);

        label2 = new JLabel("OTHER AMOUNT");
        label2.setBounds(420, 150, 200, 30);
        label2.setFont(new Font(label1.getFont().getName(), Font.PLAIN, 20));
        label2.setForeground(Color.BLACK);
        add(label2);

       
        dateLabel = new JLabel();
        dateLabel.setBounds(500, 30, 200, 30);
        dateLabel.setFont(new Font(dateLabel.getFont().getName(), Font.PLAIN, 18));
        dateLabel.setForeground(Color.BLACK);
        add(dateLabel);

        rightpanel = new JPanel();
        rightpanel.setBounds(0, 0, 300, 640);
        rightpanel.setBackground(Color.GRAY);
        rightpanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        add(rightpanel);

        try {
            c = DriverManager.getConnection("jdbc:mysql://localhost:3307/withdraw", "root", "12345678");
            System.out.println("Successfully connected to database");
            throwbalance();
            Date();
        } catch (SQLException e) {
            System.out.println("failed to connect to the database");
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == onek) {
            withdrawsystem(1000);
        } else if (source == fivek) {
            withdrawsystem(5000);
        } else if (source == tenk) {
            withdrawsystem(10000);
        } else if (source == fiftink) {
            withdrawsystem(20000);
        } else if (source == withdrawbutton) {
            String amountText = textfield2.getText();
            try {
                int amount = Integer.parseInt(amountText);
                withdrawsystem(amount);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Please enter a valid amount.");
            }
        } else if (source == backbutton) {
            JOptionPane.showMessageDialog(null, "Back to the previous page");
        }
    }

    private void withdrawsystem(int amount) {
        try {
            if (amount <= balance) {
                balance -= amount;
                updatebalance(balance);
                JOptionPane.showMessageDialog(null, "Withdrawal successful. \nNew Balance: " + balance);
            } else {
                JOptionPane.showMessageDialog(null, "Insufficient funds.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: Unable to perform transaction.");
            e.printStackTrace();
        }
    }

    private void throwbalance() {
        String query = "SELECT balance FROM accounts WHERE id = 1"; // DITO NAKADEPENDE YUNG ID
        try {
            PreparedStatement preparedStatement = c.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                balance = resultSet.getInt("balance");
                System.out.println("Current Balance: " + balance);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error fetching balance.");
            e.printStackTrace();
        }
    }

    private void updatebalance(int newBalance) throws SQLException {
        String updateQuery = "UPDATE accounts SET balance = ? WHERE id = 1";
        PreparedStatement preparedStatement = c.prepareStatement(updateQuery);
        preparedStatement.setInt(1, newBalance);
        preparedStatement.executeUpdate();
    }

    private void Date() {
        LocalDate currentDate = LocalDate.now();
        dateLabel.setText("Date: " + currentDate.toString());
    }
}

