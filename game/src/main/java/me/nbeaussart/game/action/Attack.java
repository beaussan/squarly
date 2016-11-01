package me.nbeaussart.game.action;

import com.google.common.base.Preconditions;
import me.nbeaussart.game.entity.Entity;

/**
 * Created by beaussan on 31/10/16.
 */
public class Attack extends Action{

    private final Entity target;

    public Attack(Entity source, Entity target) {
        super(source);
        this.target = Preconditions.checkNotNull(target);
        Preconditions.checkArgument(!source.equals(target));
    }

    @Override
    public void doStuff() {
        Preconditions.checkState(getSource().isALive(), "Entity is dead !");
        Preconditions.checkState(target.isALive(), "Target is dead !");

        checkIfCloseTo();

        target.setLife(target.getLife() - getSource().getAtk());

    }

    private void checkIfCloseTo(){
        boolean flag = false;
        int resX = getSource().getCord().getX() - target.getCord().getX();
        if (Math.abs(resX) == 1){
            flag = true;
        }
        int resY = getSource().getCord().getY() - target.getCord().getY();
        if ((!flag || resY != 0) && (flag || Math.abs(resY) != 1)) {
            throw new IllegalStateException("The entity was not next to the other one !");
        }
    }
}
