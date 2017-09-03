package br.com.esmocyp.reasoning.test;

import br.com.esmocyp.messaging.model.RdfMessage;
import br.com.esmocyp.reasoning.listeners.CustomCallback;
import br.com.esmocyp.reasoning.service.ServiceDLO;
import br.com.esmocyp.reasoning.service.StreamReasoningDLOImpl;
import eu.larkc.csparql.common.RDFTable;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;

/**
 * Created by ruhan on 03/09/17.
 */
@RunWith(MockitoJUnitRunner.class)
public class TestReasoningEngine2 {

    private static final String BASE_URL = "urn:x-hp:eg/";

    @Mock
    ServiceDLO serviceDLO;

    @Mock
    CustomCallback customCallback;

    @InjectMocks
    StreamReasoningDLOImpl streamReasoningDLO;

    @Test
    public void test2() throws Exception {
        streamReasoningDLO.init( "a-box-test/esmocypData.rdf", "t-box-test/tbox-esmocyp.rdf" );

        final RdfMessage rdfMessage1 = new RdfMessage();
        rdfMessage1.setSubject( BASE_URL + "salaDoRuhan" );
        rdfMessage1.setPredicate( "http://www.w3.org/1999/02/22-rdf-syntax-ns#type" );
        rdfMessage1.setObject( BASE_URL + "SalaCheia" );

        streamReasoningDLO.insertTriple( rdfMessage1 );

        Thread.sleep( 2000 );

        final ArgumentCaptor<Object> objectArgumentCaptor = ArgumentCaptor.forClass(Object.class);
        verify( customCallback, atLeast( 1 ) ).update( any(), objectArgumentCaptor.capture() );

        final RDFTable table = ( RDFTable ) objectArgumentCaptor.getValue();
        Assert.assertEquals( 0, table.size() );

    }
}
