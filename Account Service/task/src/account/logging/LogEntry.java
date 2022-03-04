package account.logging;

import javax.persistence.*;
import java.util.Date;

@Entity
public class LogEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "log_id", nullable = false, unique = true)
    private Long id;
    @Column
    private Date date;
    @Column
    @Enumerated(EnumType.STRING)
    private Event action;
    @Column
    private String subject;
    @Column
    private String object;
    @Column
    private String path;

    public LogEntry() {
    }

    public LogEntry(Long id, Date date, Event action, String subject, String object, String path) {
        this.id = id;
        this.date = date;
        this.action = action;
        this.subject = subject;
        this.object = object;
        this.path = path;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Event getAction() {
        return action;
    }

    public void setAction(Event action) {
        this.action = action;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
