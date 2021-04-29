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
    private final Properties engineProperties;
    private final MouseMovementTracker mouseMovementTracker;

    public SimulationEngine(MouseMovementTracker mouseMovementTracker) {
        this.mouseMovementTracker=mouseMovementTracker;
        engineProperties = new Properties();
        try (InputStream in = SimulationEngine.class.getResourceAsStream("/polis/engine.properties")){
            engineProperties.load(in);
        } catch (IOException ie){
            System.err.println("engine properties bestand kon niet gevonden of gelezen worden");
        }
        mouseMovementTracker.setEngineProperties(engineProperties);
        actors = new ArrayList<>();
        region = new Region(mouseMovementTracker, this);
        region.makeImmigrant();
        tempo = Integer.parseInt(engineProperties.getProperty("region.initial.rate"));
        ticks = RG.nextInt( (int) tempo);
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.millis(250),
                        this::eachTick)
        );
    }

    public void eachTick(ActionEvent ae){
        Set<Actor> newActors = new HashSet<>();
        Iterator<Actor> iterator = actors.iterator();
        while (iterator.hasNext()){
            Actor actor = iterator.next();
            actor.getNewActor().clear();
            if (!actor.isValid()){
                iterator.remove();
                mouseMovementTracker.getCityArea().getChildren().remove(actor);
            } else {
                actor.act();
            }
            newActors.addAll(actor.getNewActor());
        }
        actors.addAll(newActors);
        ticks -= 1;
        //het tempo zal zo altijd liggen tussen de initial rate en slowest rate
        setTempo(Double.parseDouble(engineProperties.getProperty("region.factor.recovery")));
        if (ticks < 0){
            /**
             * niet vergeten om dit terug toe te voegen als alles werkt
             */
            //region.makeImmigrant();
            ticks = RG.nextInt( (int) tempo);
        }
    }

    public void play(){
        timeline.play();
    }

    public void pause(){
        timeline.pause();
    }

    public void setTempo(double factor){
        tempo = Math.min(Math.max(tempo * factor,
                Integer.parseInt(engineProperties.getProperty("region.initial.rate"))),
                Integer.parseInt(engineProperties.getProperty("region.slowest.rate")));
    }

    public void addActor(Actor actor){
        actors.add(actor);
    }

    public Properties getEngineProperties(){
        return engineProperties;
    }


}
