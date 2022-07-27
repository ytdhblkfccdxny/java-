import javax.swing.*;
import java.util.Scanner;

public class LI{
        public static void main(String [] args) {
            JComboBox comboBox1=new JComboBox();//下拉列表框组件
            comboBox1.addItem("");
            Scanner sc = new Scanner(System.in);
            while (true) {
                System.out.println("请输入要查询的年份:");
                int year = sc.nextInt();
                System.out.println("请输入要查询的月份:");
                int month = sc.nextInt();
                printCalendar(year, month);
                System.out.println("1为继续，0为退出，是否退出?");
                int flag = sc.nextInt();
                if (flag == 0) {
                    break;
                }
            }
        }
        public static boolean isrunnian(int year){
            return ((year%4==0&&year%100!=0)||year%400==0);
        }
        public static String shengxiao(int year){
            String [] shengxiao={"鼠","牛","虎","兔","龙","蛇","马","羊","猴","鸡","狗","猪"};
            if(year>=2020)
                return shengxiao[(year-2020)%12];
            else
            if((2020-year)%12==0)
                return shengxiao[0];
            else
                return shengxiao[12-(2020-year)%12];
        }
        public static void printCalendar(int year,int month){
            System.out.println(year+"年"+month+"月的日历为:");
            System.out.println("*********"+shengxiao(year)+"年******"+(isrunnian(year)?"闰年":"平年")+"*****");
            System.out.println("星期日\t星期一\t星期二\t星期三\t星期四\t星期五\t星期六\t");

            //计算当前月的第一天是星期几
            int totalDays = countTotalDays(year, month);
            int firstDay;
            if(year>=1990)
                firstDay = totalDays%7 + 1;
            else
                firstDay=7-totalDays%7+1;
            //因为显示星期日是第一列 所以不用加空格
            if(firstDay >= 7){
                firstDay = firstDay%7;
            }
            for(int i=0; i<firstDay; i++){
                System.out.print("    \t");
            }
            for(int i=1; i<=monthDays(year, month); i++){
                System.out.print(i + "   \t");
                if((i+firstDay)%7 == 0){
                    System.out.println();
                }
            }
            System.out.println();
        }
        public static int monthDays(int year, int month){
            int[] monthDay = {29, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
            if(month == 2 && isrunnian(year)){
                return monthDay[0];
            }
            return monthDay[month];
        }
        public static int countTotalDays(int year, int month){
            int totalDays = 0;
            if(year>=1990) {
                for (int i = 1990; i < year; i++) {
                    if (isrunnian(i)) {
                        totalDays += 366;
                    } else {
                        totalDays += 365;
                    }
                }
                for(int i=1; i<month; i++){
                    totalDays += monthDays(year, i);
                }
            }
            else{
                for(int i=year+1;i<1990;i++)
                    if (isrunnian(i)) {
                        totalDays += 366;
                    } else {
                        totalDays += 365;
                    }
                for(int i=12; i>=month; i--){
                    totalDays += monthDays(year, i);
                }
            }
            return totalDays;
        }

    }


