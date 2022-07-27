//
//
//
//import java.awt.BorderLayout;
//import java.awt.EventQueue;
//
//
//import javax.swing.JFrame;
//import javax.swing.JOptionPane;
//import javax.swing.JPanel;
//import javax.swing.border.EmptyBorder;
//import javax.swing.table.DefaultTableModel;
//
//
//
//import javax.swing.JTable;
//import javax.swing.JScrollPane;
//import javax.swing.JButton;
//import java.awt.event.ActionListener;
//import java.util.List;
//import java.awt.event.ActionEvent;
//
//
//public class MainFrame extends JFrame {
//
//
//    private JPanel contentPane;
//    private JTable table;
//    private String[] columnCount= {"序号","姓名","成绩","生日","城市"};
//    private List<Student> list;
//    public static Student stu;
//    public static MainFrame frame;
//
//    /**
//     * Launch the application.
//     */
//    public static void main(String[] args) {
//        EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                try {
//                    frame = new MainFrame();
//                    //窗口居中
//                    frame.setLocationRelativeTo(null);
//                    frame.setVisible(true);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//    }
//
//
//    /**
//     * Create the frame.
//     */
//    public MainFrame() {
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setBounds(100, 100, 764, 469);
//        contentPane = new JPanel();
//        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
//        setContentPane(contentPane);
//        contentPane.setLayout(null);
//
//        JScrollPane scrollPane = new JScrollPane();
//        scrollPane.setBounds(29, 58, 692, 332);
//        contentPane.add(scrollPane);
//
//        table = new JTable();
//        scrollPane.setViewportView(table);
//
//        JButton button = new JButton("查询");
//        button.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                quaryAll();
//            }
//        });
//        button.setBounds(58, 22, 93, 23);
//        contentPane.add(button);
//
//        JButton button_1 = new JButton("添加");
//        button_1.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                new com.lisonglin.frame.FromFjame().setVisible(true);
//            }
//        });
//        button_1.setBounds(205, 22, 93, 23);
//        contentPane.add(button_1);
//        //全屏
////		setExtendedState(JFrame.MAXIMIZED_BOTH);
//
//        JButton button_2 = new JButton("修改");
//        button_2.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                update();
//                quaryAll();
//            }
//        });
//        button_2.setBounds(357, 22, 93, 23);
//        contentPane.add(button_2);
//
//        JButton button_3 = new JButton("删除");
//        button_3.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                remove();
//                quaryAll();
//            }
//        });
//        button_3.setBounds(539, 22, 93, 23);
//        contentPane.add(button_3);
//
//    }
//    //查询
//    public void quaryAll() {
//        StudentService ss=new StudentService();
//        list = ss.queryAll();
//        if(list==null) {
//            JOptionPane.showMessageDialog(null, "服务器繁忙");
//            return;
//        }
//        Object[][] data = DateUtil.listToArray(list);
//        table.setModel(new DefaultTableModel(data, columnCount));
//    }
//
//    //删除
//    private void remove() {
//        int i = table.getSelectedRow();
//        Student s = list.get(i);
//        int code = new StudentService().delete(s.getId());
//        if(code==0) {
//            JOptionPane.showMessageDialog(null, "删除成功");
//            return;
//        }else {
//            JOptionPane.showMessageDialog(null,DateUtil.errors.get(code) );
//        }
//        quaryAll();
//    }
//
//    //修改
//    private void update() {
//        int i = table.getSelectedRow();
//        stu = list.get(i);
//        new FromFjame().setVisible(true);
//    }
//}
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.*;

public class YearMonthDay  {
    JFrame frame = new JFrame();
    private int STARTYEAR = 1950;
    private int ENDYEAR = 2022;
    private JComboBox cboYear=null;
    private JComboBox cboMonth=null;
    private JComboBox cboDay=null;
    /**
     * 传入年月日下拉列表框组件
     */
    YearMonthDay(){
        cboYear=new JComboBox();
        cboMonth=new JComboBox();
        cboDay=new JComboBox();
        cboYear.setBounds(30,100,60,30);
        cboMonth.setBounds(30,170,60,30);
        cboDay.setBounds(30,240,60,30);
        AddInfo();

        cboMonth.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                Object obj = cboMonth.getSelectedItem();// 取得选中月份
                System.out.println("asdfghjk");
                if (obj != null) {
                    cboDay.removeAllItems();// 清空日的下拉列表框

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
                    }//if

                    for (int j = 0; j < days; j++) {
                        cboDay.addItem("" + (j + 1));
                    }//for
                }//if
            }
        });
        frame.add(cboYear);
        frame.add(cboMonth);
        frame.add(cboDay);
        frame.setBounds(400,400,400,400);
        frame.setLayout(null);
        frame.setVisible(true);
    }

    /**
     * 初始化年月日下来列表框
     */
    public void AddInfo() {
        //年下拉选择框
        for (int i = STARTYEAR; i <= ENDYEAR; i++) {
            cboYear.addItem("" + i);
        }
        //月下拉选择框
        for (int i = 0; i < 12; i++) {
            cboMonth.addItem("" + (i + 1));
        }
        //日下拉选择框
        for (int j = 0; j < 31; j++) {
            cboDay.addItem("" + (j + 1));
        }
    }

    /**
     * 月 下拉框监听事件
     *
     * @author Vecrates
     */


    public static void main(String []args){
       new YearMonthDay();


    }

}

