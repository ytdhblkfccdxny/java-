import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.ScrollPane;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.*;
import java.util.Calendar;
import java.util.Date;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;


public class ManagerUser extends JFrame implements TreeSelectionListener{
    JTree tree;
    private JPanel p;
    ManagerUser ManagerUser=this;
    ManagerUser(){
        Container c = this.getContentPane();
        DefaultMutableTreeNode root=new DefaultMutableTreeNode("分类");
        DefaultMutableTreeNode personalinfo=new DefaultMutableTreeNode("个人信息");
        DefaultMutableTreeNode info=new DefaultMutableTreeNode("个人资料");
        DefaultMutableTreeNode altermima=new DefaultMutableTreeNode("修改密码");
        DefaultMutableTreeNode yuangongattendance=new DefaultMutableTreeNode("员工考勤信息");
        DefaultMutableTreeNode add=new DefaultMutableTreeNode("增删查改");
        DefaultMutableTreeNode yuangongleave=new DefaultMutableTreeNode("员工请假信息");
        DefaultMutableTreeNode add1=new DefaultMutableTreeNode("增删改查");
        root.add(personalinfo);
        root.add(yuangongattendance);
        root.add(yuangongleave);
        personalinfo.add(info);
        personalinfo.add(altermima);
        yuangongattendance.add(add);
        yuangongleave.add(add1);
        tree=new JTree(root);
        //tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);//一次只能选一个
        tree.addTreeSelectionListener(this);
        p=new JPanel();//面板接受不同结点的界面
        p.setSize(1000,1000);
        c.add(new JScrollPane(tree),BorderLayout.WEST);
        c.add(p,BorderLayout.CENTER);
        setBounds(400,400,600,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    //查询该部门管理员的信息
    public ApartmentManager search(String id){
        ApartmentManager manager=new ApartmentManager();
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
            String sql = "select * from 部门管理员信息表 where 编号= '" + id + "'";
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
               manager.setId(rs.getString("编号"));
               manager.setName(rs.getString("姓名"));
               manager.setSex(rs.getString("性别"));
               manager.setApartment(rs.getString("部门"));
               manager.setId_num(rs.getString("身份证号"));
               manager.setBirthday(rs.getString("出生日期"));
               manager.setCall(rs.getString("联系电话"));
               manager.setAddress(rs.getString("家庭地址"));
               manager.setHobby(rs.getString("兴趣爱好"));
               manager.setAttach(rs.getString("备注"));
               System.out.println("hhhdfghn");
            }
        }catch (SQLException e2) {
            e2.printStackTrace();
        }
        return manager;
    }
    //个人资料
    public void changepanel() {
        System.out.println("移除");
        p.removeAll();
        JPanel p1 = new JPanel();
        p1.setSize(800, 800);
        p.add(p1);

        Box box, box1, box2, box3, box4, box5;
        box = Box.createVerticalBox();//垂直
        box1 = Box.createHorizontalBox();//水平
        box4 = Box.createHorizontalBox();
        box2 = Box.createVerticalBox();//垂直的盒子
        box3 = Box.createVerticalBox();

        box2.add(new JLabel("编号："));
        box2.add(Box.createHorizontalStrut(1));
        box2.add(new JLabel("姓名："));
        box2.add(Box.createHorizontalStrut(1));
        box2.add(new JLabel("性别："));
        box2.add(Box.createHorizontalStrut(1));
        box2.add(new JLabel("部门："));
        box2.add(Box.createHorizontalStrut(1));
        box2.add(new JLabel("身份证号："));
        box2.add(Box.createHorizontalStrut(1));
        box2.add(new JLabel("出生日期："));
        box2.add(Box.createHorizontalStrut(1));
        box2.add(new JLabel("联系电话："));
        box2.add(Box.createHorizontalStrut(1));
        box2.add(new JLabel("家庭地址："));
        box2.add(Box.createHorizontalStrut(1));
        box2.add(new JLabel("兴趣爱好："));
        box2.add(Box.createHorizontalStrut(1));
        box2.add(new JLabel("备注："));
        box2.setSize(100,400);

        //从数据库查找部门管理员资料
        ApartmentManager manager=search("20000");

        box3.add(new JLabel(manager.getId()));
        box3.add(Box.createHorizontalStrut(1));
        box3.add(new JLabel(manager.getName()));
        box3.add(Box.createHorizontalStrut(1));
        box3.add(new JLabel(manager.getSex()));
        box3.add(Box.createHorizontalStrut(1));
        box3.add(new JLabel(manager.getApartment()));
        box3.add(new JLabel(manager.getId_num()));
        box3.add(new JLabel(manager.getBirthday()));
        box3.add(new JLabel(manager.getCall()));
        box3.add(new JLabel(manager.getAddress()));
        box3.add(new JLabel(manager.getHobby()));
        box3.add(new JLabel(manager.getAttach()));
        box3.setSize(200,400);

        JButton button1, button2;
        button1 = new JButton("退出");
        button2 = new JButton("刷新");
        box4.add(button1);//水平
        box4.add(Box.createHorizontalStrut(20));
        box4.add(button2);
        box4.setSize(100,40);

        box1.add(box2);//水平
        box1.add(box3);
        box.add(box1);//垂直
        box.add(box4);
        box.setSize(500,500);

        p1.add(box);
        p.setVisible(true);

        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("刷新");
                changepanel();
            }
        });
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("退出");
                p.removeAll();
                System.out.println("退出成功!");
            }
        });
    }
    //连接数据库修改密码
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
            String sql1 = "update 部门管理员登录表 set 登录密码='" + mima+ "' where 编号='" + bianhao + "'";
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

    public void valueChanged(TreeSelectionEvent e) {
        DefaultMutableTreeNode selectionNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
        if (selectionNode.isLeaf()) { //叶子节点的监听
            System.out.println("叶子结点:" + selectionNode.toString());
            if (selectionNode.toString() == "个人资料") {    //个人资料
                //AddRouteListener computer = new AddRouteListener();
                System.out.println(selectionNode.toString());
                changepanel();
            }

            if (selectionNode.toString() == "修改密码") {//修改密码
                System.out.println(selectionNode.toString());
                p.removeAll();
                JPanel p1 = new JPanel();
                p.add(p1);
                JTextField textInput1;
                JTextField textInput2;
                JTextField textInput3;
                JTextField textInput4;

                JButton button1, button2, button3;
                Box box, box1, box2, box3, box4, box5;
                box = Box.createVerticalBox();
                box1 = Box.createHorizontalBox();
                box4 = Box.createHorizontalBox();
                box2 = Box.createVerticalBox();//垂直的盒子
                box3 = Box.createVerticalBox();
                box2.add(new JLabel("编号:"));
                box2.add(Box.createHorizontalStrut(1));
                box2.add(new JLabel("原密码:"));
                box2.add(Box.createHorizontalStrut(1));
                box2.add(new JLabel("新密码："));
                box2.add(Box.createHorizontalStrut(1));
                box2.add(new JLabel("确认新密码:"));
                box2.add(Box.createHorizontalStrut(1));
                button1 = new JButton("提交");
                button2 = new JButton("退出");
                button3 = new JButton("重置");
                textInput1 = new JTextField(10);
                textInput1.setText("20000");
                textInput1.setEditable(false);
                box3.add(textInput1);
                box3.add(Box.createHorizontalStrut(1));
                textInput2 = new JTextField(10);
                box3.add(textInput2);
                box3.add(Box.createHorizontalStrut(1));
                textInput3 = new JTextField(10);
                box3.add(textInput3);
                box3.add(Box.createHorizontalStrut(1));
                textInput4 = new JTextField(10);
                box3.add(textInput4);

                box4.add(button1);
                box4.add(Box.createHorizontalStrut(20));
                box4.add(button2);
                box4.add(button3);
                box1.add(box2);
                box1.add(box3);
                box.add(box1);
                box.add(box4);
                p1.add(box);
                p1.setBounds(0, 0, 800, 800);
                p.setVisible(true);

                button1.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (textInput2.getText().equals("") || textInput3.getText().equals("") || textInput4.getText().equals(""))
                            JOptionPane.showMessageDialog(null, "请将信息填写完整!!");
                        else {
                            int a = JOptionPane.showConfirmDialog(null, "确认修改吗？");
                            if (a == 0) {
                                System.out.println("部门管理员密码修改!");
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

                                    String sql1 = "select 登录密码 from  部门管理员登录表 where 编号='20000" + "'";
                                    ResultSet rs = s.executeQuery(sql1);
                                    while (rs.next()) {
                                        if (!rs.getString("登录密码").equals(textInput2.getText()))
                                            JOptionPane.showMessageDialog(null, "原密码输入有误!");
                                        else {
                                            if (!(textInput3.getText().equals(textInput4.getText())))
                                                JOptionPane.showMessageDialog(null, "重置失败，新密码输入前后不一致！");
                                            else if (textInput3.getText().length() <= 8 || textInput4.getText().length() <= 8)
                                                JOptionPane.showMessageDialog(null, "重置失败，新密码格式有误！");
                                            else {
                                                if (check("20000", textInput3.getText()))
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
                button2.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println("修改密码退出成功");
                        p.removeAll();
                    }
                });
                button3.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        textInput2.setText("");
                        textInput3.setText("");
                        textInput4.setText("");
                    }
                });
            }
        }
        if (selectionNode.toString() == "增删查改") {
            new Kaoqinn();
        }
        if (selectionNode.toString() == "增删改查") {
            new Leavejilu();
        }
    }

    public static  void main(String [] args){
        new ManagerUser();
    }



}
