package Model.Command;

public class Invoker {
    private Command command;

    public void SetCommand(Command command){
        this.command = command;
    }

    public void Execute(){
        this.command.Execute();
    }
}
