package everett.ao.springbootstart.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "test")
public class TestConfigurationPropertiesConfig {
    String key;
    String val;
}
