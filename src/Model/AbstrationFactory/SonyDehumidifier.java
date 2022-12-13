package Model.AbstrationFactory;

import Model.Bridge.SonyDehumidifierImplementor;

public class SonyDehumidifier extends Dehumidifier{
    SonyDehumidifier(){
        super("Sony",new SonyDehumidifierImplementor());
    }

    @Override
    public void GetDehumidifierState() {
        System.out.print("Sony Dehumidifier is dehumidifying");
    }
}
