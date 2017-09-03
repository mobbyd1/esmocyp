package br.com.esmocyp.reasoning.test;

import br.com.esmocyp.messaging.model.RdfMessage;
import br.com.esmocyp.reasoning.listeners.CustomCallback;
import br.com.esmocyp.reasoning.service.ServiceDLO;
import br.com.esmocyp.reasoning.service.StreamReasoningDLOImpl;
import eu.larkc.csparql.common.RDFTable;
import eu.larkc.csparql.common.RDFTuple;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Iterator;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;

/**
 * Created by ruhan on 03/09/17.
 */
@RunWith(MockitoJUnitRunner.class)
public class TestReasoningEngine3 {

    private static final String BASE_URL = "urn:x-hp:eg/";

    @Mock
    ServiceDLO serviceDLO;

    @Mock
    CustomCallback customCallback;

    @InjectMocks
    StreamReasoningDLOImpl streamReasoningDLO;

    @Test
    public void test3() throws Exception {
        streamReasoningDLO.init( "a-box-test/esmocypData.rdf", "t-box-test/tbox-esmocyp.rdf" );

        for( int i = 0; i < 5; i ++ ) {
            final RdfMessage rdfMessage1 = new RdfMessage();
            rdfMessage1.setSubject(BASE_URL + "smartphoneDoRuhan");
            rdfMessage1.setPredicate("http://www.w3.org/1999/02/22-rdf-syntax-ns#type");
            rdfMessage1.setObject(BASE_URL + "NaoEncontrado");

            final RdfMessage rdfMessage2 = new RdfMessage();
            rdfMessage2.setSubject(BASE_URL + "saladeEspera1");
            rdfMessage2.setPredicate("http://www.w3.org/1999/02/22-rdf-syntax-ns#type");
            rdfMessage2.setObject(BASE_URL + "SalaCheia");

            streamReasoningDLO.insertTriple(rdfMessage1);
            streamReasoningDLO.insertTriple(rdfMessage2);

            Thread.sleep(1000);
        }

        final ArgumentCaptor<Object> objectArgumentCaptor = ArgumentCaptor.forClass(Object.class);
        verify( customCallback, atLeast( 1 ) ).update( any(), objectArgumentCaptor.capture() );

        final RDFTable table = ( RDFTable ) objectArgumentCaptor.getValue();
        final Iterator itarator = table.iterator();

        final RDFTuple tuple = ( RDFTuple ) itarator.next();
        final String value1 = tuple.get( 0 );
        final String value2 = tuple.get( 1 );

        Assert.assertEquals( BASE_URL + "saladeEspera1", value1 );
        Assert.assertEquals( BASE_URL + "salaDoRuhan", value2 );
    }
}
