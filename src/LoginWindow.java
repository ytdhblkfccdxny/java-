import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import java.util.Random;
import javax.swing.JComponent;
import javax.swing.Timer;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.table.DefaultTableModel;

class ActionHandle<f2, f1> {
    private JFrame frame = new JFrame("企业考勤系统");//建立一个窗体
    private JLabel Label;//标签存放背景图
    private JLabel lab = new JLabel("企业考勤系统");//主标签
    private JTextField id = new JTextField();//设置文本框组件
    private JTextField yanzhengma = new JTextField();//设置文本框组件
    private JPasswordField passwd = new JPasswordField(4);//密码框组件
    private JComboBox comboBox = new JComboBox();//下拉列表框组件
    private JLabel lab1 = new JLabel("编   号:");//标签
    private JLabel lab11 = new JLabel("编号输入不规范:");//标签
    private JLabel lab2 = new JLabel("密   码:");//标签
    private JLabel lab3 = new JLabel("登录身份:");//标签
    private JButton but1 = new JButton("登录");//按钮
    private JButton but2 = new JButton("重置");//按钮
    private JButton but3 = new JButton("注册");//按钮
    ImageIcon icon=new ImageIcon("D:\\JDBC\\src\\好好看1.jpg","添加");
    private JButton but4 = new JButton("忘记密码");//忘记密码
    private JLabel lab4 = new JLabel("验证码:");//验证码
        /***员工界面***/
    class YuangongJMenuDemo {
        private  JFrame mainJFrame;//构造一个全局静态框架
        Container con; //创建一个容器
        JPanel panel;
        //JScrollPane JSPane;//创建一个上下 左右滚动的窗口
        //JTextArea text;//创建一个多行纯文本域
        JMenuBar mainMenuBar;//创建一个新的菜单栏
        JMenu personalinformation, dailyattendance, leavemanagement, more;//创建菜单栏中的菜单选项
        //“个人信息”菜单下的菜单项
        JMenuItem personaldata, changepassword;
        //"日常考勤"菜单下的菜单项
        JMenuItem qiandaoqiantui, attendancerecord;
        //"请假管理"菜单下的菜单项
        JMenuItem leave;
        JMenuItem leavecancellation;
        JMenuItem leaverecord;
        //“更多”菜单下的菜单项
        JMenuItem gonggao;
        JMenuItem exit;

