package hero.etc;

import hero.People;
import hero.PeopleDetail;

/**
 * @author 许炼江
 * @CreatTime 2022/1/3-20:02
 */
public class Slow extends People {
    @Override
    public int getType() {
        return PeopleDetail.slow.getType();
    }

    @Override
    public String getName() {
        return PeopleDetail.slow.getName();
    }
}
