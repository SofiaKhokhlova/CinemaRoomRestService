package cinema.configuration;

import jakarta.annotation.PostConstruct;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.validation.annotation.Validated;

@EnableConfigurationProperties(CinemaProps.class)
@Configuration
@PropertySource("classpath:cinema.properties")
class PropsConfig{}

@Slf4j
@Validated
@ConfigurationProperties("cinema")
public record CinemaProps(
        @Positive int total_rows,
        @Positive int total_columns,
        @Positive int numberOfFirstRows,
        @Positive int priceForBottomRows,
        @Positive int priceForTopRows,
        @NotBlank String secret
) {

    @PostConstruct
    void logAfterInit(){
        log.info("totalRows = " + total_rows);
        log.info("totalColumn = " + total_columns);
        log.info("nofr = " + numberOfFirstRows);
        log.info("pfbr = " + priceForBottomRows);
        log.info("pftr = " + priceForTopRows);
    }
}