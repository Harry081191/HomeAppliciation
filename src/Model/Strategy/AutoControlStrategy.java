package Model.Strategy;

import Model.State.AutoControl;
import Model.State.ControlStateController;
import View.Operation;

import javax.swing.*;

public class AutoControlStrategy implements ControlStrategy{

    @Override
    public void ChangeControlMode(ControlStateController controlStateController, JButton controlSwitch) {
        controlStateController.SetState(new AutoControl());
        controlSwitch.setText("手動操控");
        Operation.GetRoom().ChangeIfKeepCheck(true);
        System.out.println("Auto Control ON");
    }
}
