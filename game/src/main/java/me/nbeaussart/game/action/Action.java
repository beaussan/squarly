package me.nbeaussart.game.action;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import me.nbeaussart.game.entity.Entity;


/**
 * Created by beaussan on 31/10/16.
 */
public abstract class Action {

    private final Entity source;
    private boolean isDone = false;

    public Action(Entity source) {
        this.source = Preconditions.checkNotNull(source);
    }

    public Entity getSource() {
        return source;
    }

    public void act(){
        Preconditions.checkState(!isDone, "Exception is already done");
        doStuff();
        isDone = true;
    }

    public abstract void doStuff();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Action action = (Action) o;
        return isDone == action.isDone &&
                Objects.equal(source, action.source);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(source, isDone);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("source", source)
                .add("isDone", isDone)
                .toString();
    }
}
