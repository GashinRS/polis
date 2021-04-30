package simulation;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;

import java.util.List;

public interface InfoPanelModel extends Observable {
    List<Number> getStats();
    String getType();
}
