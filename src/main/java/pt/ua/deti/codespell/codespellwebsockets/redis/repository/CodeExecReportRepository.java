package pt.ua.deti.codespell.codespellwebsockets.redis.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pt.ua.deti.codespell.codespellwebsockets.code_exec.CodeExecReport;

@Repository
public interface CodeExecReportRepository extends CrudRepository<CodeExecReport, String> { }
