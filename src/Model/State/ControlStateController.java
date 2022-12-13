package Model.State;

import Model.AbstrationFactory.Appliance;

public class ControlStateController {
    private ControlState state;

    public void SetState(ControlState state) {
        this.state = state;
    }

    public void TurnOnAppliance(Appliance appliance) {
        state.TurnOnAppliance(appliance);
    }

    public void TurnOffAppliance(Appliance appliance) {
        state.TurnOffAppliance(appliance);
    }
}
