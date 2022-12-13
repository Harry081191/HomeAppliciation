package Model.Bridge;

public class SampoDehumidifierImplementor implements DehumidifierImplementor{
    public void Turn_on(){
        System.out.println("Sampo Dehumidifier is on");
    }
    public void Turn_off(){
        System.out.println("Sampo Dehumidifier is off");
    }
    public void ChangeHumid(double humid){
        System.out.printf("Sampo Dehumidifier is changing humidity to %f", humid);
    }

}
