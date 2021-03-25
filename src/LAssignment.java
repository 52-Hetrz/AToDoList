
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class LAssignment extends Assignment
{
    ///长期任务

    //private String deadLine;
    private JLabel thisTitle=new JLabel("备注：");
    private JLabel title4=new JLabel("截止日期：");
    private JLabel title5=new JLabel("子目录：");
    private JTextField thisDetail=new JTextField();
    private JTextField deadlineLabel=new JTextField();
    private JButton add=new JButton("添加子任务");
    private JButton open=new JButton("打开任务");
    private JButton delete=new JButton("删除任务");

    private ArrayList<SonAssignment> assignments=new ArrayList<>();
    private String[] nameList=new String[20];
    private JList<String> list=new JList<>(nameList);
    private JScrollPane scroll = new JScrollPane(list);

    LAssignment() {
        add.addActionListener(this);
        delete.addActionListener(this);
        this.setTitle("长期任务");
        this.makePanel();
    }

    void addAssignment(SonAssignment t) {
        assignments.add(t);
        changeNameList();
    }

    void deleteAssignment(SonAssignment t) {
        assignments.remove(t);
        changeNameList();
    }

    public void makePanel() {
        nameLabel.setText(getName());
        nameLabel.setFont(new Font("楷体",Font.BOLD,15));
        thisDetail.setText(getDetail());
        thisDetail.setFont(new Font("楷体",Font.BOLD,15));
        dateChooser.reset(getYear(),getMonth(),getDay());
        title1.setFont(new Font("楷体",Font.BOLD,15));
        title4.setFont(new Font("楷体",Font.BOLD,15));
        title5.setFont(new Font("楷体",Font.BOLD,15));

        panel1.setLayout(new GridLayout(5,2));
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        panel1.add(title1);
        panel1.add(nameLabel);
        panel1.add(title4);
        panel1.add(dateChooser);
        panel1.add(thisTitle);
        panel1.add(thisDetail);
        finished.setFont(new Font("楷体",Font.BOLD,15));
        unfinished.setFont(new Font("楷体",Font.BOLD,15));
        panel1.add(finished);
        panel1.add(unfinished);
        thisTitle.setFont(new Font("楷体",Font.BOLD,15));
        //panel1.add(thisTitle);
        //thisDetail.setText(getDetail());
        thisDetail.setFont(new Font("楷体",Font.BOLD,15));
        //panel1.add(thisDetail);
        title5.setFont(new Font("楷体",Font.BOLD,15));
        panel1.add(title5);
        open.setFont(new Font("楷体",Font.BOLD,15));
        open.addActionListener(this);
        panel1.add(open);

        panel2.setLayout(new GridLayout(2,2));
        add.setFont(new Font("楷体",Font.BOLD,15));
        panel2.add(add);
        delete.setFont(new Font("楷体",Font.BOLD,15));
        panel2.add(delete);
        save.setFont(new Font("楷体",Font.BOLD,15));
        panel2.add(save);
        back.setFont(new Font("Serif",Font.BOLD,15));
        panel2.add(back);

        panel.setLayout(new BorderLayout());
        panel.add(panel1,BorderLayout.NORTH);
        panel.add(scroll,BorderLayout.CENTER);
        panel.add(panel2,BorderLayout.SOUTH);

        if(getFinish())
            finished.setSelected(true);
        else
            unfinished.setSelected(true);
    }

    void changeNameList() {
        int i=0;
        for(int j=0;j<20;j++)
            nameList[j]=null;
        for(Assignment a:assignments)
        {
            nameList[i]=a.getName();
            i++;
        }
    }

    void showThis() {
        this.makePanel();
        this.setVisible(true);
    }

    public LAssignment copyThis(){
        LAssignment la=new LAssignment();
        la.setName(this.getName());
        la.setDetail(this.getDetail());
        la.setDeadLine(getYear(),getMonth(),getDay());
        for(SonAssignment sa:assignments)
        {
            SonAssignment s=sa.copyThis();
            s.setFatherAssignment(la);
            la.addAssignment(s);
        }
        la.changeNameList();
        la.makePanel();
        return la;
    }

    public void setFromFile(JSONObject a, AssignmentList al){
        setName((String)a.get("name"));
        setDetail((String)a.get("detail"));
        setDeadLine((String)a.get("year"),(String)a.get("month"),(String)a.get("day"));
        setFinish((Boolean)a.get("finish"));
        JSONArray sonAssignments=(JSONArray) a.get("sonAssignments");
        for (Object assignment : sonAssignments) {
            JSONObject sonAssignment = (JSONObject) assignment;
            SonAssignment sAssignment = new SonAssignment();
            sAssignment.setFromFile(sonAssignment, this);
            this.addAssignment(sAssignment);
        }
        setFather(al);
        makePanel();
    }

    public void writeToAssignmentFile(){
        JSONObject assignmentDetails=new JSONObject();
        assignmentDetails.put("name",getName());
        assignmentDetails.put("year",getYear());
        assignmentDetails.put("month",getMonth());
        assignmentDetails.put("day",getDay());
        assignmentDetails.put("detail",getDetail());
        assignmentDetails.put("toString",toString());
        assignmentDetails.put("finish",getFinish());
        JSONArray sonAssignments=new JSONArray();
        for(SonAssignment sn:assignments){
            JSONObject details=new JSONObject();
            sn.writeToFile(details);
            //JSONObject sonAssignment=new JSONObject();
            //sonAssignment.put("sonAssignment",details);
            sonAssignments.add(details);
        }
        assignmentDetails.put("sonAssignments",sonAssignments);
        File file=new File(".\\AToDoList\\AssignmentFile\\长期任务——"+getName()+".json");
        if(!file.exists())
        {
            try{
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try(FileWriter writer=new FileWriter(".\\AToDoList\\AssignmentFile\\长期任务——"+getName()+".json"))
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
        assignmentDetails.put("year",getYear());
        assignmentDetails.put("month",getMonth());
        assignmentDetails.put("day",getDay());
        assignmentDetails.put("detail",getDetail());
        assignmentDetails.put("toString",toString());
        assignmentDetails.put("finish",getFinish());
        JSONArray sonAssignments=new JSONArray();
        for(SonAssignment sa:assignments)
        {
            JSONObject jot=new JSONObject();
            sa.writeToFile(jot);
            sonAssignments.add(jot);
        }
        assignmentDetails.put("sonAssignments",sonAssignments);
        ja.add(assignmentDetails);
    }

    private void setDeadLine(String y, String m, String d){
        setDay(d);
        setMonth(m);
        setYear(y);
    }
    ArrayList<SonAssignment> getAssignments(){return assignments;}

    @Override
    public java.lang.String toString() {
        return "LAssignment";
    }

    public static void main(String[] args){
        new LAssignment().setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==save)
        {

            super.actionPerformed(e);
            this.setName(nameLabel.getText());
            this.setDetail(thisDetail.getText());
            setYear(dateChooser.getYear());
            setMonth(dateChooser.getMonth());
            setDay(dateChooser.getDay());
            getFather().deleteAssignment(this);
            getFather().addAssignment(this);
            (this.getFather()).changeNameList();
        }
        else if(e.getSource()==back)
        {
            dispose();
            (this.getFather()).showThis();
        }
        else if(e.getSource()==add)
        {
            SonAssignment s=new SonAssignment();
            s.setFatherAssignment(this);
            //this.addAssignment(s);
            dispose();
            s.setVisible(true);
        }
        else if(e.getSource()==open)
        {
            String s=list.getSelectedValue();
            for(SonAssignment sa:assignments)
            {
                if(sa.getName().equals(s))
                {
                    //dispose();
                    sa.setVisible(true);
                    break;
                }
            }
        }
        else if(e.getSource()==delete)
        {
            String s=list.getSelectedValue();
            for(SonAssignment sa:assignments)
            {
                if(sa.getName().equals(s))
                {
                    dispose();
                    deleteAssignment(sa);
                    this.setVisible(true);
                    break;
                }
            }
        }
    }
}
