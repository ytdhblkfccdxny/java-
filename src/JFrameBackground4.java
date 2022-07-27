//package com.lisonglin.frame;
//
//
//import java.awt.Font;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.time.LocalDate;
//
//
//import javax.swing.JButton;
//import javax.swing.JComboBox;
//import javax.swing.JFrame;
//import javax.swing.JLabel;
//import javax.swing.JOptionPane;
//import javax.swing.JPanel;
//import javax.swing.JTextField;
//import javax.swing.border.EmptyBorder;
//
//
//import com.lisonglin.model.Student;
//import com.lisonglin.service.StudentService;
//import com.lisonglin.util.DateUtil;
//import java.awt.event.ItemListener;
//import java.awt.event.ItemEvent;
//
//
//public class FromFjame extends JFrame {
//
//
//    /**
//     *
//     */
//    private static final long serialVersionUID = 1L;
//    private JPanel contentPane;
//    private JTextField textField;
//    private JTextField textField_1;
//    private JTextField textField_3;
//    private JComboBox comboBox;
//    private JComboBox comboBox_1;
//    private JComboBox comboBox_2;
//
//
//    public FromFjame() {
//        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//        setBounds(100, 100, 314, 436);
//        contentPane = new JPanel();
//        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
//        setContentPane(contentPane);
//        contentPane.setLayout(null);
//
//        JLabel label = new JLabel("添加学生");
//        label.setFont(new Font("宋体", Font.PLAIN, 17));
//        label.setBounds(118, 20, 78, 39);
//        contentPane.add(label);
//
//        JLabel label_1 = new JLabel("姓名");
//        label_1.setBounds(23, 71, 40, 15);
//        contentPane.add(label_1);
//
//        textField = new JTextField();
//        textField.setBounds(87, 68, 155, 21);
//        contentPane.add(textField);
//        textField.setColumns(10);
//
//        JLabel label_2 = new JLabel("成绩");
//        label_2.setBounds(23, 128, 40, 15);
//        contentPane.add(label_2);
//
//        textField_1 = new JTextField();
//        textField_1.setBounds(87, 125, 155, 21);
//        contentPane.add(textField_1);
//        textField_1.setColumns(10);
//
//        JLabel lblNewLabel = new JLabel("生日");
//        lblNewLabel.setBounds(23, 191, 32, 15);
//        contentPane.add(lblNewLabel);
//
//        JLabel label_3 = new JLabel("城市");
//        label_3.setBounds(23, 251, 32, 15);
//        contentPane.add(label_3);
//
//        textField_3 = new JTextField();
//        textField_3.setBounds(87, 248, 155, 21);
//        contentPane.add(textField_3);
//        textField_3.setColumns(10);
//
//        comboBox = new JComboBox();
//        comboBox.addItemListener(new ItemListener() {
//            public void itemStateChanged(ItemEvent e) {
//                addDay();
//            }
//        });
//        comboBox.setBounds(87, 188, 54, 21);
//        contentPane.add(comboBox);
//
//        comboBox_1 = new JComboBox();
//        comboBox_1.addItemListener(new ItemListener() {
//            public void itemStateChanged(ItemEvent e) {
//                addDay();
//            }
//        });
//        comboBox_1.setBounds(151, 188, 47, 21);
//        contentPane.add(comboBox_1);
//
//        comboBox_2 = new JComboBox();
//        comboBox_2.setBounds(202, 188, 40, 21);
//        contentPane.add(comboBox_2);
//
//        //选择框添加内容
//        addBirth();
//
//        JButton button = new JButton("添加");
//        button.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                if(MainFrame.stu==null) {
//                    add();
//                }else {
//                    update();
//                }
//            }
//        });
//        button.setBounds(37, 325, 93, 23);
//        contentPane.add(button);
//
//        JButton button_1 = new JButton("返回");
//        button_1.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                //每次返回清空信息
//                MainFrame.stu=null;
//                //退出
//                dispose();
//            }
//        });
//        button_1.setBounds(169, 325, 93, 23);
//        contentPane.add(button_1);
//
//        //当点击的行数的信息不为空时，进行下面操作
//        if(MainFrame.stu!=null) {
//            textField.setText(MainFrame.stu.getName());
//            textField_1.setText(MainFrame.stu.getScore()+"");
//            textField_3.setText(MainFrame.stu.getCity());
//            LocalDate birth =MainFrame.stu.getBirth();
//            comboBox.setSelectedItem(birth.getYear());
//            comboBox_1.setSelectedItem(birth.getMonthValue());
//            comboBox_2.setSelectedItem(birth.getDayOfMonth());
//            button.setText("修改");
//        }
//    }
//
//
//    //选择框填充内容
//    private void addBirth() {
//        int year=LocalDate.now().getYear();
//        for(int i=1970;i<=year;i++) {
//            comboBox.addItem(i);
//        }
//        for(int i=1;i<=12;i++) {
//            comboBox_1.addItem(i);
//        }
//    }
//
//    private void addDay() {
//        int year=1970;
//        int month=1;
//        int day=0;
//        Object oYear = comboBox.getSelectedItem();
//        Object oMonth =comboBox_1.getSelectedItem();
//        if(oYear==null||oMonth==null) {
//            return;
//        }else {
//            year=(int) oYear;
//            month=(int) oMonth;
//        }
//        boolean flag=(year%4==0&&year%100!=0)||year%400==0;
//        switch(month) {
//            case 1:
//            case 3:
//            case 5:
//            case 7:
//            case 8:
//            case 10:
//            case 12:
//                day=31;
//                break;
//            case 2:
//                day=flag?28:29;
//                break;
//            default:
//                day=30;
//                break;
//        }
//        comboBox_2.removeAllItems();
//        for(int i=1;i<=day;i++) {
//            comboBox_2.addItem(i);
//        }
//    }
//
//    //增加
//    private void add() {
//        String name=textField.getText();
//        String strSouce=textField_1.getText();
//        String city=textField_3.getText();
//        int year=(int)comboBox.getSelectedItem();
//        int month=(int) comboBox_1.getSelectedItem();
//        int day=(int) comboBox_2.getSelectedItem();
//        Student s=new Student(name,Double.parseDouble(strSouce),LocalDate.of(year, month, day),city);
//        int insert = new StudentService().insert(s);
//        if(insert==0) {
//            JOptionPane.showMessageDialog(null, "添加成功");
//            textField.setText("");
//            textField_1.setText("");
//            textField_3.setText("");
//            return;
//        }else {
//            JOptionPane.showMessageDialog(null, DateUtil.errors.get(insert));
//        }
//    }
//
//    //修改
//    private void update() {
//        String name=textField.getText();
//        String strSouce=textField_1.getText();
//        String city=textField_3.getText();
//        int year=(int)comboBox.getSelectedItem();
//        int month=(int) comboBox_1.getSelectedItem();
//        int day=(int) comboBox_2.getSelectedItem();
//        MainFrame.stu.setName(name);
//        MainFrame.stu.setScore(Double.parseDouble(strSouce));
//        MainFrame.stu.setBirth(LocalDate.of(year, month, day));
//        MainFrame.stu.setCity(city);
//        int i = new StudentService().update(MainFrame.stu);
//        if(i==0) {
//            JOptionPane.showMessageDialog(null, "修改成功");
//            MainFrame.stu=null;
//            textField.setText("");
//            textField_1.setText("");
//            textField_3.setText("");
//            MainFrame.frame.quaryAll();
//            dispose();
//            return;
//        }else {
//            JOptionPane.showMessageDialog(null, DateUtil.errors.get(i));
//        }
//    }
//}
import javax.swing.*;

