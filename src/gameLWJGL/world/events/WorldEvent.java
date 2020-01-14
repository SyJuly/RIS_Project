package gameLWJGL.world.events;

public abstract class WorldEvent implements Comparable<WorldEvent>{

    protected WorldEventType type;
    public long executionTime;

    public abstract void execute();

    @Override
    public int compareTo(WorldEvent event){
        return new Long(this.executionTime).compareTo(event.executionTime);
    }
}
