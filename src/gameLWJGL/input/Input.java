package gameLWJGL.input;

import gameLWJGL.network.common.IMsgApplicator;
import gameLWJGL.network.client.GameClient;
import gameLWJGL.network.common.networkMessages.InputMsg;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.GL_TRUE;

public class Input implements IMsgApplicator<InputMsg> {

    private List<IMoveable> moveables;

    public Input(){
        this.moveables = moveables != null ? moveables : new ArrayList<>();
    }
    private boolean shouldSendMessage = false;

    private int xDirection, yDirection, prevXDirection, prevYDirection;

    public void handleInput(long window){
        prevXDirection = xDirection;
        prevYDirection = yDirection;

        xDirection = 0;
        yDirection = 0;
        if(glfwGetKey(window, GLFW_KEY_ESCAPE) == GL_TRUE){
            glfwSetWindowShouldClose(window, true);
        }
        if(glfwGetKey(window, GLFW_KEY_LEFT) == GL_TRUE){
            xDirection = -1;
        }
        if(glfwGetKey(window, GLFW_KEY_RIGHT) == GL_TRUE){
            xDirection = 1;
        }

        if(glfwGetKey(window, GLFW_KEY_UP) == GL_TRUE){
            yDirection = 1;
        }
        if(glfwGetKey(window, GLFW_KEY_DOWN) == GL_TRUE){
            yDirection = - 1;
        }

        if(xDirection == prevXDirection && yDirection == prevYDirection) {
            shouldSendMessage = false;
            return;
        }
        shouldSendMessage = true;

    }

    public void addMoveable(IMoveable moveable){
        moveables.add(moveable);
    }

    public void removeMoveable(IMoveable moveable){
        moveables.remove(moveable);
    }

    @Override
    public boolean shouldSendMessage() {
        return shouldSendMessage;
    }

    @Override
    public InputMsg getMessage() {
        return new InputMsg(GameClient.CLIENTID, xDirection, yDirection);
    }

    @Override
    public InputMsg getStartMessage() {
        return null;
    }

    @Override
    public void receive(InputMsg networkMsg) {
        for (int i = 0; i < moveables.size(); i++){
            IMoveable tempMovable = moveables.get(i);
            if (networkMsg.clientID.equals(tempMovable.getId())){
                //System.out.println("INPUT APPLIED");
                tempMovable.move(networkMsg.xDirection, networkMsg.yDirection);
            }
        }
    }
}
