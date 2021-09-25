package com.example.demo0713.sqltest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.demo0713.sqltest.until.RandomName;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class TestJdbc {


//    public static final String mysql_password = "root";
    public static final String mysql_name="root";
    public static final String mysql_password = "1366";
    public static final String mysql_url = "jdbc:mysql://127.0.0.1:3306/world?characterencoding=utf-8&useSSL=false";
    
    public static Connection conn = null;
    public static   List<String> nameCollects = null;
    public static   List<String> birDayCollects = null;
    private static List<Integer> numbers = new ArrayList<>(10000);
    public static  Random random = new Random();

    {
        try {

                Class.forName("com.mysql.cj.jdbc.Driver");
                conn = DriverManager.getConnection(mysql_url, mysql_name, mysql_password);
                // 随机生成1000名字
                nameCollects = RandomName.makehunderName(10000).parallelStream()
                               .collect(Collectors.toList());
                // 随机产生10000个生日
                birDayCollects = RandomName.genrateBirthDay(10000);
             
            // 随机生成10000个随机数 存入数组
                for (int i = 0; i < 10000; i++) {
                    int tmpNum = random.nextInt(10000);
                    numbers.add(tmpNum);
                }
        
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        if (conn == null) {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(mysql_url, mysql_name, mysql_password);
        }
        return conn;
    }



    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        String dirname = "D:/student.json";
        long start = System.currentTimeMillis();
        eachdata(dirname);
        System.out.println("插入100w一共花费时间："+ (System.currentTimeMillis() - start) + "ms");
    }
    
    // 读取数据
    private static void eachdata(String filePath) {
        String ss = readJsonFile(filePath);
        
        JSONObject jobj = JSON.parseObject(ss);
        JSONArray studs = jobj.getJSONArray("RECORDS");//构建JSONArray数组
        
        studs.parallelStream().forEach(jsOBjs->{
            Student student = JSONObject.parseObject(jsOBjs.toString(), Student.class);
            testCrud(student,  numbers.get(random.nextInt(10000)));
        });
    }
    
    //读取json文件
    public static String readJsonFile(String dirname) {

//        String dirname = "D:/student.json";
        String jsonStr = "";
        try {
            File jsonFile = new File(dirname);
            FileReader fileReader = new FileReader(jsonFile);
            Reader reader = new InputStreamReader(new FileInputStream(jsonFile),"utf-8");
            int ch = 0;
            StringBuffer sb = new StringBuffer();
            while ((ch = reader.read()) != -1) {
                sb.append((char) ch);
            }
            fileReader.close();
            reader.close();
            jsonStr = sb.toString();
            return jsonStr;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    
    
    // 插入数据
    public static void testCrud(Student stud, Integer integer)  {
        PreparedStatement statement =null;
        ResultSet result = null;
        try {
            
            long id = stud.getId();
            String name = nameCollects.get( integer);
            String bird = birDayCollects.get(integer);
            String addr = stud.getAddr();
            int hiredate = stud.getHiredate() + integer;
            int salary = stud.getSalary() + integer;
            int deptno = stud.getDeptno() +integer;

            // 3  预编译
//            String sql = "select * from student where name=?";
//
//            String sql2 = "update student set name=? where id=?";
//            String sql3 = "delete from student  where id=?";
            String sql4 = "insert into student VALUES (?,?,?,?,?,?,?)";

            statement = getConnection().prepareStatement(sql4);
            statement.setString(1, String.valueOf(id));
            statement.setString(2, name);
            statement.setString(3, bird);

            statement.setString(4, addr);
            statement.setString(5, String.valueOf(hiredate));
            statement.setString(6, String.valueOf(salary));

            statement.setString(7, String.valueOf(deptno));
//            statement.setString(2, String.valueOf(22));
//            statement.setString(1, String.valueOf(2562));
            // 4  执行
//             result = statement.executeQuery();
            int dd =  statement.executeUpdate();
            System.out.println("插入结果："+ dd);
            // 5 循环结果
//           while (result.next()){
//               System.out.printf("res:"+ result.getString("name") + "   "+ result.getString("addr"));
//           }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
//            try {
//                if (result != null) {
//                    result.close();
//                }
//                if (statement != null) {
//                    statement.close();
//                }
//                if (conn != null) {
//                    conn.close();
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }

        }


    }


 

    public static Student jsonObjToString(JSONObject obj) {
        return JSONObject.parseObject(obj.toString(),Student.class);

    }

}
