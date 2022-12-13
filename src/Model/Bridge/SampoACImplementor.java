package Model.Bridge;

public class SampoACImplementor implements AirConditionerImplementor {
    public void TurnOn(){
        System.out.println("Sampo AirConditioner is on");
    }
    public void TurnOff(){
        System.out.println("Sampo AirConditioner is off");
    }
    public void ChangeTemp(double temp){
        System.out.printf("Sampo AirConditioner is changing temperature to %f", temp);
    }
}
