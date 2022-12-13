package Model.Command;

import Model.AbstrationFactory.Appliance;

public class TurnOffCommand extends Command{

    public TurnOffCommand(Appliance appliance) {
        super(appliance);
    }
    public void Execute(){
        appliance.TurnOff();
    }
}
