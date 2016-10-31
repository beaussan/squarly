package me.nbeaussart.game.entity;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import me.nbeaussart.engine.model.Color;
import me.nbeaussart.game.map.GameSquare;

import static com.google.common.base.Preconditions.*;

/**
 * Created by beaussan on 31/10/16.
 */
public abstract class Entity {

    private String name;
    private int life;
    private Color color;
    private int atk;
    private GameSquare gameSquare;

    public Entity(String name, int life, int atk, Color color, GameSquare gameSquare){
        setName(name);
        setLife(life);
        setAtk(atk);
        this.color=color;
        setGameSquare(gameSquare);
    }

    public abstract boolean canPassOn(GameSquare gameSquare);

    public String getName() {
        return name;
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
    }

    public Color getColor() {
        return color;
    }

    public int getAtk() {
        return atk;
    }

    public void setAtk(int atk) {
        checkArgument(atk >= 0, "The attack was below 0 (%s)", atk);
        this.atk = atk;
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

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("name", name)
                .add("life", life)
                .add("color", color)
                .add("atk", atk)
                .add("isAlive", isALive())
                .add("gameSquare", gameSquare)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entity entity = (Entity) o;
        return life == entity.life &&
                atk == entity.atk &&
                Objects.equal(name, entity.name) &&
                Objects.equal(color, entity.color);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name, life, color, atk);
    }
}
