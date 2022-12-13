package Model.Bridge;

public class SonyACImplementor implements AirConditionerImplementor{
    public void TurnOn(){
        System.out.println("Sony AirConditioner is on");
    }
    public void TurnOff(){
        System.out.println("Sony AirConditioner is off");
    }
    public void ChangeTemp(double temp){
        System.out.printf("Sony AirConditioner is changing temperature to %f", temp);
    }
}
