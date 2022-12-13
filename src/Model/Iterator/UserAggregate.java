package Model.Iterator;

import Controller.Controller;
import Model.User;

import java.util.List;

public class UserAggregate implements Aggregate{
    private Controller controller;
    private List<User> userList;

    public UserAggregate() {
        controller = Controller.GetController();
        userList = controller.GetAllUserWithPreference();
    }

    @Override
    public Iterator CreateIterator() {
        return new UserIterator(this);
    }

    public int Size(){
        return userList.size();
    }

    public User GetUser(int position) {
        return userList.get(position);
    }
}
