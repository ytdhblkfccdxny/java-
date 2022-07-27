import jxl.read.biff.BiffException;
import jxl.write.WriteException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

class BackgroundPanel extends JPanel {
    //private static final long serialVersionUID = -6352788025440244338L;
    private Image image = null;
    public BackgroundPanel(Image image) {
        this.image = image;

    }
    // 固定背景图片，允许这个JPanel可以在图片上添加其他组件
    protected void paintComponent(Graphics g) {
        g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);
    }
}
/***员工考勤信息***/
class Attendance1 {
    private Integer id;
    private String name;
    private String apartment;
    private String time;
    private String category;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getApartment() {
        return apartment;
    }

    public void setApartment(String apartment) {
        this.apartment = apartment;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Attendance{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", apartment='" + apartment + '\'' +
                ", time='" + time + '\'' +
                ", category='" + category + '\'' +
                '}';
    }
}
/***员工查询考勤信息界面***/
class AttendanceShowData {
    JComboBox cboYear=new JComboBox();
    JComboBox cboMonth=new JComboBox();
    JComboBox cboDay=new JComboBox();
    JScrollPane jScrollPane = new JScrollPane();

    /**
     * 初始化年月日下来列表框
     */
    public void AddInfo() {
        //年下拉选择框
        cboYear.addItem("年份");
        cboMonth.addItem("月份");
        cboDay.addItem("日");

        for (int i = 1950; i <= 2022; i++) {
            cboYear.addItem("" + i);
        }
        //月下拉选择框
        for (int i = 0; i < 9; i++) {
            cboMonth.addItem("0" + (i + 1));
            System.out.println("0"+(i+1));
        }

        for(int i=9;i<12;i++)
            cboMonth.addItem("" + (i + 1));
        //日下拉选择框
        for (int j = 0; j < 9; j++) {
            cboDay.addItem("0" + (j + 1));
        }
        for (int j = 9; j < 31; j++) {
            cboDay.addItem("" + (j + 1));
        }

    }

    //创建窗口，以列表展示从数据库中获取的数据
    void showFrame(String id)  {
        //1，设定窗口
        JFrame frame = new JFrame("从mysql中获取考勤信息并展示~");
        frame.setLocation(700, 400);
        //frame.setLayout(null);//导致table 在左上角，且查询不了
        //frame.setLayout(new FlowLayout());//导致table在右边
        frame.setSize(500, 300);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        System.out.println("时间下拉框");
        AddInfo();
        System.out.println("时间下拉框");
        cboYear.setSelectedIndex(0);
        cboMonth.setSelectedIndex(0);
        cboDay.setSelectedIndex(0);
        cboMonth.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                Object obj = cboMonth.getSelectedItem();// 取得选中月份
                System.out.println("asdfghjk");
                if (!obj.toString().equals("月份")) {
                    cboDay.removeAllItems();// 清空日的下拉列表框
                    cboDay.addItem("日");
                    int month = Integer.valueOf(obj.toString());
                    int days = 31;
                    if (month == 4 || month == 6 || month == 9 || month == 11) {
                        days = 30;
                    } else if (month == 2) {
                        //取得选中年份
                        int year = Integer.parseInt(cboYear.getSelectedItem()
                                .toString());
                        if (year % 400 == 0 || (year % 4 == 0 && year % 100 != 0)) {
                            // 是闰年
                            days = 29;
                        } else {
                            // 不是闰年
                            days = 28;
                        }
                    }
                    for (int j = 0; j < 9; j++) {
                        cboDay.addItem("0" + (j + 1));
                    }//for
                    for (int j = 9; j < days; j++) {
                        cboDay.addItem("" + (j + 1));
                    }//for
                }//if
            }
        });

        //添加button
        JButton button1 = new JButton("查询");
        button1.setBounds(150, 10, 50, 30);
        JButton button2 = new JButton("重置");
        button2.setBounds(210, 10, 50, 30);

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("dfghjkl");
                try {
                    connect(id);
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                frame.add(jScrollPane,FlowLayout.CENTER);// FlowLayout.CENTER
                frame.setVisible(true);
            }
        });
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("吉宏股份的");
                cboYear.setSelectedIndex(0);
                cboMonth.setSelectedIndex(0);
                cboDay.setSelectedIndex(0);
            }
        });


        //通过panel组合button，label
        cboYear.setSize(40,25);
        cboMonth.setSize(40,25);
        cboDay.setSize(40,25);
        JPanel panel = new JPanel();
        panel.setBackground(Color.white);
        panel.setSize(200, 100);
        panel.add(cboYear);
        panel.add(cboMonth);
        panel.add(cboDay);
        panel.add(button1);
        panel.add(button2);
        panel.setLayout(new GridLayout(1, 8));

        //6，添加表格、滚动条到容器中
        frame.add(panel,BorderLayout.NORTH);//BorderLayout.NORTH
        frame.setVisible(true);
    }

    void connect(String id) throws ClassNotFoundException, SQLException {
        //1,注册驱动信息
        Class.forName("com.mysql.cj.jdbc.Driver");
        //2,获取连接对象
        String url = "jdbc:mysql://localhost:3306/企业考勤系统?" +
                "useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=GMT%2B8&allowPublicKeyRetrieval=true";
        String username = "root";//连接数据库的用户名
        String password = "Llf20020717";//连接数据库的密码
        Connection conn = DriverManager.getConnection(url, username, password);
        if (!conn.isClosed())
            System.out.println("school数据库连接成功!");
        String sql = null;
        if (cboYear.getSelectedIndex()==0 && cboMonth.getSelectedIndex()==0 && cboDay.getSelectedIndex()==0)
            JOptionPane.showMessageDialog(null, "请输入指定时间进行查找！");
        else
            try {
                System.out.println("try---catch");
                System.out.println("开始查询ggg");
                System.out.println("下拉框:"+cboYear.getSelectedIndex()+" "+cboMonth.getSelectedIndex()+" "+cboDay.getSelectedIndex());
                if ((cboYear.getSelectedIndex()!=0) && cboMonth.getSelectedIndex()==0 && cboDay.getSelectedIndex()==0)
                    sql = "select * from 企业考勤系统.考勤信息表 where 编号='" + id + "' and 考勤时间 like '" + cboYear.getSelectedItem() + "%'";
                if (cboYear.getSelectedIndex()==0 && (cboMonth.getSelectedIndex()!=0) && (cboDay.getSelectedIndex()==0))
                    sql = "select * from 企业考勤系统.考勤信息表 where 编号='" + id + "' and 考勤时间 like '%-" + cboMonth.getSelectedItem() + "-%'";
                if (cboYear.getSelectedIndex()==0 && cboMonth.getSelectedIndex()==0 && (cboDay.getSelectedIndex()!=0))
                    sql = "select * from 企业考勤系统.考勤信息表 where 编号='" + id + "' and 考勤时间 like '%-%-" + cboDay.getSelectedItem() + "%'";
                if ((cboYear.getSelectedIndex()!=0) && (cboMonth.getSelectedIndex()!=0) && cboDay.getSelectedIndex()==0)
                    sql = "select * from 企业考勤系统.考勤信息表 where 编号='" + id + "' and 考勤时间 like '" + cboYear.getSelectedItem() + "-" + cboMonth.getSelectedItem() + "%'";
                if ((cboYear.getSelectedIndex()!=0) && (cboMonth.getSelectedIndex()!=0) && (cboDay.getSelectedIndex()!=0))
                    sql = "select * from 企业考勤系统.考勤信息表 where 编号='" + id + "'" +
                            " and 考勤时间 like '" + cboYear.getSelectedItem() + "-" + cboMonth.getSelectedItem() + "-" + cboDay.getSelectedItem() + "%'";
                if (cboYear.getSelectedIndex()==0 && (cboMonth.getSelectedIndex()!=0) && (cboDay.getSelectedIndex()!=0))
                    sql = "select * from 企业考勤系统.考勤信息表 where 编号='" + id + "' and 考勤时间 like '%-" + cboMonth.getSelectedItem() + "-" + cboDay.getSelectedItem() + "%'";
                if ((cboYear.getSelectedIndex()!=0) &&cboMonth.getSelectedIndex()==0 && (cboDay.getSelectedIndex()!=0))
                    sql = "select * from 企业考勤系统.考勤信息表 where 编号='" + id + "' and 考勤时间 like '" + cboYear.getSelectedItem() + "-%-" + cboDay.getSelectedItem() + "%'";

                //3,连接对象conn的方法prepareStatement获取SQL语句的预编译对象
                PreparedStatement parameter = conn.prepareStatement(sql);
                //4,执行sql
                ResultSet result = parameter.executeQuery();
                //返回的数据List
                LinkedList<Attendance1> list = new LinkedList<>();
                while (result.next()) {

                    Attendance1 attendance = new Attendance1();
                    attendance.setId(result.getInt("编号"));
                    attendance.setName(result.getString("姓名"));
                    attendance.setApartment(result.getString("部门"));
                    attendance.setTime(result.getString("考勤时间"));
                    attendance.setCategory(result.getString("考勤类别"));
                    list.add(attendance);
                    System.out.println("ok!");
                }
                if(list.size()==0)
                    JOptionPane.showMessageDialog(null,"无考勤记录！");
                result.close();
                parameter.close();
                conn.close();

                //2,添加table
                JTable table = null;
                String[] index = {"编号", "姓名", "部门", "考勤时间", "考勤类别"};
                Object[][] data = new Object[list.size()][index.length];
                //3,向data中添加数据
                for (int i = 0; i < list.size(); i++) {
                    Attendance1 atten = list.get(i);
                    data[i][0] = atten.getId();
                    data[i][1] = atten.getName();
                    data[i][2] = atten.getApartment();
                    data[i][3] = atten.getTime();
                    data[i][4] = atten.getCategory();
                }
                //int rows=list.size();int colums=index.length;
                //4,创建一个默认的表格模型
                DefaultTableModel defaultModel = new DefaultTableModel(data, index);
                table = new JTable(defaultModel)
                {
                    public boolean isCellEditable(int row,int column)
                    {
                        return false;//表格不允许被编辑
                    }
                };
                //table.isCellEditabl;
//
                table.setBackground(Color.white);
                table.setPreferredScrollableViewportSize(new Dimension(100, 80));//JTable的高度和宽度按照设定
                table.setFillsViewportHeight(true);

                //5，给表格设置滚动条
                jScrollPane.setViewportView(table);
                System.out.println("表格完成");
            } catch (Exception e){
                System.out.println(e.getMessage());
            }
    }
}
/***员工查询请假信息界面***/
class LeaverecordShowData {
    JComboBox cboYear=new JComboBox();
    JComboBox cboMonth=new JComboBox();
    JComboBox cboDay=new JComboBox();
    JScrollPane jScrollPane = new JScrollPane();

