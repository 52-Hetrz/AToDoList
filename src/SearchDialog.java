
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SearchDialog extends JFrame implements ActionListener {

    private MainWindow father;
    private JButton search=new JButton("查找");
    private JButton back=new JButton("返回");
    private JTextField name=new JTextField();
    private JPanel panel1=new JPanel();
    private JPanel panel2=new JPanel();

    public SearchDialog(MainWindow m)
    {
        this.setMinimumSize(new Dimension(100,100));
        this.father=m;
        this.setTitle("查找任务");

        name.setFont(new Font("Serif",Font.BOLD,15));
        panel1.setLayout(new GridLayout(1,1));
        panel1.add(name);

        panel2.setLayout(new GridLayout(1,2));
        search.addActionListener(this);
        search.setFont(new Font("Serif",Font.BOLD,15));
        back.addActionListener(this);
        back.setFont(new Font("Serif",Font.BOLD,15));
        panel2.add(search);
        panel2.add(back);

        this.setLayout(new GridLayout(2,1));
        this.add(panel1);
        this.add(panel2);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==search)
        {
            String n=name.getText();
            name.setText("");
            dispose();
            father.findAssignment(n);
        }
        else if(e.getSource()==back)
        {
            dispose();
            this.father.setVisible(true);
        }
    }
}
