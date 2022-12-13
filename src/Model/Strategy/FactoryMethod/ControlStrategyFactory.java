package Model.Strategy.FactoryMethod;

import Model.Strategy.AutoControlStrategy;
import Model.Strategy.ControlStrategy;
import Model.Strategy.ManualControlStrategy;

public class ControlStrategyFactory implements IControlStrategyFactory{

    // 預設為自動控制的策略
    @Override
    public ControlStrategy CreateControlStrategy(String controlType) {
        if (controlType.equalsIgnoreCase("auto")) {
            return new AutoControlStrategy();
        } else if (controlType.equalsIgnoreCase("manual")) {
            return new ManualControlStrategy();
        } else {
            return new AutoControlStrategy();
        }
    }
}
