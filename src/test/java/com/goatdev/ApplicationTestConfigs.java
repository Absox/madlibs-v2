package com.goatdev;

import com.goatdev.user.User;
import com.goatdev.user.UserDAO;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Unit test configurations.
 * Created by ran on 4/19/16.
 */
@Configuration
public class ApplicationTestConfigs {

    @Bean
    public SessionFactory sessionFactory() {
        return new org.hibernate.cfg.Configuration()
                .configure("hibernate_test_configs.cfg.xml")
                .addAnnotatedClass(User.class)
                .buildSessionFactory();
    }

}
