import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;
import java.util.Vector;


//}//连接数据库

public  class Leavejilu extends JFrame {
    ScoreDao4 sd = new ScoreDao4();
    KaoQin s = new KaoQin();
    JComboBox cboYear=new JComboBox();
    JComboBox cboMonth=new JComboBox();
    JComboBox cboDay=new JComboBox();
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
    public Leavejilu() {
        this.setTitle("员工请假信息查询");
        //this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(600, 400);
        this.setLocationRelativeTo(null);
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
        //上    查询面板
        jp1.add(cboYear);
        jp1.add(cboMonth);
        jp1.add(cboDay);
        jp1.add(lbl);
        jp1.add(jtf);
        jp1.add(jbt1);
//		jtf.setPreferredSize(new Dimension(10,10));设置文本框大小
        this.add(jp1, BorderLayout.NORTH);

        //中  查询表格
        DefaultTableModel dtm = new DefaultTableModel();
        table.setModel(dtm);
        dtm.addColumn("编号");
        dtm.addColumn("姓名");
        dtm.addColumn("部门");
        dtm.addColumn("起始时间");
        dtm.addColumn("请假时长");
        dtm.addColumn("销假时间");
        dtm.addColumn("请假理由");
        dtm.addColumn("是否销假");
        for (int i = 1; i < 5; i++) {
            Vector<Object> data = new Vector<Object>();
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
        this.add(jp3, BorderLayout.SOUTH);
        this.setVisible(true);


        //添加按钮---鼠标点击不在输入框方可添加
        jbt2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                int rows = table.getSelectedRows().length;
                System.out.println("添加多少行？" + rows);
                for (int i = 0; i < rows; i++) {
                    int row = table.getSelectedRow();
                    String id = (String) dtm.getValueAt(row, 0);
                    String name = (String) dtm.getValueAt(row, 1);
                    String apartment = (String) dtm.getValueAt(row, 2);
                    String starttime = (String) dtm.getValueAt(row, 3);
                    String length = (String) dtm.getValueAt(row, 4);
                    String overtime = (String) dtm.getValueAt(row, 5);
                    String liyou = (String) dtm.getValueAt(row, 6);
                    String state = (String) dtm.getValueAt(row, 7);

                    System.out.println("添加");
                    String str = sd.Insert(id, name, apartment, starttime, length,overtime,liyou,state);
                    JOptionPane.showMessageDialog(null, str);
                }
                Vector<Object> data = new Vector<Object>();
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
        });

        //删除按钮
        jbt3.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                table.setEnabled(true);
                int rows = table.getSelectedRows().length;
                for (int i = 0; i < rows; i++) {
                    int row = table.getSelectedRow();
                    String oid = dtm.getValueAt(row, 0).toString();
                    String str = sd.Del(oid);
                    dtm.removeRow(row);
                    System.out.println("删除：" + oid);
                    JOptionPane.showMessageDialog(null, str);
                }
            }
        });

        //修改按钮
        jbt4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                int row = table.getSelectedRow();
                String id = (String) dtm.getValueAt(row, 0);
                String name = (String) dtm.getValueAt(row, 1);
                String apartment = (String) dtm.getValueAt(row, 2);
                String starttime = (String) dtm.getValueAt(row, 3);
                String length = (String) dtm.getValueAt(row, 4);
                String overtime = (String) dtm.getValueAt(row, 5);
                String liyou = (String) dtm.getValueAt(row, 6);
                String state = (String) dtm.getValueAt(row, 7);
                System.out.println("修改" + id);
                String str = sd.Update(id, name, apartment, starttime, length,overtime,liyou,state);
                JOptionPane.showMessageDialog(null, str);
            }
        });
        //查询按钮
        jbt1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                //删除原有数据--把查询所得数据重新加入
                if (jtf.getText().equals("")) {
                    List<Leaverecord> list = sd.ScoreDao();
                    dtm.setRowCount(0);//删除原有数据
                    for (int i = 0; i < list.size(); i++) {
                        Vector<Object> data = new Vector<Object>();
                        Leaverecord yuangong = list.get(i);
                        System.out.println("查到:"+yuangong);
                        data.add(yuangong.getId());
                        data.add(yuangong.getName());
                        data.add(yuangong.getApartment());
                        data.add(yuangong.getStarttime());
                        data.add(yuangong.getLength());
                        data.add(yuangong.getOvertime());
                        data.add(yuangong.getLiyou());
                        data.add(yuangong.getState());
                        dtm.addRow(data);
                    }
                } else {
                    List<Leaverecord> list1 = sd.Select(jtf.getText());//获取输入的id
                    dtm.setRowCount(0);//删除原有数据
                    Vector<Object> data = new Vector<Object>();
                    System.out.println(list1.size());
                    for (int i = 0; i < list1.size(); i++) {
                        Vector<Object> data1 = new Vector<Object>();
                        Leaverecord yuangong = list1.get(i);
                        data1.add(yuangong.getId());
                        data1.add(yuangong.getName());
                        data1.add(yuangong.getApartment());
                        data.add(yuangong.getStarttime());
                        data.add(yuangong.getLength());
                        data.add(yuangong.getOvertime());
                        data.add(yuangong.getLiyou());
                        data.add(yuangong.getState());
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
                for (int i = 1; i < 15; i++) {
                    Vector<Object> data = new Vector<Object>();
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
        new Leavejilu ();
    }
}




