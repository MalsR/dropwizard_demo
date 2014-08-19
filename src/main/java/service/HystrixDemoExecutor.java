package service;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixThreadPool;
import com.netflix.hystrix.HystrixThreadPoolProperties;
import io.dropwizard.client.HttpClientBuilder;
import org.apache.http.client.HttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

public class HystrixDemoExecutor extends HystrixCommand<String> {

    private static final Logger LOG = LoggerFactory.getLogger(HystrixDemoExecutor.class);

    private String echo;

    public HystrixDemoExecutor(String echo) {
        super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"));

//        HystrixThreadPoolProperties.Setter setter = HystrixThreadPoolProperties.Setter()
//                .withCoreSize(20)
//                .withMaxQueueSize(10);

        this.echo = echo;
    }

    @Override
    protected String run() throws Exception {
        AsyncExecutor asyncExecutor = new AsyncExecutor(echo);
        asyncExecutor.run();
        return null;
    }

    @Override
    protected String getFallback() {
        LOG.info("------ Failed Echo {} ------", this.echo);
        //return super.getFallback();
        return null;
    }

    private class AsyncExecutor implements Runnable {

        private final Logger LOG = LoggerFactory.getLogger(AsyncExecutor.class);

        private String echo;

        public AsyncExecutor(String echo) {
            this.echo = echo;
        }

        @Override
        public void run() {
            //To demo fallback of Hystrix
            //throw new RuntimeException("Checking fallback");
            LOG.info("------ Echo {} ------", this.echo);
        }
    }
}
