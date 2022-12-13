package Model.State;

import Model.AbstrationFactory.Appliance;
import Model.Command.Invoker;
import Model.Command.TurnOffCommand;
import Model.Command.TurnOnCommand;

public class ManualControl implements ControlState{
    private Invoker invoker;

    public ManualControl() {
        invoker = new Invoker();
    }

    @Override
    public void TurnOnAppliance(Appliance appliance) {
        invoker.SetCommand(new TurnOnCommand(appliance));
        invoker.Execute();
    }

    @Override
    public void TurnOffAppliance(Appliance appliance) {
        invoker.SetCommand(new TurnOffCommand(appliance));
        invoker.Execute();
    }
}
