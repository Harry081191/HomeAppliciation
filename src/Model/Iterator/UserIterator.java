package Model.Iterator;

public class UserIterator implements Iterator{

    private int position = 0;
    private UserAggregate users;
    public UserIterator(UserAggregate users){
        this.users = users;
    }

    @Override
    public Object Next() {
        return users.GetUser(position++);
    }

    @Override
    public boolean IsDone() {
        return position >= users.Size();
    }
}
