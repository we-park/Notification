package ekko.wepark.message.commands.notification;

import ekko.wepark.entity.Message;
import ekko.wepark.entity.Receiver;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PublishNotificationCommand {
    @TargetAggregateIdentifier
    private String notificationId;
    private String userId;
    private String createdBy;
    private String sender;
    private Message message;
    private Set<Receiver> receivers;
}
