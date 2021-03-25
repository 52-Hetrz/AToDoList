
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CyAssignment extends Assignment
{
    //周期任务

    String[] cycles={"每小时","每天","每周","每两周","每月","每3个月","每6个月","每年"};
    //private String actDate;          //执行日期
    private String time;             //循环次数
    private String cycle;            //循环周期
    private JLabel title4=new JLabel("执行日期：");
    //private JTextField actDateLabel=new JTextField();
    private JLabel title5=new JLabel("循环次数：");
    private JTextField timeLabel=new JTextField();
    private JLabel title6=new JLabel("循环周期");
    private JComboBox<String> cycleBox =new JComboBox<>(cycles);

    CyAssignment() {
        this.setTitle("周期任务");
        makePanel();
    }

    public CyAssignment copyThis() {
        CyAssignment ca=new CyAssignment();
        ca.setName(this.getName());
        ca.setDetail(this.getDetail());
        ca.setCycle(this.getCycle());
        ca.setTime(this.getTime());
        ca.setActDate(getYear(),getMonth(),getDay());
        ca.setFather(this.getFather());
        ca.makePanel();
        return ca;
    }

    public void makePanel(){

        panel1.setLayout(new GridLayout(6,2));
        nameLabel.setText(getName());
        nameLabel.setFont(new Font("楷体",Font.BOLD,15));
        dateChooser.reset(getYear(),getMonth(),getDay());
        timeLabel.setText(getTime());
        timeLabel.setFont(new Font("楷体",Font.BOLD,15));
        cycleBox.setSelectedItem(getCycle());
        detailText.setText(getDetail());
        detailText.setFont(new Font("楷体",Font.BOLD,15));

        panel1.add(title1);
        panel1.add(nameLabel);
        panel1.add(title4);
        panel1.add(dateChooser);
        panel1.add(title5);
        panel1.add(timeLabel);
        panel1.add(title6);
        cycleBox.setFont(new Font("楷体",Font.BOLD,15));
        panel1.add(cycleBox);

        finished.setFont(new Font("楷体",Font.BOLD,15));
        unfinished.setFont(new Font("楷体",Font.BOLD,15));
        panel1.add(finished);
        panel1.add(unfinished);
        title2.setFont(new Font("Serif",Font.BOLD,15));
        panel1.add(title2);

        save.setFont(new Font("楷体",Font.BOLD,15));
        back.setFont(new Font("楷体",Font.BOLD,15));
        panel2.setLayout(new GridLayout(1,2));
        panel2.add(save);
        panel2.add(back);

        panel.setLayout(new BorderLayout());
        panel.add(panel1,BorderLayout.NORTH);
        panel.add(detailText,BorderLayout.CENTER);
        panel.add(panel2,BorderLayout.SOUTH);
        if(getFinish())
            finished.setSelected(true);
        else
            unfinished.setSelected(true);
    }

    public void setFromFile(JSONObject a, AssignmentList al){
        setName((String)a.get("name"));
        setDetail((String)a.get("detail"));
        setActDate((String)a.get("year"),(String)a.get("month"),(String)a.get("day"));
        setTime((String)a.get("time"));
        setCycle((String)a.get("cycle"));
        setFinish((Boolean)a.get("finish"));
        setFather(al);
        makePanel();
    }

    public void writeToAssignmentFile(){
        JSONObject assignmentDetails=new JSONObject();
        assignmentDetails.put("name",getName());
        assignmentDetails.put("detail",getDetail());
        assignmentDetails.put("toString",toString());
        assignmentDetails.put("finish",getFinish());
        assignmentDetails.put("time",getTime());
        assignmentDetails.put("cycle",getCycle());
        assignmentDetails.put("year",getYear());
        assignmentDetails.put("month",getMonth());
        assignmentDetails.put("day",getDay());
        File file=new File(".\\AToDoList\\AssignmentFile\\周期任务——"+getName()+".json");
        if(!file.exists())
        {
            try{
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try(FileWriter writer=new FileWriter(".\\AToDoList\\AssignmentFile\\周期任务——"+getName()+".json"))
        {
            writer.write(assignmentDetails.toJSONString());
            writer.flush();
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void writeToAssignmentListFile(JSONArray ja){
        JSONObject assignmentDetails=new JSONObject();
        assignmentDetails.put("name",getName());
        assignmentDetails.put("detail",getDetail());
        assignmentDetails.put("toString",toString());
        assignmentDetails.put("finish",getFinish());
        assignmentDetails.put("time",getTime());
        assignmentDetails.put("cycle",getCycle());
        assignmentDetails.put("year",getYear());
        assignmentDetails.put("month",getMonth());
        assignmentDetails.put("day",getDay());
        ja.add(assignmentDetails);
    }

    private void setActDate(String y, String m, String d){
        setDay(d);
        setMonth(m);
        setYear(y);
    }
    private void setTime(String time){this.time=time;}
    private String getTime(){return time;}
    private void setCycle(String cycle){this.cycle=cycle;}
    private String getCycle(){return cycle;}
    public String toString(){return "CyAssignment";}

    public static void main(String[] args){
        new CyAssignment().setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==save)
        {
            super.actionPerformed(e);
            this.setName(nameLabel.getText());
            this.setDetail(detailText.getText());
            this.setActDate(dateChooser.getYear(),dateChooser.getMonth(),dateChooser.getDay());
            this.setTime(timeLabel.getText());
            this.setCycle((String) cycleBox.getSelectedItem());
            getFather().deleteAssignment(this);
            getFather().addAssignment(this);
            (this.getFather()).changeNameList();
        }
        else if(e.getSource()==back)
        {
            dispose();
            (this.getFather()).showThis();
        }
    }
}
