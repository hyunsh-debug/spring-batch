package kr.co.batch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.StopWatch;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class BatchApplication {

	public static void main(String[] args) {
        StopWatch watch = new StopWatch();
        watch.start();
        
        // 1. run spring application
        log.info("-----------------------------------------------------------");
        log.info(" Batch Application is Starting ");
        
		SpringApplication.run(BatchApplication.class, args);
		
		watch.stop();
		
        log.info(" elapsed time : " + watch.getTotalTimeMillis() + " ms");
        log.info("----------------------------------------------------------- ");
	}

}
