package ekko.wepark.service;

import ekko.wepark.NotificationRepository;
import ekko.wepark.entity.Notification;
import ekko.wepark.message.commands.notification.DeleteNotificationCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService {

    @Autowired private NotificationRepository repository;

    public List<DeleteNotificationCommand> markAsDeletedByUserId(String userId) {
        List<Notification> notifications = repository.findNotificationsByUserId(userId);
        List<DeleteNotificationCommand> commands = new ArrayList<>();

        for (Notification notification : notifications) {
            DeleteNotificationCommand command = new DeleteNotificationCommand(notification.getNotificationId());
            commands.add(command);
        }
        return commands;
    }

    public List<Notification> fetchNotificationByUserId(String userId) {
        return repository.findNotificationsByUserId(userId);
    }
}
