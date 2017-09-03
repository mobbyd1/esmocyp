package br.com.esmocyp.reasoning.service;

import br.com.esmocyp.messaging.model.RdfMessage;
import br.com.esmocyp.reasoning.listeners.CustomCallback;
import eu.larkc.csparql.cep.api.RdfQuadruple;
import eu.larkc.csparql.cep.api.RdfStream;
import eu.larkc.csparql.common.utils.CsparqlUtils;
import eu.larkc.csparql.common.utils.ReasonerChainingType;
import eu.larkc.csparql.core.engine.CsparqlEngineImpl;
import eu.larkc.csparql.core.engine.CsparqlQueryResultProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * Created by ruhandosreis on 18/08/17.
 *
 * This class runs a C-SPARQL query for each incoming RDF stream.
 * The results of the query are send to the observable CustomCallback
 */

@Component
public class StreamReasoningDLOImpl extends RdfStream implements StreamReasoningDLO {

    private static final String BASE_URL = "urn:x-hp:eg/";

    @Autowired
    CustomCallback customCallback;

    public StreamReasoningDLOImpl() {
        super( "http://streamreasoning.org/streams/hospital" );
    }

    public void init( final String aBoxFile, final String tBoxFile ) throws Exception {

        final ClassLoader classLoader = StreamReasoningDLOImpl.class.getClassLoader();

        //Create csparql engine instance
        final CsparqlEngineImpl engine = new CsparqlEngineImpl();

        //Initialize the engine instance
        //The initialization creates the static engine (SPARQL) and the stream engine (CEP)
        engine.initialize();

        String queryBody = "REGISTER QUERY staticKnowledge AS "
                + "PREFIX :<urn:x-hp:eg/> "
                + "SELECT ?s ?s1 "
                + "FROM STREAM <http://streamreasoning.org/streams/hospital> [RANGE 5s STEP 1s] "
                + "FROM <http://streamreasoning.org/hospital-data> "
                + "WHERE { "
                + "?s :hasConnectionTo ?s1 . "
                + "?s a :SalaComGargalo . "
                + "} ";

        File esmocypData = new File(classLoader.getResource( aBoxFile ).getFile());
        String roomConnectionPath = esmocypData.getAbsolutePath();

        // loads the a-box into the engine
        engine.putStaticNamedModel("http://streamreasoning.org/hospital-data", CsparqlUtils.serializeRDFFile(roomConnectionPath));

        engine.registerStream(this);

        //Register new query in the engine
        CsparqlQueryResultProxy c = engine.registerQuery(queryBody, false);

        //Attach a result consumer to the query result proxy to print the results on the console
        c.addObserver( customCallback );

        // loads the t-box (ontology) to the engine
        File tboxFile = new File(classLoader.getResource( tBoxFile ).getFile());
        String tboxPath = tboxFile.getAbsolutePath();

        engine.updateReasoner(
                c.getSparqlQueryId()
                , ""
                , ReasonerChainingType.BACKWARD
                , CsparqlUtils.serializeRDFFile( tboxPath ) );

        //stream();

    }

//    public void stream() {
//        for( ; ; ) {
//            final RdfMessage rdfMessage1 = new RdfMessage();
//            rdfMessage1.setSubject(BASE_URL + "smartphoneDoRuhan");
//            rdfMessage1.setPredicate("http://www.w3.org/1999/02/22-rdf-syntax-ns#type");
//            rdfMessage1.setObject(BASE_URL + "NaoEncontrado");
//
//            final RdfMessage rdfMessage2 = new RdfMessage();
//            rdfMessage2.setSubject(BASE_URL + "saladeEspera1");
//            rdfMessage2.setPredicate("http://www.w3.org/1999/02/22-rdf-syntax-ns#type");
//            rdfMessage2.setObject(BASE_URL + "SalaCheia");
//
//            insertTriple(rdfMessage1);
//            insertTriple(rdfMessage2);
//        }
//    }

    public void insertTriple( RdfMessage triple ) {
        assert( triple != null );

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