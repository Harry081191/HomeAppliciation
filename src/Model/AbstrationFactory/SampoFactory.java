package Model.AbstrationFactory;

public class SampoFactory implements ApplianceFactory {

    private static SampoFactory sampoFactory = null;
    private Dehumidifier dehumidifier;
    private AirConditioner airConditioner;
    private SampoFactory(){
        airConditioner = new SampoAirConditioner();
        dehumidifier = new SampoDehumidifier();
    }

    public static synchronized SampoFactory GetSampoFactory() {
        if (sampoFactory == null) {
            sampoFactory = new SampoFactory();
        }

        return sampoFactory;
    }

    @Override
    public AirConditioner GetAirConditioner() {
        return airConditioner;
    }
    @Override
    public Dehumidifier GetDehumidifier(){
        return dehumidifier;
    }
}
