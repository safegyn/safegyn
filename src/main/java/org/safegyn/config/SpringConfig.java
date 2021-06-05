package org.safegyn.config;

import org.safegyn.util.NullAwarePropertyCopy;
import org.springframework.context.annotation.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@ComponentScan("org.safegyn")
@PropertySources({@PropertySource(value = "file:./safegyn.properties", ignoreResourceNotFound = true)})
public class SpringConfig {

    @Bean
    public BCryptPasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public NullAwarePropertyCopy getNullAwarePropertyCopy() {
        return new NullAwarePropertyCopy();
    }

}