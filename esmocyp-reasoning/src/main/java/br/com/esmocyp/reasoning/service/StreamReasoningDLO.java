package br.com.esmocyp.reasoning.service;

import br.com.esmocyp.messaging.model.RdfMessage;
import org.springframework.stereotype.Component;

/**
 * Created by ruhandosreis on 18/08/17.
 */
public interface StreamReasoningDLO {
    void init() throws Exception;
    void insertTriple( RdfMessage triple );
}
