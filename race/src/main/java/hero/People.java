package hero;

import lombok.Getter;

@Getter
public abstract class People implements Runnable{
    private String name;
    private int type;

    private int speed = 5;
    private int hp = 20;
    private int mp = 20;
    private int hpRecover = 100;
    private int mpRecover = 100;

    public void run() {

    }

    public abstract int getType();

    public abstract String getName();
}
