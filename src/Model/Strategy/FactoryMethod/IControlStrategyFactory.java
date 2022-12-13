package Model.Strategy.FactoryMethod;

import Model.Strategy.ControlStrategy;

// According to the book "The Zen of Design Patterns",
// we can use Factory Method to create Strategy Object,
// to resolve the problem of Strategy Pattern need to expose concrete strategy.

public interface IControlStrategyFactory {
    public ControlStrategy CreateControlStrategy(String controlType);
}
