package Model.AbstrationFactory;

import Model.Bridge.SampoDehumidifierImplementor;

public class SampoDehumidifier extends Dehumidifier{
    public SampoDehumidifier(){
        super("Sampo",new SampoDehumidifierImplementor());
    }

    @Override
    public void GetDehumidifierState() {
        System.out.print("Sampo Dehumidifier is dehumidifying");
    }
}
