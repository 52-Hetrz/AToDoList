import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AssignmentDialog extends JDialog implements ActionListener {
    private AssignmentList father;
    JButton temporary=new JButton("临时任务");
    JButton Long=new JButton("长期任务");
    JButton cycle=new JButton("周期任务");

    public AssignmentDialog(AssignmentList belong){
        this.father=belong;

        temporary.addActionListener(this);
        Long.addActionListener(this);
        cycle.addActionListener(this);
        this.setLayout(new GridLayout(3,1));
        this.add(temporary);
        this.add(Long);
        this.add(cycle);
        this.setSize(new Dimension(200,200));
    }

    public void action(Assignment a){
        a.setFather(this.father);
        //(a.getFather()).addAssignment(a);
        dispose();
        a.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==temporary)
        {
            TpAssignment tp=new TpAssignment();
            action(tp);
        }
        else if(e.getSource()==Long)
        {
            LAssignment l=new LAssignment();
            action(l);
        }
        else if(e.getSource()==cycle)
        {
            CyAssignment cy=new CyAssignment();
            action(cy);
        }
    }
}
