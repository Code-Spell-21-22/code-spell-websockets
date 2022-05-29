package pt.ua.deti.codespell.codespellwebsockets.messages;

import lombok.Data;
import pt.ua.deti.codespell.codespellwebsockets.code_exec.CodeExecReport;

import java.util.Date;

@Data
public class CodeExecResponse {

    private final CodeExecReport codeExecReport;
    private final Date timestamp;

}
