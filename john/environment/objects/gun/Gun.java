package john.environment.objects.gun;

import john.environment.Environment;
import john.environment.objects.Destroyable;
import john.environment.objects.EnvironmentObjectType;
import john.environment.objects.Storable;
import john.environment.objects.people.People;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Gun extends Storable {
    private int cntBullet;
    private int gunLevel = 1;

    List<Bullet> bullets = new ArrayList<>();

    public class Bullet {
        private double damage = 1;

        public Bullet(double damage) {
            this.damage = damage;
        }

        public double getDamage() {
            return damage;
        }

        public void setDamage(double damage) {
            this.damage = damage;
        }
    }


    public Gun(int cntBullet, Environment environment) {
        super(environment);
        this.cntBullet = cntBullet;
        addCartridge(cntBullet);
        ObjType = EnvironmentObjectType.GUN;
    }

    public Gun(int cntBullet, Environment environment, int gunLevel) {
        this(cntBullet, environment);
        this.gunLevel = gunLevel;
    }

    public int getGunLevel() {
        return gunLevel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Gun gun = (Gun) o;
        return cntBullet == gun.cntBullet &&
                gunLevel == gun.gunLevel &&
                Objects.equals(bullets, gun.bullets);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cntBullet, gunLevel, bullets);
    }

    public void levelUp() {
        gunLevel++;
    }

    public List<Bullet> getBullets() {
        return bullets;
    }

    public void addCartridge(int cntBullet) {
        for (int i = 0; i < cntBullet; i++) {
            bullets.add(new Bullet(Math.random() * 5 + 1));
        }
        this.cntBullet += cntBullet;
    }

    public double shoot(Destroyable d) {
        if (d instanceof People) {
            double hp = d.getStrength();
            if (getDefenseLevel() >= gunLevel){
                if (getDefenseLevel() == gunLevel){
                    for (Bullet bullet : bullets){
                        int bodyPart = (int) (Math.random() * 6);
                        switch (bodyPart) {
                            case 0:
                                hp -= ((People) d).getLeftArm();
                                ((People) d).setLeftArm(0);
                                if (hp <= 0)
                                    return 0;
                                break;
                            case 1:
                                hp -= ((People) d).getRightArm();
                                ((People) d).setRightArm(0);
                                if (hp <= 0)
                                    return 0;
                                break;
                            case 2:
                                hp -= ((People) d).getLeftLeg();
                                ((People) d).setLeftLeg(0);
                                if (hp <= 0)
                                    return 0;
                                break;
                            case 3:
                                hp -= ((People) d).getRightLeg();
                                ((People) d).setRightLeg(0);
                                if (hp <= 0)
                                    return 0;
                                break;
                            case 4:
                                hp -= ((People) d).getBody();
                                ((People) d).setBody(0);
                                if (hp <= 0)
                                    return 0;
                                break;
                            case 5:
                                hp -= ((People) d).getHead();
                                ((People) d).setHead(0);
                                if (hp <= 0)
                                    return 0;
                                break;
                        }
                    }
                }else{
                    bullets.remove(0);
                    return 0;
                }
            }
            return hp;

        } else {
            double dStrength = d.getStrength();
            for (Bullet bullet : bullets) {
                if (gunLevel >= d.getDefenseLevel()) {
                    if (gunLevel == d.getDefenseLevel()) {
                        dStrength -= bullet.damage;
                        bullets.remove(bullet);
                        if (dStrength <= 0)
                            return 0;
                    } else {
                        bullets.remove(bullet);
                        return 0;
                    }
                }
                else
                    break;
            }
            return dStrength;
        }
    }

    public int getCntBullet() {
        return cntBullet;
    }

}
