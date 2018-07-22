package com.home.processor.job.config;


import javax.persistence.EntityManager;
import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.home.processor.job.dto.StudentDTO;
import com.home.processor.job.persistant.domain.Student;
import com.home.processor.job.persistant.domain.StudentTarget;

@Configuration
@EnableBatchProcessing
@EntityScan("com.home.processor.job.persistant.domain")
@EnableJpaRepositories({
"com.home.processor.job.persistant.repository"})
@EnableConfigurationProperties
@EnableWebMvc
public class ProcessorDBConfig {
//    private static final String QUERY_FIND_STUDENTS = "select * from ( SELECT id, name,email, ROW_NUMBER() OVER (ORDER BY id ) as RowNo from TB_STUDENT) t where RowNo between ";
//    private static final String QUERY_FIND_STUDENTS = "select * from TB_STUDENT LIMIT ";
//	private static final String QUERY_FIND_STUDENTS = "select * from TB_STUDENT ORDER BY id OFFSET ";
//	private static final String QUERY_FIND_STUDENTS = "select * from ( Select id, name,email, ROW_NUMBER() OVER (ORDER BY id ) as RowNo from TB_Student) t where RowNo between ";
	
	private static final String QUERY_FIND_STUDENTS = "select id, name, email from niko.TB_STUDENT ";
    private EntityManager entityManager;
    
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    
    private DataSource dataSource; 
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Autowired
    public ProcessorDBConfig( EntityManager entityManager,DataSource dataSource,JdbcTemplate jdbcTemplate) {
       this.entityManager = entityManager; 
       this.dataSource=dataSource;
       this.jdbcTemplate=new JdbcTemplate(dataSource);
    }

   

    @Bean(name="firstJob")
    public Job firstJob(JobTaskExecuterListener jobTaskExecuterListener,Step step) throws Exception {
        return jobBuilderFactory.get("step")
                .repository(jobRepository())
                .listener(jobTaskExecuterListener)
                .flow(step)
                .end()
                .build();
    }
    
  
    @Bean
    public Step firstStep(ItemReader<StudentDTO> itemReader) throws Exception {
        return stepBuilderFactory.get("firstStep")
                .repository(jobRepository())
                .<StudentDTO, StudentTarget> chunk(50)
                .reader(itemReader)
                .processor(studentItemProcessor())
                .writer(writer())
                .build();
    }
    
    @Bean
    public StudentItemProcessor studentItemProcessor() {
        return new StudentItemProcessor();
    }
    
    @Bean
    @StepScope
    public ItemReader<StudentDTO> itemReader(@Value("#{jobParameters['start']}") String start,@Value("#{jobParameters['end']}") String end) throws UnexpectedInputException, ParseException, Exception {
        JdbcCursorItemReader<StudentDTO> itemReader = new JdbcCursorItemReader<>();
        StringBuilder sb = new StringBuilder();
 
//        sb.append(QUERY_FIND_STUDENTS).append(Integer.parseInt(start)).append(" ROWS FETCH NEXT  ").append(Integer.parseInt(end)).append(" ROWS").append(" ONLY");
        sb.append(QUERY_FIND_STUDENTS);
        
        itemReader.setDataSource(dataSource);
        itemReader.setSql(sb.toString());
        itemReader.setRowMapper(new StudentMapper());
        itemReader.setFetchSize(50);
        itemReader.setSaveState(false);
        itemReader.setVerifyCursorPosition(false);
        itemReader.afterPropertiesSet();
       
        ExecutionContext executionContext = new ExecutionContext();
        itemReader.open(executionContext);
        
        return itemReader;
    }
    
    
   
    @Bean
    public ItemWriter<StudentTarget>  writer() {
        return new StudentItemWriter();
    }
    
    @Bean(name="batchTransactionManager")
    public PlatformTransactionManager batchTransactionManager() {

        DataSourceTransactionManager tm = new DataSourceTransactionManager();
        tm.setDataSource(dataSource);
        
        return tm;
    }

    @Bean(name ="jobRepository")
    public JobRepository jobRepository() throws Exception {
        JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
        factory.setDataSource(dataSource);
        factory.setTransactionManager(batchTransactionManager());
        factory.afterPropertiesSet();
        return factory.getObject();
    }
  
  @Bean(name="threadPoolTaskExecutor")
  public ThreadPoolTaskExecutor taskExecuter(){
      ThreadPoolTaskExecutor  threadPoolTaskExecutor = new ThreadPoolTaskExecutor();      
      threadPoolTaskExecutor.setCorePoolSize(20);
        threadPoolTaskExecutor.setMaxPoolSize(100);
        threadPoolTaskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        threadPoolTaskExecutor.setQueueCapacity(100);
        threadPoolTaskExecutor.setThreadNamePrefix("myHomethread");
        threadPoolTaskExecutor.initialize();
        return threadPoolTaskExecutor;    
        
  }   
  
  @Bean(name="threadPoolLauncher")
  public ThreadPoolTaskExecutor threadLuncher(){
        ThreadPoolTaskExecutor  threadPoolTaskExecutor = new ThreadPoolTaskExecutor();      
        threadPoolTaskExecutor.setCorePoolSize(10);
        threadPoolTaskExecutor.setMaxPoolSize(50);
        threadPoolTaskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        threadPoolTaskExecutor.setQueueCapacity(100);
        threadPoolTaskExecutor.setThreadNamePrefix("myHomethread2");
        threadPoolTaskExecutor.initialize();
        return threadPoolTaskExecutor;    
        
  }   
  @Bean
  public JobTaskExecuterListener  jobTaskExecuterListener() {
      return new JobTaskExecuterListener();
  }
  
}    
