package Model.Strategy;

import Model.State.ControlStateController;

import javax.swing.*;

public interface ControlStrategy {

    public void ChangeControlMode(ControlStateController controlStateController, JButton controlSwitch);
}
