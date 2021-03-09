package mvc;

abstract public class Command {
    protected Model model;
    public Command(Model m){
        model = m;
    }
    public abstract void execute() throws Exception;
}
