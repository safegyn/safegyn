package org.safegyn.config;

import org.apache.log4j.Logger;
import org.safegyn.api.UserApi;
import org.safegyn.config.security.UserRole;
import org.safegyn.db.entity.User;
import org.safegyn.model.error.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class StartupAdminInitialise implements ApplicationListener<ContextRefreshedEvent> {

    private final Logger logger = Logger.getLogger(StartupAdminInitialise.class);

    @Autowired
    protected BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserApi userApi;

    @Autowired
    private ApplicationProperties properties;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if (userApi.getByRole(UserRole.ADMIN).isEmpty()) {
            User adminUser = new User();
            adminUser.setUserRole(UserRole.ADMIN);
            adminUser.setUsername(properties.getAdminUsername());
            adminUser.setPasswordDigest(passwordEncoder.encode(properties.getAdminPassword()));
            adminUser.setName("ADMIN_USERNAME");

            try {
                userApi.add(adminUser);
            } catch (ApiException e) {
                logger.error("Admin cannot be inserted in DB by class:" + StartupAdminInitialise.class);
                e.printStackTrace();
            }
        }

    }
}
