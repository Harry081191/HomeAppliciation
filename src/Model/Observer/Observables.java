package Model.Observer;

public interface Observables {
    public void AddObserver(Observer observer);
    public void remove();
    public void NotifyObserver(String appliance, String operation);
}
