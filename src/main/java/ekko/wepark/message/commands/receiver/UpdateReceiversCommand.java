package ekko.wepark.message.commands.receiver;

import ekko.wepark.entity.Receiver;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateReceiversCommand {
    @TargetAggregateIdentifier
    private String notificationId;
    private Set<Receiver> receivers;
}
