
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class FileOpen extends JFrame implements ActionListener {
    File file;
    AssignmentList father;
    int rVal;

    private JButton open=new JButton("打开");
    private JFileChooser jfc=new JFileChooser(new File(".\\AToDoList\\AssignmentFile"));

    FileOpen(AssignmentList al){
        this.father=al;
        this.setSize(new Dimension(500,500));
        this.add(jfc);
        jfc.addActionListener(this);
    }


    void showThis(){    rVal=jfc.showOpenDialog(FileOpen.this);}

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
               father.importAssignment(file);
            }
    }
}
