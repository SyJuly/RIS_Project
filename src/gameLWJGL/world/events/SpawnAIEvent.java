package gameLWJGL.world.events;

import gameLWJGL.objects.AIManager;

public class SpawnAIEvent extends WorldEvent{

    private AIManager aiManager;
    private int level;

    public SpawnAIEvent(AIManager aiManager, long executionTime, int level){
        type = WorldEventType.SPAWN_AI;
        this.aiManager = aiManager;
        this.executionTime = executionTime;
        this.level = level;
    }

    @Override
    public void execute() {
        aiManager.spawnAI();
    }
}
