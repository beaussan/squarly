package me.nbeaussart.game.entity;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import me.nbeaussart.engine.model.Color;
import me.nbeaussart.engine.model.Cord;
import me.nbeaussart.game.map.GameSquare;
import me.nbeaussart.game.map.SquareContent;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.*;

/**
 * Created by beaussan on 31/10/16.
 */
public abstract class Entity {

    private List<EntityListener> listeners = new ArrayList<>();

    private String name;
    private int life;
    private int atk;
    private GameSquare gameSquare;
    private SquareContent squareContent;

    public Entity(String name, int life, int atk, GameSquare gameSquare, SquareContent squareContent){
        setName(name);
        setLife(life);
        setAtk(atk);
        setGameSquare(gameSquare);
        this.squareContent= checkNotNull(squareContent);
    }

    public boolean addListener(EntityListener entityListener) {
        return listeners.add(entityListener);
    }

    public abstract boolean canPassOn(GameSquare gameSquare);

    public Cord getCord() {
        return gameSquare.getCord();
    }

    public String getName() {
        return name;
    }

    public SquareContent getSquareContent() {
        return squareContent;
    }

    public void setName(String name) {
        this.name= checkNotNull(name, "Name is null");
    }

    public boolean isALive(){
        return life > 0;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = Math.max(life, 0);
        listeners.forEach(entityListener -> entityListener.entityLifeChanged(this));
        if(!isALive()){
            listeners.forEach(entityListener -> entityListener.entityDeath(this));
            if (gameSquare != null) {
                gameSquare.setEntity(null);
            }
        }
    }

    public int getAtk() {
        return atk;
    }

    public void setAtk(int atk) {
        checkArgument(atk >= 0, "The attack was below 0 (%s)", atk);
        this.atk = atk;
        listeners.forEach(entityListener -> entityListener.entityStrengthChanged(this));
    }

    public GameSquare getGameSquare() {
        return gameSquare;
    }

    public void setGameSquare(GameSquare gameSquare) {
        if (this.gameSquare != null){
            this.gameSquare.setEntity(null);
        }
        this.gameSquare=checkNotNull(gameSquare, "gameSquare is null");
        this.gameSquare.setEntity(this);

    }

    public void setSquareContent(SquareContent squareContent) {
        this.squareContent = squareContent;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("name", name)
                .add("life", life)
                .add("atk", atk)
                .add("isAlive", isALive())
                .add("gameSquare", gameSquare)
                .add("squareContent", squareContent)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entity entity = (Entity) o;
        return life == entity.life &&
                atk == entity.atk &&
                Objects.equal(name, entity.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name, life, atk);
    }
}