    /**
     * 初始化年月日下来列表框
     */
    public void AddInfo() {
        //年下拉选择框
        cboYear.addItem("年份");
        cboMonth.addItem("月份");
        cboDay.addItem("日");

        for (int i = 1950; i <= 2022; i++) {
            cboYear.addItem("" + i);
        }
        //月下拉选择框
        for (int i = 0; i < 9; i++) {
            cboMonth.addItem("0" + (i + 1));
            System.out.println("0"+(i+1));
        }

        for(int i=9;i<12;i++)
            cboMonth.addItem("" + (i + 1));
        //日下拉选择框
        for (int j = 0; j < 9; j++) {
            cboDay.addItem("0" + (j + 1));
        }
        for (int j = 9; j < 31; j++) {
            cboDay.addItem("" + (j + 1));
        }

    }

    //创建窗口，以列表展示从数据库中获取的数据
    public  void showFrame(String id)  {
        //1，设定窗口
        JFrame frame = new JFrame("从mysql中获取请假信息并展示~");
        frame.setLocation(700, 400);
        //frame.setLayout(null);//导致table 在左上角，且查询不了
        //frame.setLayout(new FlowLayout());//导致table在右边
        frame.setSize(500, 300);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        AddInfo();
        cboYear.setSelectedIndex(0);
        cboMonth.setSelectedIndex(0);
        cboDay.setSelectedIndex(0);
        cboMonth.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                Object obj = cboMonth.getSelectedItem();// 取得选中月份
                System.out.println("月份监听器!");
                if (obj != null) {
                    cboDay.removeAllItems();// 清空日的下拉列表框
                    cboDay.addItem("日");
                    int month = Integer.valueOf(obj.toString());
                    int days = 31;
                    if (month == 4 || month == 6 || month == 9 || month == 11) {
                        days = 30;
                    } else if (month == 2) {
                        //取得选中年份
                        int year = Integer.parseInt(cboYear.getSelectedItem()
                                .toString());
                        if (year % 400 == 0 || (year % 4 == 0 && year % 100 != 0)) {
                            // 是闰年
                            days = 29;
                        } else {
                            // 不是闰年
                            days = 28;
                        }
                    }
                    for (int j = 0; j < 9; j++) {
                        cboDay.addItem("0" + (j + 1));
                    }//for
                    for (int j = 9; j < days; j++) {
                        cboDay.addItem("" + (j + 1));
                    }//for

                }//if
            }
        });

        //添加button
        JButton button1 = new JButton("查询");
        button1.setBounds(150, 10, 50, 30);
        JButton button2 = new JButton("重置");
        button2.setBounds(210, 10, 50, 30);

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("dfghjkl");
                try {
                    connect(id);
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                frame.add(jScrollPane, FlowLayout.CENTER);// FlowLayout.CENTER
                frame.setVisible(true);
            }
        });
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("吉宏股份的");
                cboYear.setSelectedIndex(0);
                cboMonth.setSelectedIndex(0);
                cboDay.setSelectedIndex(0);
            }
        });


        //通过panel组合button，label
        JPanel panel = new JPanel();
        panel.setBackground(Color.white);
        panel.setSize(200, 100);
        cboYear.setSize(40,25);
        cboMonth.setSize(40,25);
        cboDay.setSize(40,25);
        panel.setBackground(Color.white);
        panel.setSize(200, 100);
        panel.add(cboYear);
        panel.add(cboMonth);
        panel.add(cboDay);
        panel.add(button1);
        panel.add(button2);
        panel.setLayout(new GridLayout(1, 8));

        //6，添加表格、滚动条到容器中
        frame.add(panel,BorderLayout.NORTH);//BorderLayout.NORTH
        frame.setVisible(true);
    }

    void connect(String id) throws ClassNotFoundException, SQLException {
        //1,注册驱动信息
        Class.forName("com.mysql.cj.jdbc.Driver");
        //2,获取连接对象
        String url = "jdbc:mysql://localhost:3306/企业考勤系统?" +
                "useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=GMT%2B8&allowPublicKeyRetrieval=true";
        String username = "root";//连接数据库的用户名
        String password = "Llf20020717";//连接数据库的密码
        Connection conn = DriverManager.getConnection(url, username, password);
        if (!conn.isClosed())
            System.out.println("school数据库连接成功!");
        String sql = null;
        if (cboYear.getSelectedIndex()==0 && cboMonth.getSelectedIndex()==0 && cboDay.getSelectedIndex()==0)
            JOptionPane.showMessageDialog(null, "请输入指定时间进行查找！");
        else
            try {
                System.out.println("try---catch");
                System.out.println("开始查询");
                if ((cboYear.getSelectedIndex()!=0) && cboMonth.getSelectedIndex()==0 && cboDay.getSelectedIndex()==0)
                    sql = "select * from 企业考勤系统.员工请假记录表 where 编号='" + id + "' and 起始时间 like '" + cboYear.getSelectedItem() + "%'";
                if (cboYear.getSelectedIndex()==0 && (cboMonth.getSelectedIndex()!=0) && (cboDay.getSelectedIndex()==0))
                    sql = "select * from 企业考勤系统.员工请假记录表 where 编号='" + id + "' and 起始时间 like '%-" + cboMonth.getSelectedItem() + "-%'";
                if (cboYear.getSelectedIndex()==0 && cboMonth.getSelectedIndex()==0 && (cboDay.getSelectedIndex()!=0))
                    sql = "select * from 企业考勤系统.员工请假记录表 where 编号='" + id + "' and 起始时间 like '%-%-" + cboDay.getSelectedItem() + "%'";
                if ((cboYear.getSelectedIndex()!=0) && (cboMonth.getSelectedIndex()!=0) && cboDay.getSelectedIndex()==0)
                    sql = "select * from 企业考勤系统.员工请假记录表 where 编号='" + id + "' and 起始时间 like '" + cboYear.getSelectedItem() + "-" + cboMonth.getSelectedItem() + "%'";
                if ((cboYear.getSelectedIndex()!=0) && (cboMonth.getSelectedIndex()!=0) && (cboDay.getSelectedIndex()!=0))
                    sql = "select * from 企业考勤系统.员工请假记录表 where 编号='" + id + "'" +
                            " and 起始时间 like '" + cboYear.getSelectedItem() + "-" + cboMonth.getSelectedItem() + "-" + cboDay.getSelectedItem() + "%'";
                if (cboYear.getSelectedIndex()==0 && (cboMonth.getSelectedIndex()!=0) && (cboDay.getSelectedIndex()!=0))
                    sql = "select * from 企业考勤系统.员工请假记录表 where 编号='" + id + "' and 起始时间 like '%-" + cboMonth.getSelectedItem() + "-" + cboDay.getSelectedItem() + "%'";
                if ((cboYear.getSelectedIndex()!=0) &&cboMonth.getSelectedIndex()==0 && (cboDay.getSelectedIndex()!=0))
                    sql = "select * from 企业考勤系统.员工请假记录表 where 编号='" + id + "' and 起始时间 like '" + cboYear.getSelectedItem() + "-%-" + cboDay.getSelectedItem() + "%'";
//

                //3,连接对象conn的方法prepareStatement获取SQL语句的预编译对象
                PreparedStatement parameter = conn.prepareStatement(sql);
                //4,执行sql
                ResultSet result = parameter.executeQuery();
                //返回的数据List
                LinkedList<Leaverecord> list = new LinkedList<>();
                while (result.next()) {

                    Leaverecord leave = new Leaverecord();
                    leave.setId(result.getString("编号"));
                    leave.setName(result.getString("姓名"));
                    leave.setApartment(result.getString("部门"));
                    leave.setStarttime(result.getString("起始时间"));
                    leave.setLength(result.getString("请假时长"));
                    leave.setOvertime(result.getString("销假时间"));
                    leave.setLiyou(result.getString("请假理由"));
                    leave.setState(result.getString("是否销假"));
                    list.add(leave);
                    System.out.println("ok!");
                }
                if(list.size()==0)
                    JOptionPane.showMessageDialog(null,"无请假记录！");
                result.close();
                parameter.close();
                conn.close();

                //2,添加table
                JTable table = null;
                String[] index = {"编号", "姓名", "部门", "起始时间", "请假时长","销假时间","请假理由","是否销假"};
                Object[][] data = new Object[list.size()][index.length];
                //3,向data中添加数据
                for (int i = 0; i < list.size(); i++) {
                    Leaverecord leave = list.get(i);
                    data[i][0] = leave.getId();
                    data[i][1] = leave.getName();
                    data[i][2] = leave.getApartment();
                    data[i][3] = leave.getStarttime();
                    data[i][4] = leave.getLength();
                    data[i][5] = leave.getOvertime();
                    data[i][6] = leave.getLiyou();
                    data[i][7] = leave.getState();

                }
                //int rows=list.size();int colums=index.length;
                //4,创建一个默认的表格模型
                DefaultTableModel defaultModel = new DefaultTableModel(data, index);
                table = new JTable(defaultModel)
                {
                    public boolean isCellEditable(int row,int column)
                    {
                        return false;//表格不允许被编辑
                    }
                };
//
                table.setBackground(Color.white);
                table.setPreferredScrollableViewportSize(new Dimension(100, 80));//JTable的高度和宽度按照设定
                table.setFillsViewportHeight(true);

                //5，给表格设置滚动条
                jScrollPane.setViewportView(table);
                System.out.println("表格完成");
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
    }
}
/***定义验证码***/
class Yanzhencode  extends JComponent implements MouseListener {
    private String codes;  //自动生成的验证码
    private int width, height = 40;  //设置验证码高度、宽度
    private int codesLength = 4;  //设置代码长度
    private Random random = new Random(); //生成数字的方法
    public Yanzhencode() {
        width = this.codesLength * 16 + (this.codesLength - 1) * 10; //根据验证码长度设置宽度
        setPreferredSize(new Dimension(width, height));  //设置背景大小
        setSize(width, height);  //设置验证码长度和宽度
        this.addMouseListener(this);//鼠标事件
        setToolTipText("点击可更换验证码");
    }
    //得到生成的验证码长度
    public int getCodesLength() {
        return codesLength;
    }
    //得到生成的验证码
    public String getCode() {
        return codes;
    }
    //设置验证码的长度
    public void setCodesLength(int codeLength) {
        if(codesLength < 4) {
            this.codesLength = 4;
        } else {
            this.codesLength = codeLength;
        }
    }
    //让验证码产生随机的颜色
    public Color getRandColor(int min, int max) {
        if (min > 255)
            min = 255;
        if (max > 255)
            max = 255;
        int red = random.nextInt(max - min) + min;
        int green = random.nextInt(max - min) + min;
        int blue = random.nextInt(max - min) + min;
        return new Color(red, green, blue);
    }
    // 设置验证码具体的数字或字母是什么（内容）
    protected String generateCode() {
        char[] codes = new char[this.codesLength];
        for (int i = 0, len = codes.length; i < len; i++) {
            //boolean random.nextBoolean()方法用于从该随机数生成器的序列得到下一个伪均匀分布的布尔值
            if (random.nextBoolean()) {
                codes[i] = (char) (random.nextInt(10) + 48);
            } else {
                codes[i] = (char) (random.nextInt(26) + 97);
            }
        }
        this.codes = new String(codes);//将字符数组转换为字符串形式
        return this.codes;
    }
    @Override
    //Graphics类可以理解为画笔，为我们提供了各种绘制图形
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(this.codes == null || this.codes.length() != this.codesLength) {  //判断生成的验证码是否为空或超出长度
            this.codes = generateCode();
        }
        width = this.codesLength * 16 + (this.codesLength - 1) * 10;
        super.setSize(width, height);  //接口使用，验证码字体大小
        super.setPreferredSize(new Dimension(width, height));//接口使用，验证码背景大小
        Font mFont = new Font("Arial", Font.BOLD | Font.ITALIC, 25);  //设置字体和字体大小
        g.setFont(mFont);  //设置对象
        //绘制出验证码的背景的矩形轮廓
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(getRandColor(200, 250));
        g2d.fillRect(0, 0, width, height);
        g2d.setColor(getRandColor(180, 200));
        g2d.drawRect(0, 0, width - 1, height - 1);
        //绘制出验证码背景的线
        int i = 0, len = 150;
        for (; i < len; i++) {
            int x = random.nextInt(width - 1);
            int y = random.nextInt(height - 1);
            int x1 = random.nextInt(width - 10) + 10;
            int y1 = random.nextInt(height - 4) + 4;
            g2d.setColor(getRandColor(180, 200));
            g2d.drawLine(x, y, x1, y1);
        }
        //绘制出验证码的具体字母
        i = 0; len = this.codesLength;
        FontMetrics fm = g2d.getFontMetrics();
        int base = (height - fm.getHeight())/2 + fm.getAscent();
        for(;i<len;i++) {
            int b = random.nextBoolean() ? 1 : -1;
            g2d.rotate(random.nextInt(10)*0.01*b);
            g2d.setColor(getRandColor(20, 130));
            g2d.drawString(codes.charAt(i)+"", 16 * i + 10, base);
        }
    }
    //下一个验证码
    public void nextCode() {
        generateCode();
        repaint();
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        nextCode();
    }
    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub
    }
    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub
    }
    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
    }
};
/***定义注册***/
class Register  {
    //这里创建三个列表用于后期接收传来的内容，在注册功能中比对密码的正确性
    java.util.List username = new ArrayList();
    java.util.List password1 = new ArrayList();
    java.util.List password2 = new ArrayList();

