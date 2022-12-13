package View;

import Model.Proxy.FixRequestProxy;
import Model.Proxy.IFixAppliance;
import Model.Room;
import Model.State.AutoControl;
import Model.State.ControlStateController;
import Model.Strategy.ControlStrategy;
import Model.Strategy.FactoryMethod.ControlStrategyFactory;
import Model.Strategy.FactoryMethod.IControlStrategyFactory;
import Model.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// 操作畫面，同時為控制模式策略的Context
public class Operation extends JFrame implements ActionListener {
    // 畫面畫布
    private JPanel panel;

    // 溫度、濕度標籤
    private static JLabel humidity, temperature, preferTemp, preferHumid;

    // 偏好溫度、偏好濕度、使用者名稱、冷氣機及除濕機標籤
    private JLabel username, airConditionerLabel, dehumidifierLabel;

    // 冷氣開關、除濕機開關
    private static JButton airConditionerSwitch, dehumidifierSwitch;

    // 設定、模式控制、登出按鈕
    private JButton setting, controlSwitch, logout, makeFixRequestBtn;

    // 冷氣機、除濕機是否開啟
    private boolean isAirConditionerOn, isDehumidifierOn;

    // 房間
    private static Room room;

    // 使用者
    private User user;

    // 狀態控制controller
    private ControlStateController controlStateController;

    // 是否開啟烘乾、空氣清淨功能
    private static boolean isDryingOn, isAirPurifyOn;

    // 生產策略的工廠
    private IControlStrategyFactory controlStrategyFactory;

    // 控制模式策略的物件
    private ControlStrategy strategy;

    private IFixAppliance requestProxy;
    Operation(User user){
        this.user = user;
        room = new Room(user);
        controlStateController = new ControlStateController();

        // 預設為自動操控狀態
        controlStateController.SetState(new AutoControl());

        isAirConditionerOn = room.GetRoomTemperature() > user.GetPreferTemperature();
        isDehumidifierOn = room.GetRoomHumidity() > user.GetPreferHumidity();

        controlStrategyFactory = new ControlStrategyFactory();

        isDryingOn = false;
        isAirPurifyOn = false;

        requestProxy = new FixRequestProxy(room);

        panel = new JPanel();
        setContentPane(panel);
        panel.setLayout(null);

        preferTemp = new JLabel("偏好溫度：" + user.GetPreferTemperature());
        preferTemp.setBounds(50,110,120,28);
        panel.add(preferTemp);

        humidity = new JLabel("目前濕度：" + room.GetRoomHumidity());
        humidity.setBounds(50,140,120,28);
        panel.add(humidity);

        temperature = new JLabel("目前溫度：" + room.GetRoomTemperature());
        temperature.setBounds(50,80,120,28);
        panel.add(temperature);

        username = new JLabel("使用者名稱：" + user.GetUserName());
        username.setBounds(50,50,200,28);
        panel.add(username);

        airConditionerLabel = new JLabel("冷氣開關：");
        airConditionerLabel.setBounds(50,200,120,28);
        panel.add(airConditionerLabel);

        airConditionerSwitch = new JButton((isAirConditionerOn ? "關" : "開"));
        airConditionerSwitch.setBounds(130, 200, 60, 28);
        panel.add(airConditionerSwitch);

        dehumidifierLabel = new JLabel("除濕機開關：");
        dehumidifierLabel.setBounds(50,230,120,28);
        panel.add(dehumidifierLabel);

        dehumidifierSwitch = new JButton((isDehumidifierOn ? "關" : "開"));
        dehumidifierSwitch.setBounds(130, 230, 60, 28);
        panel.add(dehumidifierSwitch);

        preferHumid = new JLabel("偏好濕度：" + user.GetPreferHumidity());
        preferHumid.setBounds(50,170,120,28);
        panel.add(preferHumid);

        setting = new JButton("設定");
        setting.setBounds(20,260,100,28);
        panel.add(setting);

        controlSwitch = new JButton("手動操控");
        controlSwitch.setBounds(130, 260, 100, 28);
        panel.add(controlSwitch);

        logout = new JButton("登出");
        logout.setBounds(240, 260, 100, 28);
        panel.add(logout);

        makeFixRequestBtn = new JButton("報修家電");
        makeFixRequestBtn.setBounds(50, 20, 100, 28);
        panel.add(makeFixRequestBtn);

        initActionListener();

        this.setTitle("HomeApplication");
        this.setBounds(100,50,360,350);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);

