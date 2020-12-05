package john.environment.objects;

public interface EnvironmentActionListener {
    void onEnter(EnvironmentObject obj);
    void onLeave(EnvironmentObject obj);
}
