package br.com.esmocyp.reasoning.listeners;

import eu.larkc.csparql.core.ResultFormatter;
import org.springframework.stereotype.Component;

import java.util.Observable;

/**
 * Created by ruhan on 02/09/17.
 */
@Component
public class CustomCallback extends ResultFormatter {

    @Override
    public void update(Observable o, Object arg) {

    }
}
