
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class SonAssignment extends Assignment{
    private LAssignment fatherAssignment;
    //private String deadline;

    private JLabel title4=new JLabel("截止日期：");
    private JTextField deadlineLabel=new JTextField();

    public SonAssignment() {
        this.setTitle("子任务");
        this.makePanel();
    }

    public void makePanel() {

        nameLabel.setText(getName());
        nameLabel.setFont(new Font("楷体",Font.BOLD,15));
        detailText.setText(getDetail());
        detailText.setFont(new Font("楷体",Font.BOLD,15));
        dateChooser.reset(getYear(),getMonth(),getDay());
        panel1.setLayout(new GridLayout(4,2));
        panel1.add(title1);
        panel1.add(nameLabel);
        panel1.add(title4);
        panel1.add(dateChooser);
        finished.setFont(new Font("楷体",Font.BOLD,15));
        unfinished.setFont(new Font("楷体",Font.BOLD,15));
        panel1.add(finished);
        panel1.add(unfinished);
        title2.setFont(new Font("楷体",Font.BOLD,15));
        panel1.add(title2);

        save.setFont(new Font("楷体",Font.BOLD,15));
        back.setFont(new Font("楷体",Font.BOLD,15));
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
            unfinished.setSelected(true);
    }

    public void setFromFile(JSONObject a,LAssignment la){
        setName((String)a.get("name"));
        setDetail((String)a.get("detail"));
        setFinish((Boolean)a.get("finish"));
        setDeadLine((String)a.get("year"),(String)a.get("month"),(String)a.get("day"));
        setFatherAssignment(la);
        makePanel();
    }

    public void writeToFile(JSONObject a){
        a.put("name",getName());
        a.put("detail",getDetail());
        a.put("year",getYear());
        a.put("month",getMonth());
        a.put("day",getDay());
        a.put("finish",getFinish());
    }


    public void setFatherAssignment(LAssignment a){
        this.fatherAssignment=a;
    }
    public LAssignment getFatherAssignment(){
        return fatherAssignment;
    }

    public SonAssignment copyThis(){
        SonAssignment sa=new SonAssignment();
        sa.setName(this.getName());
        sa.setDeadLine(getYear(),getMonth(),getDay());
        sa.setDetail(this.getDetail());
        sa.setFatherAssignment(this.getFatherAssignment());
        sa.makePanel();
        return sa;
    }

    public void setDeadLine(String y,String m,String d){
        setDay(d);
        setMonth(m);
        setYear(y);
    }

    public static void main(String[] args) {
        TpAssignment t=new TpAssignment();
        t.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource()==save){
            super.actionPerformed(e);
            this.setName(nameLabel.getText());
            this.setDetail(detailText.getText());
            setYear(dateChooser.getYear());
            setMonth(dateChooser.getMonth());
            setDay(dateChooser.getDay());
            getFatherAssignment().deleteAssignment(this);
            getFatherAssignment().addAssignment(this);
            (this.getFatherAssignment()).changeNameList();
        }
        else if(e.getSource()==back)
        {
            dispose();
            (this.getFatherAssignment()).showThis();
        }
    }
}