    //这里是登录权限数组
    java.util.List shenfenshuzu=new ArrayList();
    //这里是性别
    Gender genderint = new Gender(null);
    //这里是姓名
    java.util.List nameshuzu = new ArrayList();


    //这里是兴趣
    java.util.List xingqushuzu = new ArrayList();

    //这里创建JLabel 放置用户名密码
    JLabel l1 = new JLabel("编号");
    JLabel l2 = new JLabel("密  码 ");
    JLabel l3 = new JLabel("兴趣");
    JLabel l4 = new JLabel("注册结果");
    JLabel l5 = new JLabel("登录身份");//登录身份标签
    JTextField t1 = new JTextField(10);//编号
    JPasswordField pf1 = new JPasswordField(10);//密码
    JPasswordField pf2 = new JPasswordField(10);//确认密码
    JTextField pf = new JTextField(15);

    //这里放置注册重置
    JButton b1 = new JButton("注册");
    private JComboBox comboBox = new JComboBox();//下拉列表框组件
    Register() throws IOException {
        JFrame frame = new JFrame();
        frame.setSize(400, 400);
        //创建一个编号的text
        JPanel p1 = new JPanel(new FlowLayout());//面板p1流式布局
        p1.add(l1);
        t1.setText("5位数字");
        t1.setEditable(true);
        p1.add(t1); //存放用户名和文本框
        /* 给对象名为comboBox的Combobox控件，添加内容。 */
        JPanel p = new JPanel(new FlowLayout());//面板p流式布局
        comboBox.addItem("---请选择登录身份---");
        comboBox.addItem("部门管理员");
        comboBox.addItem("员工");
        p.add(l5);p.add(comboBox);

        //给按键添加监听
        comboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                //如果他的状态为被选中
                if (e.getStateChange() == 1) {
                    //将选中的这个item转化为String赋值给shenfenshuzu
                    //以下同理
                    shenfenshuzu.add(String.valueOf(e.getItem()));
                }
            }
        });

        //创建密码的jpassword

        JPanel p2 = new JPanel(new FlowLayout());//面板p2流式布局
        p2.add(l2);
        pf1.setText("长度大于八位，可为任意字符");
        pf1.setEditable(true);
        p2.add(pf1); //存放密码和密码框

        //这里放置确认密码功能
        JPanel p6 = new JPanel(new FlowLayout());
        //放置确认密码
        p6.add(new JLabel("确认密码"));
        p6.add(pf2);

        JPanel p3 = new JPanel(new FlowLayout());//面板p3流式布局
        p3.add(b1);

        //这里存放性别
        JLabel labelgenger = new JLabel("性别");
        JLabel empty = new JLabel();//空标签
        ButtonGroup gender = new ButtonGroup();//创建按钮组
        JRadioButton man = new JRadioButton("男");//创建单选按钮
        JRadioButton woman = new JRadioButton("女");//创建单选按钮
        //点击男性时候 把性别参数改为1
        //这里是男性按钮
        man.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //外部创建了一个类Gender 其中有一个Object参数表示男女 0代表女 1代表男
                genderint.setGenger(1);
                //System.out.println(genderint.getGenger());
            }
        });
        //这里是女性按钮
        //点击时候设置gender 类的 genderint实例变量的gender属性为0
        woman.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                genderint.setGenger(0);
                //System.out.println(genderint.getGenger());
            }
        });
        gender.add(man);
        gender.add(woman);//将单选按钮加入按钮组
        JPanel p7 = new JPanel(new GridLayout(2, 2));//网格布局
        p7.add(labelgenger);
        p7.add(man);
        p7.add(empty);
        p7.add(woman);


        //这里存放姓名
        JPanel p8 = new JPanel(new FlowLayout());
        JLabel labelName = new JLabel("姓名");

        p8.add(labelName);
        pf.setText("只为英文或中文");
        pf.setEditable(true);
        p8.add(pf);

        //这里存放兴趣
        JCheckBox jc1 = new JCheckBox("听音乐");
        JCheckBox jc2 = new JCheckBox("看电影");
        JCheckBox jc3 = new JCheckBox("绘画");
        JCheckBox jc4 = new JCheckBox("打篮球");
        JCheckBox jc5 = new JCheckBox("羽毛球");
        JCheckBox jc6 = new JCheckBox("足球");
        JCheckBox jc8 = new JCheckBox("滑板");
        JCheckBox jc9 = new JCheckBox("跑步");
        //jc9.setForeground(Color.red);
        JPanel p9 = new JPanel(new GridLayout(2, 5));
        p9.add(l3);
        p9.add(jc1);
        p9.add(jc2);
        p9.add(jc3);
        p9.add(jc4);
        p9.add(new JLabel(" "));
        p9.add(jc5);
        p9.add(jc6);
        p9.add(jc8);
        p9.add(jc9);
        jc1.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                //如果这个是被选中的
                if (jc1.isSelected() == true) {
                    //将这个文本添加到xingqushuzu里面
                    xingqushuzu.add(jc1.getText());
                    //如果没有选中
                } else if (jc1.isSelected() == false) {
                    //将这个文本删除，因为可能有人点了勾勾 又取消了勾勾
                    //以下同理
                    xingqushuzu.remove(jc1.getText());
                }
            }
        });
        jc2.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (jc2.isSelected() == true) {
                    xingqushuzu.add(jc2.getText());
                } else if (jc2.isSelected() == false) {
                    xingqushuzu.remove(jc2.getText());
                }
            }
        });
        jc3.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (jc3.isSelected() == true) {
                    xingqushuzu.add(jc3.getText());
                } else if (jc3.isSelected() == false) {
                    xingqushuzu.remove(jc3.getText());
                }
            }
        });
        jc4.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (jc4.isSelected() == true) {
                    xingqushuzu.add(jc4.getText());
                } else if (jc4.isSelected() == false) {
                    xingqushuzu.remove(jc4.getText());
                }
            }
        });
        jc5.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (jc5.isSelected() == true) {
                    xingqushuzu.add(jc5.getText());
                } else if (jc5.isSelected() == false) {
                    xingqushuzu.remove(jc5.getText());
                }
            }
        });
        jc6.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (jc6.isSelected() == true) {
                    xingqushuzu.add(jc6.getText());
                } else if (jc6.isSelected() == false) {
                    xingqushuzu.remove(jc6.getText());
                }
            }
        });
        jc8.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (jc8.isSelected() == true) {
                    xingqushuzu.add(jc8.getText());
                } else if (jc8.isSelected() == false) {
                    xingqushuzu.remove(jc8.getText());
                }
            }
        });
        jc9.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (jc9.isSelected() == true) {
                    xingqushuzu.add(jc9.getText());
                } else if (jc9.isSelected() == false) {
                    xingqushuzu.remove(jc9.getText());
                }
            }
        });

        //这里是备注 注册成功将信息显示在此面板上
        //以下是一系列的设置，设置滚动换行什么的
        JPanel p5 = new JPanel(new GridLayout(1, 2));
        JTextArea jTextArea1 = new JTextArea(5, 20);
        jTextArea1.setEditable(false);//不可编辑
        jTextArea1.setLineWrap(true);//将文本框设置为自动换行
        JScrollPane jScrollPane = new JScrollPane(jTextArea1);
        jScrollPane.setBorder(BorderFactory.createLoweredBevelBorder());//为组件设置花样边框
        p5.add(l4);
        p5.add(jScrollPane);


        //这里给注册和重置按钮监听
        b1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
