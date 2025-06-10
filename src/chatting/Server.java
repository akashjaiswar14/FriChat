package chatting;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.net.*;
import java.io.*;

public class Server implements ActionListener {

    JTextField t1;
    JPanel p1,a1;
    static Box verticle = Box.createVerticalBox();
    static DataOutputStream dout;
    static JFrame f = new JFrame();

    Server(){
        f.setLayout(null);

        p1 = new JPanel();
        p1.setBackground(new Color(234, 8, 137));
        p1.setBounds(0,0,450,70);
        p1.setLayout(null);
        f.add(p1);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/3.png"));
        Image i2 = i1.getImage().getScaledInstance(25,25,Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel back = new JLabel(i3);
        back.setBounds(5,20,25,25);
        p1.add(back);

        back.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent ae){
                System.exit(0);
            }
        });

        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("icons/1.png"));
        Image i5 = i4.getImage().getScaledInstance(50,50,Image.SCALE_DEFAULT);
        ImageIcon i6 = new ImageIcon(i5);
        JLabel profile = new JLabel(i6);
        profile.setBounds(40,10,50,50);
        p1.add(profile);

        ImageIcon i7 = new ImageIcon(ClassLoader.getSystemResource("icons/video.png"));
        Image i8 = i7.getImage().getScaledInstance(30,30,Image.SCALE_DEFAULT);
        ImageIcon i9 = new ImageIcon(i8);
        JLabel video = new JLabel(i9);
        video.setBounds(300,20,30,30);
        p1.add(video);

        ImageIcon i10 = new ImageIcon(ClassLoader.getSystemResource("icons/phone.png"));
        Image i11 = i10.getImage().getScaledInstance(35,30,Image.SCALE_DEFAULT);
        ImageIcon i12 = new ImageIcon(i11);
        JLabel phone = new JLabel(i12);
        phone.setBounds(360,20,35,30);
        p1.add(phone);

        ImageIcon i13 = new ImageIcon(ClassLoader.getSystemResource("icons/3icon.png"));
        Image i14 = i13.getImage().getScaledInstance(10,25,Image.SCALE_DEFAULT);
        ImageIcon i15 = new ImageIcon(i14);
        JLabel moreVert = new JLabel(i15);
        moreVert.setBounds(420,20,10,25);
        p1.add(moreVert);

        JLabel name = new JLabel("Veer");
        name.setBounds(110,15,100,18);
        name.setForeground(Color.white);
        name.setFont(new Font("SAN SERIF",Font.BOLD,18));
        p1.add(name);


        JLabel status = new JLabel("online");
        status.setBounds(110,35,100,18);
        status.setForeground(Color.white);
        status.setFont(new Font("SAN SERIF",Font.BOLD,10));
        p1.add(status);

        a1 = new JPanel();
        a1.setBounds(5,75,440,570);
        f.add(a1);

        t1 = new JTextField();
        t1.setToolTipText("Type your message here");
        t1.setBounds(5,655,310,40);
        t1.setFont(new Font("SAN SERIF",Font.PLAIN,16));
        f.add(t1);

        JButton b = new JButton("send");
        b.setBounds(320,655,123,40);
        b.setBackground(new Color(234,8,137));
        b.setForeground(Color.white);
        b.setFont(new Font("SAN SERIF",Font.PLAIN,16));
        b.addActionListener(this);
        f.add(b);


        f.setSize(450,700);
        f.setLocation(200,50);
        f.setUndecorated(true);
        f.getContentPane().setBackground(new Color(239, 106, 193));
        f.setVisible(true);

    }
    @Override
    public void actionPerformed(ActionEvent e) {

        try{
            String out = t1.getText();

            JPanel p2 = formatLabel1(out);

            a1.setLayout(new BorderLayout());

            JPanel right = new JPanel(new BorderLayout());
            right.add(p2, BorderLayout.LINE_END);
            verticle.add(right);
            verticle.add(Box.createVerticalStrut(15));

            dout.writeUTF(out);

            a1.add(verticle, BorderLayout.PAGE_START);

            t1.setText("");

            f.repaint();
            f.invalidate();
            f.validate();
        }catch (Exception g){
            g.printStackTrace();
        }

    }

    public static JPanel formatLabel1(String out){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel output  = new JLabel(out);
        output.setFont(new Font("Tahuma",Font.PLAIN, 16));
        output.setBackground(new Color(234, 8, 137));
        output.setOpaque(true);
        output.setBorder(new EmptyBorder(15,15,15,50));
        panel.add(output);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        JLabel time = new JLabel();
        time.setText(sdf.format(calendar.getTime()));

        panel.add(time);

        return panel;
    }
    public static void main(String[] args) {
        new Server();

        try{
            ServerSocket skt = new ServerSocket(6001);
            while (true){
                Socket s =  skt.accept();
                DataInputStream din = new DataInputStream(s.getInputStream());
                dout = new DataOutputStream(s.getOutputStream());

                while (true){
                    String msg = din.readUTF();
                    JPanel panel = formatLabel1(msg);

                    JPanel left = new JPanel(new BorderLayout());
                    left.add(panel, BorderLayout.LINE_START);
                    verticle.add(left);
                    f.validate();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
