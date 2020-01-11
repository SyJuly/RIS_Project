package gameLWJGL.objects;

public interface IObjectHolder {
    GameObject[] getNewlyCreatedObjects();
    String[] getRemovedObjects();
    void removeObject(String id);
}
