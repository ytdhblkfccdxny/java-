import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
//导入操作Excel的包
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

//excel 读取类
class excelRead{
    private static String driver = "com.mysql.cj.jdbc.Driver";
    String url="jdbc:mysql://localhost:3306/企业考勤系统?" +
            "useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=GMT%2B8&allowPublicKeyRetrieval=true";
    String username="root";//连接数据库的用户名
    String password="Llf20020717";//连接数据库的密码
    private static Connection con = null;
    private static PreparedStatement pstatement=null;

    public void readExcel(File path) throws BiffException, IOException{
        //连接数据库
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        try {
            con = DriverManager.getConnection(url,username,password);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if(con!=null) {
            System.out.println("sucess connect to mysql");
        }
        //读取excel数据表
        Workbook workbook = Workbook.getWorkbook(path);//获得指定路径下的文件
        Sheet[] sheets = workbook.getSheets();
        if(sheets!=null)
        {
            for(Sheet sheet:sheets)
            {
                //获取行数
                int rows = sheet.getRows();
                System.out.println(rows);
                //获取列数
                int cols = sheet.getColumns();
                System.out.println(cols);
                //读取数据
                for(int row = 0;row<rows;row++)  //z这里row从2开始是因为去除了表头占的两行
                {
                    String values[] = new String[5];
                    for(int col=0;col<cols;col++)
                    {
                        //将每行不同列的内容放入数组
                        values[col] = sheet.getCell(col,row).getContents();
                    }
                    //将读取出来的内容写入mysql数据库
                    try {
                        pstatement = con.prepareStatement("insert into 考勤信息" +
                                "表 values(?,?,?,?,?);");
                        pstatement.setNString(1, values[0]);
                        pstatement.setNString(2,values[1]);
                        pstatement.setNString(3,values[2]);
                        pstatement.setNString(4,values[3]);
                        pstatement.setNString(5,values[4]);
                        pstatement.executeUpdate();  //执行sql语句插入内容
                        System.out.println("导入成功!");
                    } catch (SQLException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                JOptionPane.showMessageDialog(null,"导入成功!");
            }
        }
        workbook.close();
        try {
            con.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
public class testExcel2Mysql {
    public static void main(String[] args) throws BiffException, IOException{
        File path = new File("D:/export.xls");
        new excelRead().readExcel(path);  // 传入要操作的excel路径
    }
}


