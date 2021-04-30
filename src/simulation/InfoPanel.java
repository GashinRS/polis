package simulation;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import tiles.bigPictureTile.BigPictureTile;
import tiles.bigPictureTile.CommerceTile;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class InfoPanel extends StackPane implements InvalidationListener {

    private InfoPanelModel model;
    private final Text buildingType;
    private final Text buildingInformation;
    private final List<String> stats;

    public InfoPanel(GeneralStatistics generalStatistics) {
        stats = new ArrayList<>(List.of("0","0","0","0","0","0","0","0"));
        setMaxSize(300 , 200);
        setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        buildingType = new Text();
        buildingInformation = new Text();
        buildingType.setFont(new Font(25));
        buildingInformation.setFont(new Font(20));
        setAlignment(buildingType, Pos.TOP_LEFT);
        setAlignment(buildingInformation, Pos.CENTER);
        getChildren().addAll(buildingType, buildingInformation);
        model = generalStatistics;
        model.addListener(this);
    }

    public void setModel(InfoPanelModel model){
        model.removeListener(this);
        this.model=model;
        model.addListener(this);
    }

    @Override
    public void invalidated(Observable observable) {
        buildingType.setText(model.getType().toUpperCase());
        List<Number> newStats = model.getStats();
        for (int i=0; i<newStats.size(); i++){
            stats.set(i, newStats.get(i).toString());
        }
        Map<String, String> displayInformation = Map.of(
                "residence", "Inhabitants: " + stats.get(0) + " / " + stats.get(1),
                "industry", "Jobs: " + stats.get(0) + " / " + stats.get(1),
                "commerce", "Jobs: " + stats.get(0) + " / " + stats.get(1) + "\n" +
                        "Goods: " + stats.get(2) + " / " + stats.get(3) + "\n" +
                        "Customers: " + stats.get(4) + " / " + stats.get(5),
                "statistics", "Inhabitants: " + stats.get(0) + " / " + stats.get(1) + "\n" +
                        "Jobs: " + stats.get(2) + " / " + stats.get(3) + "\n" +
                        "Goods: " + stats.get(4) + " / " + stats.get(5) + "\n" +
                        "Customers: " + stats.get(6) + " / " + stats.get(7)
        );
        buildingInformation.setText(displayInformation.get(model.getType()));
    }

}
