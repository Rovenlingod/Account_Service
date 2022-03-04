package account.mappers;

import account.dtos.LogEntryDTO;
import account.logging.LogEntry;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class LogEntryMapper {

    public LogEntryDTO toDto(LogEntry logEntry) {
        LogEntryDTO result = new LogEntryDTO();
        result.setId(logEntry.getId());
        result.setDate(logEntry.getDate().toString());
        result.setAction(logEntry.getAction());
        result.setSubject(logEntry.getSubject());
        result.setObject(logEntry.getObject());
        result.setPath(logEntry.getPath());
        return result;
    }

    public List<LogEntryDTO> toDtos(List<LogEntry> logEntries) {
        return logEntries.stream().map(this::toDto).collect(Collectors.toList());
    }
}
