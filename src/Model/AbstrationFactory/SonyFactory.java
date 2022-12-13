package Model.AbstrationFactory;

public class SonyFactory implements ApplianceFactory{

    private static SonyFactory sonyFactory = null;
    private Dehumidifier dehumidifier;
    private AirConditioner airConditioner;
    private SonyFactory() {
        airConditioner = new SonyAirConditioner();
        dehumidifier = new SonyDehumidifier();
    }

    public static synchronized SonyFactory GetSonyFactory() {
        if (sonyFactory == null) {
            sonyFactory = new SonyFactory();
        }

        return sonyFactory;
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
