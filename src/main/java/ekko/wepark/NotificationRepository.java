package ekko.wepark;

import ekko.wepark.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, String> {
    List<Notification> findNotificationsByUserId(@Param(value = "userId") String userId);
}
