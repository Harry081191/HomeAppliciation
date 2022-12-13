package Model.AbstrationFactory;

import Model.Bridge.AirConditionerImplementor;

public abstract class AirConditioner implements Appliance {
    AirConditionerImplementor airConditionerImplementor;
    private int nowTime;
    final private double[] temperatures =
            {28.0, 28.2, 28.4, 28.6, 28.8, 29.0,
                    29.0, 29.0, 29.0, 29.2, 29.5, 30.0,
                    31.2, 31.5, 32.0, 32.0, 31.0, 30.0,
                    29.0, 28.8, 28.6, 28.4, 28.2, 28.0}; // 凌晨12點到半夜11點的室溫(寫死的)
    String brandName;
    public AirConditioner(String brandName,AirConditionerImplementor airConditionerImplementor){
        this.brandName = brandName;
        this.airConditionerImplementor = airConditionerImplementor;
    }

    @Override
    public void TurnOn(){
        airConditionerImplementor.TurnOn();
    }

    @Override
    public void TurnOff(){
        airConditionerImplementor.TurnOff();
    }

    public void ChangeTemp(double temp){
        airConditionerImplementor.ChangeTemp(temp);
    }

    @Override
    public String GetApplianceName(){
        return brandName + " Air Conditioner";
    }

    public double GetTemperature() {
        nowTime = Integer.parseInt(java.time.LocalTime.now().toString().substring(0, 2));
        return temperatures[nowTime];
    }
}
