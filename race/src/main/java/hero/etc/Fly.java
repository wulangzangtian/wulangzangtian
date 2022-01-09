package hero.etc;

import hero.People;
import hero.PeopleDetail;

/**
 * @author 许炼江
 * @CreatTime 2022/1/3-20:03
 */
public class Fly extends People {
    @Override
    public int getType() {
        return PeopleDetail.fly.getType();
    }

    @Override
    public String getName() {
        return PeopleDetail.fly.getName();
    }
}
