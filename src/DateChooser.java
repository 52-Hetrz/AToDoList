
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class DateChooser extends JPanel implements MouseListener, ActionListener {
    private String[] years={"2019","2020","2021","2022","2023","2024"};
    private String[] months={"1","2","3","4","5","6","7","8","9","10","11","12"};
    private String[] days1={"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"};
    private String yearNow;
    private String monthNow;
    private String dayNow;
    private JComboBox<String> year =new JComboBox<>(years);
    private JComboBox<String> month =new JComboBox<>(months);
    private JComboBox<String> day =new JComboBox<>(days1);

    DateChooser(){
        year.addActionListener(this);
        month.addActionListener(this);
        day.addActionListener(this);
        year.addMouseListener(this);
        month.addMouseListener(this);
        day.addMouseListener(this);
        makePanel();
    }
    public void makePanel(){

        this.setLayout(new GridLayout(1,3));
        year.setSelectedItem(getYear());
        month.setSelectedItem(getMonth());
        /*if(getMonth()==2&&check(getYear()))
        {
            day =new JComboBox<>(days3);
        }
        else if(getMonth()==2&&!check(getYear()))
        {
            day =new JComboBox<>(days4);
        }
        else if(getMonth()==1||getMonth()==3||getMonth()==5||getMonth()==7||getMonth()==8||getMonth()==10||getMonth()==12)
            day =new JComboBox<>(days1);
        else if(getMonth()==4||getMonth()==6||getMonth()==9||getMonth()==11)
            day =new JComboBox<>(days2);*/
        day.setSelectedItem(getDay());
        this.add(year);
        this.add(month);
        this.add(day);
    }
    private Boolean check(int y){
        if(y %4==0&& y %100!=0|| y %400==0){
            return true;
        }else{
            return false;
        }
    }
    public String getYear(){return yearNow;}
    public String getMonth(){return monthNow;}
    public String getDay(){return dayNow;}
    public void setYear(String y){this.yearNow=y;}
    public void setMonth(String m){this.monthNow=m;}
    public void setDay(String d){this.dayNow=d;}
    void reset(String y, String m, String d){
        setYear(y);
        setDay(d);
        setMonth(m);
        makePanel();
    }

    public static void main(String[] args)
    {
        JFrame j=new JFrame();
        j.setSize(new Dimension(200,200));
        DateChooser m=new DateChooser();
        m.reset("2020","4","5");
        j.add(m);
        j.setVisible(true);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        //setYear((String) year.getSelectedItem());
        //System.out.print(getYear());
       // setMonth((String) month.getSelectedItem());
        //setDay((String) day.getSelectedItem());
       // makePanel();
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {
    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==year)
            setYear((String)year.getSelectedItem());
        else if(e.getSource()==month)
            setMonth((String)month.getSelectedItem());
        else if(e.getSource()==day)
            setDay((String)day.getSelectedItem());
    }
}

