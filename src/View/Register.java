package View;

import Controller.Controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// 註冊畫面
public class Register extends JFrame implements ActionListener {

    // 與model(DatabaseService)溝通用，使用註冊等等與使用者相關之服務
    private Controller controller;

    // 畫面畫布
    private JPanel panel;

    // 帳號、密碼、再次輸入密碼標籤
    private JLabel userNameLabel, passwordLabel, passwordAgainLabel;

    // 帳號輸入框
    private JTextField userName;

    // 密碼、再次輸入密碼輸入框
    private JPasswordField password, passwordAgain;

    // 註冊、取消按鈕
    private JButton registerBtn, cancelBtn;

    Register() {
        controller = Controller.GetController();

        panel = new JPanel();
        setContentPane(panel);
        panel.setLayout(null);

        userNameLabel = new JLabel("帳號");
        userNameLabel.setBounds(100,76,54,28);

        passwordLabel = new JLabel("密碼");
        passwordLabel.setBounds(100,135,54,28);


        passwordAgainLabel = new JLabel("請再輸入一次密碼");
        passwordAgainLabel.setBounds(25,194,100,28);

        userName = new JTextField();
        userName.setBounds(139,76,161,28);

        password = new JPasswordField();
        password.setBounds(139,135,161,28);

        passwordAgain = new JPasswordField();
        passwordAgain.setBounds(139,194,161,28);

        registerBtn = new JButton("註冊");
        registerBtn.setBounds(96, 250, 80, 23);

        cancelBtn = new JButton("取消");
        cancelBtn.setBounds(192, 250, 80, 23);

        panel.add(userNameLabel);
        panel.add(userName);
        panel.add(passwordLabel);
        panel.add(password);
        panel.add(passwordAgainLabel);
        panel.add(passwordAgain);
        panel.add(registerBtn);
        panel.add(cancelBtn);

        setTitle("Register");
        setBounds(100,50,360,350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);

        InitActionListener();
    }

    // 事件綁定
    public void InitActionListener(){
        registerBtn.addActionListener(this);
        cancelBtn.addActionListener(this);
    }

    // 事件執行
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == registerBtn) {
            String uid = userName.getText(), pwd = new String(password.getPassword());

            // 若密碼與再次輸入的密碼相同，且帳號不存在
            if(new String(password.getPassword()).equals(new String(passwordAgain.getPassword())) && controller.IsUserNameNotExist(userName.getText())){

                // 在資料庫中新增該使用者之帳號密碼
                controller.AddUser(uid, pwd);

                // 回到登入畫面
                new Login();
                this.dispose();

                System.out.println("Create User Success");
            } else {
                System.out.println("User Name Exist Or Password is not the Same.");
            }
        } else if(e.getSource() == cancelBtn) {

            // 回到登入畫面
            new Login();
            this.dispose();
        }
    }
}
