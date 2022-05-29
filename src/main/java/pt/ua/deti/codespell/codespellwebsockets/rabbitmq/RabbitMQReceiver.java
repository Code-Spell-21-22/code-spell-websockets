package pt.ua.deti.codespell.codespellwebsockets.rabbitmq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pt.ua.deti.codespell.codespellwebsockets.code_exec.CodeExecReport;
import pt.ua.deti.codespell.codespellwebsockets.controllers.MainController;
import pt.ua.deti.codespell.codespellwebsockets.notifications.CodeExecutedNotification;
import pt.ua.deti.codespell.codespellwebsockets.redis.service.CodeExecReportService;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Component
@Log4j2
public class RabbitMQReceiver {

    private final MainController mainController;
    private final CodeExecReportService codeExecReportService;

    @Autowired
    public RabbitMQReceiver(MainController mainController, CodeExecReportService codeExecReportService) {
        this.mainController = mainController;
        this.codeExecReportService = codeExecReportService;
    }

    public void receiveMessage(byte[] messageBytes) {
        String message = new String(messageBytes, StandardCharsets.UTF_8);
        processMessage(message);
        log.info("Received message: " + message);
    }

    public void receiveMessage(String message) {
        processMessage(message);
        log.info("Received message: " + message);
    }

    private void processMessage(String message) {

        CodeExecutedNotification codeExecutedNotification;

        try {
            codeExecutedNotification = new ObjectMapper().readValue(message, CodeExecutedNotification.class);
        } catch (JsonProcessingException jsonException) {
            log.warn(String.format("Unable to parse received message %s to Code Executed Notification.", message));
            return;
        }

        Optional<CodeExecReport> codeExecReportOptional = codeExecReportService.getById(codeExecutedNotification.getCodeUniqueId());
        codeExecReportOptional.ifPresent(mainController::sendCodeExecReport);

    }

}
