
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class ListFileOpen extends JFrame implements ActionListener {
    private File file;
    private MainWindow father;
    private int rVal;

    private JButton open=new JButton("打开");
    private JFileChooser jfc=new JFileChooser(new File(".\\AToDoList\\AssignmentListFile"));

    ListFileOpen(MainWindow al){
        this.father=al;
        this.setSize(new Dimension(500,500));
        this.add(jfc);
        jfc.addActionListener(this);
    }


    void showThis(){  rVal=jfc.showOpenDialog(ListFileOpen.this);}

    public File getFile(){return file;}

    public static void main(String[] args)
    {
        FileOpen f=new FileOpen(null);
        f.showThis();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(rVal==JFileChooser.APPROVE_OPTION)
        {
            file=jfc.getSelectedFile();
            father.importAssignmentList(file);
        }
    }
}
