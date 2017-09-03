package br.com.esmocyp.reasoning.listeners;

import eu.larkc.csparql.common.RDFTable;
import eu.larkc.csparql.common.RDFTuple;
import eu.larkc.csparql.core.ResultFormatter;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.Observable;

/**
 * Created by ruhan on 02/09/17.
 *
 * This class takes the result from the continous query and print them in the console
 */
@Component
public class CustomCallback extends ResultFormatter {

    /**
     * Method called each time that the continous query engine has new results
     * @param o
     * @param arg the RDF result
     */
    @Override
    public void update(Observable o, Object arg) {
        RDFTable q = (RDFTable)arg;
        System.out.println();
        System.out.println("-------" + q.size() + " results at SystemTime=[" + System.currentTimeMillis() + "]--------");
        Iterator var4 = q.iterator();

        while(var4.hasNext()) {
            RDFTuple t = (RDFTuple)var4.next();
            System.out.println(t.toString());
        }

        System.out.println();
    }
}
