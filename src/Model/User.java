package Model;

import Model.Observer.Observer;

// 使用者model，同時為訂閱者(Observer)
public class User implements Observer {
    // 使用者名稱(帳號)
    private String userName;

    // 偏好溫度、偏好濕度
    private double preferTemp, preferHumidity;

    // 是否初次登入
    private boolean isUserFirstLogin;

    public User(String userName, double preferTemp, double preferHumidity, boolean isUserFirstLogin){
        this.userName = userName;
        this.preferTemp = preferTemp;
        this.preferHumidity = preferHumidity;
        this.isUserFirstLogin = isUserFirstLogin;
    }

    // 取得使用者名稱
    public String GetUserName() {
        return this.userName;
    }

    // 取得使用者偏好溫度
    public double GetPreferTemperature() {
        return this.preferTemp;
    }

    // 取得使用者偏好濕度
    public double GetPreferHumidity() {
        return this.preferHumidity;
    }

    // 取得使用者是否初次登入
    public boolean GetIsUserFirstLogin() {
        return this.isUserFirstLogin;
    }

    // 設定使用者是否初次登入
    public void SetIsUserFirstLogin(boolean isUserFirstLogin) {
        this.isUserFirstLogin = isUserFirstLogin;
    }
    public void SetPreferTemp(double preferTemp) {
        this.preferTemp = preferTemp;
    }

    public void SetPreferHumid(double preferHumid) {
        this.preferHumidity = preferHumid;
    }

    // Observerable狀態改變時要通知使用者
    @Override
    public void Update(String appliance, String operation){
        System.out.println("User " + userName + " knows his/her " + appliance + " is " + operation + ".");
    }

}
