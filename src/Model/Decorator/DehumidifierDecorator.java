package Model.Decorator;

import Model.AbstrationFactory.Dehumidifier;

public abstract class DehumidifierDecorator extends Dehumidifier {

    protected Dehumidifier dehumidifier;
    DehumidifierDecorator(Dehumidifier dehumidifier){
        this.dehumidifier = dehumidifier;
    }
    public abstract void GetDehumidifierState();
}
