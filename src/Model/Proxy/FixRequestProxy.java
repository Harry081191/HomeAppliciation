package Model.Proxy;

public class FixRequestProxy implements IFixAppliance {
    private IFixAppliance requestMaker;

    public FixRequestProxy(IFixAppliance requestMaker) {
        this.requestMaker = requestMaker;
    }

    @Override
    public void Request() {
        this.requestMaker.Request();
        this.PostFixRequest();
    }

    private void PostFixRequest() {
        System.out.println("Send this Request to Cooperative plumbers.");
    }
}
