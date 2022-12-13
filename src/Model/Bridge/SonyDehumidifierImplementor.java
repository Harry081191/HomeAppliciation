package Model.Bridge;

public class SonyDehumidifierImplementor implements DehumidifierImplementor{
    public void Turn_on(){
        System.out.println("Sony Dehumidifier is on");
    }
    public void Turn_off(){
        System.out.println("Sony Dehumidifier is off");
    }
    public void ChangeHumid(double humid){
        System.out.printf("Sony Dehumidifier is changing humidity to %f", humid);
    }
}
