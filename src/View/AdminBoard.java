package View;

import Model.Iterator.Iterator;
import Model.Iterator.UserAggregate;
import Model.Room;
import Model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminBoard extends JFrame implements ActionListener {
    private JPanel buttonPanel;
    private JScrollPane contentPanel;

    private JButton logoutBtn;
    private JTable table;
    private String[] columnsName = {"使用者名稱", "冷氣是否開啟", "除濕機是否開啟"};
    private String[][] userDatas;
    private UserAggregate userAggregate;
    private Iterator userIterator;
    private Room room;
    public AdminBoard() {
        userAggregate = new UserAggregate();
        userIterator = userAggregate.CreateIterator();
        this.InitData();

        table = new JTable(userDatas, columnsName);
        table.setBounds(30, 40, 200, 300);
        contentPanel = new JScrollPane(table);
        contentPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        buttonPanel = new JPanel();

        logoutBtn = new JButton("登出");
        logoutBtn.addActionListener(this);
        buttonPanel.add(logoutBtn);

        this.add(contentPanel, BorderLayout.CENTER);
        this.add(buttonPanel, BorderLayout.SOUTH);

        this.setSize(500, 500);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    private void InitData() {
        userDatas = new String[userAggregate.Size()][3];
        int index = 0;
        User user;

        while(!userIterator.IsDone()) {
            user = (User) userIterator.Next();

            if(user.GetUserName().equals("admin")) {
                continue;
            }

            room = new Room(user);

            userDatas[index][0] = user.GetUserName();
            userDatas[index][1] = room.GetRoomTemperature() > user.GetPreferTemperature() ? "開" : "關";
            userDatas[index][2] = room.GetRoomHumidity() > user.GetPreferHumidity() ? "開" : "關";
            index++;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == logoutBtn) {
            this.dispose();
            new Login();
            System.out.println("Admin Logout");
        }
    }
}
