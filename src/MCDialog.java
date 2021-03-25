
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MCDialog extends JDialog implements ActionListener {

    private MC flag;
    private AssignmentList father;
    private Assignment work;
    String[] nameList=new String[20];
    private JList<String> list=new JList<>(nameList);
    private JButton sure=new JButton("确认");
    private JButton cancel=new JButton("取消");
    private JLabel title=new JLabel("移动任务到：");
    private JPanel panel2=new JPanel();
    private JPanel panel1=new JPanel();

    public MCDialog(AssignmentList al) {
        this.father=al;

        this.setSize(new Dimension(300,300));

        sure.setFont(new Font("Serif",Font.BOLD,15));
        cancel.setFont(new Font("Serif",Font.BOLD,15));
        sure.addActionListener(this);
        cancel.addActionListener(this);
    }

    public void makePanel() {
        makeList();

        panel1.setLayout(new GridLayout(1,2));
        title.setFont(new Font("Serif",Font.BOLD,15));
        panel1.add(title);

        panel2.setLayout(new GridLayout(1,2));
        panel2.add(sure);
        panel2.add(cancel);

        this.setLayout(new BorderLayout());
        this.add(panel1,BorderLayout.NORTH);
        this.add(list,BorderLayout.CENTER);
        this.add(panel2,BorderLayout.SOUTH);
    }

    public void makeList()
    {
        int i=0;
        for(int j=0;j<20;j++)
            nameList[j]=null;
        for(AssignmentList a:getFather().getFather().getAssignmentLists())
        {
            if(!a.getName().equals(getFather().getName()))
            {
                nameList[i]=a.getName();
                i++;
            }
        }
    }

    public void showThis() {
        makePanel();
        if(flag==MC.COPY)
            this.setTitle("复制任务到：");
        else if(flag==MC.MOVE)
            this.setTitle("移动任务到：");
        this.setVisible(true);
    }

    public void setFlag(MC flag){
        this.flag=flag;
    } //如果flag是1 代表移动；如果flag是2，代表

    public AssignmentList getFather(){return father;}

    public void setMCAssignment(Assignment a) {
        this.work=a;
    }

    public Assignment getMCAssignment(){return this.work;}

    public void sureMove()
    {
        String s=list.getSelectedValue();
        for(AssignmentList al:(getFather().getFather()).getAssignmentLists())
        {
            if(al.getName().equals(s))
            {
                System.out.print(s);
                work.setFather(al);
                al.addAssignment(work);
                getFather().deleteAssignment(work);
                dispose();
            }
        }
    }

    public void sureCopy(){
        String s=list.getSelectedValue();
        AssignmentList aList = null;
        for(AssignmentList al:(getFather().getFather()).getAssignmentLists())
        {
            if(al.getName().equals(s))
            {
                aList=al;
                break;
            }
        }
        Assignment a=getMCAssignment();
        if(a instanceof TpAssignment)
        {
            TpAssignment tp=((TpAssignment) a).copyThis();
            tp.setFather(aList);
            assert aList != null;
            aList.addAssignment(tp);
            dispose();
        }
        else if(a instanceof CyAssignment)
        {
            CyAssignment ca=((CyAssignment) a).copyThis();
            ca.setFather(aList);
            assert aList != null;
            aList.addAssignment(ca);
            dispose();
        }
        else if(a instanceof LAssignment)
        {
            LAssignment la=((LAssignment) a).copyThis();
            la.setFather(aList);
            assert aList != null;
            aList.addAssignment(la);
            dispose();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==sure)
        {
           if(flag==MC.MOVE)
               sureMove();
           else if(flag==MC.COPY)
               sureCopy();
        }
        else if(e.getSource()==cancel)
        {
            dispose();
        }
    }
}
