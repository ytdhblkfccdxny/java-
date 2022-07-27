import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
public class Jdbcbinghu {
        public static void main(String[]args) throws ClassNotFoundException, SQLException {
            //1.注册驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
            //2.连接数据库
            String url = "jdbc:mysql://localhost:3306/冰壶游戏?" +
                    "useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=GMT%2B8";
            //连接数据库的url
            String username = "root";//连接数据库的用户名
            String password = "Llf20020717";//连接数据库的密码
            Connection conn = DriverManager.getConnection(url, username, password);
            //3.获取执行SQL的对象
            Statement stat = conn.createStatement();
            //4.定义执行SQL语句
            ///////////////注册//////////////////////
            Scanner sc = new Scanner(System.in);
            String userID = sc.nextLine();//输入用户账号(字母、数字、汉字均可以)，遇换行符结束
            int gameID = 0;//初始化游戏账号为0,游戏账号为自增字段
            boolean flag = true;
            String mima = null;//定义mima
            while (flag) {
                mima = sc.next();
                int length = mima.length();
                if (length < 6 || length > 12) {
                    System.out.println("密码长度不规范，要求6至12位，请重新设置！");
                    mima = sc.next();
                }
                int zimusum = 0, shuzisum = 0, xiahuaxiansum = 0;
                for (int i = 0; i < length; i++) {
                    char c = mima.charAt(i);
                    if (c == '_')
                        xiahuaxiansum++;
                    else if (c >= '0' && c <= '9')
                        shuzisum++;
                    else if (c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z')
                        zimusum++;
                    else {
                        System.out.println("有不规范字符，密码只能至少为字母、数字、下划线中的两个组合，请重新设置！");
                        continue;
                    }
                }
                if ((zimusum == 0 && shuzisum == 0) || (zimusum == 0 && xiahuaxiansum == 0)
                        || (shuzisum == 0 && xiahuaxiansum == 0)) {
                    System.out.println("密码至少为字母、数字、下划线中的两个组合，请重新设置！");
                    continue;
                }
                System.out.println("密码设置成功！");
                flag = false;
            }
            //String sql1 = "insert into 注册登录 (用户账号,游戏账号,密码)" +
                   // "values("'+userID+'","+ (++gameID)+","'+ mima+'")";
            //int row = stat.executeUpdate(sql1);//执行完返回受影响的行数
            //5.处理结果
           // if (row >= 1)
                System.out.println("注册成功");
           // else
                System.out.println("注册不成功");
            ////////////////登录/////////////////
            String sql2="";
            //6.释放资源
            stat.close();
            conn.close();

        }
}


