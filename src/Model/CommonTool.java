package Model;

public class CommonTool {
    public CommonTool(){}
    private static String connectionString = "jdbc:sqlserver://localhost:1433;" //資料庫伺服器主機位置，一般測試用在本機(localhost)
            + "database=HomeApplication;" // 資料庫名稱
            + "user=sa;" // 使用者帳戶
            + "password=32887297;" // 使用者密碼
            + "encrypt=true;"
            + "trustServerCertificate=true;"
            + "loginTimeout=30;"
            + "trustStore=C:\\Program Files\\Java\\jdk-18.0.2.1\\lib\\security\\cacerts;trustStorePassword=changeit;";

    public static String GetConnectionString(){
        return connectionString;
    }
}
