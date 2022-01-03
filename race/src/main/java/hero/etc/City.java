package hero.etc;

import hero.People;
import hero.PeopleDetail;

/**
 * @author 许炼江
 * @CreatTime 2022/1/3-20:04
 */
public class City extends People {

    public int getType(){
        return PeopleDetail.city.getType();
    }

    public String getName(){
        return PeopleDetail.city.getName();
    }
}
