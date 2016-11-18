package io.nbe.game.ia;

import io.nbe.game.action.Action;
import io.nbe.game.entity.Monster;

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
