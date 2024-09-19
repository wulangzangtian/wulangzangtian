package wu.lang.wedding.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author 寒
 * @date 2021/12/6
 */
@Component
public class RedisUtils {

    @Autowired
    private RedisTemplate redisTemplate;

    public static RedisUtils redisUtils;

    @PostConstruct
    public void init() {
        redisUtils = this;
        redisUtils.redisTemplate = this.redisTemplate;
    }
    /**
     * Hash添加
     */
    public static void addHash(String key, long id, Object obj, long time) {

    }
}
