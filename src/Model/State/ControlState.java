package Model.State;

import Model.AbstrationFactory.Appliance;

public interface ControlState {
    public void TurnOnAppliance(Appliance appliance);
    public void TurnOffAppliance(Appliance appliance);
}
