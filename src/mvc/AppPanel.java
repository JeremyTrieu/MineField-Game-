package mvc;

import tools.Utilities;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class AppPanel extends JPanel implements ActionListener, PropertyChangeListener {
    private View view;
    private Model model;
    private JFrame frame;
    private AppFactory factory;
    protected JPanel controlPanel;
    public static int FRAME_WIDTH = 500;
    public static int FRAME_HEIGHT = 300;

    public AppPanel(AppFactory f){
        factory = f;
        model = factory.makeModel();
        view = factory.makeView(model);
        controlPanel = new JPanel();

        setLayout((new GridLayout(1, 2)));
        add(controlPanel);
        add(view);

        controlPanel.setBackground(Color.PINK);
        view.setBackground(Color.GRAY);
        frame = new JFrame();
        Container cp = frame.getContentPane();
        cp.add(this);
        frame.setJMenuBar(createMenuBar());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle(factory.getTitle());
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);

    }
    protected JMenuBar createMenuBar() {
        JMenuBar result = new JMenuBar();
        JMenu fileMenu =
                Utilities.makeMenu("File", new String[] {"New", "Save", "Save as", "Open", "Quit"}, this);
        result.add(fileMenu);
        JMenu editMenu =
                Utilities.makeMenu("Edit", factory.getEditCommands(), this);
        result.add(editMenu);
        JMenu helpMenu =
                Utilities.makeMenu("Help", new String[] {"About", "Help"}, this);
        result.add(helpMenu);
        return result;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmmd= e.getActionCommand();
        try {
            if (cmmd == "New") {
                Model m = factory.makeModel();
                model = m;
                view.setModel(m);

            } else if (cmmd == "Save") {
                if(model.getFileName() == null) {
                    String fName = Utilities.getFileName(null, false);
                    if(!fName.isEmpty()) {
                        ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(fName));
                        os.writeObject(model);
                        os.close();
                        model.setFileName(fName);
                        model.saved();
                    }
                }else{
                    ObjectOutputStream os = new ObjectOutputStream((new FileOutputStream(model.getFileName())));
                    os.writeObject(model);
                    os.close();
                    model.saved();
                }

            } else if (cmmd == "Save as") {
                String fName = Utilities.getFileName(null, false);
                if(!fName.isEmpty()) {
                    ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(fName));
                    os.writeObject(model);
                    os.close();
                    model.setFileName(fName);
                    model.saved();
                }

            } else if (cmmd == "Open") {
                String fName = Utilities.getFileName(null, false);
                ObjectInputStream is = new ObjectInputStream((new FileInputStream((fName))));
                model = (Model)is.readObject();
                view.setModel(model);

            } else if (cmmd == "Quit") {
                if(model.getChanged()){
                    if(model.getFileName() == null) {
                        String fName = Utilities.getFileName(null, false);
                        ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(fName));
                        os.writeObject(model);
                        os.close();
                    }else{
                        ObjectOutputStream os = new ObjectOutputStream((new FileOutputStream(model.getFileName())));
                        os.writeObject(model);
                        os.close();
                    }
                }
                System.exit(1);
            } else if (cmmd == "Help") {
                Utilities.inform(factory.getHelp());

            } else if (cmmd == "About") {
                Utilities.inform(factory.about());

            } else {
                Command editCommand = factory.makeEditCommand(model, cmmd);
                editCommand.execute();
            }
        }catch(Exception ex) {
            Utilities.inform("Error, Unrecognized command: " + e.getActionCommand());
        }
    }

    public void display(){ frame.setVisible(true); }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        view.repaint();
    }
}
