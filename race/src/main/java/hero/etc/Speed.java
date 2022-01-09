package hero.etc;

import hero.People;
import hero.PeopleDetail;

/**
 * @author 许炼江
 * @CreatTime 2022/1/3-19:40
 */
public class Speed extends People {
    public int getType() {
        return PeopleDetail.speed.getType();
    }

    public String getName() {
        return PeopleDetail.speed.getName();
    }
}