public class JFrameBackground4 extends JFrame
{
    //创建一个JLayeredPane用于分层的。
    JLayeredPane layeredPane;
    //创建一个Panel和一个Label用于存放图片，作为背景。
    JPanel jp;
    JLabel jl;
    ImageIcon image;

    //创建一个按钮用于测试的。
    JButton jb;
    public static void main(String[] args)
    {
        new JFrameBackground4();
    }

    public JFrameBackground4()
    {
        layeredPane=new JLayeredPane();
        image=new ImageIcon("images\\background.jpg");//随便找一张图就可以看到效果。
        //创建背景的那些东西
        jp=new JPanel();
        jp.setBounds(0,0,image.getIconWidth(),image.getIconHeight());

        jl=new JLabel(image);
//		jl.setBounds(0,0,image.getIconWidth(),image.getIconHeight());
        jp.add(jl);

        //创建一个测试按钮
        jb=new JButton("测试按钮");
        jb.setBounds(100,100,100,100);

        //将jp放到最底层。
        layeredPane.add(jp,JLayeredPane.DEFAULT_LAYER);
        //将jb放到高一层的地方
        layeredPane.add(jb,JLayeredPane.MODAL_LAYER);

        this.setLayeredPane(layeredPane);
        this.setSize(image.getIconWidth(),image.getIconHeight());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocation(image.getIconWidth(),image.getIconHeight());
        this.setVisible(true);
    }
}
