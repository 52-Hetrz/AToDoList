
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class AssignmentList extends JFrame implements ActionListener
{

    private static int count=0;
    private int thisCount;
    private FileOpen fileOpen=new FileOpen(this);
    private MCDialog moveDialog=new MCDialog(this);
    private MainWindow father;
    private String name;
    private String type;
    private AssignmentDialog dialog=new AssignmentDialog(this);

    private String[] nameList=new String[20];
    private ArrayList<Assignment> assignments=new ArrayList<>();
    private ArrayList<JButton> buttons=new ArrayList<>();
    private String[] types={"学习","生活","工作","家人","朋友"};
    private JList<String> list=new JList<>(nameList);
    private JScrollPane scroll = new JScrollPane(list);
    private JPanel panel=new JPanel();
    private JPanel panel1=new JPanel();
    private JPanel panel2=new JPanel();
    private JLabel title1=new JLabel("名称：");
    private JTextField nameLabel=new JTextField();
    private JLabel title2=new JLabel("类型：");
    private JComboBox<String> typeBox=new JComboBox<>(types);
    private JTextField typeLabel=new JTextField();
    private JLabel title3=new JLabel("任务列表：");
    private JButton countSort=new JButton("按建立顺序排序");
    private JButton typeSort=new JButton("按类型排序");
    private JButton wordSort =new JButton("按截止日期排序");
    private JButton add=new JButton("添加任务");
    private JButton delete=new JButton("删除任务");
    private JButton save=new JButton("保存");
    private JButton back=new JButton("返回");
    private JButton open=new JButton("打开任务");
    private JButton copy=new JButton("复制任务");
    private JButton move=new JButton("移动任务");
    private JButton in=new JButton("导入任务");
    private JButton out=new JButton("导出任务");

    AssignmentList() {
        count++;
        thisCount=count;
        this.setTitle("任务清单");
        makePanel();
        makeFrame();
        changeNameList();
    }

    void addAssignment(Assignment a) {
        assignments.add(a);
        changeNameList();
        makePanel();
    }

    void deleteAssignment(Assignment t) {
        assignments.remove(t);
        changeNameList();
        makePanel();
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

    void setFather(MainWindow a){
        this.father=a;
    }

    public void makePanel() {
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        nameLabel.setText(getName());
        //typeLabel.setText(getThisType());
        panel1.setLayout(new GridLayout(3,2));
        title1.setFont(new Font("楷体",Font.BOLD,15));
        panel1.add(title1);
        nameLabel.setFont(new Font("楷体",Font.BOLD,15));
        panel1.add(nameLabel);
        title2.setFont(new Font("楷体",Font.BOLD,15));
        panel1.add(title2);
        //typeLabel.setFont(new Font("Serif",Font.BOLD,15));
        typeBox.setSelectedItem(getThisType());
        panel1.add(typeBox);
        title3.setFont(new Font("楷体",Font.BOLD,15));
        panel1.add(title3);
        //open.setFont(new Font("Serif",Font.BOLD,15));
        //panel1.add(open);

        panel2.setLayout(new GridLayout(6,2));
        buttons.add(open);
        buttons.add(countSort);
        buttons.add(typeSort);
        buttons.add(wordSort);
        buttons.add(add);
        buttons.add(delete);
        buttons.add(copy);
        buttons.add(move);
        buttons.add(in);
        buttons.add(out);
        buttons.add(save);
        buttons.add(back);
        for(JButton b:buttons)
        {
            b.setFont(new Font("楷体",Font.BOLD,15));
            //b.addActionListener(this);
            panel2.add(b);
        }

        panel.setLayout(new BorderLayout());
        panel.add(panel1,BorderLayout.NORTH);
        panel.add(scroll,BorderLayout.CENTER);
        panel.add(panel2,BorderLayout.SOUTH);
    }

    private void makeFrame() {
        this.setMinimumSize(new Dimension(400,500));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(panel);
        countSort.addActionListener(this);
        typeSort.addActionListener(this);
        wordSort.addActionListener(this);
        open.addActionListener(this);
        add.addActionListener(this);
        delete.addActionListener(this);
        save.addActionListener(this);
        back.addActionListener(this);
        copy.addActionListener(this);
        move.addActionListener(this);
        in.addActionListener(this);
        out.addActionListener(this);

    }

    void showThis(){
        this.makePanel();
        this.setVisible(true);
    }

    private void sortByCount(){
        int middle=0;
        int i=0;
        String s="";
        int flag=0;
        for(int j=0;j<20;j++)
            nameList[j]=null;
        while(i<assignments.size())
        {
            middle=0;
            for(Assignment a:assignments)
            {
                if(a.getCount()>middle)
                {
                    flag=0;
                    for(String str:nameList)
                    {
                        if(a.getName().equals(str))
                        {
                            flag=1;
                            break;
                        }
                    }
                    if(flag==0)
                    {
                        middle=a.getCount();
                        s=a.getName();
                    }
                }
            }
            nameList[i++]=s;
        }
        dispose();
        this.setVisible(true);
    }

    private void sortByType(){
        int i=0;
        String s="";
        for(int j=0;j<20;j++)
            nameList[j]=null;
        String[] ss={"TpAssignment","CyAssignment","LAssignment"};
        for(String str:ss)
        {
            for(Assignment al:getAssignments())
            {
                if(al.toString().equals(str))
                {
                    nameList[i++]=al.getName();
                }
            }
        }
        dispose();
        this.setVisible(true);
    }

   private void sortByDEADLINE(){
       int i=0;
       String s="";
       int flag=0;
       int middle=100000000;
       for(int j=0;j<20;j++)
           nameList[j]=null;
       while(i<assignments.size())
       {
           middle=100000000;
           for(Assignment a:assignments)
           {
               if(a.getDEADLINE()<middle)
               {
                   flag=0;
                   for(String str:nameList)
                   {
                       if(a.getName().equals(str))
                       {
                           flag=1;
                           break;
                       }
                   }
                   if(flag==0)
                   {
                       middle=a.getDEADLINE();
                       s=a.getName();
                   }
               }
           }
           nameList[i++]=s;
       }
       dispose();
       this.setVisible(true);
   }

    void importAssignment(File f){
        JSONParser jsonParser=new JSONParser();
        try(FileReader reader =new FileReader(f))
        {
            Object obj=jsonParser.parse(reader);
            JSONObject assignment=(JSONObject) obj;
            String s=(String)assignment.get("toString");
            if(s.equals("TpAssignment"))
            {
                TpAssignment ta=new TpAssignment();
                ta.setFromFile(assignment,this);
                this.addAssignment(ta);
            }
            else if(s.equals("CyAssignment"))
            {
                CyAssignment ca=new CyAssignment();
                ca.setFromFile(assignment,this);
                this.addAssignment(ca);
            }
            else if(s.equals("LAssignment"))
            {
                LAssignment la=new LAssignment();
                la.setFromFile(assignment,this);
                this.addAssignment(la);
            }
        } catch(IOException | ParseException e){
            e.printStackTrace();
        }
        dispose();
        setVisible(true);
    }

    void writeToAssignmentListFile(){
        File file=new File(".\\AToDoList\\AssignmentListFile\\"+getName()+".json");
        if(!file.exists())
        {
            try{
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        JSONObject assignmentDetails=new JSONObject();
        assignmentDetails.put("name",getName());
        assignmentDetails.put("type",getThisType());
        JSONArray assignmentList=new JSONArray();
        for(Assignment a:assignments)
        {
            a.writeToAssignmentListFile(assignmentList);
        }
        assignmentDetails.put("assignments",assignmentList);
        try(FileWriter writer=new FileWriter(".\\AToDoList\\AssignmentListFile\\"+getName()+".json"))
        {
            writer.write(assignmentDetails.toJSONString());
            writer.flush();
        }catch(Exception e)
        {
            e.printStackTrace();
        }

    }

    public void setName(String name){this.name=name;}              //设置名称
    public String getName(){return name;}
    void setThisType(String type){this.type=type;}              //设置类型
    String getThisType(){return type;}
    public JPanel getPanel(){return panel;}
    MainWindow getFather(){return this.father;}
    ArrayList<Assignment> getAssignments(){return this.assignments;}
    private MCDialog getMCDialog(){return moveDialog;}
    int getCount(){return thisCount;}

    public static void main(String[] args){
       AssignmentList a=new AssignmentList();
       a.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==add)
        {
            dispose();
            (this.dialog).setVisible(true);
        }
        else if(e.getSource()==save)
        {
            setName(nameLabel.getText());
            //setType(typeLabel.getText());
            setThisType((String)typeBox.getSelectedItem());
            getFather().deleteAssignmentList(this);
            getFather().addAssignmentList(this);
            getFather().changeNameList();
        }
        else if(e.getSource()==open)
        {
            String s=list.getSelectedValue();
            for(Assignment sa:assignments)
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
            for(Assignment sa:assignments)
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
        else if(e.getSource()==back)
        {
            dispose();
            getFather().showThis();
        }
        else if(e.getSource()==move)
        {
            getMCDialog().setFlag(MC.MOVE);
            String s=list.getSelectedValue();
            for(Assignment a:assignments)
            {
                if(a.getName().equals(s))
                    getMCDialog().setMCAssignment(a);
            }
            getMCDialog().showThis();
        }
        else if(e.getSource()==copy)
        {
            getMCDialog().setFlag(MC.COPY);
            String s=list.getSelectedValue();
            for(Assignment a:assignments)
            {
                if(a.getName().equals(s))
                    getMCDialog().setMCAssignment(a);
            }
            getMCDialog().showThis();
        }
        else if(e.getSource()==countSort)
            sortByCount();
        else if(e.getSource()==typeSort)
            sortByType();
        else if(e.getSource()==wordSort)
            sortByDEADLINE();
        else if(e.getSource()==in)
            this.fileOpen.showThis();
        else if(e.getSource()==out)
        {
            String s=list.getSelectedValue();
            for(Assignment a:assignments)
            {
                if(a.getName().equals(s))
                    a.writeToAssignmentFile();
            }
        }
    }
}
