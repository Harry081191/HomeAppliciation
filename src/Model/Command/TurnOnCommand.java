package Model.Command;

import Model.AbstrationFactory.Appliance;

public class TurnOnCommand extends Command{

    public TurnOnCommand(Appliance appliance) {
        super(appliance);
    }
    public void Execute(){
        appliance.TurnOn();
    }
}
