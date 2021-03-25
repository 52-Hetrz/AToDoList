
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
import java.io.IOException;
import java.util.ArrayList;

public class MainWindow extends JFrame implements ActionListener
{

    private ListFileOpen fileOpen=new ListFileOpen(this);
    private SearchDialog myDialog=new SearchDialog(this);
    ArrayList<AssignmentList> assignmentLists=new ArrayList<>();
    ArrayList<JButton> buttons=new ArrayList<>();
    String[] nameList=new String[20];
    private JPanel panel=new JPanel();
    private JPanel panel1=new JPanel();
    private JPanel panel2=new JPanel();
    private JLabel title=new JLabel("清单列表");
    private JButton open=new JButton("打开清单");
    private JButton in=new JButton("导入清单");
    private JButton out=new JButton("导出清单");
    private JButton add=new JButton("添加清单");
    private JButton delete=new JButton("删除清单");
    private JButton countSort=new JButton("按建立时间排序");
    private JButton typeSort=new JButton("按类型排序");
    private JButton search=new JButton("查找任务");
    private JButton close=new JButton("关闭");
    private JList<String> list=new JList<>(nameList);
    private JScrollPane scroll=new JScrollPane(list);

    private MainWindow() {
        makeFrame();
        makePanel();
    }

    private void makeFrame(){
        this.setTitle("提醒事项");
        this.setMinimumSize(new Dimension(400,500));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(panel);

        buttons.add(add);
        buttons.add(delete);
        buttons.add(countSort);
        buttons.add(typeSort);
        buttons.add(in);
        buttons.add(out);
        buttons.add(search);
        buttons.add(close);
        for(JButton jb:buttons)
        {
            jb.addActionListener(this);
            jb.setFont(new Font("楷体",Font.BOLD,15));
        }
        open.addActionListener(this);
        open.setFont(new Font("楷体",Font.BOLD,15));
    }

    public void makePanel() {
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        panel1.setLayout(new GridLayout(1,2));
        title.setFont(new Font("楷体",Font.BOLD,15));
        panel1.add(title);
        panel1.add(open);

        panel2.setLayout(new GridLayout(4,2));
        for(JButton jb:buttons)
            panel2.add(jb);

        panel.setLayout(new BorderLayout());
        panel.add(panel1,BorderLayout.NORTH);
        panel.add(scroll,BorderLayout.CENTER);
        panel.add(panel2,BorderLayout.SOUTH);
    }

    void showThis() {
        makePanel();
        this.setVisible(true);
    }

    void findAssignment(String s) {
        for(AssignmentList al:assignmentLists)
        {
            for(Assignment a:al.getAssignments())
            {
                if(a.getName().equals(s))
                    a.setVisible(true);
                if(a instanceof LAssignment)
                    for(SonAssignment sn:((LAssignment) a).getAssignments())
                    {
                        if(sn.getName().equals(s))
                            sn.setVisible(true);
                    }
            }
        }
    }              //查找任务

    void changeNameList() {
        int i=0;
        for(int j=0;j<20;j++)
            nameList[j]=null;
        for(AssignmentList a:assignmentLists)
        {
            nameList[i]=a.getName();
            i++;
        }
    }

    void addAssignmentList(AssignmentList al) {
       assignmentLists.add(al);
        this.changeNameList();
        makePanel();
    }

    void deleteAssignmentList(AssignmentList al) {
        assignmentLists.remove(al);
        changeNameList();
        makePanel();
    }

    private void sortByCount(){
        int middle=0;
        int i=0;
        String s="";
        int flag=0;
        for(int j=0;j<20;j++)
            nameList[j]=null;
        while(i<assignmentLists.size())
        {
            middle=0;
            for(AssignmentList a:assignmentLists)
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
        String[] ss={"学习","生活","工作","朋友","家人"};
        for(String str:ss)
        {
            for(AssignmentList al:getAssignmentLists())
            {
                if(al.getThisType().equals(str))
                {
                    nameList[i++]=al.getName();
                }
            }
        }
        dispose();
        this.setVisible(true);
    }

    void importAssignmentList(File file){
        JSONParser jsonParser=new JSONParser();
        AssignmentList List=new AssignmentList();
        try(FileReader reader =new FileReader(file))
        {
            Object obj=jsonParser.parse(reader);
            JSONObject assignmentList=(JSONObject) obj;
            List.setName((String)assignmentList.get("name"));
            List.setThisType((String)assignmentList.get("type"));
            List.setFather(this);
            JSONArray ja=(JSONArray)assignmentList.get("assignments");
            for (Object o : ja) {
                JSONObject jot = (JSONObject) o;
                if (jot.get("toString").equals("TpAssignment")) {
                    TpAssignment ta = new TpAssignment();
                    ta.setFromFile(jot, List);
                    List.addAssignment(ta);
                } else if (jot.get("toString").equals("CyAssignment")) {
                    CyAssignment ca = new CyAssignment();
                    ca.setFromFile(jot, List);
                    List.addAssignment(ca);
                } else if (jot.get("toString").equals("LAssignment")) {
                    LAssignment la = new LAssignment();
                    la.setFromFile(jot, List);
                    List.addAssignment(la);
                }
            }
            this.addAssignmentList(List);
        } catch(IOException | ParseException e){
            e.printStackTrace();
        }
        dispose();
        setVisible(true);
    }

    ArrayList<AssignmentList> getAssignmentLists(){ return this.assignmentLists;}

    public static void main(String[] args) {
        MainWindow m=new MainWindow();
        m.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==add)
        {
            AssignmentList al=new AssignmentList();
            al.setFather(this);
            //this.addAssignmentList(al);
            dispose();
            al.setVisible(true);
        }
        else if(e.getSource()==open)
        {
            String s=list.getSelectedValue();
            for(AssignmentList al:assignmentLists)
            {
                if(al.getName().equals(s))
                {
                    dispose();
                    al.setVisible(true);
                }
            }
        }
        else if(e.getSource()==delete)
        {
            String s=list.getSelectedValue();
            for(AssignmentList al:assignmentLists)
            {
                if(al.getName().equals(s))
                {
                    deleteAssignmentList(al);
                    dispose();
                    this.setVisible(true);
                    break;
                }
            }
        }
        else if(e.getSource()==search)
        {
            //dispose();
            myDialog.setVisible(true);
        }
        else if(e.getSource()==close)
            System.exit(0);
        else if(e.getSource()==typeSort)
            sortByType();
        else if(e.getSource()==countSort)
            sortByCount();
        else if(e.getSource()==out)
        {
            String s=list.getSelectedValue();
            for(AssignmentList a:assignmentLists)
            {
                if(a.getName().equals(s))
                    a.writeToAssignmentListFile();
            }
        }
        else if(e.getSource()==in)
        {
            this.fileOpen.showThis();
        }
    }
}
