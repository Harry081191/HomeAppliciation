package Model.Decorator;

import Model.AbstrationFactory.Dehumidifier;

public class AirPurifyDecorator extends DehumidifierDecorator {
    public AirPurifyDecorator (Dehumidifier dehumidifier) {
        super(dehumidifier);
    }

    @Override
    public void GetDehumidifierState() {
        this.dehumidifier.GetDehumidifierState();
        System.out.print(" and air purifying");
    }
}
