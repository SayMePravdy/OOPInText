package john.environment.objects;

import john.exceptions.WrongEnvironmentException;

public interface Storage <T> {
    void addObject(T obj) throws WrongEnvironmentException;
    void removeObject(T obj);
}
