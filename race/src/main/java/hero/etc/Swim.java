package hero.etc;

import hero.People;
import hero.PeopleDetail;

/**
 * @author 许炼江
 * @CreatTime 2022/1/3-20:03
 */
public class Swim extends People {
    public int getType() {
        return PeopleDetail.swim.getType();
    }

    public String getName() {
        return PeopleDetail.swim.getName();
    }
}
