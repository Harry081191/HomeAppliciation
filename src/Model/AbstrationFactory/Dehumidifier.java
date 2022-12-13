package Model.AbstrationFactory;

import Model.Bridge.DehumidifierImplementor;

public abstract class Dehumidifier implements Appliance {
    private DehumidifierImplementor dehumidifierImplementor;
    private int nowTime;
    final private double[] humidities =
            {0.70, 0.60, 0.50, 0.40, 0.45, 0.48,
                    0.50, 0.51, 0.55, 0.53, 0.60, 0.65,
                    0.60, 0.58, 0.55, 0.53, 0.50, 0.48,
                    0.45, 0.43, 0.47, 0.58, 0.63, 0.67}; // 凌晨12點到半夜11點的室內溼度(寫死的)
    private String brandName;

    public Dehumidifier(){}

    public Dehumidifier(String brandName,DehumidifierImplementor dehumidifierImplementor){
        this.brandName = brandName;
        this.dehumidifierImplementor = dehumidifierImplementor;
    }

    @Override
    public void TurnOn(){
        dehumidifierImplementor.Turn_on();
    }

    @Override
    public void TurnOff(){
        dehumidifierImplementor.Turn_off();
    }

    public void ChangeHumid(double humidity){
        dehumidifierImplementor.ChangeHumid(humidity);
    }

    @Override
    public String GetApplianceName() {
        return brandName + " Dehumidifier";
    }

    public double GetHumidity() {
        nowTime = Integer.parseInt(java.time.LocalTime.now().toString().substring(0, 2));
        return humidities[nowTime];
    }

    public abstract void GetDehumidifierState();
}
