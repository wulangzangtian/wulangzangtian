package hero.etc;

import hero.People;
import hero.PeopleDetail;

/**
 * @author 许炼江
 * @CreatTime 2022/1/3-20:04
 */
public class Mountain extends People {
    @Override
    public int getType() {
        return PeopleDetail.mountain.getType();
    }

    @Override
    public String getName() {
        return PeopleDetail.mountain.getName();
    }
}
