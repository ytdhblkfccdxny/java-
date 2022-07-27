import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jxl.Cell;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import javax.swing.*;

class Export {
     public void writeExcel(String path) throws WriteException, IOException {
         //1. 导出Excel的路径
         String filePath = path;
         WritableWorkbook wwb = null;

         try {
             wwb = Workbook.createWorkbook(new File(filePath));
         } catch (Exception e) {
             e.printStackTrace();
         }

         //创建Excel表的"学生"区域的数据
         WritableSheet sheet = wwb.createSheet("员工考勤信息", 0);
         //或Sheet sheet=wwb.getSheet(0)获取第一个区域
         try {
             //2. 连接数据库的几行代码
             Connection con = null;
             PreparedStatement ps = null;
             ResultSet rs = null;
             String url = "jdbc:mysql://localhost:3306/企业考勤系统?" +
                     "useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=GMT%2B8&allowPublicKeyRetrieval=true";
             String username = "root";//连接数据库的用户名
             String password = "Llf20020717";//连接数据库的密码
             con = DriverManager.getConnection(url, username, password);
             String sql = "select * from 考勤信息表";
             ps = con.prepareStatement(sql);// SQL预处理
             rs = ps.executeQuery();
             //ResultSet是数据库中的数据，将其转换为List类型
             List<KaoQin> list = new ArrayList<KaoQin>();
             while (rs.next()) {
                 KaoQin stu = new KaoQin();
                 stu.setId(rs.getString("编号"));
                 stu.setName(rs.getString("姓名"));
                 stu.setApartment(rs.getString("部门"));
                 stu.setTime(rs.getString("考勤时间"));
                 stu.setLeibie(rs.getString("考勤类别"));
                 list.add(stu);
             }
             ps.close();
             con.close();
             //添加表格第一行的列名
             String[] title = {"编号", "姓名", "部门", "考勤时间", "考勤类别"};
             Label label = null;
             for (int i = 0; i < title.length; i++) {
                 label = new Label(i, 0, title[i]);
                 sheet.addCell(label);
             }
             for (int i = 0; i < list.size(); i++) {
                 //Number对应数据库的int类型数据
                 sheet.addCell(new jxl.write.Label(0, i + 1, list.get(i).getId()));//0列i+1行
                 //Label对应数据库String类型数据
                 sheet.addCell(new jxl.write.Label(1, i + 1, list.get(i).getName()));//1列i+1行
                 sheet.addCell(new jxl.write.Label(2, i + 1, list.get(i).getApartment()));//1列i+1行
                 sheet.addCell(new jxl.write.Label(3, i + 1, list.get(i).getTime()));//1列i+1行
                 sheet.addCell(new jxl.write.Label(4, i + 1, list.get(i).getLeibie()));//1列i+1行
             }
             wwb.write();
         } catch (SQLException e) {
             e.printStackTrace();
         } finally {
             //表格输出
             for (int i = 0; i < sheet.getRows(); i++) {
                 for (int j = 0; j < sheet.getColumns(); j++) {
                     Cell cell = sheet.getCell(j, i);//i行j列
                     System.out.print(cell.getContents() + " ");
                 }
                 System.out.println();
             }
             wwb.close();
         }
         JOptionPane.showMessageDialog(null,"导出成功!");
     }
}
public class ExportTest{
     public static void main(String[]args) throws WriteException, IOException {
         new Export().writeExcel("D:/export1.xls");

     }
}

