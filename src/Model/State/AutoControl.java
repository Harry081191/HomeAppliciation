package Model.State;

import Model.AbstrationFactory.Appliance;

public class AutoControl implements ControlState{
    @Override
    public void TurnOnAppliance(Appliance appliance) {
        System.out.println(appliance.GetApplianceName() + " is controlled by system. User can't turn it on manually.");
    }

    @Override
    public void TurnOffAppliance(Appliance appliance) {
        System.out.println(appliance.GetApplianceName() + " is controlled by system. User can't turn it off manually.");
    }
}
