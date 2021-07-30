package io.codezone.iplapplication.config;

import io.codezone.iplapplication.models.Match;
import io.codezone.iplapplication.models.MatchInput;
import io.codezone.iplapplication.processor.MatchProcessor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    private static final String[] MATCH_INPUT_FIELD = new String[] {
            "id","city","date","player_of_match","venue","neutral_venue","team1","team2","toss_winner"
            ,"toss_decision","winner","result","result_margin","eliminator","method","umpire1","umpire2"
    } ;

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job importUserJob(JobCompletionNotificationListener listener, Step step1) {
        return jobBuilderFactory.get("importUserJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step1)
                .end()
                .build();
    }

    @Bean
    public Step step1(JdbcBatchItemWriter<Match> writer) {
        return stepBuilderFactory.get("step1")
                .<MatchInput, Match> chunk(10)
                .reader(reader())
                .processor(processor())
                .writer(writer)
                .build();
    }

    @Bean
    public FlatFileItemReader<MatchInput> reader() {
        return new FlatFileItemReaderBuilder<MatchInput>()
                .name("matchReader")
                .resource(new ClassPathResource("ipl_match_data.csv"))
                .delimited()
                .names(MATCH_INPUT_FIELD)
                .fieldSetMapper(new BeanWrapperFieldSetMapper<MatchInput>() {{
                    setTargetType(MatchInput.class);
                }})
                .build();
    }

    @Bean
    public MatchProcessor processor() {
        return new MatchProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<Match> writer(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Match>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("INSERT INTO match (id,city,date,season,player_of_match,venue,team1,team2,toss_winner" +
                        ",toss_decision,winner,result,result_margin,umpire1,umpire2) " +
                        "VALUES (:id,:city,:date,:season,:playerOfMatch,:venue,:team1,:team2,:tossWinner," +
                        ":tossDecision,:winner,:result,:resultMargin,:umpire1,:umpire2)")
                .dataSource(dataSource)
                .build();
    }



}
