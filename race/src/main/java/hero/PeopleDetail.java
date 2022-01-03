package hero;

import lombok.Getter;

import java.util.HashMap;

@Getter
public enum PeopleDetail {
    people(0, "普通人"),
    speed(1, "速度提升"),
    slow(2, "不受陷阱影响"),
    fly(3, "飞翔"),
    swim(4, "游泳速度提升"),
    mountain(5, "山地"),
    city(6, "城市出身"),
    ;
    private int type;

    private String name;

    private final static HashMap<Integer, String> map = new HashMap<Integer, String>();

    PeopleDetail(int type, String name) {
        this.type = type;
        this.name = name;
    }

    static {
        for (PeopleDetail people : values()) {
            map.put(people.getType(), people.getName());
        }
    }

    public String getPeopleName(int type){
        String name = map.get(type);
        if (name == null){
            return people.getName();
        }
        return name;
    }
}
