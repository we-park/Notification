package ekko.wepark.message.commands.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateMessageCommand {
    @TargetAggregateIdentifier
    private String notificationId;
    private String messageType;
    private Date createdTime;
    private String content;
}
