package wu.lang.wedding.api.task;

import wu.lang.wedding.utils.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AsyncTask {
    @Async("mainTaskExecutor")
    public void sendRemain(int serverId, String url, long remain) {
        String serverRes = HttpUtils.post(url, null, null, remain + "");
        if (serverRes == null) {
            log.info("update coupon server :{} fail", serverId);
        }
    }

    @Async("mainTaskExecutor")
    public void sendHellFirstSuccess(int serverId, String url, int mapId) {
        String serverRes = HttpUtils.post(url, null, null, mapId + "");
        if (serverRes == null) {
            log.info("update coupon server :{} fail", serverId);
        }
    }
}
