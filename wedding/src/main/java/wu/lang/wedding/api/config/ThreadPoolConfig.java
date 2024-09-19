package wu.lang.wedding.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

@Configuration
public class ThreadPoolConfig {
    /**
     * 替换默认线程池
     * @return
     */
    @Bean("mainTaskExecutor")
    public ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();
        //设置核心线程数
        pool.setCorePoolSize(5);
        //最大线程数
        pool.setMaxPoolSize(10);
        //缓冲队列数
        pool.setQueueCapacity(50);
        //线程池名前缀
        pool.setThreadNamePrefix("mainTaskThreadPool-");
        //允许线程空闲时间（单位：默认为秒）
        pool.setKeepAliveSeconds(120);
        // 设置拒绝策略，当线程池阻塞队列已满时对新任务的处理。调节机制，即饱和时回退主线程执行
        pool.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 等待所有任务结束后再关闭线程池
        pool.setWaitForTasksToCompleteOnShutdown(true);
        // 初始化
        pool.initialize();
        return pool;
    }
}
