package Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseService {
    private String connectionString = CommonTool.GetConnectionString();

    // 登入
    public boolean Login(String userName, String password) {

        // 只要帳號或密碼沒有輸入就return false
        if(userName.isEmpty() || password.isEmpty()){
            return false;
        }

        String sql = String.format("SELECT COUNT(*) AS IsUserExist  FROM USERDATA WHERE USERNAME = '%s' AND PASSWORD = '%s'", userName, password);
        ResultSet resultSet;

        try (Connection connection = DriverManager.getConnection(connectionString);
             Statement statement = connection.createStatement()){

            // 只要沒有撈到資料就等於資料庫裡沒有對應的 User Data，所以直接return false
            // 如果有撈到資料但資料總數卻大於一筆就代表有誤，也return false
            resultSet = statement.executeQuery(sql);
            if (!resultSet.next() || resultSet.getInt("IsUserExist") != 1) {
                return false;
            }

            connection.close();
        }catch (SQLException e){
            e.printStackTrace();
        }

        return true;
    }

    // 確認帳號是否不存在，若不存在才可以以此帳號註冊
    public boolean IsUserNameNotExist(String userName){
        String sql = String.format("SELECT COUNT(*) AS IsUserNameExist FROM USERDATA WHERE USERNAME = '%s'", userName);
        ResultSet resultSet;

        try (Connection connection = DriverManager.getConnection(connectionString);
             Statement statement = connection.createStatement()){

            // 只要有撈到東西就代表帳號已經存在，因此return false
            resultSet = statement.executeQuery(sql);
            if (resultSet.next() && resultSet.getInt("IsUserNameExist") == 1) {
                return false;
            }

            connection.close();
        }catch (SQLException e){
            e.printStackTrace();
        }

        return true;
    }

    // 註冊
    public void AddUser(String userName, String password){
        String sql = String.format("INSERT INTO userdata (USERNAME, PASSWORD, PREFERTEMPERATURE, PREFERHUMIDITY, ROOMAC, ROOMDEHUMIDIFIER, ISUSERFIRSTLOGIN) VALUES ('%s', '%s', 30.0, 0.5, '%s', '%s', 1)", userName, password, "Sony", "Sony");

        try (Connection connection = DriverManager.getConnection(connectionString);
             PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){

            preparedStatement.execute();
            connection.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    // 取得使用者偏好
    public double[] GetUserPreference(String userName) {
        String sql = String.format("SELECT PREFERTEMPERATURE, PREFERHUMIDITY FROM USERDATA WHERE USERNAME = '%s'", userName);
        ResultSet resultSet = null;
        double[] userPreference = new double[2];

        try (Connection connection = DriverManager.getConnection(connectionString);
             Statement statement = connection.createStatement()){

            resultSet = statement.executeQuery(sql);

            resultSet.next();
            userPreference[0] = resultSet.getDouble("PREFERTEMPERATURE");
            userPreference[1] = resultSet.getDouble("PREFERHUMIDITY");

            connection.close();
        }catch (SQLException e){
            e.printStackTrace();
        }

        return userPreference;
    }

    public void SaveAppliancesSetting(String userName, String ACBrand, String DHBrand, String preferTemp, String preferHumid){
        String sql = String.format("UPDATE USERDATA SET ROOMAC = '%s', ROOMDEHUMIDIFIER = '%s', PREFERTEMPERATURE = '%f', PREFERHUMIDITY = '%f' WHERE USERNAME = '%s'",
                ACBrand, DHBrand, Float.parseFloat(preferTemp), Float.parseFloat(preferHumid), userName);

        try (Connection connection = DriverManager.getConnection(connectionString);
             PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){

            preparedStatement.execute();
            connection.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public boolean IsUserFirstLogin(String userName) {
        String sql = String.format("SELECT ISUSERFIRSTLOGIN FROM USERDATA WHERE USERNAME = '%s'", userName);
        ResultSet resultSet = null;
        boolean isUserFirstLogin = false;

        try (Connection connection = DriverManager.getConnection(connectionString);
             Statement statement = connection.createStatement()){

            resultSet = statement.executeQuery(sql);

            resultSet.next();
            isUserFirstLogin = resultSet.getBoolean("ISUSERFIRSTLOGIN");

            connection.close();
        }catch (SQLException e){
            e.printStackTrace();
        }

        return isUserFirstLogin;
    }

    public void SetUserNotFirstLogin(String userName) {
        String sql = String.format("UPDATE USERDATA SET ISUSERFIRSTLOGIN = 0 WHERE USERNAME = '%s'", userName);

        try (Connection connection = DriverManager.getConnection(connectionString);
             PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){

            preparedStatement.execute();
            connection.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public String GetRoomACBrand(String userName) {
        String sql = String.format("SELECT ROOMAC FROM USERDATA WHERE USERNAME = '%s'", userName);
        ResultSet resultSet = null;
        String roomAC = "";

        try (Connection connection = DriverManager.getConnection(connectionString);
             Statement statement = connection.createStatement()){

            resultSet = statement.executeQuery(sql);

            resultSet.next();
            roomAC = resultSet.getNString("ROOMAC");

            connection.close();
        }catch (SQLException e){
            e.printStackTrace();
        }

        return roomAC;
    }

    public String GetRoomDehumidifierBrand(String userName) {
        String sql = String.format("SELECT ROOMDEHUMIDIFIER FROM USERDATA WHERE USERNAME = '%s'", userName);
        ResultSet resultSet = null;
        String roomDehumidifier = "";

        try (Connection connection = DriverManager.getConnection(connectionString);
             Statement statement = connection.createStatement()){

            resultSet = statement.executeQuery(sql);

            resultSet.next();
            roomDehumidifier = resultSet.getNString("ROOMDEHUMIDIFIER");

            connection.close();
        }catch (SQLException e){
            e.printStackTrace();
        }

        return roomDehumidifier;
    }

    public List<User> GetAllUserWithPreference() {
        String sql = String.format("SELECT USERNAME, PREFERTEMPERATURE, PREFERHUMIDITY, ISUSERFIRSTLOGIN FROM USERDATA WHERE USERNAME != 'admin'");
        ResultSet resultSet = null;
        List<User> userList = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(connectionString);
             Statement statement = connection.createStatement()){

            resultSet = statement.executeQuery(sql);

            while(resultSet.next()) {
                userList.add(new User(resultSet.getNString("USERNAME"),
                        resultSet.getDouble("PREFERTEMPERATURE"),
                        resultSet.getDouble("PREFERHUMIDITY"),
                        resultSet.getBoolean("ISUSERFIRSTLOGIN")));
            }


            connection.close();
        }catch (SQLException e){
            e.printStackTrace();
        }

        return userList;
    }
}
