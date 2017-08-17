package br.com.esmocyp.messaging.model;

/**
 * Created by ruhandosreis on 16/08/17.
 */
public class RdfMessage implements IMessage {

    private final String className;
    private String subject;
    private String predicate;
    private String object;

    public RdfMessage() {
        this.className = getClass().getName();
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getPredicate() {
        return predicate;
    }

    public void setPredicate(String predicate) {
        this.predicate = predicate;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    @Override
    public String getClassName() {
        return null;
    }
}