//                if (!comboBox.getSelectedItem().toString().equals(""))
//                    shenfenshuzu.add(0, comboBox.getSelectedItem().toString());
                if (!t1.getText().equals("")) {
                    //如果取值不为空，就让他得到文本框中的内容
                    username.add(0, t1.getText());
                }
                if (!pf.getText().equals(""))
                    nameshuzu.add(0, pf.getText());
                //将密码栏中的信息得到，添加给passwrod数组 用于确认密码
                if (!pf1.getPassword().equals(""))
                    password1.add(0, new String(pf1.getPassword()));
                if (!pf2.getPassword().equals(""))
                    password2.add(0, new String(pf2.getPassword()));
                if (username.size() == 0 || password1.size() == 0 || password2.size() == 0 || genderint.getGenger() == null ||
                        nameshuzu.size() == 0 || xingqushuzu.size() == 0 || shenfenshuzu.size() == 0)
                    JOptionPane.showMessageDialog(null, "请将信息填写完整!");
                else {
                    int a = JOptionPane.showConfirmDialog(null, "确定信息填写完整并正确吗？", "提示：", JOptionPane.YES_NO_OPTION);
                    if(a==0){
                        if(t1.getText().length()!=5)
                            JOptionPane.showMessageDialog(null, "编号格式不正确！");
                        else
                        if (String.valueOf(pf1.getPassword()).length() <= 8 || String.valueOf(pf2.getPassword()).length() <= 8)
                            JOptionPane.showMessageDialog(null, "输入密码格式不正确！");
                        else if (!String.valueOf(pf1.getPassword()).equals(String.valueOf(pf2.getPassword())))
                            JOptionPane.showMessageDialog(null, "输入密码前后不一致！");
                        else {
//                        if (username.size() != 0 && password1.size() != 0 && password2.size() != 0 && genderint.getGenger() != null &&
//                                nameshuzu.size() != 0 && xingqushuzu.size() != 0 && shenfenshuzu.size() != 0 && check()) {
                            //if(a==0) {
                            if (check()) {
                                JOptionPane.showMessageDialog(null, "注册成功!请返回登录界面重新登录！");
                                //}
                                //如果性别的值为1 就打印男 这里的"\r\n"是换行 手动换一下行 不然不好看
                                if (genderint.getGenger().equals(1)) {
                                    jTextArea1.setText("注册成功！" + "\r\n" + "编号为：" + username.get(0) + ";\r\n" + "姓名为：" + nameshuzu.get(0) + ";\r\n" + "性别为：男" + ";\r\n" +
                                            "密码为：" + password1.get(0) + ";\r\n" + "登录身份为" + shenfenshuzu.get(0) + ";\r\n" + "兴趣为：" + xingqushuzu + "\r\n" +
                                            "欢迎您加入我们！");
                                }
                                //如果性别的值为0 就打印女
                                else if (genderint.getGenger().equals(0)) {
                                    jTextArea1.setText("注册成功！" + "\r\n" + "编号为：" + username.get(0) + "\r\n" + "姓名为：" + nameshuzu.get(0) + "\r\n" + "性别为：女" + "\r\n" +
                                            "密码为：" + password1.get(0) + "\r\n" + "登录身份为：" + shenfenshuzu.get(0) + "\r\n" + "兴趣为：" + xingqushuzu + "\r\n" +
                                            "欢迎您加入我们！");
                                }
                            }else
                                JOptionPane.showMessageDialog(null, "注册失败!");

                        }
                    }
                }
            }
        });

        p1.setBounds(40,10,60,25);
        frame.setLayout(new GridLayout(5, 2));//设置网格式布局管理器，将组件垂直摆放在一列
        frame.getContentPane().setBackground(Color.LIGHT_GRAY);

        //按照顺序添加 JPanel()

        //编号
        frame.add(p1);
        //兴趣
        frame.add(p9);
        //姓名
        frame.add(p8);
        //注册结果
        frame.add(p5);
        //性别
        frame.add(p7);
        //空白panel
        frame.add(new JPanel());
        //密码
        frame.add(p2);
        //登录身份
        frame.add(p);
        //确认密码
        frame.add(p6);
        //按钮
        frame.add(p3);

        ((JPanel) frame.getContentPane()).setOpaque(false);//把内容面板转换为JPanel,并设置为透明，否则看不到分层面板的背景图片
        //等价于下面这句代码
        //((JPanel)frame.getContentPane()).setVisible(true);
        /*获取背景图*/
        Image img = ImageIO.read(new File("D:\\JDBC\\src\\山水画1.jpg"));//图片在项目的位置
        ImageIcon icon = new ImageIcon(img);
        icon.setImage(icon.getImage().getScaledInstance(400, 400, Image.SCALE_DEFAULT));
        JLabel Label = new JLabel(icon);//标签存放背景图
        frame.getLayeredPane().setLayout(null);
        frame.getLayeredPane().add(Label, new Integer(Integer.MIN_VALUE));
        Label.setBounds(0, 0, 400, 400);

        frame.setBounds(400,100,700,700);
        frame.setTitle("用户注册");
        frame.setVisible(true);
    }
    public boolean check(){
        String sex;
        //0女1男
        if(genderint.getGenger().equals(0))
            sex="女";
        else
            sex="男";
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
            /* 部门管理员的检查 */
            if (comboBox.getSelectedItem().toString().equals("部门管理员")) {
                System.out.println(t1.getText()+" "+pf.getText()+" "+sex);
                String sql = "select * from 部门管理员信息表 " + "where  编号='" + t1.getText() + "' and 姓名 = '"
                        + pf.getText() + "' and 性别 = '"+sex+"'";
                ResultSet rs = s.executeQuery(sql);
                if(rs.next()){
                    //将兴趣插入表中
                    String sql1 = "update 部门管理员信息表 set 兴趣爱好='"+xingqushuzu+"' where 编号='"+t1.getText()+"'";
                    int row=s.executeUpdate(sql1);
                    if(row==1)
                        System.out.println("信息插入成功!");
                    else
                        System.out.println("信息插入不成功!");
                    //将登录信息插入登录表
                    String sql2 = "insert into 部门管理员登录表 values('"+t1.getText()+"','"+pf.getText()+"','"+pf1.getText()+"')";
                    row=s.executeUpdate(sql2);
                    if(row==1) {
                        System.out.println("信息插入成功!");
                        result=true;
                    }
                    else
                        System.out.println("信息插入不成功!");
                }
            } else
                /* 员工的检查 */
                if (comboBox.getSelectedItem().toString().equals("员工")) {
                    System.out.println(t1.getText()+" "+pf.getText()+" "+sex);
                    String sql = "select * from 员工信息表 " + "where  编号='" + t1.getText() + "' and 姓名 = '"
                            + pf.getText() + "' and 性别 = '" +sex+"'";
                    ResultSet rs = s.executeQuery(sql);
                    //System.out.println(rs.next());
                    if (rs.next()) {
                        System.out.println(" xingqushuzu");
                        //将兴趣插入表中
                        String sql1 = "update 员工信息表 set 兴趣爱好='" + xingqushuzu + "' where 编号='" + t1.getText() + "'";
                        int row = s.executeUpdate(sql1);
                        if (row == 1)
                            System.out.println("信息插入成功!");
                        else
                            System.out.println("信息插入不成功!");
                        //将登录信息插入登录表
                        String sql2 = "insert into 员工登录表 values('" + t1.getText() + "','" + pf.getText() + "','" + pf1.getText() + "')";
                        row = s.executeUpdate(sql2);
                        if (row == 1){
                            System.out.println("信息插入成功!");
                            result = true;
                        }
                        else
                            System.out.println("信息插入不成功!");
                    }
                }
        }catch (SQLException e2) {
            e2.printStackTrace();
        }
        return result;
    }
};
/***定义忘记密码***/
class Changepassword extends JFrame {
    private JTextField bianhaoTextField;//编号文本框
    private JPasswordField passwordField1;//密码框
    private JPasswordField passwordField2;//密码框
    private JComboBox comboBox = new JComboBox();//下拉列表框组件
    /*** Create the frame.*/
    public Changepassword() {
        setTitle("重新设置密码");
        JLabel bianhaoLabel = new JLabel("当前编号:");//编号标签
        bianhaoLabel.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        bianhaoTextField = new JTextField();//编号文本框
        bianhaoTextField.setToolTipText("编号");
        bianhaoTextField.setFont(new Font("微软雅黑", Font.PLAIN, 15));

        JLabel passwordLabel1 = new JLabel("输入新密码:");//密码标签
        passwordLabel1.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        passwordField1 = new JPasswordField();//密码文本框
        passwordField1.setFont(new Font("微软雅黑", Font.PLAIN, 15));

        JLabel passwordLabel2 = new JLabel("确认密码:");
        passwordLabel2.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        passwordField2 = new JPasswordField();//密码文本框
        passwordField2.setFont(new Font("微软雅黑", Font.PLAIN, 15));

        JLabel shengfenLabel = new JLabel("登录身份:");//登录身份标签
        shengfenLabel.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        /* 给对象名为comboBox的Combobox控件，添加内容。 */
        comboBox.addItem("---请选择登录身份---");
        comboBox.addItem("管理员");
        comboBox.addItem("部门管理员");
        comboBox.addItem("员工");
        comboBox.setFont(new Font("微软雅黑", Font.PLAIN, 15));

        JButton submitButton = new JButton("提交");//提交按钮
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showConfirmDialog(null, "确定重置密码吗？", "提示：", JOptionPane.YES_NO_OPTION);
                //System.out.println(passwordField1.getPassword().toString().equals(passwordField2.getPassword().toString()));
                //System.out.println(String.valueOf(passwordField1.getPassword())+" "+passwordField1.getPassword().toString()+" "+passwordField1.getPassword().toString().length() );
                if (!String.valueOf(passwordField1.getPassword()).equals(String.valueOf(passwordField2.getPassword())))
                    JOptionPane.showMessageDialog(null, "重置失败，密码输入前后不一致！");
                else if (String.valueOf(passwordField1.getPassword()).length() <= 8 || String.valueOf(passwordField1.getPassword()).length() <=8)
                    JOptionPane.showMessageDialog(null, "重置失败，密码格式有误！");
                else {
                    if(check())
                        JOptionPane.showMessageDialog(null, "重置密码成功！");
                    else
                        JOptionPane.showMessageDialog(null, "重置密码失败！");
                }
            }
        });

        JButton cancelButton = new JButton("退出");//取消按钮
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        setVisible(true);
        this.setLayout(null);
        this.getContentPane().setBackground(Color.lightGray);
        bianhaoLabel.setBounds(80,50,80,30);
        passwordLabel1.setBounds(80,80,80,30);
        passwordLabel2.setBounds(80,110,80,30);
        shengfenLabel.setBounds(80,140,80,30);
        bianhaoTextField.setBounds(150,52,120,25);
        passwordField1.setBounds(150,82,120,25);
        passwordField2.setBounds(150,112,120,25);
        comboBox.setBounds(150,142,140,25);
        submitButton.setBounds(100,200,60,30);
        cancelButton.setBounds(200,200,60,30);
        this.add(bianhaoLabel);
        this.add(bianhaoTextField);
        this.add(passwordLabel1);
        this.add(passwordField1);
        this.add(passwordLabel2);
        this.add(passwordField2);
        this.add(shengfenLabel);
        this.add(comboBox);
        this.add(submitButton);
        this.add(cancelButton);
        setSize(400, 400);//窗体长和宽
        setLocation(400, 200);//窗体位置
        //setLocationRelativeTo(null);// 让窗体居中显示
    }
    public boolean check () {
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
            /* 部门管理员的检查 */
            if (comboBox.getSelectedItem().toString().equals("部门管理员")) {
                String sql1 = "update 部门管理员登录表 set 登录密码='" + String.valueOf(passwordField1.getPassword()) + "' where 编号='" + bianhaoTextField.getText() + "'";
                int row = s.executeUpdate(sql1);
                if (row == 1)
                    System.out.println("密码修改成功!");
                else
                    System.out.println("密码修改不成功!");
                result = true;
            } else
                /* 员工的检查 */
                if (comboBox.getSelectedItem().toString().equals("员工")) {
                    String sql1 = "update 员工登录表 set 登录密码='" + String.valueOf(passwordField1.getPassword()) + "' where 编号='" + bianhaoTextField.getText() + "'";
                    int row = s.executeUpdate(sql1);
                    if (row == 1)
                        System.out.println("密码修改成功!");
                    else
                        System.out.println("密码修改不成功!");
                    result = true;
                }
                else
                    /*管理员的检查*/
                    if (comboBox.getSelectedItem().toString().equals("管理员")) {
                        String sql1 = "update 管理员登录表 set 登录密码='" + String.valueOf(passwordField1.getPassword()) + "' where 编号='" + bianhaoTextField.getText() + "'";
                        int row = s.executeUpdate(sql1);
                        if (row == 1)
                            System.out.println("密码修改成功!");
                        else
                            System.out.println("密码修改不成功!");
                        result = true;
                    }

        } catch (SQLException e2) {
            e2.printStackTrace();
        }
        return result;
    }
};
/***系统时间***/
class NowTime1 extends JFrame {

