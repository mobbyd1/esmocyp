package br.com.esmocyp.cep.listeners;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;
import org.springframework.stereotype.Component;

/**
 * Created by ruhandosreis on 16/08/17.
 */
@Component
public class FullRoomEventListener implements UpdateListener {

    @Override
    public void update(EventBean[] eventBeans, EventBean[] eventBeans1) {

    }
}
