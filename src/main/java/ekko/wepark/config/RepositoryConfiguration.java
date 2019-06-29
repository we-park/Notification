package ekko.wepark.config;


import ekko.wepark.entity.Notification;
import org.axonframework.commandhandling.model.GenericJpaRepository;
import org.axonframework.commandhandling.model.Repository;
import org.axonframework.common.jpa.EntityManagerProvider;
import org.axonframework.eventhandling.EventBus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RepositoryConfiguration {

    @Bean
    public Repository<Notification> repository(EntityManagerProvider entityManagerProvider, EventBus eventbus) {
        return new GenericJpaRepository<Notification>(entityManagerProvider, Notification.class, eventbus);
    }
}