    private static final long serialVersionUID = 4306803332677233920L;

    // 添加 显示时间的JLabel
    public NowTime1() {
        JLabel time = new JLabel();
        time.setForeground(Color.BLUE);
        time.setBounds(30, 0, 900, 130);
        time.setFont(new Font("微软雅黑", Font.BOLD, 80));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setTitle("now time");
        this.setBounds(500, 200, 930, 200);
        this.setVisible(true);
        this.add(time);
        this.setTimer(time);
    }

    // 设置Timer 1000ms实现一次动作 实际是一个线程
    private void setTimer(JLabel time) {
        final JLabel varTime = time;
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
}
class Gender {
    private Object genger;

    public Gender() {
    }

    @Override
    public String toString() {
        return "Gender{" +
                "genger=" + genger +
                '}';
    }

    public Object getGenger() {
        return  genger;
    }

    public void setGenger(Object genger) {
        this.genger = genger;
    }

    public Gender(Object genger) {
        this.genger = genger;
    }
}
/***利用文件选择器将考勤信息导出数据库到Excel表格***/
class JFileChooserrDaochuKaoqin extends JFrame {

    public JFileChooserrDaochuKaoqin() throws BiffException, IOException, WriteException {
        JFrame frame=new JFrame();
        JFileChooser fc=new JFileChooser();//创建文件导航框
        fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);//设置只能选择文件夹
        fc.setMultiSelectionEnabled(false);//是否允许多选
        int result=fc.showOpenDialog(null);//弹出文件导航框,返回选择的按钮
        //如果单击了确定、打开或保存按钮
        if(result==JFileChooser.APPROVE_OPTION)
        {
            File file=fc.getSelectedFile();
            String path=file.getAbsolutePath();
            System.out.println(path);
            //new excelRead().readExcel(new File(path));//将Excel导入数据库
            new Export().writeExcel(path);

        }
        frame.setTitle("测试");
        //frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
}
/***利用文件选择器Excel表格将考勤信息导入数据库***/
class JFileChooserrrDaoruKaoqin extends JFrame {

