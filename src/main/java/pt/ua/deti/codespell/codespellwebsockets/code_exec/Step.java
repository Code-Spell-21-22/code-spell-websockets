package pt.ua.deti.codespell.codespellwebsockets.code_exec;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

import java.util.List;

@Data
@NoArgsConstructor
@RedisHash("Step")
public class Step {

    private int id;
    private boolean successful;
    private List<String> args;

}
