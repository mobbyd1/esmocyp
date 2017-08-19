package br.com.esmocyp.reasoning.service;

import br.com.esmocyp.messaging.model.RdfMessage;
import eu.larkc.csparql.cep.api.RdfQuadruple;
import eu.larkc.csparql.cep.api.RdfStream;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by ruhandosreis on 18/08/17.
 */
@Component
public class StreamReasoningDLOImpl extends RdfStream implements StreamReasoningDLO {

    private static String BASE_URL = "urn:x-hp:eg/";

    public StreamReasoningDLOImpl() {
        super( BASE_URL );
    }

    @PostConstruct
    public void init() {

    }

    public void insertTriple( RdfMessage triple ) {
        final String subject = triple.getSubject();
        final String predicate = triple.getPredicate();
        final String object = triple.getObject();

        final RdfQuadruple q = new RdfQuadruple(
                subject
                , predicate
                , object
                , System.currentTimeMillis());

        this.put( q );
    }
}
te