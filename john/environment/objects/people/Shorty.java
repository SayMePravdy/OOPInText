package john.environment.objects.people;

import john.environment.Environment;

import java.util.Objects;

public class Shorty extends People {

    public Shorty(String name, Environment environment){
        super(name, environment, true);
    }

    public Shorty(String name, Environment environment, int health) {
        super(name, environment, health, true);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shorty shorty = (Shorty) o;
        return name.equals(shorty.name) &&
                objects.equals(shorty.objects);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, objects);
    }

    @Override
    public String toString() {
        return "Shorty{" +
                "name='" + name + '\'' +
                ", environment=" + environment +
                ", flying=" + flying +
                ", ObjType=" + ObjType +
                '}';
    }
}
