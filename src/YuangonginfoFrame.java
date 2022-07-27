import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;


//class ApartmentManager{
//    String id;
//    String name;
//    String sex;
//    String apartment;
//    String id_num;
//    String birthday;
//    String call;
//    String address;
//    String hobby;
//    String attach;
//
//    @Override
//    public String toString() {
//        return "ApartmentManager{" +
//                "id='" + id + '\'' +
//                ", name='" + name + '\'' +
//                ", sex='" + sex + '\'' +
//                ", apartment='" + apartment + '\'' +
//                ", id_num='" + id_num + '\'' +
//                ", birthday='" + birthday + '\'' +
//                ", call='" + call + '\'' +
//                ", address='" + address + '\'' +
//                ", hobby='" + hobby + '\'' +
//                ", attach='" + attach + '\'' +
//                '}';
//    }
//
//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getSex() {
//        return sex;
//    }
//
//    public void setSex(String sex) {
//        this.sex = sex;
//    }
//
//    public String getApartment() {
//        return apartment;
//    }
//
//    public void setApartment(String apartment) {
//        this.apartment = apartment;
//    }
//
//    public String getId_num() {
//        return id_num;
//    }
//
//    public void setId_num(String id_num) {
//        this.id_num = id_num;
//    }
//
//    public String getBirthday() {
//        return birthday;
//    }
//
//    public void setBirthday(String birthday) {
//        this.birthday = birthday;
//    }
//
//    public String getCall() {
//        return call;
//    }
//
//    public void setCall(String call) {
//        this.call = call;
//    }
//
//    public String getAddress() {
//        return address;
//    }
//
//    public void setAddress(String address) {
//        this.address = address;
//    }
//
//    public String getHobby() {
//        return hobby;
//    }
//
//    public void setHobby(String hobby) {
//        this.hobby = hobby;
//    }
//
//    public String getAttach() {
//        return attach;
//    }
//
//    public void setAttach(String attach) {
//        this.attach = attach;
//    }
//}
//class Apartment{
//    String id;
//    String name;
//    String attach;
//
//    @Override
//    public String toString() {
//        return "Apartment{" +
//                "id='" + id + '\'' +
//                ", name='" + name + '\'' +
//                ", attach='" + attach + '\'' +
//                '}';
//    }
//
//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getAttach() {
//        return attach;
//    }
//
//    public void setAttach(String attach) {
//        this.attach = attach;
//    }
//}


public  class YuangonginfoFrame extends JFrame {
    ScoreDao sd = new ScoreDao();
    Yuangonginfo s = new Yuangonginfo();

    private JButton jbt1 = new JButton("查询");
    private JButton jbt2 = new JButton("添加");
    private JButton jbt3 = new JButton("删除");
    private JButton jbt4 = new JButton("修改");
    private JButton jbt5 = new JButton("返回");
    private JTextField jtf = new JTextField(10);
    private JLabel lbl = new JLabel("请输入员工编号：");

    //窗口中添加表格
    private static JTable table = new JTable();
    private JScrollPane jsp = new JScrollPane(table);


    //把按钮放入面板
    private JPanel jp1 = new JPanel();

    private JPanel jp3 = new JPanel();


