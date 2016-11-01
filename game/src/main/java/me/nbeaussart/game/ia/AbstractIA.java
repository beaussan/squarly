package me.nbeaussart.game.ia;

import me.nbeaussart.game.action.Action;
import me.nbeaussart.game.entity.Monster;

import java.util.Optional;

/**
 * Created by beaussan on 31/10/16.
 */
public abstract class AbstractIA {

    protected abstract Action doStuff(Monster monster);

    public Optional<Action> getAction(Monster monster){
        if (!monster.isALive()) {
            return Optional.empty();
        }
        return Optional.ofNullable(doStuff(monster));
    }

}
