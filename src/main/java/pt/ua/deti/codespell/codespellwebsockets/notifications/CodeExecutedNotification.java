package pt.ua.deti.codespell.codespellwebsockets.notifications;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class CodeExecutedNotification {

    private UUID codeUniqueId;
    private CodeExecutionStatus codeExecutionStatus;

}
