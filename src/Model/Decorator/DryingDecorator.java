package Model.Decorator;

import Model.AbstrationFactory.Dehumidifier;

public class DryingDecorator extends DehumidifierDecorator{
    public DryingDecorator(Dehumidifier dehumidifier) {
        super(dehumidifier);
    }

    @Override
    public void GetDehumidifierState() {
        this.dehumidifier.GetDehumidifierState();
        System.out.print(" and drying");
    }
}