        public  YuangongJMenuDemo() throws IOException {
            mainJFrame = new JFrame();
            mainJFrame.setPreferredSize(new Dimension(700, 700));
            mainJFrame.setLayout(null);
            mainJFrame.setTitle("员工界面");

            JLabel jl = new JLabel("您好，欢迎使用系统!", JLabel.CENTER);
            jl.setPreferredSize(new Dimension(700, 30));
            mainJFrame.add(jl);
            jl.setBounds(-150, 0, 700, 30);
            jl.setFont(new Font("宋体", Font.BOLD, 25));
            jl.setForeground(Color.decode("#375a7f"));

            con = mainJFrame.getContentPane();
            Image img = ImageIO.read(new File("D:\\JDBC\\src\\山水画4.jpg"));//图片在项目的位置
            ImageIcon icon = new ImageIcon(img);
            icon.setImage(icon.getImage().getScaledInstance(400, 400, Image.SCALE_DEFAULT));
            panel=new BackgroundPanel(icon.getImage());
            con.add(panel);
            panel.setBounds(-1,50,700,400);
            panel.setLayout(null);
            //panel.setBounds(0,70,650,650);
            JLabel label=new JLabel("");
            label.setFont(new Font("宋体", Font.PLAIN, 10));
            label.setBounds(120,100,100,30);
            panel.add(label);
            mainJFrame.getContentPane().setBackground(Color.lightGray);
            mainMenuBar = new JMenuBar();//创建JMemuBar
            /***必须在此添加菜单，才能下移菜单条***/
            mainJFrame.add(mainMenuBar);
            mainMenuBar.setBounds(0, 30, 390, 25);
            mainMenuBar.setBackground(Color.lightGray);

            personalinformation = new JMenu("个人信息");//创建四个JMenu
            dailyattendance = new JMenu("日常考勤");
            leavemanagement = new JMenu("请假管理");
            more = new JMenu("更多");

            //*****创建JMenuItem并添加对应的JMenu中

            //个人信息菜单
            mainMenuBar.add(personalinformation);
            personalinformation.setPreferredSize(new Dimension(100, 30));//菜单长宽度
            personalinformation.setForeground(Color.white);//菜单字体颜色

            //创建 个人信息菜单 下面的菜单项
            personaldata = new JMenuItem("个人资料");
            changepassword = new JMenuItem("修改密码");
            //*********在菜单栏中添加项目
            personalinformation.add(personaldata);
            personalinformation.add(changepassword);
            /***为个人资料安装监听***/
            personaldata.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String bianhao=null,name=null,sex=null,apartment=null,id_num=null,
                            birthday=null,call=null,address=null,hobby=null,attach=null;
                    System.out.println(panel);
                    mainJFrame.remove(panel);
                    // 打开一个新的页面
                    Image img = null;//图片在项目的位置
                    try {
                        img = ImageIO.read(new File("D:\\JDBC\\src\\山水画4.jpg"));
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    ImageIcon icon = new ImageIcon(img);
                    icon.setImage(icon.getImage().getScaledInstance(400, 400, Image.SCALE_DEFAULT));
                    panel=new BackgroundPanel(icon.getImage());
                    mainJFrame.add(panel);
                    panel.setBounds(-1, 51, 400, 400);
                    panel.setLayout(null);

                    try {
                        Class.forName("com.mysql.cj.jdbc.Driver");//创建驱动
                        System.out.println("又成功加载MySQL驱动！");
                    } catch (ClassNotFoundException s) {
                        System.out.println("找不到MySQL驱动!");
                        s.printStackTrace();
                    }
                    try {
                        String url = "jdbc:mysql://localhost:3306/企业考勤系统?" +
                                "useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=GMT%2B8&allowPublicKeyRetrieval=true";
                        String username = "root";//连接数据库的用户名
                        String passwords = "Llf20020717";
                        Connection c = DriverManager.getConnection(url, username, passwords);
                        if (c != null) {
                            //等效于if(!c.isClosed())
                            System.out.println("数据库又连接成功!");
                        } else {
                            System.out.println("数据库连接失败!");
                        }
                        Statement s = c.createStatement();//获取执行SQL的对象
                        /* 员工的检查 */
                        String sql = "select * from 员工信息表 where 编号= '" + id.getText() + "'";
                        ResultSet rs = s.executeQuery(sql);
                        while (rs.next()) {
                            bianhao = rs.getString("编号");
                            name = rs.getString("姓名");
                            sex = rs.getString("性别");
                            apartment = rs.getString("部门");
                            id_num = rs.getString("身份证号");
                            birthday = rs.getString("出生日期");
                            call = rs.getString("联系电话");
                            address = rs.getString("家庭地址");
                            hobby = rs.getString("兴趣爱好");
                            attach = rs.getString("备注");
                            System.out.println("hhhdfghn");
                        }
                    }catch (SQLException e2) {
                        e2.printStackTrace();
                    }
                    JLabel l1 = new JLabel("编   号:");//标签
                    l1.setBounds(60,30,60,25);
                    JLabel l2 = new JLabel("姓   名:");//标签
                    l2.setBounds(60,60,60,25);
                    JLabel l3 = new JLabel("性   别:");//标签
                    l3.setBounds(60,90,60,25);
                    JLabel l4 = new JLabel("部   门:");//标签
                    l4.setBounds(60,120,60,25);
                    JLabel l5 = new JLabel("身份证号:");//标签
                    l5.setBounds(60,150,60,25);
                    JLabel l6 = new JLabel("出生日期:");//标签
                    l6.setBounds(60,180,60,25);
                    JLabel l7 = new JLabel("联系电话:");//标签
                    l7.setBounds(60,210,60,25);
                    JLabel l8 = new JLabel("家庭地址:");//标签
                    l8.setBounds(60,240,60,25);
                    JLabel l9 = new JLabel("兴趣爱好:");//标签
                    l9.setBounds(60,270,60,25);
                    JLabel l10 = new JLabel("备   注:");//标签
                    l10.setBounds(60,300,60,25);
                    JLabel l11 = new JLabel(bianhao);//标签
                    l11.setBounds(130,30,100,25);
                    JLabel l12 = new JLabel(name);//标签
                    l12.setBounds(130,60,100,25);
                    JLabel l13 = new JLabel(sex);//标签
                    l13.setBounds(130,90,100,25);
                    JLabel l14 = new JLabel(apartment);//标签
                    l14.setBounds(130,120,100,25);
                    JLabel l15 = new JLabel(id_num);//标签
                    l15.setBounds(130,150,180,25);
                    JLabel l16 = new JLabel(birthday);//标签
                    l16.setBounds(130,180,180,25);
                    JLabel l17 = new JLabel(call);//标签
                    l17.setBounds(130,210,180,25);
                    JLabel l18 = new JLabel(address);//标签
                    l18.setBounds(130,240,200,25);
                    JLabel l19 = new JLabel(hobby);//标签
                    l19.setBounds(130,270,200,25);
                    JLabel l20 = new JLabel(attach);//标签
                    l20.setBounds(130,300,200,25);
                    panel.add(l1);panel.add(l2);panel.add(l3);panel.add(l4);panel.add(l5);
                    panel.add(l6);panel.add(l7);panel.add(l8);panel.add(l9);panel.add(l10);
                    panel.add(l11);panel.add(l12);panel.add(l13);panel.add(l14);panel.add(l15);
                    panel.add(l16);panel.add(l17);panel.add(l18);panel.add(l19);panel.add(l20);
                    System.out.println(panel);
                }
            });

