package Model.AbstrationFactory;

import Model.Bridge.AirConditionerImplementor;
import Model.Bridge.SampoACImplementor;

public class SampoAirConditioner extends AirConditioner{
    SampoAirConditioner(){
        super("Sampo", new SampoACImplementor());
    }
}
