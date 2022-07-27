import java.sql.*;
public class JdbcTest {
    public static void main(String[]args) throws ClassNotFoundException, SQLException {
        //1.注册驱动/加载驱动器
        try{
            //调用Class.forName()方法加载驱动程序
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("成功加载MySQL驱动！");
        }catch(ClassNotFoundException e1){
            System.out.println("找不到MySQL驱动!");
            e1.printStackTrace();
        }
        //2.连接数据库
        //数据库地址
        String url="jdbc:mysql://localhost:3306/企业考勤系统?" +
                "useUnicode=true&characterEncoding=UTF-8&useSSL=false&" +
                "serverTimezone=GMT%2B8&allowPublicKeyRetrieval=true";
        String username="root";//连接数据库的用户名
        String password="mysqlLlf20020717";//连接数据库的密码
        Connection conn=DriverManager.getConnection(url,username,password);
        if(!conn.isClosed())
            System.out.println("school数据库连接成功!");
        //3.获取执行SQL的对象
        Statement stat=conn.createStatement();
        //4.定义执行SQL语句
        String tableSql = "create table t_buyers (username varchar(50) not null primary key,"
                + "password varchar(20) not null ); ";
        String databaseSql = "create database 商品;";
        //String sql1="insert into students values('2020210932','黎哈康',21)";
        //int row=stat.executeUpdate(sql1);//执行完返回受影响的行数
        //5.处理结果
        //if(row>=1)
            //System.out.println("执行成功");
        //else
            //System.out.println("执行不成功");
        // 执行建库语句
        //stat.executeUpdate(databaseSql);
        stat.close();
        conn.close();
        url="jdbc:mysql://localhost:3306/商品?" +
                "useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=GMT%2B8&allowPublicKeyRetrieval=true";
        Connection conn1=DriverManager.getConnection(url,username,password);
        if(!conn1.isClosed())
            System.out.println("商品数据库连接成功!");
        Statement stat1=conn1.createStatement();
        int row=stat1.executeUpdate("drop table t_buyers");
        if(row==0)
            System.out.println("删除成功");
        //int i=stat1.executeUpdate(tableSql);
        //if (i == 0) {
            //System.out.println("t_buyersr表已经创建成功！");
       //}
        //stat1.executeUpdate("insert into t_buyers values( '张三','123456')");
        //stat1.executeUpdate("insert into t_buyers values( '李四','1314521')");

        //查询数据
        ResultSet result = stat1.executeQuery("select * from t_buyers");
        while (result.next())
        {
            System.out.println(result.getString(1) + " " + result.getString(2));
        }
//        String id="2020210931";
//        String sql2="SELECT * FROM students where id='"+id+"'";
//        ResultSet rs=stat.executeQuery(sql2);
//        while(rs.next()){
//            System.out.println("学号="+rs.getString("id"));
//            System.out.println("姓名="+rs.getString("name"));
//            System.out.println("年龄="+rs.getString("age"));
//        }
        //6.释放资源
        //rs.close();
        result.close();
        stat1.close();
        conn1.close();

    }
}



//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import javax.swing.*;
//
//class LoginCheck1{
//    private String name;
//    private String password;
//    public LoginCheck1(String name,String password){
//        this.name=name;
//        this.password=password;
//    }
//    public boolean equals(){
//        if("root".equals(name)&&"123456".equals(password)){
//            return true;
//        }else{
//            return false;
//        }
//    }
//};
//class ActionHandle1{
//    private JFrame frame=new JFrame("学生信息管理系统");
//    private JTextField name=new JTextField();//设置文本框
//    private JPasswordField pass=new JPasswordField();
//    private JLabel but1=new JLabel("用户名:");
//    private JLabel but2=new JLabel("密   码:");
//    private JButton but3=new JButton("登录");
//    private JButton but4=new JButton("重置");
//
//    public ActionHandle1(){
//        but3.addActionListener(new ActionListener(){
//            public void actionPerformed(ActionEvent e){
//                if(e.getSource()==but3){
//                    String sname=name.getText();
//                    String spass=new String(pass.getPassword());
//                    LoginCheck1 log=new LoginCheck1(sname,spass);
//                    if(log.equals()){
//                        try {
//                            new Menu();
//                        } catch (Exception e1) {
//
//                            e1.printStackTrace();
//                        }
//                        frame.setVisible(false);
//
//                    }else{
//                        JOptionPane.showMessageDialog(null, "登录失败，错误的用户名或密码！");
//                    }
//                }
//            }
//        });
//        but4.addActionListener(
//                new ActionListener(){
//                    public void actionPerformed(ActionEvent e){
//                        if(e.getSource()==but4){
//                            name.setText("");
//                            pass.setText("");
//                        }
//                    }
//
//                });
//        frame.setLayout(null);
//        but1.setBounds(80, 40 , 80,30);
//        name.setBounds(140,42, 120, 25);    //
//        but2.setBounds(80, 80 , 80,30);
//        pass.setBounds(140,82, 120, 25);
//        but3.setBounds(130, 150 , 60,30);
//        but4.setBounds(210, 150 , 60,30);
//        frame.setSize(400,330);
//        frame.setLocation(300, 200);
//        frame.add(but1);
//        frame.add(name);
//        frame.add(pass);
//        frame.add(but2);
//        frame.add(but3);
//        frame.add(but4);
//        frame.setVisible(true);
//    }
//}
//public class  Enter{
//    public static void main(String[] args) {
//        new ActionHandle1();
//    }
//
//}
//
