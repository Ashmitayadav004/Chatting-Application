package ChattingApplication;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.net.*;
import java.io.*;

public class Client implements ActionListener {
    JTextField text;
    static JPanel a1;
    static Box vertical = Box.createVerticalBox();
    static DataOutputStream dout;
    static JFrame f = new JFrame();

    Client() {
        f.setLayout(null);

        // Header Panel
        JPanel p1 = new JPanel();
        p1.setBackground(new Color(7, 94, 84));
        p1.setBounds(0, 0, 450, 70);
        p1.setLayout(null);
        f.add(p1);

        // Set frame properties
        f.setSize(450, 620);
        f.setLocation(800, 50);
        f.setUndecorated(true);
        f.getContentPane().setBackground(Color.WHITE);

        // Back button
        ImageIcon i1 = new ImageIcon(getClass().getResource("./3.png"));
        Image i2 = i1.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel back = new JLabel(i3);
        back.setBounds(5, 20, 25, 25);
        p1.add(back);

        back.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
        });

        // Profile image
        ImageIcon i4 = new ImageIcon(getClass().getResource("./2.png"));
        Image i5 = i4.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
        ImageIcon i6 = new ImageIcon(i5);
        JLabel profile = new JLabel(i6);
        profile.setBounds(40, 10, 50, 50);
        p1.add(profile);

        // Video call image icon
        ImageIcon i7 = new ImageIcon(getClass().getResource("./video.png"));
        Image i8 = i7.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        ImageIcon i9 = new ImageIcon(i8);
        JLabel video = new JLabel(i9);
        video.setBounds(300, 20, 30, 30);
        p1.add(video);

        // Phone call
        ImageIcon i10 = new ImageIcon(getClass().getResource("./phone.png"));
        Image i11 = i10.getImage().getScaledInstance(35, 30, Image.SCALE_DEFAULT);
        ImageIcon i12 = new ImageIcon(i11);
        JLabel phone = new JLabel(i12);
        phone.setBounds(360, 20, 35, 30);
        p1.add(phone);

        // Three dot option
        ImageIcon i13 = new ImageIcon(getClass().getResource("./3icon.png"));
        Image i14 = i13.getImage().getScaledInstance(10, 25, Image.SCALE_DEFAULT);
        ImageIcon i15 = new ImageIcon(i14);
        JLabel morevert = new JLabel(i15);
        morevert.setBounds(420, 20, 10, 25);
        p1.add(morevert);

        // Name label
        JLabel name = new JLabel("Adhrith");
        name.setBounds(110, 15, 100, 20);
        name.setForeground(Color.WHITE);
        name.setFont(new Font("Arial", Font.BOLD, 18));
        p1.add(name);

        // Status label
        JLabel status = new JLabel("Active now");
        status.setBounds(110, 35, 100, 20);
        status.setForeground(Color.WHITE);
        status.setFont(new Font("Arial", Font.BOLD, 14));
        p1.add(status);

        // Main chat area
        a1 = new JPanel();
        a1.setBounds(5, 75, 440, 500); // Adjusted height to fit the text field
        a1.setBackground(Color.WHITE); // Set background color for the chat area
        f.add(a1);

        // Text field for input
        text = new JTextField();
        text.setBounds(5, 580, 310, 40);
        text.setBackground(Color.LIGHT_GRAY);
        text.setFont(new Font("Arial", Font.PLAIN, 16));
        f.add(text); // Add text field to the main frame

        // Send button (optional)
        JButton sendButton = new JButton("Send");
        sendButton.setBounds(320, 580, 100, 40);
        sendButton.setBackground(new Color(7, 94, 84));
        sendButton.setForeground(Color.WHITE);
        sendButton.setFont(new Font("Arial", Font.BOLD, 16));
        sendButton.addActionListener(this);
        f.add(sendButton); // Add send button to the main frame

        f.setResizable(false);
        f.setVisible(true);
    }

    public static void main(String[] args) {
        new Client();
        try {
            Socket s = new Socket("127.0.0.1", 6001);
            DataInputStream din = new DataInputStream(s.getInputStream());// input messages received from client
            dout = new DataOutputStream(s.getOutputStream());// output message to client

            while (true) {
                a1.setLayout(new BorderLayout());
                String msg = din.readUTF();// input messages from server on left side
                JPanel panel = formatLabel(msg);
                JPanel left = new JPanel(new BorderLayout());// recieved message on left side
                left.add(panel, BorderLayout.LINE_START);
                vertical.add(left);

                vertical.add(Box.createHorizontalStrut(10));
                a1.add(vertical, BorderLayout.LINE_START);

                f.validate();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Handle send button action
        // You can add functionality to send messages here
        // System.out.println("Send button clicked!");
        try {
            String out = text.getText();
            System.out.println(out);

            JPanel p2 = formatLabel(out);

            a1.setLayout(new BorderLayout());
            JPanel right = new JPanel(new BorderLayout());
            right.add(p2, BorderLayout.LINE_END);
            vertical.add(right);
            vertical.add(Box.createVerticalStrut(10));
            a1.add(vertical, BorderLayout.PAGE_START);
            dout.writeUTF(out);

            text.setText(" ");
            f.repaint();
            f.invalidate();
            f.validate();

        } catch (Exception ae) {
            ae.printStackTrace();
        }
    }

    public static JPanel formatLabel(String out) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel output = new JLabel("<html><p style=\"width: 150px\">" + out + "</p></html>");
        output.setFont(new Font("Tahoma", Font.PLAIN, 16));
        output.setBackground(new Color(37, 211, 102));
        output.setOpaque(true);
        output.setBorder(new EmptyBorder(15, 15, 15, 50));
        panel.add(output);

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        JLabel time = new JLabel();
        time.setText(sdf.format(cal.getTime()));

        panel.add(time);
        return panel;
    }
}
