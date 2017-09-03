package br.com.esmocyp.messaging.model;

/**
 * Created by ruhandosreis on 16/08/17.
 *
 * Represents a RDF triple that is a message
 */
public class RdfMessage implements IMessage {

    /**
     * The class name
     */
    private final String className;

    /**
     * RDF subject
     */
    private String subject;

    /**
     * RDF predicate
     */
    private String predicate;

    /**
     * RDF object
     */
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