        // 一切準備就緒之後房間就開始自動偵測(多執行緒)
        room.start();
    }

    // 事件執行
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == setting) {
            //開啟設定頁面
            new Setting(user, this);
            this.setVisible(false);
        } else if(e.getSource() == controlSwitch) {
            // 若當下為自動操控狀態
            if(controlSwitch.getText().equalsIgnoreCase("手動操控")) {
                // 按下按鈕後設定為手動操控策略
                strategy = controlStrategyFactory.CreateControlStrategy("manual");
            } else if(controlSwitch.getText().equalsIgnoreCase("自動操控")) {
                // 若當下為手動操控狀態
                // 按下按鈕後設定為自動操控策略
                strategy = controlStrategyFactory.CreateControlStrategy("auto");
            }

            // 執行模式切換
            strategy.ChangeControlMode(controlStateController, controlSwitch);
        } else if(e.getSource() == airConditionerSwitch) {

            // 若冷氣機正在開
            if(isAirConditionerOn) {

                // 按下按鈕後執行該控制狀態的關閉冷氣機
                controlStateController.TurnOffAppliance(room.GetAirConditioner());
            } else {
                // 否則開啟
                controlStateController.TurnOnAppliance(room.GetAirConditioner());
            }

            // 如果是手動操控的狀態下，當按鈕被按下時要去修改按鈕上的字(開到關，關到開)，並修改冷氣是否開啟
            if(!controlSwitch.getText().equalsIgnoreCase("手動操控")) {
                airConditionerSwitch.setText((isAirConditionerOn ? "開" : "關"));
                isAirConditionerOn = !isAirConditionerOn;
            }
        } else if(e.getSource() == dehumidifierSwitch) {
            // 同冷氣機
            if(isDehumidifierOn) {
                controlStateController.TurnOffAppliance(room.GetDehumidifier());
            } else {
                controlStateController.TurnOnAppliance(room.GetDehumidifier());
            }

            if(!controlSwitch.getText().equalsIgnoreCase("手動操控")) {
                dehumidifierSwitch.setText((isDehumidifierOn ? "開" : "關"));
                isDehumidifierOn = !isDehumidifierOn;

                //如果是手動操控且除濕機為開啟的狀態下，要print出除濕機當下的狀態以及是否有多加的功能(Decorator)
                if(isDehumidifierOn) {
                    room.GetDehumidifierState();
                }
            }
        } else if (e.getSource() == makeFixRequestBtn) {
            requestProxy.Request();

            // 有家電壞掉就停止自動偵測並切換成手動操控
            room.ChangeIfKeepCheck(false);

            if(controlSwitch.getText().equalsIgnoreCase("手動操控")) {
                // 按下按鈕後設定為手動操控策略
                strategy = controlStrategyFactory.CreateControlStrategy("manual");

                // 執行模式切換
                strategy.ChangeControlMode(controlStateController, controlSwitch);

                // 若冷氣機正在開
                if(isAirConditionerOn) {

                    // 按下按鈕後執行該控制狀態的關閉冷氣機
                    controlStateController.TurnOffAppliance(room.GetAirConditioner());
                }

                if(isDehumidifierOn) {
                    controlStateController.TurnOffAppliance(room.GetDehumidifier());
                }
            }

        } else if(e.getSource() == logout) {
            // 登出，回到登入畫面
            room.ChangeIfKeepCheck(false);
            System.out.println("User Logout");
            this.dispose();
            new Login();
        }
    }

    // 事件綁定
    public void initActionListener() {
        setting.addActionListener(this);
        controlSwitch.addActionListener(this);
        airConditionerSwitch.addActionListener(this);
        dehumidifierSwitch.addActionListener(this);
        logout.addActionListener(this);
        makeFixRequestBtn.addActionListener(this);
    }

    // 取得房間
    public static Room GetRoom() {
        return room;
    }

    // 取得溫度標籤
    public static JLabel GetTemperatureLabel() {
        return temperature;
    }

    // 取得濕度標籤
    public static JLabel GetHumidityLabel() {
        return humidity;
    }
    public static JLabel GetPreferTempLabel() {
        return preferTemp;
    }

    public static JLabel GetPreferHumidLabel() {
        return preferHumid;
    }

    // 取得冷氣開關
    public static JButton GetAirConditionerSwitch() {
        return airConditionerSwitch;
    }

    // 取得除濕機開關
    public static JButton GetDehumidifierSwitch() {
        return dehumidifierSwitch;
    }

    // 設定烘乾功能是否開啟
    public static void SetIsDryingOn(boolean flag) { isDryingOn = flag; }

    // 取得烘乾功能是否開啟
    public static boolean GetIsDryingOn() {
        return isDryingOn;
    }

    // 設定空氣清淨功能是否開啟
    public static void SetIsAirPurifyOn(boolean flag) { isAirPurifyOn = flag; }

    // 取得空氣清淨功能是否開啟
    public static boolean GetIsAirPurifyOn() {
        return isAirPurifyOn;
    }
}
