package Model.Strategy;

import Model.State.ControlStateController;
import Model.State.ManualControl;
import View.Operation;

import javax.swing.*;

public class ManualControlStrategy implements ControlStrategy{

    @Override
    public void ChangeControlMode(ControlStateController controlStateController, JButton controlSwitch) {
        controlStateController.SetState(new ManualControl());
        controlSwitch.setText("自動操控");
        Operation.GetRoom().ChangeIfKeepCheck(false);
        System.out.println("Manual Control ON");
    }
}
