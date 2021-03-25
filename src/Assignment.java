
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public abstract class Assignment extends JFrame implements ActionListener
{

    String year,month,day;
    int DEADLINE;
    DateChooser dateChooser=new DateChooser();
    static int count;
    int thisCount;
    private AssignmentList father;
    private String name;
    private String detail;
    private Boolean finish;
    JPanel panel=new JPanel();
    JPanel panel1=new JPanel();
    JPanel panel2=new JPanel();
    JLabel title1=new JLabel("名称：");
    JTextField nameLabel=new JTextField();
    JLabel title2=new JLabel("详细信息：");
    JTextArea detailText=new JTextArea();
    JRadioButton finished=new JRadioButton("完成");
    JRadioButton unfinished=new JRadioButton("未完成");
    ButtonGroup choose=new ButtonGroup();
    JButton save=new JButton("保存");
    JButton back=new JButton("返回");
    ArrayList<JLabel> labels=new ArrayList<>();
    ArrayList<JTextField> texts=new ArrayList<>();


    Assignment() {

        count++;
        thisCount=count;
        this.setTitle(this.name);
        this.setMinimumSize(new Dimension(400,300));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(panel);

        save.addActionListener(this);
        back.addActionListener(this);
        finished.addActionListener(this);
        unfinished.addActionListener(this);

        this.finish=false;
        detailText.setLineWrap(true);

        labels.add(title1);
        texts.add(nameLabel);

        choose.add(finished);
        choose.add(unfinished);
    }

    public void makePanel() {
        nameLabel.setText(getName());
        for(int i=0;i<texts.size();i++)
        {
            (labels.get(i)).setFont(new Font("Serif",Font.BOLD,15));
            (texts.get(i)).setFont(new Font("Serif",Font.BOLD,15));
            panel1.add(labels.get(i));
            panel1.add(texts.get(i));
        }
        finished.setFont(new Font("Serif",Font.BOLD,15));
        unfinished.setFont(new Font("Serif",Font.BOLD,15));
        panel1.add(finished);
        panel1.add(unfinished);
        title2.setFont(new Font("Serif",Font.BOLD,15));
        panel1.add(title2);

        save.setFont(new Font("Serif",Font.BOLD,15));
        back.setFont(new Font("Serif",Font.BOLD,15));
        panel2.setLayout(new GridLayout(1,2));
        panel2.add(save);
        panel2.add(back);

        detailText.setText(getDetail());
        panel.setLayout(new BorderLayout());
        panel.add(panel1,BorderLayout.NORTH);
        panel.add(detailText,BorderLayout.CENTER);
        panel.add(panel2,BorderLayout.SOUTH);
        if(getFinish())
            finished.setSelected(true);
        else
            unfinished.setSelected(false);
    }

    public int getDEADLINE(){
        return Integer.parseInt(getYear())*10000+Integer.parseInt(getMonth())*100+Integer.parseInt(getDay());
    }

    int getCount(){return thisCount;}
    public void setName(String name){this.name=name;}
    public String getName(){return this.name;}
    void setDetail(String detail){this.detail=detail;}
    String getDetail(){return this.detail;}
    void setFinish(Boolean finish){this.finish=finish;}
    Boolean getFinish(){return this.finish;}
    void setFather(AssignmentList a){this.father=a;}
    AssignmentList getFather(){return this.father;}
    public JPanel getPanel(){return panel;}
    public Assignment copyThis(){return null;}
    public String toString(){return null;}
    public void setFromFile(JSONObject a, AssignmentList al){}
    public char getFirstWord(){
        char[] c=getName().toCharArray();
        return c[0];
    }
    public  void writeToAssignmentFile(){}
    public  void writeToAssignmentListFile(JSONArray ja){}
    public void setYear(String y){this.year=y;}
    public String getYear(){return year;}
    public void setMonth(String m){this.month=m;}
    public String getMonth(){return month;}
    public void setDay(String d){this.day=d;}
    public String getDay(){return day;}

    @Override
    public void actionPerformed(ActionEvent e) {
        if(finished.isSelected())
        {
            setFinish(true);
        }
        else if(unfinished.isSelected())
        {
            setFinish(false);
        }
    }
}
