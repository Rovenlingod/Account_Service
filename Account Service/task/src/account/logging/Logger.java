package account.logging;

public interface Logger {
    void logWithSubject(Event action, String subject);
    void log(Event action, String subject, String object);
    void log(Event action, String object);
    void log(Event action);
}
