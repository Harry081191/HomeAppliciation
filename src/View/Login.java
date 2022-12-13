package View;

import Controller.Controller;
import Model.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

// 登入畫面
public class Login extends JFrame implements ActionListener {
    // 與model(DatabaseService)溝通用，使用登入等等與使用者相關之服務
    private Controller controller;

    // 畫面畫布
    private JPanel panel;

    // 帳號密碼的標籤
    private JLabel userNameLabel, passwordLabel;

    // 登入、註冊、離開按鈕
    private JButton loginBtn, registerBtn, exitBtn;

    // 帳號輸入框
    private JTextField userName;

    //密碼輸入框
    private JPasswordField password;

    public Login(){
        //取得controller
        controller = Controller.GetController();

        /* 畫面處理開始 */
        panel = new JPanel();
        setContentPane(panel);
        panel.setLayout(null);

        userNameLabel = new JLabel("使用者");
        userNameLabel.setBounds(80,76,54,28);
        panel.add(userNameLabel);

        passwordLabel = new JLabel("密碼");
        passwordLabel.setBounds(80,135,54,28);
        panel.add(passwordLabel);

        userName = new JTextField();
        userName.setBounds(139, 80, 161, 25);
        panel.add(userName);

        password = new JPasswordField();
        password.setBounds(139, 140, 161, 25);
        panel.add(password);

        loginBtn = new JButton("登入");
        loginBtn.setBounds(40, 210, 80, 23);
        panel.add(loginBtn);

        registerBtn = new JButton("註冊");
        registerBtn.setBounds(130, 210, 80, 23);
        panel.add(registerBtn);

        exitBtn = new JButton("結束");
        exitBtn.setBounds(220, 210, 80, 23);
        panel.add(exitBtn);

        this.setTitle("Login");
        this.setBounds(100,50,360,350);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
        /* 畫面處理結束 */

        initActionListener();
    }

    //事件執行
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == loginBtn) {
            String uid = userName.getText(), pwd = new String(password.getPassword());

            // 登入判定
            if(controller.Login(uid, pwd)) {
                // 登入成功
                System.out.println("Login Success");

                // 如果使用者名稱 = admin
                if(uid.equals("admin")) {

                    // 進入管理員頁面
                    new AdminBoard();
                } else {

                    // 否則先取得使用者偏好
                    double[] userPreference = controller.GetUserPreference(uid);
                    User user = new User(uid, userPreference[0], userPreference[1], true);

                    // 接著判斷使用者是否為初次登入
                    if (controller.IsUserFirstLogin(uid)) {

                        // 是就先進入設定畫面，先把冷氣機、除濕機型號設定好
                        JFrame previousGUI = new Operation(user);
                        previousGUI.setVisible(false);
                        new Setting(user, previousGUI);
                    } else {

                        // 否就直接進入操作畫面
                        user.SetIsUserFirstLogin(false);
                        new Operation(user);
                    }
                }

                this.dispose();
            } else {
                // 登入失敗並清空密碼欄位
                System.out.println("Login Fail");

                password.setText("");
            }
        }else if(e.getSource() == registerBtn) {
            // 開啟註冊畫面
            new Register();
            this.dispose();
        }else if(e.getSource() == exitBtn) {
            this.dispose();
        }
    }

    //事件綁定
    public void initActionListener() {
        loginBtn.addActionListener(this);
        registerBtn.addActionListener(this);
        exitBtn.addActionListener(this);
    }
}
