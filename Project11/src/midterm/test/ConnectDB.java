package midterm.test;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConnectDB {


    public static Connection connect = null;
    public static Statement statement = null;
    public static PreparedStatement ps = null;
    public static ResultSet resultSet = null;

    public static Connection connectToMySql() throws IOException, SQLException, ClassNotFoundException {
        //Properties prop = loadProperties();
        //String driverClass = prop.getProperty("MYSQLJDBC.driver");
        //String url = prop.getProperty("MYSQLJDBC.url");
        //String userName = prop.getProperty("MYSQLJDBC.userName");
        //String password = prop.getProperty("MYSQLJDBC.password");

        String driverClass = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/Student";
        String userName = "root";
        String password = "12345";


        Class.forName(driverClass);
        connect = DriverManager.getConnection(url,userName,password);
        System.out.println("Database is connected");
        return connect;
    }
    public List<String> readDataBase(String tableName, String columnName)throws Exception{
        List<String> data = new ArrayList<>();

        try{
            connectToMySql();
            statement = connect.createStatement();
            resultSet = statement.executeQuery("select * from " + tableName);
            data = getResultSetData(resultSet, columnName);

        } catch (ClassNotFoundException e) {
            throw e;
        }finally{
            close();
        }
        return data;
    }
    private void close() {
        try{
            if(resultSet != null){
                resultSet.close();
            }
            if(statement != null){
                statement.close();
            }
            if(connect != null){
                connect.close();
            }
        }catch(Exception e){

        }
    }
    private List<String> getResultSetData(ResultSet resultSet2, String columnName) throws SQLException {
        List<String> dataList = new ArrayList<String>();
        while(resultSet.next()){
            String itemName = resultSet.getString(columnName);

            dataList.add(itemName);

        }
        return dataList;
    }

    public void InsertDataFromArrayToMySql(int [] ArrayData,String tableName, String columnName)
    {
        try {
            connectToMySql();
            ps = connect.prepareStatement("DROP TABLE IF EXISTS `"+tableName+"`;");
            ps.executeUpdate();
            ps = connect.prepareStatement("CREATE TABLE `"+tableName+"` (`ID` int(11) NOT NULL AUTO_INCREMENT,`SortingNumbers` bigint(20) DEFAULT NULL,  PRIMARY KEY (`ID`) );");
            ps.executeUpdate();
            for(int n=0; n<ArrayData.length; n++){
                ps = connect.prepareStatement("INSERT INTO "+tableName+" ( "+columnName+" ) VALUES(?)");
                ps.setInt(1,ArrayData[n]);
                ps.executeUpdate();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void InsertDataFromStringToMySql(String ArrayData,String tableName, String columnName)
    {
        try {
            connectToMySql();
            ps = connect.prepareStatement("INSERT INTO "+tableName+" ( "+columnName+" ) VALUES(?)");
            ps.setString(1,ArrayData);
            ps.executeUpdate();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public List<String> directDatabaseQueryExecute(String passQuery,String dataColumn)throws Exception{
        List<String> data = new ArrayList<String>();

        try {
            connectToMySql();
            statement = connect.createStatement();
            resultSet = statement.executeQuery(passQuery);
            data = getResultSetData(resultSet, dataColumn);
        } catch (ClassNotFoundException e) {
            throw e;
        }finally{
            close();
        }
        return data;
    }

    public void InsertDataFromArrayListToMySql(List<Integer> list,String tableName, String columnName)
    {
        try {
            connectToMySql();
            for(Object st:list){
                ps = connect.prepareStatement("INSERT INTO "+tableName+" ( "+columnName+" ) VALUES(?)");
                ps.setObject(1,st);
                ps.executeUpdate();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void insertProfileToMySql(String tableName, String columnName1, String columnName2, String columnName3)
    {
        try {
            connectToMySql();
            ps = connect.prepareStatement("INSERT INTO "+tableName+" ( " + columnName1 + "," + columnName2 +"," + columnName3 +") VALUES(?,?,?)");
            ps.setInt(1,1);
            ps.setString(2,"Jahidul Islam");
            ps.setString(3,"Marine Science");
            ps.executeUpdate();


        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static List<User> readFromMySql()throws IOException, SQLException, ClassNotFoundException{
        List<User> list = new ArrayList<>();
        User user = null;
        try{
            Connection conn = connectToMySql();
            String query = "SELECT * FROM Alum";
            // create the java statement
            Statement st = conn.createStatement();
            // execute the query, and get a java resultset
            ResultSet rs = st.executeQuery(query);
            // iterate through the java resultset
            while (rs.next())
            {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String major = rs.getString("major");
                System.out.format("%s, %s, %s\n", name, id, major);
                user = new User(id, name , major);
                list.add(user);

            }
            st.close();
        }catch (Exception e){
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return list;
    }

    public static void main(String[] args)throws IOException, SQLException, ClassNotFoundException {


        insertProfileToMySql("Alum","id", "name","major");
        List<User> list = readFromMySql();
        for(User user:list){
            System.out.println(user.getName()+ " " + user.getId()+" "+user.getMajor());
        }

       /*  public static MongoDatabase connectToMongoDB() {
        MongoClient mongoClient = new MongoClient();
        mongoDatabase = mongoClient.getDatabase("user");
        System.out.println("Database Connected");

        return mongoDatabase;
        }*/

    }

}





