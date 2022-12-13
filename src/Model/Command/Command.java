package Model.Command;

import Model.AbstrationFactory.Appliance;

public abstract class Command {
    protected Appliance appliance;
    public Command(Appliance appliance) {
        this.appliance = appliance;
    }
    public abstract void Execute();
}
