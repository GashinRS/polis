package simulation;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.util.Duration;
import polis.MouseMovementTracker;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class SimulationEngine {

    private static final Random RG = new Random();
    private final Region region;
    private final List<Actor> actors;
    private final Timeline timeline;
    private int ticks;
    private double tempo;
    private final Properties properties;
    private final MouseMovementTracker mouseMovementTracker;

    public SimulationEngine(MouseMovementTracker mouseMovementTracker) {
        this.mouseMovementTracker=mouseMovementTracker;
        properties = new Properties();
        try (InputStream in = SimulationEngine.class.getResourceAsStream("/polis/engine.properties")){
            properties.load(in);
        } catch (IOException ie){
            System.err.println("Properties bestand kon niet gevonden of gelezen worden");
        }
        int duration = 250;
        actors = new ArrayList<>();
        region = new Region(mouseMovementTracker, Integer.parseInt(properties.getProperty("immigrant.age")),
                this, Double.parseDouble(properties.getProperty("region.factor.slow.down")));
        region.makeImmigrant();
        tempo = Integer.parseInt(properties.getProperty("region.initial.rate"));
        ticks = RG.nextInt( (int) tempo);
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.millis(duration),
                        this::eachTick)
        );
    }

    public void eachTick(ActionEvent ae){
        Iterator<Actor> iterator = actors.iterator();
        while (iterator.hasNext()){
            Actor actor = iterator.next();;
            actor.act();
            if (!actor.isValid()){
                iterator.remove();
                mouseMovementTracker.getCityArea().getChildren().remove(actor);
            }
        }
        ticks -= 1;
        //het tempo zal zo altijd liggen tussen de initial rate en slowest rate
        setTempo(Double.parseDouble(properties.getProperty("region.factor.recovery")));
        System.out.println(tempo);
        if (ticks < 0){
            region.makeImmigrant();
            ticks = RG.nextInt( (int) tempo);
        }
        //er moet nog iets toevoegd worden zodat actors die toegevoegd worden tijdens de iteratie niet ook hun act uitvoeren
    }

    public void play(){
        timeline.play();
    }

    public void pause(){
        timeline.pause();
    }

    public void setTempo(double factor){
        tempo = Math.min(Math.max(tempo * factor,
                Integer.parseInt(properties.getProperty("region.initial.rate"))),
                Integer.parseInt(properties.getProperty("region.slowest.rate")));
    }

    public void addActor(Actor actor){
        actors.add(actor);
        mouseMovementTracker.getCityArea().getChildren().add(actor);
    }

}
