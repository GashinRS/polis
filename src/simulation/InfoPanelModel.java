package simulation;

import javafx.beans.Observable;

import java.util.List;

/**
 * De modellen van het infopaneel implementeren deze interface. Het infopaneel kan met getStats() de nodige informatie
 * ophalen op de correcte statistieken te weergeven en met getType kan het infopaneel weten wat voor informatie
 * het moet weergeven
 */

public interface InfoPanelModel extends Observable {

    List<Number> getStats();

    String getType();

}
