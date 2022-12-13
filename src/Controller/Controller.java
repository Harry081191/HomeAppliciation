package Controller;

import Model.DatabaseService;
import Model.User;

import java.util.List;

public class Controller {
    private DatabaseService dbService = new DatabaseService();
    private static Controller controller = null;

    // private建構子增加此類別的保護，只有自己可以建構自己
    private Controller(){}

    /*
     * [ Day 5 ] 初探設計模式 - 單例模式 (Singleton)
     * https://ithelp.ithome.com.tw/articles/10203092
     * '''存取ＩＯ和資料庫等資源，這時候要考慮使用單例模式'''。
     */
    public static synchronized Controller GetController(){
        // synchronized 增加對於多線程的保護
        if (controller == null) {
            controller = new Controller();
        }

        return controller;
    }

    public boolean IsUserNameNotExist(String userName) {
        return dbService.IsUserNameNotExist(userName);
    }

    public boolean Login(String userName, String password) {
        return dbService.Login(userName, password);
    }

    public void AddUser(String userName, String password) {
        dbService.AddUser(userName, password);
    }

    public double[] GetUserPreference(String userName) {
        return dbService.GetUserPreference(userName);
    }

    public void SaveAppliancesSetting(String userName, String ACBrand, String DHBrand, String preferTemp, String preferHumid){
        dbService.SaveAppliancesSetting(userName, ACBrand, DHBrand, preferTemp, preferHumid);
    }

    public boolean IsUserFirstLogin(String userName) {
        return dbService.IsUserFirstLogin(userName);
    }

    public void SetUserNotFirstLogin(String userName) {
        dbService.SetUserNotFirstLogin(userName);
    }

    public String GetRoomACBrand(String userName) {
        return dbService.GetRoomACBrand(userName);
    }

    public String GetRoomDehumidifierBrand(String userName) {
        return dbService.GetRoomDehumidifierBrand(userName);
    }

    public List<User> GetAllUserWithPreference() {
        return dbService.GetAllUserWithPreference();
    }
}
