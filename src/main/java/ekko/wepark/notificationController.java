package ekko.wepark;

import ekko.wepark.entity.Notification;
import ekko.wepark.message.commands.message.UpdateMessageCommand;
import ekko.wepark.message.commands.notification.DeleteNotificationCommand;
import ekko.wepark.message.commands.notification.PublishNotificationCommand;
import ekko.wepark.message.commands.notification.ReadNotificationCommand;
import ekko.wepark.message.commands.receiver.UpdateReceiversCommand;
import ekko.wepark.service.NotificationService;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class notificationController {

    @Autowired private CommandGateway commandGateway;
    @Autowired private NotificationService notificationService;

    @PostMapping("/notification")
    @ResponseStatus(HttpStatus.OK)
    @Transactional
    public void createNotification(@RequestBody PublishNotificationCommand command) {
        command.setNotificationId(UUID.randomUUID().toString());
        this.commandGateway.sendAndWait(command);
    }

    @PutMapping("/message")
    @ResponseStatus(HttpStatus.OK)
    @Transactional
    public void updateMessage(@RequestBody UpdateMessageCommand command) {
        this.commandGateway.sendAndWait(command);
    }

    @PutMapping("/notification/{notificationId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @Transactional
    public void markAsRead(@PathVariable(value = "notificationId") String notificationId) {
        ReadNotificationCommand command = new ReadNotificationCommand(notificationId);
        this.commandGateway.sendAndWait(command);
    }

    @DeleteMapping("/notification/{notificationId}")
    @Transactional
    public void markAsDeleted(@PathVariable(value = "notificationId") String notificationId) {
        DeleteNotificationCommand command = new DeleteNotificationCommand(notificationId);
        this.commandGateway.sendAndWait(command);
    }

    @DeleteMapping("/notifications/{userId}")
    @ResponseStatus(HttpStatus.OK)
    @Transactional
    public void markAsDeletedByUserId(@PathVariable(value = "userId") String userId) {
        List<DeleteNotificationCommand> commands = notificationService.markAsDeletedByUserId(userId);

        for (DeleteNotificationCommand command : commands) {
            this.commandGateway.sendAndWait(command);
        }
    }

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Mono<List<Notification>> fetchNotificationsByUserId() {
        String userId = "v704942";
        List<Notification> notifications = notificationService.fetchNotificationByUserId(userId);
        return Mono.just(notifications);
    }

    @PutMapping("/receivers/{notificationId}")
    @ResponseStatus(HttpStatus.OK)
    @Transactional
    public void updateReceivers(@RequestBody UpdateReceiversCommand command) {
        commandGateway.sendAndWait(command);
    }
}
