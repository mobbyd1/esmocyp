package br.com.esmocyp.reasoning.service;

import br.com.esmocyp.messaging.model.RdfMessage;
import org.springframework.stereotype.Component;

/**
 * Created by ruhandosreis on 18/08/17.
 *
 * Defines a interface to initialize a continuos query engine and insert RDF triples
 */
public interface StreamReasoningDLO {

    /**
     * Initializes the continous query engine
     *
     * @param aBoxFile
     * @param tBoxFile
     * @throws Exception
     */
    void init( final String aBoxFile, final String tBoxFile ) throws Exception;

    /**
     * Inserts new RDF triples to the countinous query engine
     *
     * @param triple
     */
    void insertTriple( final RdfMessage triple );
}
