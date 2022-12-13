package Model;

import Controller.Controller;
import Model.AbstrationFactory.*;
import Model.Command.Invoker;
import Model.Command.TurnOffCommand;
import Model.Command.TurnOnCommand;
import Model.Observer.Observer;
import Model.Observer.Observables;

// Sensor作為Observables(被訂閱者)，每偵測一次就要通知一次Observer(訂閱者)家電現在是否開啟
public class IntegratedSensor implements Observables {
    private Invoker invoker;
    private Observer observer;
    private AirConditioner airConditioner;
    private Dehumidifier dehumidifier;
    private ApplianceFactory applianceFactory;
    private Controller controller;
    private TurnOnCommand turnOnCommand;
    private TurnOffCommand turnOffCommand;

    private boolean isACNeedToTurnOn = false, isACTurnedOn;
    private boolean isDehumidifierNeedToTurnedOn = false, isDehumidifierTurnedOn;

    public IntegratedSensor(User user){
        controller = Controller.GetController();

        this.AddObserver(user);
        this.SetAirConditioner();
        this.SetDehumidifier();

        invoker = new Invoker();
    }

    public double GetTemperature(){
        return airConditioner.GetTemperature();
    }

    public double GetHumidity(){
        return dehumidifier.GetHumidity();
    }

    public boolean DoesACNeedToTurnedOn(){
        if (this.GetTemperature() > ((User)observer).GetPreferTemperature()) {
            turnOnCommand = new TurnOnCommand(airConditioner);
            invoker.SetCommand(turnOnCommand);
            isACNeedToTurnOn = true;
        } else {
            turnOffCommand = new TurnOffCommand(airConditioner);
            invoker.SetCommand(turnOffCommand);
            isACNeedToTurnOn = false;
        }

        return isACNeedToTurnOn;
    }

    public boolean DoesDehumidifierNeedToTurnedOn(){
        if (this.GetHumidity() > ((User)observer).GetPreferHumidity()) {
            turnOnCommand = new TurnOnCommand(dehumidifier);
            invoker.SetCommand(turnOnCommand);
            isDehumidifierNeedToTurnedOn = true;
        } else {
            turnOffCommand = new TurnOffCommand(dehumidifier);
            invoker.SetCommand(turnOffCommand);
            isDehumidifierNeedToTurnedOn = false;
        }

        return isDehumidifierNeedToTurnedOn;
    }

    public void SetIsACTurnedOn(boolean prevState) {
        this.isACTurnedOn = prevState;
    }

    public boolean IsACTurnedOn() {
        return this.isACTurnedOn;
    }

    public void SetIsDehumidifierTurnedOn(boolean prevState) {
        this.isDehumidifierTurnedOn = prevState;
    }

    public boolean IsDehumidifierTurnedOn() {
        return this.isDehumidifierTurnedOn;
    }

    @Override
    public void AddObserver(Observer observer){
        this.observer = observer;
    }

    @Override
    public void remove(){
        observer = null;
    }

    @Override
    public void NotifyObserver(String appliance, String operation){
        observer.Update(appliance, operation);
    }

    public void TurnOnAppliance() {
        invoker.Execute();
    }

    public void TurnOffAppliance(){
        invoker.Execute();
    }

    public AirConditioner GetAirConditioner() {
        return airConditioner;
    }

    public Dehumidifier GetDehumidifier(){
        return dehumidifier;
    }

    public void SetAirConditioner() {
        if (controller.GetRoomACBrand(((User )observer).GetUserName()).equalsIgnoreCase("Sampo")) {
            applianceFactory = SampoFactory.GetSampoFactory();
        } else {
            applianceFactory = SonyFactory.GetSonyFactory();
        }

        airConditioner = applianceFactory.GetAirConditioner();
    }

    public void SetDehumidifier() {
        if (controller.GetRoomDehumidifierBrand(((User )observer).GetUserName()).equalsIgnoreCase("Sampo")) {
            applianceFactory = SampoFactory.GetSampoFactory();
        } else {
            applianceFactory = SonyFactory.GetSonyFactory();
        }

        dehumidifier = applianceFactory.GetDehumidifier();
    }
}
