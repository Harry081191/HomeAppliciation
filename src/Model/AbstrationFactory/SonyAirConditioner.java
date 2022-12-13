package Model.AbstrationFactory;

import Model.Bridge.SonyACImplementor;

public class SonyAirConditioner extends AirConditioner  {
    SonyAirConditioner(){
        super("Sony",new SonyACImplementor());
    }
}
