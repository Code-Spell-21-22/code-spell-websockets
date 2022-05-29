package pt.ua.deti.codespell.codespellwebsockets.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import pt.ua.deti.codespell.codespellwebsockets.code_exec.CodeExecReport;

@Controller
@Log4j2
public class MainController {

    private final SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    public MainController(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    public void sendCodeExecReport(CodeExecReport codeExecReport) {

        String codeExecReportJSON;

        try {
            codeExecReportJSON = new ObjectMapper().writeValueAsString(codeExecReport);
        } catch (JsonProcessingException e) {
            log.warn(String.format("Error converting CodeExecReport (%s) to JSON string.", codeExecReport));
            return;
        }

        this.simpMessagingTemplate.convertAndSend("/code-spell/outgoing/execution-report", codeExecReportJSON);

        log.info(String.format("Sending message: %s", codeExecReportJSON));

    }

}
