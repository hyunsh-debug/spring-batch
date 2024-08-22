package kr.co.batch.sample.job;

import java.util.UUID;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisCursorItemReader;
import org.mybatis.spring.batch.builder.MyBatisBatchItemWriterBuilder;
import org.mybatis.spring.batch.builder.MyBatisCursorItemReaderBuilder;
import org.mybatis.spring.batch.builder.MyBatisPagingItemReaderBuilder;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import kr.co.batch.sample.dvo.ReadDvo;
import kr.co.batch.sample.dvo.WriteDvo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Configuration
@Slf4j
public class DbToDbJobConfig {
	
	private static final int CHUNK_SIZE = 10;
	
	private final JobRepository jobRepository;
	private final PlatformTransactionManager platformTransactionManager;
	private final SqlSessionFactory sqlSessionFactory;
	
	@Bean
	public Job dbToDbJob(Step step1) {
		return new JobBuilder("DbToDbJob", jobRepository)
				.incrementer(new RunIdIncrementer())
				.start(step1)				
				.build();
	}
	
	@Bean
	public Step step1() {
		return new StepBuilder("step1", jobRepository)
				.<ReadDvo, WriteDvo>chunk(CHUNK_SIZE, platformTransactionManager)
				.reader(reader())
				.processor(processor())
				.writer(writer())
				.build();
	}
	
	private ItemReader<ReadDvo> reader() {
		return new MyBatisCursorItemReaderBuilder<ReadDvo>()
	            .sqlSessionFactory(sqlSessionFactory)
	            .queryId("kr.co.batch.sample.mapper.SampleMapper.selectItems")
	            .build();
	}
	
	/**
	 * ReadDvo to WriteDvo
	 */
	private ItemProcessor<ReadDvo, WriteDvo> processor() {
		return item -> {
			String testName = UUID.randomUUID().toString().substring(0, 16);
			
			WriteDvo dvo = new WriteDvo();
			dvo.setReadTestId(item.getReadTestId());
			dvo.setName(testName);
			item.setName(testName);
			return dvo;
		};
	}
	
	private ItemWriter<WriteDvo> writer() {
       return new MyBatisBatchItemWriterBuilder<WriteDvo>()
               .sqlSessionFactory(sqlSessionFactory)
               .statementId("kr.co.batch.sample.mapper.SampleMapper.updateItems")
               .build();
	}

}
