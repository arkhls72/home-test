package com.home.processor.job.config;

//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
//import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
//import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
//import org.springframework.batch.core.configuration.annotation.StepScope;
//import org.springframework.batch.item.ItemProcessor;
//import org.springframework.batch.item.ItemReader;
//import org.springframework.batch.item.ItemWriter;
//import org.springframework.batch.item.data.MongoItemWriter;
//import org.springframework.batch.item.file.FlatFileItemReader;
//import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
//import org.springframework.batch.item.file.mapping.DefaultLineMapper;
//import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

//import com.home.processor.dto.TradeSupplierDTO;
//import com.home.processor.model.TradeSupplier;

@Configuration
@EnableBatchProcessing
public class ProcessorFileConfig {
//	@Autowired
//	private JobBuilderFactory jobBuilderFactory;
//	
//	private StepBuilderFactory stepBuilderFactory; 
//	
//	
//	private MongoTemplate mongoTemplate;
//
//	@Bean(name="jobFiles")
//	public Job jobfiles(JobTaskExecuterListener listener,@Value("#{jobParameters[source]}") String source){
//		return jobBuilderFactory.get("addNewPodcastJob")
//				.start(step(source)).listener(listener)
//				.build();
//	}
//
//	@Bean
//	@StepScope
//	public Step step(@Value("#{jobParameters[source]}") String source){
//		return stepBuilderFactory.get("step").<TradeSupplierDTO,TradeSupplier> chunk(10)
//				.reader(reader(source))
//				.processor(processor())
//				.writer(writer()).taskExecutor(executorJobFile()).throttleLimit(15)			
//				.build();
//	}
//
//	@Bean
//	public ItemReader<TradeSupplierDTO> reader(@Value("#{jobParameters[source]}") String source){
//		 FlatFileItemReader<TradeSupplierDTO> reader = new FlatFileItemReader<TradeSupplierDTO>();
//	        reader.setResource(new ClassPathResource("sample-data.csv"));
//	        reader.setLineMapper(new DefaultLineMapper<TradeSupplierDTO>() {{
//	            setLineTokenizer(new DelimitedLineTokenizer() {{
//	                setNames(new String[] { "firstName", "lastName" });
//	            }});
//	            setFieldSetMapper(new BeanWrapperFieldSetMapper<TradeSupplierDTO>() {{
//	                setTargetType(TradeSupplierDTO.class);
//	            }});
//	        }});
//	        return reader;
//	}	
//
//	@Bean
//    public ItemWriter<TradeSupplier> writer() {
//        MongoItemWriter<TradeSupplier> writer = new MongoItemWriter<TradeSupplier>();
//        try {
//            writer.setTemplate(mongoTemplate);
//        } catch (Exception e) {
//        
//        }
//        
//        writer.setCollection("people");
//        return writer;
//    }
//
//	@Bean
//    public ItemProcessor<TradeSupplierDTO, TradeSupplier> processor() {
//        return new TradeItemProcessor();
//    }
//	
//	@Bean(name="executorJobFile")
//	public ThreadPoolTaskExecutor executorJobFile(){
//		ThreadPoolTaskExecutor  threadPoolTaskExecutor = new ThreadPoolTaskExecutor();		
//		threadPoolTaskExecutor.setCorePoolSize(10);
//        threadPoolTaskExecutor.setMaxPoolSize(20);
//        threadPoolTaskExecutor.setWaitForTasksToCompleteOnShutdown(true);
//        threadPoolTaskExecutor.setQueueCapacity(50);
//        threadPoolTaskExecutor.setThreadNamePrefix("myHomethread");
//        threadPoolTaskExecutor.initialize();
//        return threadPoolTaskExecutor;	
//        
//	}	
		
}
	
