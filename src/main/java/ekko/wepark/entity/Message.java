package ekko.wepark.entity;

import ekko.wepark.message.commands.message.UpdateMessageCommand;
import ekko.wepark.message.events.message.MessageUpdatedEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String messageId;
    @AggregateIdentifier
    private String notificationId;
    private String messageType;
    private Date createdTime;
    private String content;

    @CommandHandler
    public void handle(UpdateMessageCommand command) {
        MessageUpdatedEvent event = new MessageUpdatedEvent();
        event.setMessageType(command.getMessageType());
        event.setContent(command.getContent());
    }

    @EventSourcingHandler
    public void handle(MessageUpdatedEvent event) {
        this.messageType = event.getMessageType();
        this.content = event.getContent();
    }
}
