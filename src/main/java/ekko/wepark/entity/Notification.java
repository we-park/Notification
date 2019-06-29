package ekko.wepark.entity;

import ekko.wepark.message.commands.notification.DeleteNotificationCommand;
import ekko.wepark.message.commands.notification.PublishNotificationCommand;
import ekko.wepark.message.commands.notification.ReadNotificationCommand;
import ekko.wepark.message.events.notification.NotificationCreatedEvent;
import lombok.Data;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Entity
@Aggregate
@Data
@Table(name = "Notification")
public class Notification {

    @Id
    @AggregateIdentifier
    private String notificationId;
    private String userId;
    private String sender;
    private String createdBy;
    private boolean read;
    private boolean deleted;

    @OneToOne
    private Message message;

    @OneToMany
    private Set<Receiver> receiver;

    public Notification() {}

    @CommandHandler
    public Notification(PublishNotificationCommand command) {
        NotificationCreatedEvent event = new NotificationCreatedEvent();
        event.setNotificationId(command.getNotificationId());
        event.setUserId(command.getUserId());
        event.setCreatedBy(command.getCreatedBy());
        event.setSender(command.getSender());

        // Message
        Message message = command.getMessage();
        message.setMessageId(UUID.randomUUID().toString());
        message.setNotificationId(command.getNotificationId());
        message.setCreatedTime(new Date());
        event.setMessage(command.getMessage());

        // Receivers
        Set<Receiver> receivers = command.getReceivers();
        event.setReceivers(pairReceiverWithNotificationId(receivers, command.getNotificationId()));
    }

    public Set<Receiver> pairReceiverWithNotificationId(Set<Receiver> receivers, String notificationId) {
        for (Receiver receiver : receivers) {
            receiver.setNotificationId(notificationId);
            receiver.setReceiverId(UUID.randomUUID().toString());
        }
        return receivers;
    }

    @CommandHandler
    public void handle(ReadNotificationCommand command) {
        this.read = true;
    }

    @CommandHandler
    public void handle(DeleteNotificationCommand command) {
        this.deleted = true;
    }

    @EventSourcingHandler
    public void handle(NotificationCreatedEvent event) {
        this.notificationId = event.getNotificationId();
        this.sender = event.getSender();
        this.createdBy = event.getCreatedBy();
        this.message = event.getMessage();
        this.receiver = event.getReceivers();
        this.read = false;
        this.deleted = false;
    }
}