    public YuangonginfoFrame() {
        this.setTitle("员工信息查询");
        //this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(600, 400);
        this.setLocationRelativeTo(null);
        //上    查询面板
        jp1.add(lbl);
        jp1.add(jtf);
        jp1.add(jbt1);
//		jtf.setPreferredSize(new Dimension(10,10));设置文本框大小
        this.add(jp1,BorderLayout.NORTH);

        //中  查询表格
        DefaultTableModel dtm = new DefaultTableModel();
        table.setModel(dtm);
        dtm.addColumn("编号");
        dtm.addColumn("姓名");
        dtm.addColumn("性别");
        dtm.addColumn("部门");
        dtm.addColumn("身份证号");
        dtm.addColumn("出生日期");
        dtm.addColumn("联系电话");
        dtm.addColumn("家庭地址");
        dtm.addColumn("兴趣爱好");
        dtm.addColumn("备注");
        for(int i=1;i<5;i++)
        {
            Vector<Object> data = new Vector<Object>();
            data.add("");
            data.add("");
            data.add("");
            data.add("");
            data.add("");
            data.add("");
            data.add("");
            data.add("");
            data.add("");
            data.add("");
            dtm.addRow(data);
        }
        //放入中部
        this.add(jsp, BorderLayout.CENTER);
        jsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);//水平滚动条始终显示
        jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);//垂直滚动条始终显示
        //下 增删改查面板
        jp3.add(jbt2);
        jp3.add(jbt3);
        jp3.add(jbt4);
        jp3.add(jbt5);
        this.add(jp3,BorderLayout.SOUTH);
        this.setVisible(true);


        //添加按钮---鼠标点击不在输入框方可添加
        jbt2.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                int rows = table.getSelectedRows().length;
                System.out.println("添加多少行？"+rows);
                for (int i = 0; i < rows; i++) {
                    int row=table.getSelectedRow();
                    String id=(String) dtm.getValueAt(row, 0);
                    String name = (String) dtm.getValueAt(row, 1);
                    String sex = (String) dtm.getValueAt(row, 2);
                    String apartment = (String) dtm.getValueAt(row, 3);
                    String id_num = (String) dtm.getValueAt(row, 4);
                    String birthday = (String) dtm.getValueAt(row, 5);
                    String call = (String) dtm.getValueAt(row, 6);
                    String address = (String) dtm.getValueAt(row, 7);
                    String hobby = (String) dtm.getValueAt(row, 8);
                    String attach = (String) dtm.getValueAt(row, 9);
                    System.out.println("添加");
                    String str = sd.Insert(id, name, sex, apartment,id_num,birthday,call,address,hobby,attach);
                    JOptionPane.showMessageDialog(null, str);
                }
                    Vector<Object> data = new Vector<Object>();
                    data.add("");
                    data.add("");
                    data.add("");
                    data.add("");
                    dtm.addRow(data);
            }
        });

        //删除按钮
        jbt3.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int rows = table.getSelectedRows().length;
                for (int i = 0; i < rows; i++) {
                    int row = table.getSelectedRow();
                    System.out.println("删除行："+row);
                    String oid = dtm.getValueAt(row, 0).toString();
                    System.out.println("删除："+oid);
                    String str = sd.Del(oid);
                    System.out.println("删除："+oid);
                    dtm.removeRow(row);
                    JOptionPane.showMessageDialog(null, str);
                }
            }
        });

        //修改按钮
        jbt4.addActionListener(new ActionListener() {
                public void actionPerformed (ActionEvent arg0){
                    int row = table.getSelectedRow();
                    String id=(String) dtm.getValueAt(row, 0);
                    String name = (String) dtm.getValueAt(row, 1);
                    String sex = (String) dtm.getValueAt(row, 2);
                    String apartment = (String) dtm.getValueAt(row, 3);
                    String id_num = (String) dtm.getValueAt(row, 4);
                    String birthday = (String) dtm.getValueAt(row, 5);
                    String call = (String) dtm.getValueAt(row, 6);
                    String address = (String) dtm.getValueAt(row, 7);
                    String hobby = (String) dtm.getValueAt(row, 8);
                    String attach = (String) dtm.getValueAt(row, 9);
                    System.out.println("修改"+id);
                    String str = sd.Update(id, name, sex, apartment,id_num,birthday,call,address,hobby,attach);
                    JOptionPane.showMessageDialog(null, str);
                }
        });
        //查询按钮
        jbt1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                //删除原有数据--把查询所得数据重新加入
                if (jtf.getText().equals("")) {
                    List<Yuangonginfo> list = sd.ScoreDao();
                    dtm.setRowCount(0);//删除原有数据
                    for (int i = 0; i < list.size(); i++) {
                        Vector<Object> data = new Vector<Object>();
                        Yuangonginfo yuangong = list.get(i);
                        data.add(yuangong.getId());
                        data.add(yuangong.getName());
                        data.add(yuangong.getSex());
                        data.add(yuangong.getApartment());
                        data.add(yuangong.getId_num());
                        data.add(yuangong.getBirthday());
                        data.add(yuangong.getCall());
                        data.add(yuangong.getAddress());
                        data.add(yuangong.getHobby());
                        data.add(yuangong.getAttach());
                        dtm.addRow(data);
                    }
                }
                else {
                    List<Yuangonginfo> list1= sd.Select(jtf.getText());//获取输入的id
                    dtm.setRowCount(0);//删除原有数据
                    Vector<Object> data = new Vector<Object>();
                    System.out.println(list1.size());
                    for (int i = 0; i < list1.size(); i++) {
                        Vector<Object> data1 = new Vector<Object>();
                        Yuangonginfo yuangong = list1.get(i);
                        data1.add(yuangong.getId());
                        data1.add(yuangong.getName());
                        data1.add(yuangong.getSex());
                        data1.add(yuangong.getApartment());
                        data1.add(yuangong.getId_num());
                        data1.add(yuangong.getBirthday());
                        data1.add(yuangong.getCall());
                        data1.add(yuangong.getAddress());
                        data1.add(yuangong.getHobby());
                        data1.add(yuangong.getAttach());
                        dtm.addRow(data1);
                    }
                }
            }
        });
        //返回按钮
        jbt5.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                //删除查询的数据重新加入所有数据
                dtm.setRowCount(0);
                for(int i=1;i<15;i++)
                {
                    Vector<Object> data = new Vector<Object>();
                    data.add("");
                    data.add("");
                    data.add("");
                    data.add("");
                    data.add("");
                    data.add("");
                    data.add("");
                    data.add("");
                    data.add("");
                    data.add("");
                    dtm.addRow(data);
                }

            }
        });

    }

    public static void main(String[] args) {
        new YuangonginfoFrame();
    }
}


