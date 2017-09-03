package br.com.esmocyp.reasoning.test;

import br.com.esmocyp.messaging.consumer.MessageConsumerCallback;
import br.com.esmocyp.messaging.model.RdfMessage;
import br.com.esmocyp.reasoning.listeners.CustomCallback;
import br.com.esmocyp.reasoning.service.ServiceDLO;
import br.com.esmocyp.reasoning.service.StreamReasoningDLO;
import br.com.esmocyp.reasoning.service.StreamReasoningDLOImpl;
import eu.larkc.csparql.common.RDFTable;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;
import static org.springframework.test.util.AssertionErrors.assertEquals;

/**
 * Created by ruhandosreis on 30/08/17.
 */
@RunWith(MockitoJUnitRunner.class)
public class TestReasoningEngine1 {

    private static final String BASE_URL = "urn:x-hp:eg/";

    @Mock
    ServiceDLO serviceDLO;

    @Mock
    CustomCallback customCallback;

    @InjectMocks
    StreamReasoningDLOImpl streamReasoningDLO;

    @Test
    public void test1() throws Exception {
        streamReasoningDLO.init( "a-box-test/esmocypData.rdf", "t-box-test/tbox-esmocyp.rdf" );

        final RdfMessage rdfMessage1 = new RdfMessage();
        rdfMessage1.setSubject( BASE_URL + "smartphoneDoRuhan" );
        rdfMessage1.setPredicate( "http://www.w3.org/1999/02/22-rdf-syntax-ns#type" );
        rdfMessage1.setObject( BASE_URL + "NaoEncontrado" );

        streamReasoningDLO.insertTriple( rdfMessage1 );

        Thread.sleep( 2000 );

        final ArgumentCaptor<Object> objectArgumentCaptor = ArgumentCaptor.forClass(Object.class);
        verify( customCallback, atLeast( 1 ) ).update( any(), objectArgumentCaptor.capture() );

        final RDFTable table = ( RDFTable ) objectArgumentCaptor.getValue();
        Assert.assertEquals( 0, table.size() );

    }
}
