package Model;

import Model.AbstrationFactory.AirConditioner;
import Model.AbstrationFactory.Dehumidifier;
import Model.Decorator.AirPurifyDecorator;
import Model.Decorator.DryingDecorator;
import Model.Proxy.IFixAppliance;
import View.Operation;

// 房間model
public class Room extends Thread implements IFixAppliance {

    // 整合式感應器
    private IntegratedSensor sensor;

    // 是否持續偵測
    private boolean keepCheck = true;

    // 使用者
    private User user;

    public Room(User user){
        this.user = user;

        sensor = new IntegratedSensor(user);
    }

    // 取得房間溫度
    public double GetRoomTemperature() {
        return sensor.GetTemperature();
    }

    // 取得房間濕度
    public double GetRoomHumidity() {
        return sensor.GetHumidity();
    }

    // 取得冷氣機
    public AirConditioner GetAirConditioner() {
        return sensor.GetAirConditioner();
    }

    // 取得除濕機
    public Dehumidifier GetDehumidifier(){
        return sensor.GetDehumidifier();
    }

    // 設定冷氣機
    public void SetAirConditioner() {
        sensor.SetAirConditioner();
    }

    // 設定除濕機
    public void SetDehumidifier() {
        sensor.SetDehumidifier();
    }

    // 使用多執行緒，這樣即便不在操作畫面，sensor也可以持續每分鐘偵測
    // 只要keepCheck是true就會一直偵測下去
    @Override
    public void run(){
        // 先反向設定確保第一次Notify一定會出來
        sensor.SetIsACTurnedOn(!sensor.DoesACNeedToTurnedOn());
        sensor.SetIsDehumidifierTurnedOn(!sensor.DoesDehumidifierNeedToTurnedOn());
        while (keepCheck){
            this.AutoCheck();
        }
    }

    // 當切換為手動操控狀態時就要停止自動偵測，切回自動操控狀態就繼續自動偵測
    public void ChangeIfKeepCheck(boolean keepCheck){
        this.keepCheck = keepCheck;

        if(keepCheck) {
            System.out.println("Sensor start auto check");
        } else {
            System.out.println("Sensor stop auto check");
        }
    }

    // print出除濕機當下的狀態以及是否有多加的功能(Decorator)
    public void GetDehumidifierState() {
        // 取得物件主體(除濕機)
        Dehumidifier dehumidifier = sensor.GetDehumidifier();

        // 如果被勾選了什麼要多加的功能，就用Decorator加上去
        if (sensor.DoesDehumidifierNeedToTurnedOn()) {
            if(Operation.GetIsDryingOn() && Operation.GetIsAirPurifyOn()){
                dehumidifier = new DryingDecorator(new AirPurifyDecorator(dehumidifier));
            } else if (Operation.GetIsDryingOn()) {
                dehumidifier = new DryingDecorator(dehumidifier);
            } else if (Operation.GetIsAirPurifyOn()) {
                dehumidifier = new AirPurifyDecorator(dehumidifier);
            }
        }

        // print
        dehumidifier.GetDehumidifierState();
        System.out.println();
    }

    private void AutoCheck() {
        try {
            String appliance, operation;

            System.out.println("----------Auto Check Start----------");

            /* Start of Air Conditioner */
            appliance = "Air Conditioner";
            // 如果冷氣需要開啟就開啟，並設定冷氣開關的按鈕字樣，反之亦然
            if (sensor.DoesACNeedToTurnedOn()) {
                sensor.TurnOnAppliance();
                operation = "cooling the room";
                Operation.GetAirConditionerSwitch().setText("關");
            } else {
                sensor.TurnOnAppliance();
                operation = "keeping temperature at a constant";
                Operation.GetAirConditionerSwitch().setText("開");
            }

            // 如果是否需要開啟與前次偵測的狀態不同，表示狀態不同因此需要通知訂閱者
            if (sensor.DoesACNeedToTurnedOn() != sensor.IsACTurnedOn()) {
                // 通知訂閱者
                sensor.NotifyObserver(appliance, operation);
            }

            // 設定溫度標籤字樣
            Operation.GetTemperatureLabel().setText("目前溫度：" + sensor.GetTemperature());
            /* End of Air Conditioner */

            /* Start of Dehumidifier */
            appliance = "Dehumidifier";
            //同冷氣機
            if (sensor.DoesDehumidifierNeedToTurnedOn()) {
                sensor.TurnOnAppliance();
                operation = "turned on";
                Operation.GetDehumidifierSwitch().setText("關");

                this.GetDehumidifierState();
            } else {
                sensor.TurnOffAppliance();
                operation = "turned off";
                Operation.GetDehumidifierSwitch().setText("開");
            }

            if (sensor.DoesDehumidifierNeedToTurnedOn() != sensor.IsDehumidifierTurnedOn()) {
                // 通知訂閱者
                sensor.NotifyObserver(appliance, operation);
            }

            Operation.GetHumidityLabel().setText("目前濕度：" + sensor.GetHumidity());
            /* End of Dehumidifier */

            System.out.println("----------Auto Check Stop----------");

            // 記住下一次偵測前的狀態
            sensor.SetIsACTurnedOn(sensor.DoesACNeedToTurnedOn());
            sensor.SetIsDehumidifierTurnedOn(sensor.DoesDehumidifierNeedToTurnedOn());

            sleep(60000); // 執行到這行時這支method會休息60秒
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void Request() {
        System.out.println("Make a Fix Appliance Request.");
    }
}


