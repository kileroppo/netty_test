package com.example.demo0713.sqltest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.sql.*;

public class TestJdbc {


    public static final String mysql_password = "root";
    public static final String mysql_name="root";
    public static final String mysql_url = "jdbc:mysql://127.0.0.1:3306/world?characterencoding=utf-8&useSSL=false";
    public static Connection conn = null;

    {
        try {

                Class.forName("com.mysql.cj.jdbc.Driver");
                conn = DriverManager.getConnection(mysql_url, mysql_name, mysql_password);

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
        System.out.println("一共话费时间："+ (System.currentTimeMillis() - start));
    }


    public static void testCrud(Student  stud)  {
        PreparedStatement statement =null;
        ResultSet result = null;
        try {
            // 1 加载驱动
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            conn = DriverManager.getConnection(mysql_url, mysql_name, mysql_password);

//             conn = getConnection();
            // 2 获取连接


            long id = stud.getId()+1000010;
            String name = stud.getName();
            String bird = stud.getBirthday();
            String addr = stud.getAddr();
            int hiredate = stud.getHiredate();
            int salary = stud.getSalary();
            int deptno = stud.getDeptno();

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
    private static void eachdata(String filePath) {
        String ss = readJsonFile(filePath);
        JSONObject jobj = JSON.parseObject(ss);
        JSONArray studs = jobj.getJSONArray("RECORDS");//构建JSONArray数组
            studs.parallelStream().forEach(jsOBjs->{
                Student student = JSONObject.parseObject(jsOBjs.toString(), Student.class);
                testCrud(student);
            });

//        for (int i = 0 ; i < studs.size();i++){
//            JSONObject key = (JSONObject)studs.get(i);
//
//            Student stud = new Student();
//            stud.setId(key.getLong("id"));
//            stud.setName(key.getString("name"));
//            stud.setBirthday(key.getString("birthday"));
//            stud.setAddr(key.getString("addr"));
//            stud.setHiredate( key.getIntValue("hiredate"));
//            stud.setSalary(key.getIntValue("salary"));
//            stud.setDeptno(key.getIntValue("deptno"));
////            System.out.printf("stud:"+ stud);
////            testInset(stud);
//            testCrud(stud);
//        }
    }

    public static Student jsonObjToString(JSONObject obj) {
        return JSONObject.parseObject(obj.toString(),Student.class);

    }

}
