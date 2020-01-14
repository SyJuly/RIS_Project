package gameLWJGL.world.events;

public class PrintEvent extends WorldEvent {
    String text;

    public PrintEvent(String text, long executionTime){
        this.text = text;
        this.executionTime = executionTime;
    }
    @Override
    public void execute() {
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println();
        System.out.println(text);
        System.out.println();
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
    }
}
