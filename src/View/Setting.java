package View;

import Controller.Controller;
import Model.Room;
import Model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// 設定畫面
public class Setting extends JFrame implements ActionListener {

    // 與model(DatabaseService)溝通用，使用取得偏好等等與使用者相關之服務
    private Controller controller;

    // 選擇區域畫布、按鈕區域畫布
    private JPanel selectorPanel, buttonPanel;

    // 冷氣機、除濕機標籤
    private JLabel acLabel, dhLabel, preferTempLabel, preferHumidLabel;

    // 冷氣機、除濕機品牌下拉式選單
    private JComboBox airConditionerSelector, dehumidifierSelector, preferTempSeletor, preferHumidSeletor;

    // 是否開啟除濕機烘乾、空氣清淨功能之核選方塊
    private JCheckBox dryingFunctionCheckbox, airPurifyFunctionCheckbox;

    // (確定)設定、取消按鈕
    private JButton setting, cancel;

    // 家電品牌下拉式選單資料來源
    private String[] homeApplianceBrands = {"Sampo", "Sony"};
    private Double[] preferTemps = {16.0, 17.0, 18.0, 19.0, 20.0, 21.0, 22.0, 23.0, 24.0, 25.0, 26.0, 27.0, 28.0, 29.0, 30.0, 31.0, 32.0};
    private Double[] preferHumids = {0.3, 0.35, 0.4, 0.45, 0.5, 0.55, 0.6, 0.65, 0.7, 0.75, 0.8};
    private User user;

    // 上一個畫面
    private JFrame previousGUI;

    public Setting(User user, JFrame previousGUI){
        controller = Controller.GetController();
        this.user = user;
        this.previousGUI = previousGUI;

        selectorPanel = new JPanel();
        buttonPanel = new JPanel();

        acLabel = new JLabel("請選擇房間內的冷氣品牌：");
        dhLabel = new JLabel("請選擇房間內的除濕機品牌：");
        preferTempLabel = new JLabel("請選擇房間內自動偵測的偏好溫度");
        preferHumidLabel = new JLabel("請選擇房間內自動偵測的偏好濕度");

        airConditionerSelector = new JComboBox(homeApplianceBrands);
        dehumidifierSelector = new JComboBox(homeApplianceBrands);
        preferTempSeletor = new JComboBox(preferTemps);
        preferHumidSeletor = new JComboBox(preferHumids);

        // 將下拉式選單設定為使用者當前的設定
        airConditionerSelector.setSelectedItem(controller.GetRoomACBrand(user.GetUserName()));
        dehumidifierSelector.setSelectedItem(controller.GetRoomDehumidifierBrand(user.GetUserName()));
        preferTempSeletor.setSelectedItem(user.GetPreferTemperature());
        preferHumidSeletor.setSelectedItem(user.GetPreferHumidity());

        // 這不是瞎掰的，現在認真真的有這種除濕機
        dryingFunctionCheckbox = new JCheckBox("除濕機開啟烘乾功能", Operation.GetIsDryingOn());
        airPurifyFunctionCheckbox = new JCheckBox("除濕機開啟空氣清淨功能", Operation.GetIsAirPurifyOn());

        setting = new JButton("設定");
        cancel = new JButton("取消");

        // 初次登入的話不可以按取消
        if(user.GetIsUserFirstLogin()){
            cancel.setEnabled(false);
        }

        selectorPanel.add(acLabel);
        selectorPanel.add(airConditionerSelector);
        selectorPanel.add(dhLabel);
        selectorPanel.add(dehumidifierSelector);
        selectorPanel.add(preferTempLabel);
        selectorPanel.add(preferTempSeletor);
        selectorPanel.add(preferHumidLabel);
        selectorPanel.add(preferHumidSeletor);
        selectorPanel.add(dryingFunctionCheckbox);
        selectorPanel.add(airPurifyFunctionCheckbox);

        buttonPanel.add(setting);
        buttonPanel.add(cancel);

        this.add(selectorPanel, BorderLayout.CENTER);
        this.add(buttonPanel, BorderLayout.SOUTH);

        initActionListener();

        this.setTitle("Setting");
        this.setBounds(100,50,360,350);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
    }

    // 事件執行
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == setting) {

            // 初次登入判斷式
            if (user.GetIsUserFirstLogin()) {

                // 因為只有初次登入會在一開始就進來設定頁面
                // 若因為初次登入進到這個畫面
                // 就把使用者初次登入設為false並記錄在資料庫中
                user.SetIsUserFirstLogin(false);
                controller.SetUserNotFirstLogin(user.GetUserName());
            }

            String preferTemp = preferTempSeletor.getSelectedItem().toString(), preferHumid = preferHumidSeletor.getSelectedItem().toString();

            // 保存使用者設定至資料庫
            controller.SaveAppliancesSetting(user.GetUserName(), airConditionerSelector.getSelectedItem().toString(), dehumidifierSelector.getSelectedItem().toString(), preferTemp, preferHumid);

            // 存檔當下就要設定好房間的冷氣機及除濕機了
            Room room = Operation.GetRoom();
            room.SetAirConditioner();
            room.SetDehumidifier();

            // 存檔之後也要直接print出除濕機當下的狀態以及是否有多加的功能(Decorator)
            room.GetDehumidifierState();

            user.SetPreferTemp(Double.parseDouble(preferTemp));
            user.SetPreferHumid(Double.parseDouble(preferHumid));
            Operation.GetPreferTempLabel().setText("偏好溫度：" + preferTemp);
            Operation.GetPreferHumidLabel().setText("偏好濕度：" + preferHumid);

            previousGUI.setVisible(true);
            this.dispose();
        } else if (e.getSource() == cancel) {
            previousGUI.setVisible(true);
            this.dispose();

            /* 以下兩個核選方塊被打勾或取消都要設定到操作畫面的是否開啟烘乾、空氣清淨功能 */
        } else if (e.getSource() == dryingFunctionCheckbox) {
            Operation.SetIsDryingOn(dryingFunctionCheckbox.isSelected());
        } else if (e.getSource() == airPurifyFunctionCheckbox) {
            Operation.SetIsAirPurifyOn(airPurifyFunctionCheckbox.isSelected());
        }
    }

    // 事件綁定
    public void initActionListener(){
        setting.addActionListener(this);
        cancel.addActionListener(this);
        dryingFunctionCheckbox.addActionListener(this);
        airPurifyFunctionCheckbox.addActionListener(this);
    }
}
