package account.logging;

import account.repositories.LogEntryRepository;
import account.utilities.CustomUtils;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.Objects;

@Component
@Transactional
public class LoggerImpl implements Logger {

    private LogEntryRepository logEntryRepository;

    public LoggerImpl(LogEntryRepository logEntryRepository) {
        this.logEntryRepository = logEntryRepository;
    }

    @Override
    public void log(Event action, String object) {
        LogEntry newLogEntry = new LogEntry();
        newLogEntry.setAction(action);
        newLogEntry.setObject(object);
        newLogEntry.setSubject(CustomUtils.getCurrentUserName());
        newLogEntry.setDate(new Date());
        newLogEntry.setPath(CustomUtils.getCurrentRequestURI());
        logEntryRepository.save(newLogEntry);
    }

    @Override
    public void log(Event action) {
        LogEntry newLogEntry = new LogEntry();
        newLogEntry.setAction(action);
        newLogEntry.setSubject(CustomUtils.getCurrentUserName());
        newLogEntry.setDate(new Date());
        newLogEntry.setPath(CustomUtils.getCurrentRequestURI());
        newLogEntry.setObject(CustomUtils.getCurrentRequestURI());
        logEntryRepository.save(newLogEntry);
    }

    @Override
    public void logWithSubject(Event action, String subject) {
        LogEntry newLogEntry = new LogEntry();
        newLogEntry.setAction(action);
        newLogEntry.setSubject(subject);
        newLogEntry.setDate(new Date());
        newLogEntry.setPath(CustomUtils.getCurrentRequestURI());
        newLogEntry.setObject(CustomUtils.getCurrentRequestURI());
        logEntryRepository.save(newLogEntry);
    }

    @Override
    public void log(Event action, String subject, String object) {
        LogEntry newLogEntry = new LogEntry();
        newLogEntry.setAction(action);
        newLogEntry.setSubject(subject);
        newLogEntry.setObject(object);
        newLogEntry.setDate(new Date());
        newLogEntry.setPath(CustomUtils.getCurrentRequestURI());
        logEntryRepository.save(newLogEntry);
    }
}
