package ekko.wepark.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.model.AggregateIdentifier;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Receiver {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String receiverId;
    @AggregateIdentifier
    private String notificationId;
    private String receiver;
}