    public JFileChooserrrDaoruKaoqin() throws BiffException, IOException, WriteException {
        JFrame frame=new JFrame();
        JFileChooser fc=new JFileChooser();//创建文件导航框
        fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);//设置只能选择文件夹
        fc.setMultiSelectionEnabled(false);//是否允许多选
        int result=fc.showOpenDialog(null);//弹出文件导航框,返回选择的按钮
        //如果单击了确定、打开或保存按钮
        if(result==JFileChooser.APPROVE_OPTION)
        {
            File file=fc.getSelectedFile();
            String path=file.getAbsolutePath();
            System.out.println(path);
            new excelRead().readExcel(new File(path));//将Excel导入数据库
            //new Export().writeExcel(path);

        }
        frame.setTitle("测试");
        //frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
}
/***员工请假记录表信息***/
class Leaverecord {
    private String id;
    private String name;
    private String apartment;
    private String starttime;
    private String length;
    private String overtime;
    private String liyou;
    private String state;

    @Override
    public String toString() {
        return "Leaverecord{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", apartment='" + apartment + '\'' +
                ", starttime='" + starttime + '\'' +
                ", length='" + length + '\'' +
                ", overtime='" + overtime + '\'' +
                ", liyou='" + liyou + '\'' +
                ", state='" + state + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getApartment() {
        return apartment;
    }

    public void setApartment(String apartment) {
        this.apartment = apartment;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getOvertime() {
        return overtime;
    }

    public void setOvertime(String overtime) {
        this.overtime = overtime;
    }

    public String getLiyou() {
        return liyou;
    }

    public void setLiyou(String liyou) {
        this.liyou = liyou;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
/***连接数据库***/
class BaseDao {
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    String url="jdbc:mysql://localhost:3306/企业考勤系统?" +
            "useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=GMT%2B8&allowPublicKeyRetrieval=true";
    String username="root";//连接数据库的用户名
    String password="Llf20020717";//连接数据库的密码
    static{
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public Connection getConn() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url,username,password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
    public void close(ResultSet rs,Statement state,Connection conn) {
        try {
            if(rs != null) {
                rs.close();
            }
            if(state != null) {
                state.close();
            }
            if(conn !=null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
/***员工信息表***/
class Yuangonginfo{
    String id;
    String name;
    String sex;
    String apartment;
    String id_num;
    String birthday;
    String call;
    String address;
    String hobby;
    String attach;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getApartment() {
        return apartment;
    }

    public void setApartment(String apartment) {
        this.apartment = apartment;
    }

    public String getId_num() {
        return id_num;
    }

    public void setId_num(String id_num) {
        this.id_num = id_num;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getCall() {
        return call;
    }

    public void setCall(String call) {
        this.call = call;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    @Override
    public String toString() {
        return "Yuangonginfo{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", apartment='" + apartment + '\'' +
                ", id_num='" + id_num + '\'' +
                ", birthday='" + birthday + '\'' +
                ", call='" + call + '\'' +
                ", address='" + address + '\'' +
                ", hobby='" + hobby + '\'' +
                ", attach='" + attach + '\'' +
                '}';
    }
}
/***员工信息表的增删查改***/
class ScoreDao {
    //实现增删改查的方法

    BaseDao bd = new BaseDao();
    Connection conn = null;
    Statement state = null;
    ResultSet rs = null;
    PreparedStatement pstate;
    String [] str = new String[4];

    //将数据库数据导出
    public java.util.List<Yuangonginfo> ScoreDao() {
        conn = bd.getConn();
        java.util.List<Yuangonginfo> list1 = new ArrayList<Yuangonginfo>();
        try {
            state = conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String sql = "select * from 员工信息表";
        try {
            rs = state.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            while(rs.next()) {
                Yuangonginfo yuangong = new Yuangonginfo();
                yuangong.setId(rs.getString("编号"));
                yuangong.setName(rs.getString("姓名"));
                yuangong.setSex(rs.getString("性别"));
                yuangong.setApartment(rs.getString("部门"));
                yuangong.setId_num(rs.getString("身份证号"));
                yuangong.setBirthday(rs.getString("出生日期"));
                yuangong.setCall(rs.getString("联系电话"));
                yuangong.setAddress(rs.getString("家庭地址"));
                yuangong.setHobby(rs.getString("兴趣爱好"));
                yuangong.setAttach(rs.getString("备注"));
                System.out.println(yuangong);
                list1.add(yuangong);
            }
            bd.close(rs, pstate, conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list1;
    }

    //查询，返回查询出来的数据
    public java.util.List<Yuangonginfo> Select(String id) {
        java.util.List<Yuangonginfo> list2 = new ArrayList<Yuangonginfo>();
        conn = bd.getConn();
        int result = -1;
        String sql = "select * from 员工信息表 where 编号 = ?";
        try {
            pstate = conn.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            pstate.setString(1, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            ResultSet rs = pstate.executeQuery();
            while(rs.next()) {
                Yuangonginfo yuangong = new Yuangonginfo();
                yuangong.setId(rs.getString("编号"));
                yuangong.setName(rs.getString("姓名"));
                yuangong.setSex(rs.getString("性别"));
                yuangong.setApartment(rs.getString("部门"));
                yuangong.setId_num(rs.getString("身份证号"));
                yuangong.setBirthday(rs.getString("出生日期"));
                yuangong.setCall(rs.getString("联系电话"));
                yuangong.setAddress(rs.getString("家庭地址"));
                yuangong.setHobby(rs.getString("兴趣爱好"));
                yuangong.setAttach(rs.getString("备注"));
                System.out.println(yuangong);
                list2.add(yuangong);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            bd.close(null, pstate, conn);
        }
        return list2;
    }
    //添加数据入库
    @SuppressWarnings("finally")
    public String Insert( String id,String name,String sex,String apartment,String id_num,String birthday,String call,String address,String hobby,String attach) {
        conn = bd.getConn();
        int result = -1;
        String sql = "insert into 员工信息表 values('"+id+"','"+name+"','"+sex+"','"+apartment+"','"+id_num+"','"+birthday+"','"+call+"','"+
                address+"','"+hobby+"','"+attach+"')";
        try {
            pstate = conn.prepareStatement(sql);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            result = pstate.executeUpdate();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally {
            bd.close(null, pstate, conn);
            if(result > 0) {
                return "添加成功";
            }else {
                return "添加失败";
            }
        }
    }


    //删除数据
    @SuppressWarnings("finally")
    public String Del(String id) {
        conn = bd.getConn();
        int result = -1;
        String sql = "delete from 员工信息表 where 编号 ='"+id+"'";
        try {
            pstate = conn.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            result = pstate.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            bd.close(null, pstate, conn);
            if(result > 0) {
                return "删除成功";
            }else {
                return "删除失败";
            }
        }
    }

    //修改数据，会把所有数据修改一遍id除外
    @SuppressWarnings("finally")
    public String Update(String id,String name,String sex,String apartment,String id_num,String birthday,String call,String address,String hobby,String attach) {
        conn = bd.getConn();
        conn = bd.getConn();
        int result = -1;
        String sql = "update 员工信息表 set  姓名 ='"+name+"',性别 ='"+sex+"',部门='"+apartment+"',身份证号='"+id_num+"',出生日期='"+birthday+"',联系电话='"
                +call+"',家庭地址='"+address+"',兴趣爱好='"+hobby+"',备注='"+attach+"' where 编号='"+id+"'";
        try {
            pstate = conn.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            result = pstate.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            bd.close(null, pstate, conn);
            if(result > 0) {
                return "修改成功";
            }else {
                return "修改失败";
            }
        }
    }



}
/***员工请假记录表的增删查改***/
class ScoreDao4 {
    //实现增删改查的方法

    BaseDao bd = new BaseDao();
    Connection conn = null;
    Statement state = null;
    ResultSet rs = null;
    PreparedStatement pstate;
    String [] str = new String[4];

    //将数据库数据导出
    public java.util.List<Leaverecord> ScoreDao() {
        conn = bd.getConn();
        java.util.List<Leaverecord> list1 = new ArrayList<Leaverecord>();
        try {
            state = conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String sql = "select * from 员工请假记录表";
        try {
            rs = state.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            while(rs.next()) {
                Leaverecord yuangong = new Leaverecord();
                yuangong.setId(rs.getString("编号"));
                yuangong.setName(rs.getString("姓名"));
                yuangong.setApartment(rs.getString("部门"));
                yuangong.setStarttime(rs.getString("起始时间"));
                yuangong.setLength(rs.getString("请假时长"));
                yuangong.setOvertime(rs.getString("销假时间"));
                yuangong.setLiyou(rs.getString("请假理由"));
                yuangong.setState(rs.getString("是否销假"));
                System.out.println(yuangong);
                list1.add(yuangong);
            }
            bd.close(rs, pstate, conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list1;
    }

    //查询，返回查询出来的数据
    public java.util.List<Leaverecord> Select(String id) {
        java.util.List<Leaverecord> list2 = new ArrayList<Leaverecord>();
        conn = bd.getConn();
        int result = -1;
        String sql = "select * from 员工请假记录表 where 编号 = ?";
        try {
            pstate = conn.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            pstate.setString(1, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            ResultSet rs = pstate.executeQuery();
            while(rs.next()) {
                Leaverecord yuangong = new Leaverecord();
                yuangong.setId(rs.getString("编号"));
                yuangong.setName(rs.getString("姓名"));
                yuangong.setApartment(rs.getString("部门"));
                yuangong.setStarttime(rs.getString("起始时间"));
                yuangong.setLength(rs.getString("请假时长"));
                yuangong.setOvertime(rs.getString("销假时间"));
                yuangong.setLiyou(rs.getString("请假理由"));
                yuangong.setState(rs.getString("是否销假"));
                System.out.println(yuangong);
                list2.add(yuangong);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            bd.close(null, pstate, conn);
        }
        return list2;
    }
    //添加数据入库
    @SuppressWarnings("finally")
    public String Insert( String id,String name,String apartment,String starttime,String length,
                          String overtime,String liyou,String state) {
        conn = bd.getConn();
        int result = -1;
        String sql = "insert into 员工请假记录表 values('"+id+"','"+name+"','"+
                apartment+"','"+starttime+"','"+length+"','"+overtime+"','"+liyou+"','"+state+"')";
        try {
            pstate = conn.prepareStatement(sql);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            result = pstate.executeUpdate();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally {
            bd.close(null, pstate, conn);
            if(result > 0) {
                return "添加成功";
            }else {
                return "添加失败";
            }
        }
    }


    //删除数据
    @SuppressWarnings("finally")
    public String Del(String id) {
        conn = bd.getConn();
        int result = -1;
        String sql = "delete from 员工请假记录表 where 编号 ='"+id+"'";
        try {
            pstate = conn.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            result = pstate.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            bd.close(null, pstate, conn);
            if(result > 0) {
                return "删除成功";
            }else {
                return "删除失败";
            }
        }
    }

    //修改数据，会把所有数据修改一遍id除外
    @SuppressWarnings("finally")
    public String Update( String id,String name,String apartment,String starttime,String length,
                          String overtime,String liyou,String state) {
        conn = bd.getConn();
        conn = bd.getConn();
        int result = -1;
        String sql = "update 员工请假记录表 set  姓名 ='"+name+"',部门 ='"+apartment+"',起始时间='"+starttime+"',请假时长='"+length+"',销假时间='"+overtime+
                "',请假理由='"+liyou+"',是否销假='"+state+"' where 编号='"+id+"'";
        try {
            pstate = conn.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            result = pstate.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            bd.close(null, pstate, conn);
            if(result > 0) {
                return "修改成功";
            }else {
                return "修改失败";
            }
        }
    }
}
/***部门管理员信息表***/
class ApartmentManager{
    String id;
    String name;
    String sex;
    String apartment;
    String id_num;
    String birthday;
    String call;
    String address;
    String hobby;
    String attach;

    @Override
    public String toString() {
        return "ApartmentManager{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", apartment='" + apartment + '\'' +
                ", id_num='" + id_num + '\'' +
                ", birthday='" + birthday + '\'' +
                ", call='" + call + '\'' +
                ", address='" + address + '\'' +
                ", hobby='" + hobby + '\'' +
                ", attach='" + attach + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getApartment() {
        return apartment;
    }

    public void setApartment(String apartment) {
        this.apartment = apartment;
    }

    public String getId_num() {
        return id_num;
    }

    public void setId_num(String id_num) {
        this.id_num = id_num;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getCall() {
        return call;
    }

    public void setCall(String call) {
        this.call = call;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }
}
/***部门管理员信息表的增删查改***/
class ScoreDao1 {
    //实现增删改查的方法

    BaseDao bd = new BaseDao();
    Connection conn = null;
    Statement state = null;
    ResultSet rs = null;
    PreparedStatement pstate;
    String [] str = new String[4];

    //将数据库数据导出
    public java.util.List<ApartmentManager> ScoreDao() {
        conn = bd.getConn();
        java.util.List<ApartmentManager> list1 = new ArrayList<ApartmentManager>();
        try {
            state = conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String sql = "select * from 部门管理员信息表";
        try {
            rs = state.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            while(rs.next()) {
                ApartmentManager yuangong = new ApartmentManager();
                yuangong.setId(rs.getString("编号"));
                yuangong.setName(rs.getString("姓名"));
                yuangong.setSex(rs.getString("性别"));
                yuangong.setApartment(rs.getString("部门"));
                yuangong.setId_num(rs.getString("身份证号"));
                yuangong.setBirthday(rs.getString("出生日期"));
                yuangong.setCall(rs.getString("联系电话"));
                yuangong.setAddress(rs.getString("家庭地址"));
                yuangong.setHobby(rs.getString("兴趣爱好"));
                yuangong.setAttach(rs.getString("备注"));
                System.out.println(yuangong);
                list1.add(yuangong);
            }
            bd.close(rs, pstate, conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list1;
    }

    //查询，返回查询出来的数据
    public java.util.List<ApartmentManager> Select(String id) {
        java.util.List<ApartmentManager> list2 = new ArrayList<ApartmentManager>();
        conn = bd.getConn();
        int result = -1;
        String sql = "select * from 部门管理员信息表 where 编号 = ?";
        try {
            pstate = conn.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            pstate.setString(1, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            ResultSet rs = pstate.executeQuery();
            while(rs.next()) {
                ApartmentManager yuangong = new ApartmentManager();
                yuangong.setId(rs.getString("编号"));
                yuangong.setName(rs.getString("姓名"));
                yuangong.setSex(rs.getString("性别"));
                yuangong.setApartment(rs.getString("部门"));
                yuangong.setId_num(rs.getString("身份证号"));
                yuangong.setBirthday(rs.getString("出生日期"));
                yuangong.setCall(rs.getString("联系电话"));
                yuangong.setAddress(rs.getString("家庭地址"));
                yuangong.setHobby(rs.getString("兴趣爱好"));
                yuangong.setAttach(rs.getString("备注"));
                System.out.println(yuangong);
                list2.add(yuangong);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            bd.close(null, pstate, conn);
        }
        return list2;
    }
    //添加数据入库
    @SuppressWarnings("finally")
    public String Insert( String id,String name,String sex,String apartment,String id_num,String birthday,String call,String address,String hobby,String attach) {
        conn = bd.getConn();
        int result = -1;
        String sql = "insert into 部门管理员信息表 values('"+id+"','"+name+"','"+sex+"','"+apartment+"','"+id_num+"','"+birthday+"','"+call+"','"+
                address+"','"+hobby+"','"+attach+"')";
        try {
            pstate = conn.prepareStatement(sql);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            result = pstate.executeUpdate();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally {
            bd.close(null, pstate, conn);
            if(result > 0) {
                return "添加成功";
            }else {
                return "添加失败";
            }
        }
    }


    //删除数据
    @SuppressWarnings("finally")
    public String Del(String id) {
        conn = bd.getConn();
        int result = -1;
        String sql = "delete from 部门管理员信息表 where 编号 ='"+id+"'";
        try {
            pstate = conn.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            result = pstate.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            bd.close(null, pstate, conn);
            if(result > 0) {
                return "删除成功";
            }else {
                return "删除失败";
            }
        }
    }

    //修改数据，会把所有数据修改一遍id除外
    @SuppressWarnings("finally")
    public String Update(String id,String name,String sex,String apartment,String id_num,String birthday,String call,String address,String hobby,String attach) {
        conn = bd.getConn();
        conn = bd.getConn();
        int result = -1;
        String sql = "update 部门管理员信息表 set  姓名 ='"+name+"',性别 ='"+sex+"',部门='"+apartment+"',身份证号='"+id_num+"',出生日期='"+birthday+"',联系电话='"
                +call+"',家庭地址='"+address+"',兴趣爱好='"+hobby+"',备注='"+attach+"' where 编号='"+id+"'";
        try {
            pstate = conn.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            result = pstate.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            bd.close(null, pstate, conn);
            if(result > 0) {
                return "修改成功";
            }else {
                return "修改失败";
            }
        }
    }



}
/***部门信息表***/
class Apartment{
    String id;
    String name;
    String attach;

    @Override
    public String toString() {
        return "Apartment{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", attach='" + attach + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }
}
/***部门信息表的增删查改***/
class ScoreDao2 {
    //实现增删改查的方法
    BaseDao bd = new BaseDao();
    Connection conn = null;
    Statement state = null;
    ResultSet rs = null;
    PreparedStatement pstate;
    String[] str = new String[4];

    //将数据库数据导出
    public java.util.List<Apartment> ScoreDao() {
        conn = bd.getConn();
        java.util.List<Apartment> list1 = new ArrayList<Apartment>();
        try {
            state = conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String sql = "select * from 部门信息表";
        try {
            rs = state.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            while (rs.next()) {
                Apartment yuangong = new Apartment();
                yuangong.setId(rs.getString("编号"));
                yuangong.setName(rs.getString("名称"));
                yuangong.setAttach(rs.getString("备注"));
                System.out.println(yuangong);
                list1.add(yuangong);
            }
            bd.close(rs, pstate, conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list1;
    }

    //查询，返回查询出来的数据
    public java.util.List<Apartment> Select(String id) {
        java.util.List<Apartment> list2 = new ArrayList<Apartment>();
        conn = bd.getConn();
        int result = -1;
        String sql = "select * from 部门信息表 where 编号 = ?";
        try {
            pstate = conn.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            pstate.setString(1, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            ResultSet rs = pstate.executeQuery();
            while (rs.next()) {
                Apartment yuangong = new Apartment();
                yuangong.setId(rs.getString("编号"));
                yuangong.setName(rs.getString("名称"));
                yuangong.setAttach(rs.getString("备注"));
                System.out.println(yuangong);
                list2.add(yuangong);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            bd.close(null, pstate, conn);
        }
        return list2;
    }

    //添加数据入库
    @SuppressWarnings("finally")
    public String Insert(String id, String name, String attach) {
        conn = bd.getConn();
        int result = -1;
        String sql = "insert into 部门信息表 values('" + id + "','" + name + "','" + attach + "')";
        try {
            pstate = conn.prepareStatement(sql);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            result = pstate.executeUpdate();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            bd.close(null, pstate, conn);
            if (result > 0) {
                return "添加成功";
            } else {
                return "添加失败";
            }
        }
    }


    //删除数据
    @SuppressWarnings("finally")
    public String Del(String id) {
        conn = bd.getConn();
        int result = -1;
        String sql = "delete from 部门信息表 where 编号 ='" + id + "'";
        try {
            pstate = conn.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            result = pstate.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            bd.close(null, pstate, conn);
            if (result > 0) {
                return "删除成功";
            } else {
                return "删除失败";
            }
        }
    }

    //修改数据，会把所有数据修改一遍id除外
    @SuppressWarnings("finally")
    public String Update(String id, String name, String attach) {
        conn = bd.getConn();
        conn = bd.getConn();
        int result = -1;
        String sql = "update 部门信息表 set  名称 ='" + name + "',备注='" + attach + "' where 编号='" + id + "'";
        try {
            pstate = conn.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            result = pstate.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            bd.close(null, pstate, conn);
            if (result > 0) {
                return "修改成功";
            } else {
                return "修改失败";
            }
        }
    }
}
/***员工考勤信息表***/
class KaoQin{
    String id;
    String name;
    String apartment;
    String time;
    String leibie;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getApartment() {
        return apartment;
    }

    public void setApartment(String apartment) {
        this.apartment = apartment;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLeibie() {
        return leibie;
    }

    public void setLeibie(String leibie) {
        this.leibie = leibie;
    }

    @Override
    public String toString() {
        return "Kaiqin{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", apartment='" + apartment + '\'' +
                ", time='" + time + '\'' +
                ", leibie='" + leibie + '\'' +
                '}';
    }
}
/***员工考勤信息表增删改查***/
class ScoreDao3 {
    //实现增删改查的方法

    BaseDao bd = new BaseDao();
    Connection conn = null;
    Statement state = null;
    ResultSet rs = null;
    PreparedStatement pstate;
    String [] str = new String[4];

    //将数据库数据导出
    public java.util.List<KaoQin> ScoreDao() {
        conn = bd.getConn();
        java.util.List<KaoQin> list1 = new ArrayList<KaoQin>();
        try {
            state = conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String sql = "select * from 考勤信息表";
        try {
            rs = state.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            while(rs.next()) {
                KaoQin yuangong = new KaoQin();
                yuangong.setId(rs.getString("编号"));
                yuangong.setName(rs.getString("姓名"));
                yuangong.setApartment(rs.getString("部门"));
                yuangong.setTime(rs.getString("考勤时间"));
                yuangong.setLeibie(rs.getString("考勤类别"));
                System.out.println(yuangong);
                list1.add(yuangong);
            }
            bd.close(rs, pstate, conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list1;
    }

    //查询，返回查询出来的数据
    public java.util.List<KaoQin> Select(String id) {
        java.util.List<KaoQin> list2 = new ArrayList<KaoQin>();
        conn = bd.getConn();
        int result = -1;
        String sql = "select * from 考勤信息表 where 编号 = ?";
        try {
            pstate = conn.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            pstate.setString(1, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            ResultSet rs = pstate.executeQuery();
            while(rs.next()) {
                KaoQin yuangong = new KaoQin();
                yuangong.setId(rs.getString("编号"));
                yuangong.setName(rs.getString("姓名"));
                yuangong.setApartment(rs.getString("部门"));
                yuangong.setTime(rs.getString("考勤时间"));
                yuangong.setLeibie(rs.getString("考勤类别"));
                System.out.println(yuangong);
                list2.add(yuangong);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            bd.close(null, pstate, conn);
        }
        return list2;
    }
    //添加数据入库
    @SuppressWarnings("finally")
    public String Insert( String id,String name,String apartment,String time,String leibie) {
        conn = bd.getConn();
        int result = -1;
        String sql = "insert into 考勤信息表 values('"+id+"','"+name+"','"+
                apartment+"','"+time+"','"+leibie+"')";
        try {
            pstate = conn.prepareStatement(sql);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            result = pstate.executeUpdate();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally {
            bd.close(null, pstate, conn);
            if(result > 0) {
                return "添加成功";
            }else {
                return "添加失败";
            }
        }
    }


    //删除数据
    @SuppressWarnings("finally")
    public String Del(String id) {
        conn = bd.getConn();
        int result = -1;
        String sql = "delete from 考勤信息表 where 编号 ='"+id+"'";
        try {
            pstate = conn.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            result = pstate.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            bd.close(null, pstate, conn);
            if(result > 0) {
                return "删除成功";
            }else {
                return "删除失败";
            }
        }
    }

    //修改数据，会把所有数据修改一遍id除外
    @SuppressWarnings("finally")
    public String Update( String id,String name,String apartment,String time,String leibie) {
        conn = bd.getConn();
        conn = bd.getConn();
        int result = -1;
        String sql = "update 考勤信息表 set  姓名 ='"+name+"',部门 ='"+apartment+"',考勤时间='"+time+"',考勤类别='"+leibie+"' where 编号='"+id+"'";
        try {
            pstate = conn.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            result = pstate.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            bd.close(null, pstate, conn);
            if(result > 0) {
                return "修改成功";
            }else {
                return "修改失败";
            }
        }
    }



}
public class Lei {
}
