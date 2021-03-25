
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class TpAssignment extends Assignment {
    //短期任务

    private String deadLine;
    private JLabel title4=new JLabel("截止日期：");
    private JTextField deadlineLabel=new JTextField();

    TpAssignment() {
        this.setTitle("短期任务");
        this.makePanel();
    }

    public void makePanel(){

        //deadlineLabel.setText(getDeadLine());

        panel1.setLayout(new GridLayout(4,2));
        nameLabel.setText(getName());
        nameLabel.setFont(new Font("楷体",Font.BOLD,15));
        detailText.setText(getDetail());
        detailText.setFont(new Font("楷体",Font.BOLD,15));
        dateChooser.reset(getYear(),getMonth(),getDay());
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

    public TpAssignment copyThis(){
        TpAssignment tp=new TpAssignment();
        //tp.setDeadLine(this.getDeadLine());
        tp.setDeadLine(getYear(),getMonth(),getDay());
        tp.setName(this.getName());
        tp.setDetail(this.getDetail());
        tp.setFather(this.getFather());
        tp.setFinish(getFinish());
        tp.makePanel();

        return tp;
    }

    public void writeToAssignmentFile(){
       JSONObject assignmentDetails=new JSONObject();
       assignmentDetails.put("name",getName());
       assignmentDetails.put("detail",getDetail());
       assignmentDetails.put("toString",toString());
       assignmentDetails.put("finish",getFinish());
       assignmentDetails.put("year",getYear());
       assignmentDetails.put("month",getMonth());
       assignmentDetails.put("day",getDay());
       File file=new File(".\\AToDoList\\AssignmentFile\\临时任务——"+getName()+".json");
       if(!file.exists())
       {
           try{
               file.createNewFile();
       } catch (IOException e) {
            e.printStackTrace();
           }
       }
       try(FileWriter writer=new FileWriter(".\\AToDoList\\AssignmentFile\\临时任务——"+getName()+".json"))
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
        //assignmentDetails.put("deadLine", getDeadLine());
        assignmentDetails.put("year",getYear());
        assignmentDetails.put("month",getMonth());
        assignmentDetails.put("day",getDay());
        assignmentDetails.put("detail",getDetail());
        assignmentDetails.put("toString",toString());
        assignmentDetails.put("finish",getFinish());
        ja.add(assignmentDetails);
    }

    public void setFromFile(JSONObject a,AssignmentList al){
        setName((String)a.get("name"));
        setDetail((String)a.get("detail"));
        setFinish((Boolean)a.get("finish"));
        setDeadLine((String) a.get("year"),(String) a.get("month"),(String) a.get("day"));
        setFather(al);
        makePanel();
    }

    public void setDeadLine(String y,String m,String d){
        setDay(d);
        setMonth(m);
        setYear(y);
    }

    public String toString(){return "TpAssignment";}

    public static void main(String[] args) {
        TpAssignment t=new TpAssignment();
        t.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==save){
            super.actionPerformed(e);
            this.setName(nameLabel.getText());
            this.setDetail(detailText.getText());
            //this.setDeadLine(deadlineLabel.getText());
            setYear(dateChooser.getYear());
            setMonth(dateChooser.getMonth());
            setDay(dateChooser.getDay());
            getFather().deleteAssignment(this);
            getFather().addAssignment(this);
            (this.getFather()).changeNameList();
            //writeToAssignmentFile();
        }
        else if(e.getSource()==back)
        {
            dispose();
            (this.getFather()).showThis();
            //readFromFile("AToDoList/AssignmentFile/Q.json");
        }
    }
}
