package com.example.demo0713.sqltest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FileTest {


//    public static Connection conn = null;
//    public static  TestJdbc testJdbc;
//    public static final String mysql_password = "root";
//    public static final String mysql_name="root";
//    public static final String mysql_url = "jdbc:mysql://127.0.0.1:3306/world?characterencoding=utf-8&useSSL=false";
//
//    {
//            try {
//                Class.forName("com.mysql.cj.jdbc.Driver");
//                conn = DriverManager.getConnection(mysql_url, mysql_name, mysql_password);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//             testJdbc = new TestJdbc(conn);
//        }

    public static void main(String[] args) throws IOException {
        String dirname = "D:/student.json";
        long start = System.currentTimeMillis();
        eachdata(dirname);
        System.out.println("一共话费时间："+ (System.currentTimeMillis() - start));

    }

    private static void eachdata(String filePath) {
        String ss = readJsonFile(filePath);
        JSONObject jobj = JSON.parseObject(ss);
        JSONArray studs = jobj.getJSONArray("RECORDS");//构建JSONArray数组

        for (int i = 0 ; i < studs.size();i++){
            JSONObject key = (JSONObject)studs.get(i);
            Student stud = new Student();
            stud.setId(key.getLong("id"));
            stud.setName(key.getString("name"));
            stud.setBirthday(key.getString("birthday"));
            stud.setAddr(key.getString("addr"));
            stud.setHiredate( key.getIntValue("hiredate"));
            stud.setSalary(key.getIntValue("salary"));
            stud.setDeptno(key.getIntValue("deptno"));
            System.out.printf("stud:"+ stud);
//            testInset(stud);
//            testJdbc.testCrud(stud);
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
}

