package br.com.esmocyp.reasoning.test;

import br.com.esmocyp.messaging.consumer.MessageConsumerCallback;
import br.com.esmocyp.reasoning.service.ServiceDLO;
import br.com.esmocyp.reasoning.service.StreamReasoningDLO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.springframework.test.util.AssertionErrors.assertEquals;

/**
 * Created by ruhandosreis on 30/08/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ReasoningTest {

    @Autowired
    StreamReasoningDLO streamReasoningDLO;

    @Autowired
    ServiceDLO serviceDLO;

    @MockBean
    MessageConsumerCallback messageConsumerCallback;

    @Test
    public void test1() {
        assertEquals( "igual", true, true );
    }

}
