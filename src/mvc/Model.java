package mvc;

import tools.Bean;

public class Model extends Bean {
    private boolean unsavedChanges = false;
    private String fileName = null;

    public boolean getChanged(){ return unsavedChanges; }
    public String getFileName() { return fileName; }
    public void setFileName(String name){
        fileName = name;
    }

    public void saved(){
        unsavedChanges = false;
    }

    public void changed(){
        firePropertyChange("Property",null,this);
        unsavedChanges = true;
    }
}
