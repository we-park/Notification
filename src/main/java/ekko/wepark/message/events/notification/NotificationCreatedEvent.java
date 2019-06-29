package ekko.wepark.message.events.notification;

import ekko.wepark.entity.Message;
import ekko.wepark.entity.Receiver;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationCreatedEvent {
    private String notificationId;
    private String userId;
    private String createdBy;
    private String sender;
    private Message message;
    private Set<Receiver> receivers;
}
