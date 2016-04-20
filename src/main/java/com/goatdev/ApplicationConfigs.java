package com.goatdev;

import com.goatdev.user.HibernateUserDAO;
import com.goatdev.user.User;
import com.goatdev.user.UserDAO;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class with beans.
 * Created by ran on 4/19/16.
 */
@Configuration
public class ApplicationConfigs {

    @Bean
    public SessionFactory sessionFactory() {
        return new org.hibernate.cfg.Configuration()
                .configure()
                .addAnnotatedClass(User.class)
                .buildSessionFactory();
    }

}
