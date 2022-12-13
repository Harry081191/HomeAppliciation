package Model.AbstrationFactory;

public interface ApplianceFactory {
    public AirConditioner GetAirConditioner();
    public Dehumidifier GetDehumidifier();

}
