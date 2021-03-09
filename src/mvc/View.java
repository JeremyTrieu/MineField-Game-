package mvc;

import javax.swing.JPanel;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class View extends JPanel implements PropertyChangeListener{
    protected Model model;

    public View(Model m){
        model = m;
        model.addPropertyChangeListener(this);
    }

    public void setModel(Model m){
        model.removePropertyChangeListener(this);
        model = m;
        model.initSupport();
        model.addPropertyChangeListener(this);
        model.changed();
    }

    public void propertyChange(PropertyChangeEvent arg0) {
        repaint();
    }
}
