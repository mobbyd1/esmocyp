package br.com.esmocyp.reasoning.test;

import br.com.esmocyp.messaging.consumer.MessageConsumerCallback;
import br.com.esmocyp.reasoning.listeners.CustomCallback;
import br.com.esmocyp.reasoning.service.ServiceDLO;
import br.com.esmocyp.reasoning.service.StreamReasoningDLO;
import br.com.esmocyp.reasoning.service.StreamReasoningDLOImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.springframework.test.util.AssertionErrors.assertEquals;

/**
 * Created by ruhandosreis on 30/08/17.
 */
@RunWith(MockitoJUnitRunner.class)
public class ReasoningTest {

    @Mock
    ServiceDLO serviceDLO;

    @Mock
    CustomCallback customCallback;

    @InjectMocks
    StreamReasoningDLOImpl streamReasoningDLO;

    @Test
    public void test1() throws Exception {
        streamReasoningDLO.init();
    }

}