            /***为修改密码安装监听***/
            changepassword.addActionListener(new ActionListener() {
                 boolean check(String bianhao,String mima) {
                    boolean result = false;
                    try {
                        Class.forName("com.mysql.cj.jdbc.Driver");//创建驱动
                        System.out.println("成功加载MySQL驱动！");
                    } catch (ClassNotFoundException e) {
                        System.out.println("找不到MySQL驱动!");
                        e.printStackTrace();
                    }
                    try {
                        String url = "jdbc:mysql://localhost:3306/企业考勤系统?" +
                                "useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=GMT%2B8&allowPublicKeyRetrieval=true";
                        String username = "root";//连接数据库的用户名
                        String passwords = "Llf20020717";
                        Connection c = DriverManager.getConnection(url, username, passwords);
                        if (c != null) {
                            //等效于if(!c.isClosed())
                            System.out.println("数据库连接成功!");
                        } else {
                            System.out.println("数据库连接失败!");
                        }
                        Statement s = c.createStatement();//获取执行SQL的对象
                        /* 员工的检查 */
                        String sql1 = "update 员工登录表 set 登录密码='" + mima+ "' where 编号='" + bianhao + "'";
                        int row = s.executeUpdate(sql1);
                        if (row == 1)
                            System.out.println("密码修改成功!");
                        else
                            System.out.println("密码修改不成功!");
                        result = true;

                    } catch (SQLException e2) {
                        e2.printStackTrace();
                    }
                    return result;
                }
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println(panel);
                    mainJFrame.remove(panel);
                    //添加新的panel
                    Image img = null;//图片在项目的位置
                    try {
                        img = ImageIO.read(new File("D:\\JDBC\\src\\山水画4.jpg"));
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    ImageIcon icon = new ImageIcon(img);
                    icon.setImage(icon.getImage().getScaledInstance(400, 400, Image.SCALE_DEFAULT));
                    panel=new BackgroundPanel(icon.getImage());
                    mainJFrame.add(panel);
                    panel.setBounds(-2, 51, 400, 400);
                    panel.setLayout(null);

                    JTextField bianhaoTextField;//编号文本框
                    JPasswordField passwordField;//原密码框
                    JPasswordField passwordField1;//密码框
                    JPasswordField passwordField2;//密码框
                    JLabel bianhaoLabel = new JLabel("编号:");//编号标签
                    bianhaoLabel.setFont(new Font("微软雅黑", Font.PLAIN, 15));
                    bianhaoTextField = new JTextField();//编号文本框
                    bianhaoTextField.setText(id.getText());
                    bianhaoTextField.setEditable(false);
                    //bianhaoTextField.setToolTipText("编号");
                    bianhaoTextField.setFont(new Font("微软雅黑", Font.PLAIN, 15));
                    JLabel passwordLabel=new JLabel("原密码:");
                    passwordLabel.setFont(new Font("微软雅黑", Font.PLAIN, 15));
                    passwordField=new JPasswordField();//原密码文本框
                    passwordField.setFont(new Font("微软雅黑", Font.PLAIN, 15));
                    JLabel passwordLabel1 = new JLabel("新密码:");//密码标签
                    passwordLabel1.setFont(new Font("微软雅黑", Font.PLAIN, 15));
                    passwordField1 = new JPasswordField();//密码文本框
                    passwordField1.setFont(new Font("微软雅黑", Font.PLAIN, 15));
                    JLabel passwordLabel2 = new JLabel("确认密码:");
                    passwordLabel2.setFont(new Font("微软雅黑", Font.PLAIN, 15));
                    passwordField2 = new JPasswordField();//密码文本框
                    passwordField2.setFont(new Font("微软雅黑", Font.PLAIN, 15));
                    JButton submitButton = new JButton("提交");//提交按钮
                    submitButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            if (String.valueOf(passwordField.getPassword()).equals("") || String.valueOf(passwordField1.getPassword()).equals("") || String.valueOf(passwordField2.getPassword()).equals(""))
                                JOptionPane.showMessageDialog(null, "请将信息填写完整!!");
                            else {
                                int a = JOptionPane.showConfirmDialog(null, "确定修改密码吗？", "提示：", JOptionPane.YES_NO_OPTION);
                                System.out.println(a);
                                if(a == 0) {
                                    try {
                                        Class.forName("com.mysql.cj.jdbc.Driver");//创建驱动
                                        System.out.println("成功加载MySQL驱动！");
                                    } catch (ClassNotFoundException s) {
                                        System.out.println("找不到MySQL驱动!");
                                        s.printStackTrace();
                                    }
                                    try {
                                        String url = "jdbc:mysql://localhost:3306/企业考勤系统?" +
                                                "useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=GMT%2B8&allowPublicKeyRetrieval=true";
                                        String username = "root";//连接数据库的用户名
                                        String passwords = "Llf20020717";
                                        Connection c = DriverManager.getConnection(url, username, passwords);
                                        if (c != null) {
                                            //等效于if(!c.isClosed())
                                            System.out.println("数据库连接成功!");
                                        } else {
                                            System.out.println("数据库连接失败!");
                                        }
                                        Statement s = c.createStatement();//获取执行SQL的对象
                                        /* 员工的检查 */
                                        String sql1 = "select 登录密码 from  员工登录表 where 编号='" + id.getText() + "'";
                                        ResultSet rs = s.executeQuery(sql1);
                                        while (rs.next()) {
                                            System.out.println("员工密码修改***");
                                            if (!rs.getString("登录密码").equals(String.valueOf(passwordField2.getPassword())))
                                                JOptionPane.showMessageDialog(null, "原密码输入有误!");
                                            else {
                                                if (!String.valueOf(passwordField1.getPassword()).equals(String.valueOf(passwordField2.getPassword())))
                                                    JOptionPane.showMessageDialog(null, "重置失败，新密码输入前后不一致！");
                                                else if (String.valueOf(passwordField1.getPassword()).length() <= 8 || String.valueOf(passwordField1.getPassword()).length() <= 8)
                                                    JOptionPane.showMessageDialog(null, "重置失败，新密码格式有误！");
                                                else {
                                                    if (check(bianhaoTextField.getText(), String.valueOf(passwordField1.getPassword())))
                                                        JOptionPane.showMessageDialog(null, "重置密码成功！");
                                                    else
                                                        JOptionPane.showMessageDialog(null, "重置密码失败！");
                                                }
                                            }
                                        }
                                    } catch (SQLException e2) {
                                        e2.printStackTrace();
                                    }
                                }
                            }
                        }
                    });
                    bianhaoLabel.setBounds(80, 50, 80, 30);
                    passwordLabel.setBounds(80,80,80,30);
                    passwordLabel1.setBounds(80, 110, 80, 30);
                    passwordLabel2.setBounds(80, 140, 80, 30);
                    bianhaoTextField.setBounds(150, 52, 120, 25);
                    passwordField.setBounds(150, 82, 120, 25);
                    passwordField1.setBounds(150, 112, 120, 25);
                    passwordField2.setBounds(150, 142, 120, 25);
                    submitButton.setBounds(100, 200, 60, 30);
                    panel.add(bianhaoLabel);
                    panel.add(bianhaoTextField);
                    panel.add(passwordLabel);
                    panel.add(passwordField);
                    panel.add(passwordLabel1);
                    panel.add(passwordField1);
                    panel.add(passwordLabel2);
                    panel.add(passwordField2);
                    panel.add(submitButton);
                    panel.setVisible(true);
                    System.out.println(panel);
                }
            });

            //日常考勤菜单
            mainMenuBar.add(dailyattendance);
            dailyattendance.setPreferredSize(new Dimension(100, 30));//菜单长宽度
            dailyattendance.setForeground(Color.white);//菜单字体颜色
            //创建 日常考勤菜单 下面的菜单项
            qiandaoqiantui = new JMenuItem("上、下班签到签退");
            attendancerecord = new JMenuItem("考勤记录");
            //*********在菜单栏中添加项目
            dailyattendance.add(qiandaoqiantui);
            dailyattendance.addSeparator();
            dailyattendance.add(attendancerecord);
            /***为签到、退安装监听***/
            qiandaoqiantui.addActionListener(new ActionListener() {
                void setTimer(JLabel time) {
                    final JLabel varTime = time;
                    varTime.setBackground(Color.PINK);
                    varTime.setForeground(Color.BLUE);
                    varTime.setBounds(75, 80, 250, 30);
                    varTime.setFont(new Font("微软雅黑", Font.BOLD, 20));
                    Timer timeAction = new Timer(1000, new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            long timemillis = System.currentTimeMillis();
                            // 转换日期显示格式
                            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            varTime.setText(df.format(new java.util.Date(timemillis)));
                        }
                    });
                    timeAction.start();//Timer可以通过start()方法来启动，stop()方法既是停止Timer。
                }
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println(panel);
                    mainJFrame.remove(panel);
                    // 打开一个新的页面
                    Image img = null;//图片在项目的位置
                    try {
                        img = ImageIO.read(new File("D:\\JDBC\\src\\山水画4.jpg"));
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    ImageIcon icon = new ImageIcon(img);
                    icon.setImage(icon.getImage().getScaledInstance(400, 400, Image.SCALE_DEFAULT));
                    panel=new BackgroundPanel(icon.getImage());
                    //添加新的panel
                    mainJFrame.add(panel);
                    panel.setBounds(-3, 51, 400, 400);
                    panel.setLayout(null);
                    JLabel time=new JLabel();
                    setTimer(time);
                    panel.add(time);
                    JButton button1=new JButton("签到");
                    //button1.setForeground(Color.BLACK);
                    button1.setFont(new Font("微软雅黑", Font.BOLD, 25));
                    button1.setBounds(60,200,100,50);
                    panel.add(button1);
                    button1.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            String name=null,apartment=null;
                            JOptionPane.showMessageDialog(null,"签到时间："+time.getText()+"\n\t签到成功!");
                            try {
                                Class.forName("com.mysql.cj.jdbc.Driver");//创建驱动
                                System.out.println("又成功加载MySQL驱动！");
                            } catch (ClassNotFoundException s) {
                                System.out.println("找不到MySQL驱动!");
                                s.printStackTrace();
                            }
                            try {
                                String url = "jdbc:mysql://localhost:3306/企业考勤系统?" +
                                        "useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=GMT%2B8&allowPublicKeyRetrieval=true";
                                String username = "root";//连接数据库的用户名
                                String passwords = "Llf20020717";
                                Connection c = DriverManager.getConnection(url, username, passwords);
                                if (c != null) {
                                    //等效于if(!c.isClosed())
                                    System.out.println("数据库又连接成功!");
                                } else {
                                    System.out.println("数据库连接失败!");
                                }
                                Statement s = c.createStatement();//获取执行SQL的对象
                                /* 员工的检查 */
                                String sql = "select * from 员工信息表 where 编号= '" + id.getText() + "'";
                                ResultSet rs = s.executeQuery(sql);
                                while (rs.next()) {
                                    name = rs.getString("姓名");
                                    apartment = rs.getString("部门");
                                    System.out.println("hhhdfghn");
                                }
                                String sql1 = "insert into 考勤信息表 values ('"+id.getText()+"','"+name+"','"+apartment+"','"
                                        +time.getText()+"','上班')";
                                int row=s.executeUpdate(sql1);
                                if(row==1)
                                    System.out.println("签到信息插入成功!");
                                else
                                    System.out.println("签到信息插入不成功!");
                            }catch (SQLException e2) {
                                e2.printStackTrace();
                            }
                        }
                    });
                    JButton button2=new JButton("签退");
                    button2.setFont(new Font("微软雅黑", Font.BOLD, 25));
                    button2.setBounds(240,200,100,50);
                    panel.add(button2);
                    button2.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            String name=null,apartment=null;
                            JOptionPane.showMessageDialog(null,"时间："+time.getText()+"\n\t签退成功!");
                            try {
                                Class.forName("com.mysql.cj.jdbc.Driver");//创建驱动
                                System.out.println("又成功加载MySQL驱动！");
                            } catch (ClassNotFoundException s) {
                                System.out.println("找不到MySQL驱动!");
                                s.printStackTrace();
                            }
                            try {
                                String url = "jdbc:mysql://localhost:3306/企业考勤系统?" +
                                        "useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=GMT%2B8&allowPublicKeyRetrieval=true";
                                String username = "root";//连接数据库的用户名
                                String passwords = "Llf20020717";
                                Connection c = DriverManager.getConnection(url, username, passwords);
                                if (c != null) {
                                    //等效于if(!c.isClosed())
                                    System.out.println("数据库又连接成功!");
                                } else {
                                    System.out.println("数据库连接失败!");
                                }
                                Statement s = c.createStatement();//获取执行SQL的对象
                                /* 员工的检查 */
                                String sql = "select * from 员工信息表 where 编号= '" + id.getText() + "'";
                                ResultSet rs = s.executeQuery(sql);
                                while (rs.next()) {
                                    name = rs.getString("姓名");
                                    apartment = rs.getString("部门");
                                    System.out.println("hhhdfghn");
                                }
                                String sql1 = "insert into 考勤信息表 values ('"+id.getText()+"','"+name+"','"+apartment+"','"
                                        +time.getText()+"','下班')";
                                int row=s.executeUpdate(sql1);
                                if(row==1)
                                    System.out.println("签退信息插入成功!");
                                else
                                    System.out.println("签退信息插入不成功!");
                            }catch (SQLException e2) {
                                e2.printStackTrace();
                            }
                        }
                    });
                    System.out.println(panel);
                }
            });
            /***为考勤记录安装监听***/
            attendancerecord.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println(panel);
                    mainJFrame.remove(panel);
                    // 打开一个新的页面
                    Image img = null;//图片在项目的位置
                    try {
                        img = ImageIO.read(new File("D:\\JDBC\\src\\山水画4.jpg"));
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    ImageIcon icon = new ImageIcon(img);
                    icon.setImage(icon.getImage().getScaledInstance(400, 400, Image.SCALE_DEFAULT));
                    panel=new BackgroundPanel(icon.getImage());
                    //添加新的panel
                    mainJFrame.add(panel);
                    JLabel label=new JLabel();
                    label.setText("温馨提示：请按“弹出”进行考勤信息的查阅!");
                    label.setBounds(40,100,300,30);
                    JLabel label1=new JLabel();
                    label1.setText("在弹出界面可输入年份或月份或指定时间进行查询");
                    label1.setBounds(40,130,300,30);
                    JButton button=new JButton("弹出");
                    button.setBounds(100,200,60,30);
                    button.setBackground(Color.PINK);

                    button.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            AttendanceShowData attendance=new AttendanceShowData();
                            attendance.showFrame(id.getText());
                        }
                    });
                    panel.add(label);panel.add(label1);panel.add(button);
                    panel.setBounds(-4, 51, 400, 400);
                    panel.setLayout(null);
                    mainJFrame.setVisible(true);
                    System.out.println(panel);
                }
            });

            //请假管理菜单
            mainMenuBar.add(leavemanagement);
            leavemanagement.setPreferredSize(new Dimension(100, 30));//菜单长宽度
            leavemanagement.setForeground(Color.white);//菜单字体颜色
            //创建 格式菜单 下面的菜单项
            //wrapItem = new JCheckBoxMenuItem("自动换行");
            leave = new JMenuItem("请假");
            leavecancellation=new JMenuItem("销假");
            leaverecord = new JMenuItem("请假记录");
            //*********在菜单栏中添加项目
            leavemanagement.add(leave);
            leavemanagement.addSeparator();
            leavemanagement.add(leavecancellation);
            leavemanagement.addSeparator();
            leavemanagement.add(leaverecord);
            /***为请假安装监听***/
            leave.addActionListener(new ActionListener() {
                boolean check(String bianhao,String name,String bumen,String timestart,String timelong,String liyou) {
                    boolean result = false;
                    try {
                        Class.forName("com.mysql.cj.jdbc.Driver");//创建驱动
                        System.out.println("成功加载MySQL驱动！");
                    } catch (ClassNotFoundException e) {
                        System.out.println("找不到MySQL驱动!");
                        e.printStackTrace();
                    }
                    try {
                        String url = "jdbc:mysql://localhost:3306/企业考勤系统?" +
                                "useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=GMT%2B8&allowPublicKeyRetrieval=true";
                        String username = "root";//连接数据库的用户名
                        String passwords = "Llf20020717";
                        Connection c = DriverManager.getConnection(url, username, passwords);
                        if (c != null) {
                            //等效于if(!c.isClosed())
                            System.out.println("数据库连接成功!");
                        } else {
                            System.out.println("数据库连接失败!");
                        }
                        Statement s = c.createStatement();//获取执行SQL的对象
                        /* 员工的检查 */
                        String sql1 = "insert  into 员工请假记录表 (编号,姓名,部门,起始时间,请假时长,请假理由)values('"+
                                bianhao+"','"+name+"','"+bumen+"','"
                                +timestart+"','"+timelong+"','"+liyou+"')";
                        int row = s.executeUpdate(sql1);
                        if (row == 1)
                            System.out.println("请假申请成功!");
                        else
                            System.out.println("请假申请不成功!");
                        result = true;

                    } catch (SQLException e2) {
                        e2.printStackTrace();
                    }
                    return result;
                }
                void setTimer(JLabel time) {
                    final JLabel varTime = time;
                    varTime.setBackground(Color.PINK);
                    varTime.setForeground(Color.BLUE);
                    //varTime.setBounds(130,120,400,60);
                    varTime.setFont(new Font("微软雅黑", Font.BOLD, 10));
                    Timer timeAction = new Timer(1000, new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            long timemillis = System.currentTimeMillis();
                            // 转换日期显示格式
                            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            varTime.setText(df.format(new java.util.Date(timemillis)));
                        }
                    });
                    timeAction.start();//Timer可以通过start()方法来启动，stop()方法既是停止Timer。
                }
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println(panel);
                    mainJFrame.remove(panel);
                    // 打开一个新的页面
                    Image img = null;//图片在项目的位置
                    try {
                        img = ImageIO.read(new File("D:\\JDBC\\src\\山水画4.jpg"));
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    ImageIcon icon = new ImageIcon(img);
                    icon.setImage(icon.getImage().getScaledInstance(400, 400, Image.SCALE_DEFAULT));
                    panel=new BackgroundPanel(icon.getImage());
                    //添加新的panel
                    mainJFrame.add(panel);
                    panel.setBounds(-5, 51, 400, 400);
                    panel.setLayout(null);
                    String bianhao=null,name=null,apartment=null;

                    try {
                        Class.forName("com.mysql.cj.jdbc.Driver");//创建驱动
                        System.out.println("又成功加载MySQL驱动！");
                    } catch (ClassNotFoundException s) {
                        System.out.println("找不到MySQL驱动!");
                        s.printStackTrace();
                    }
                    try {
                        String url = "jdbc:mysql://localhost:3306/企业考勤系统?" +
                                "useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=GMT%2B8&allowPublicKeyRetrieval=true";
                        String username = "root";//连接数据库的用户名
                        String passwords = "Llf20020717";
                        Connection c = DriverManager.getConnection(url, username, passwords);
                        if (c != null) {
                            //等效于if(!c.isClosed())
                            System.out.println("数据库又连接成功!");
                        } else {
                            System.out.println("数据库连接失败!");
                        }
                        Statement s = c.createStatement();//获取执行SQL的对象
                        /* 员工的检查 */
                        String sql = "select * from 员工信息表 where 编号= '" + id.getText() + "'";
                        ResultSet rs = s.executeQuery(sql);
                        while (rs.next()) {
                            bianhao = rs.getString("编号");
                            name = rs.getString("姓名");
                            apartment = rs.getString("部门");
                            System.out.println("hhhdfghn");
                        }
                    }catch (SQLException e2) {
                        e2.printStackTrace();
                    }
                    JButton button=new JButton("提交");
                    button.setBackground(Color.GRAY);
                    button.setBounds(50,220,60,30);
                    JLabel l1 = new JLabel("编   号:");//标签
                    l1.setBounds(100,30,60,25);
                    JLabel l2 = new JLabel("姓   名:");//标签
                    l2.setBounds(100,60,60,25);
                    JLabel l4 = new JLabel("部   门:");//标签
                    l4.setBounds(100,90,60,25);
                    JLabel l5 = new JLabel("起始时间:");//标签
                    l5.setBounds(100,120,60,25);
                    JLabel l6 = new JLabel("请假时长:");//标签
                    l6.setBounds(100,150,60,25);
                    JLabel l7 = new JLabel("请假理由:");//标签
                    l7.setBounds(100,180,60,25);
                    JLabel l11 = new JLabel(bianhao);//标签
                    l11.setBounds(170,30,100,25);
                    JLabel l12 = new JLabel(name);//标签
                    l12.setBounds(170,60,100,25);
                    JLabel l14 = new JLabel(apartment);//标签
                    l14.setBounds(170,90,100,25);
                    JLabel time = new JLabel();//标签
                    setTimer(time);
                    time.setBounds(170,120,400,30);
                    JTextField timelong=new JTextField();
                    timelong.setBounds(170,150,40,25);
                    JLabel hourslabel=new JLabel("小时");
                    hourslabel.setBounds(210,150,30,30);
                    JTextArea liyou=new JTextArea(40,50);//理由文本域
                    JScrollPane msgPane=new JScrollPane(liyou);//创建带滚动条的面板
                    msgPane.setBounds(170,180,200,50);
                    panel.add(l1);panel.add(l2);panel.add(l4);panel.add(l5);
                    panel.add(l6);panel.add(l7); panel.add(button);
                    panel.add(l11);panel.add(l12);panel.add(l14);panel.add(time);
                    panel.add(timelong);panel.add(hourslabel);panel.add(msgPane);
                    button.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            int a=JOptionPane.showConfirmDialog(null,"确认提交吗？","提示",JOptionPane.YES_NO_OPTION);
                            if(a==0){
                                if(check(id.getText(),l12.getText(),l14.getText(),time.getText(),timelong.getText(), liyou.getText()))
                                    JOptionPane.showMessageDialog(null,"提交成功！记得及时销假哦！");
                            }
                        }
                    });
                    System.out.println(panel);
                }
            });
            /***为销假安装监听***/
            leavecancellation.addActionListener(new ActionListener() {
            void setTimer(JLabel time) {
                final JLabel varTime = time;
                varTime.setBackground(Color.PINK);
                varTime.setForeground(Color.BLUE);
                varTime.setBounds(75, 80, 250, 30);
                varTime.setFont(new Font("微软雅黑", Font.BOLD, 20));
                Timer timeAction = new Timer(1000, new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        long timemillis = System.currentTimeMillis();
                        // 转换日期显示格式
                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        varTime.setText(df.format(new java.util.Date(timemillis)));
                    }
                });
                timeAction.start();//Timer可以通过start()方法来启动，stop()方法既是停止Timer。
            }
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(panel);
                mainJFrame.remove(panel);
                // 打开一个新的页面
                Image img = null;//图片在项目的位置
                try {
                    img = ImageIO.read(new File("D:\\JDBC\\src\\山水画4.jpg"));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                ImageIcon icon = new ImageIcon(img);
                icon.setImage(icon.getImage().getScaledInstance(400, 400, Image.SCALE_DEFAULT));
                panel=new BackgroundPanel(icon.getImage());
                //添加新的panel
                mainJFrame.add(panel);
                panel.setBounds(-3, 51, 400, 400);
                panel.setLayout(null);
                JLabel time=new JLabel();
                setTimer(time);
                panel.add(time);
                JButton button=new JButton("销假");
                button.setForeground(Color.LIGHT_GRAY);
                button.setFont(new Font("微软雅黑", Font.BOLD, 25));
                button.setBounds(150,200,100,50);
                panel.add(button);
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String name = null, apartment = null;
                        int a = JOptionPane.showConfirmDialog(null, "是否销假?");
                        if (a == 0) {
                            try {
                                Class.forName("com.mysql.cj.jdbc.Driver");//创建驱动
                                System.out.println("又成功加载MySQL驱动！");
                            } catch (ClassNotFoundException s) {
                                System.out.println("找不到MySQL驱动!");
                                s.printStackTrace();
                            }
                            try {
                                String url = "jdbc:mysql://localhost:3306/企业考勤系统?" +
                                        "useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=GMT%2B8&allowPublicKeyRetrieval=true";
                                String username = "root";//连接数据库的用户名
                                String passwords = "Llf20020717";
                                Connection c = DriverManager.getConnection(url, username, passwords);
                                if (c != null) {
                                    //等效于if(!c.isClosed())
                                    System.out.println("数据库又连接成功!");
                                } else {
                                    System.out.println("数据库连接失败!");
                                }
                                Statement s = c.createStatement();//获取执行SQL的对象
                                /* 员工的检查 */
                                String sql = "update 员工请假记录表 set 销假时间='"+time.getText()+"',是否销假='是' where 编号= '" + id.getText() + "' and 是否销假='否'";
                                int row = s.executeUpdate(sql);
                                if (row == 1) {
                                    System.out.println("销假成功!");
                                    JOptionPane.showMessageDialog(null,"销假成功!");
                                }
                                else {
                                    System.out.println("销假不成功!");
                                    JOptionPane.showMessageDialog(null,"销假成功!");
                                }
                            } catch (SQLException e2) {
                                e2.printStackTrace();
                            }
                        }
                    }
                });
                System.out.println(panel);
            }
        });
            /***为请假记录安装监听***/
            leaverecord.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed (ActionEvent e){
            System.out.println(panel);
            mainJFrame.remove(panel);
            // 打开一个新的页面
            Image img = null;//图片在项目的位置
            try {
                img = ImageIO.read(new File("D:\\JDBC\\src\\山水画4.jpg"));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            ImageIcon icon = new ImageIcon(img);
            icon.setImage(icon.getImage().getScaledInstance(400, 400, Image.SCALE_DEFAULT));
            panel=new BackgroundPanel(icon.getImage());
            //添加新的panel
            mainJFrame.add(panel);
            JLabel label=new JLabel();
            label.setText("温馨提示：请按“弹出”进行请假记录的查阅!");
            label.setBounds(40,100,300,30);
            JLabel label1=new JLabel();
            label1.setText("在弹出界面可输入任意请假起始年份或月份或指定时间进行查询");
            label1.setBounds(40,130,400,30);
            JButton button=new JButton("弹出");
            button.setBounds(100,200,60,30);
            button.setBackground(Color.PINK);

            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    LeaverecordShowData leave=new LeaverecordShowData();
                    leave.showFrame(id.getText());
                }
            });
            panel.add(label);panel.add(label1);panel.add(button);
            panel.setBounds(-4, 51, 400, 400);
            panel.setLayout(null);
            mainJFrame.setVisible(true);
            System.out.println(panel);
        }
            });

            //更多菜单
            mainMenuBar.add(more);
            more.setPreferredSize(new Dimension(100, 30));//菜单长宽度
            more.setForeground(Color.white);//菜单字体颜色
            //创建 格式菜单 下面的菜单项
            exit = new JMenuItem("回到首页");
            JMenuItem exit1 = new JMenuItem("退出系统");
            //*********在菜单栏中添加项目
            more.add(exit);
            more.addSeparator();
            more.add(exit1);
            /***为首页监听***/
            exit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println(panel);
                    mainJFrame.remove(panel);
                    // 打开一个新的页面
                    Image img = null;//图片在项目的位置
                    try {
                        img = ImageIO.read(new File("D:\\JDBC\\src\\山水画4.jpg"));
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    ImageIcon icon = new ImageIcon(img);
                    icon.setImage(icon.getImage().getScaledInstance(400, 400, Image.SCALE_DEFAULT));
                    panel=new BackgroundPanel(icon.getImage());
                    //添加新的panel
                    mainJFrame.add(panel);
                    panel.setBounds(-9, 51, 400, 400);
                    panel.setLayout(null);
                    JLabel label = new JLabel("这是默认的界面");
                    label.setFont(new Font("宋体", Font.PLAIN, 99));
                    label.setBounds(0, 100, 326, 297);
                    //panel.add(label);
                    System.out.println(panel);
                }
            });
            /***为退出系统监听***/
            exit1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                      mainJFrame.dispose();
                }
            });
            mainJFrame.setVisible(true);
            mainJFrame.setBounds(400, 400, 400, 400);
        }
    }

    /* 控制Label中的字体风格 */
    Font f1 = new Font("楷体", Font.BOLD, 25);
    Font f2 = new Font("楷体", Font.BOLD, 14);

    public ActionHandle() throws IOException {
        Yanzhencode vcode = new Yanzhencode();//创建一个验证码对象
        /* 给对象名为comboBox的Combobox控件，添加内容。 */
        comboBox.addItem("---请选择登录身份---");
        comboBox.addItem("管理员");
        comboBox.addItem("部门管理员");
        comboBox.addItem("员工");
        /*字体风格Font参数和Label控件的对象绑定。*/
        lab.setFont(f1);
        lab1.setFont(f2);
        lab2.setFont(f2);
        lab3.setFont(f2);
        but1.setBackground(Color.LIGHT_GRAY);//设置登录按钮字体颜色
        but2.setForeground(Color.GRAY);//设置重置按钮填充色
        /*设置密码框的掩码*/
        passwd.setEchoChar('*');
        passwd.setEditable(true);
        passwd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                while(passwd.getPassword().length<8||passwd.getPassword().length>=16) {
                    lab11.setForeground(Color.red);
                    frame.add(lab11);
                }

            }
        });
        but1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == but1) {
                    String bianhao = id.getText();
                    String password = new String(passwd.getPassword());
                    if (0 == bianhao.length() && 0 == password.length())
                        JOptionPane.showMessageDialog(null, "编号和密码不能为空");
                    else {
                        if (0 == bianhao.length()) {
                            JOptionPane.showMessageDialog(null, "编号不能为空");
                            id.grabFocus();
                        } else if (0 == password.length()) {
                            JOptionPane.showMessageDialog(null, "密码不可为空!");
                            passwd.grabFocus();
                        } else {
                            int s = check1(vcode);
                            if (comboBox.getSelectedItem().toString().equals("---请选择登录身份---")) {
                                JOptionPane.showMessageDialog(null, "请选择登录身份");
                            } else if (s == 0)
                                JOptionPane.showMessageDialog(null, "验证码不能为空");
                            else if (s == 2) {
                                JOptionPane.showMessageDialog(null, "验证码错误");
                            } else if (s == 1) {
                                /* 管理员的检查 */
                                if (comboBox.getSelectedItem().toString().equals("管理员")) {
                                    if (check(bianhao, password)) {
                                        JOptionPane.showMessageDialog(null, "登陆成功");
                                        new Manager();

                                    } else
                                        JOptionPane.showMessageDialog(null, "编号或密码错误");
                                }
                                /* 部门管理员的检查 */
                                if (comboBox.getSelectedItem().toString().equals("部门管理员")) {
                                    if (check(bianhao, password)) {
                                        JOptionPane.showMessageDialog(null, "登陆成功");
                                        new ManagerUser();
                                    } else
                                        JOptionPane.showMessageDialog(null, "编号或密码错误");
                                }
                                /* 员工的检查 */
                                if (comboBox.getSelectedItem().toString().equals("员工")) {
                                    if (check(bianhao, password)) {
                                        JOptionPane.showMessageDialog(null, "登陆成功");
                                        try {
                                            new YuangongJMenuDemo();//进入员工界面
                                        } catch (IOException ex) {
                                            ex.printStackTrace();
                                        }

                                    } else
                                        JOptionPane.showMessageDialog(null, "编号或密码错误");
                                }
                            }
                        }
                    }
                }
            }
        });
        but2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == but2) {
                    id.setText("");
                    passwd.setText("");
                }
            }

        });
        but3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    new Register();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        but4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    new Changepassword();
            }
        });
        frame.setLayout(null);
        lab.setBounds(new Rectangle(120, 20, 220, 40));//主标签
        lab1.setBounds(80, 50, 80, 30);//编号标签
        lab11.setBounds(250,50,150,30);
        id.setBounds(140, 52, 120, 25);//编号文本框、、、
        lab2.setBounds(80, 80, 80, 30);//密码标签
        lab11.setBounds(250,80,150,30);
        passwd.setBounds(140, 82, 120, 25);//密码框组件、、、
        lab3.setBounds(80, 110, 80, 30);//身份标签
        lab4.setBounds(80, 140, 80, 30);//验证码标签
        yanzhengma.setBounds(140, 142, 120, 25);//验证码文本框
        vcode.setLocation(265, 140);//验证码位置
        but1.setBounds(90, 180, 60, 30);//登录按钮
        but2.setBounds(180, 180, 60, 30);//重置按钮
        but3.setBounds(270, 180, 60, 30);//注册按钮
        but4.setBounds(100, 220, 200, 40);//忘记密码
        comboBox.setBounds(new Rectangle(140, 112, 140, 25));//下拉框组件
        frame.setSize(400, 400);//窗体长和宽
        frame.setLocation(400, 200);//窗体位置
        icon.setImage(icon.getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT));//按钮图片大小
        frame.add(lab);
        frame.add(lab3);
        frame.add(comboBox);
        frame.add(lab1);
        frame.add(id);
        frame.add(lab2);
        frame.add(passwd);
        frame.add(but4);
        frame.add(but1);
        frame.add(but2);
        frame.add(but3);
        frame.add(lab4);
        frame.add(yanzhengma);
        frame.add(vcode);
        frame.setVisible(true);
        //frame.setBackground(Color.blue);//把分层面板设置为蓝色，但是得先设置内容面板为透明状态
        //frame.getContentPane().setBackground(Color.orange);
        ((JPanel) frame.getContentPane()).setOpaque(false);//把内容面板转换为JPanel,并设置为透明，否则看不到分层面板的背景图片
        //等价于下面这句代码
        //((JPanel)frame.getContentPane()).setVisible(true);
        /*获取背景图*/
        Image img = ImageIO.read(new File("D:\\JDBC\\src\\山水画4.jpg"));//图片在项目的位置
        ImageIcon icon = new ImageIcon(img);
        icon.setImage(icon.getImage().getScaledInstance(400, 400, Image.SCALE_DEFAULT));
        Label = new JLabel(icon);//标签存放背景图
        frame.getLayeredPane().setLayout(null);
        frame.getLayeredPane().add(Label, new Integer(Integer.MIN_VALUE));
        Label.setBounds(0, 0, 400, 400);
        //frame.getContentPane().setBackground(Color.orange);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    //判断输入验证码是否正确
    public int  check1(Yanzhencode vcode) {
        int s;
        if (yanzhengma.getText() == null) {
             s=0;//空
        } else if (vcode.getCode().equals(yanzhengma.getText())) {
             s=1;//正确
        } else {
           s=2;//错误
        }
        return s;
    }
    public boolean check(String bianhao,String password){
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");//创建驱动
                System.out.println("成功加载MySQL驱动！");
            } catch (ClassNotFoundException e) {
                System.out.println("找不到MySQL驱动!");
                e.printStackTrace();
            }
            boolean result = false;
            try {
                String url = "jdbc:mysql://localhost:3306/企业考勤系统?" +
                        "useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=GMT%2B8&allowPublicKeyRetrieval=true";
                String username = "root";//连接数据库的用户名
                String passwords = "Llf20020717";
                Connection c = DriverManager.getConnection(url, username, passwords);
                if (c != null) {
                    //等效于if(!c.isClosed())
                    System.out.println("数据库连接成功!");
                } else {
                    System.out.println("数据库连接失败!");
                }
                Statement s = c.createStatement();//获取执行SQL的对象
                /* 管理员的检查 */
                if (comboBox.getSelectedItem().toString().equals("管理员")) {
                    String sql = "select * from 管理员登录表 " + "where  编号='" + bianhao + "' and 登录密码 = '"
                            + password + "'";
                    ResultSet rs = s.executeQuery(sql);
                    while (rs.next()) {
                        if (rs.getString("编号").equals(bianhao)
                                && rs.getString("登录密码").equals(password)) {
                            result = true;
                        } else {
                            return false;
                        }
                    }
                } else {
                    /* 部门管理员的检查 */
                    if (comboBox.getSelectedItem().toString().equals("部门管理员")) {
                        String sql = "select * from 部门管理员登录表 " + "where 编号 = '" + bianhao + "' and 登录密码 = '"
                                + password + "'";
                        ResultSet rs = s.executeQuery(sql);
                        while (rs.next()) {
                            if (rs.getString("编号").equals(bianhao)
                                    && rs.getString("登录密码").equals(password)) {
                                result = true; // 如果用户名和密码都正确，那么就将result返回为true；
                            } else {
                                return false;
                            }
                        }
                    } else
                        /* 员工的检查 */
                        if (comboBox.getSelectedItem().toString().equals("员工")) {
                        String sql = "select * from 员工登录表 where 编号= '" + bianhao
                                + "' and 登录密码 = '" + password + "'";
                        ResultSet rs = s.executeQuery(sql);
                        while (rs.next()) {
                            if (rs.getString("编号").equals(bianhao)
                                    && rs.getString("登录密码").equals(password)) {
                                result = true; // 如果用户名和密码都正确，那么就将result返回为true；
                            } else
                                return false;
                        }
                    }
                }
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
            return result;
        }
    }
public class  LoginWindow{
    public static void main(String[] args) throws IOException {
        new ActionHandle();
    }
}