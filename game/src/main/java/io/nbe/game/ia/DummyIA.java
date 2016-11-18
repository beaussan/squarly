package io.nbe.game.ia;

import io.nbe.game.entity.Player;
import io.nbe.squarly.model.Direction;
import io.nbe.game.action.Action;
import io.nbe.game.action.Attack;
import io.nbe.game.action.Move;
import io.nbe.game.entity.Monster;
import io.nbe.game.map.GameSquare;

import java.util.Optional;
import java.util.Random;

/**
 * Created by beaussan on 31/10/16.
 */
public class DummyIA extends AbstractIA {

    private final Random r = new Random();
    private final Player player;

    public DummyIA(Player player) {
        this.player = player;
    }

    @Override
    protected Action doStuff(Monster monster) {
        for (Direction direction : Direction.values()) {
            Optional<GameSquare> curr = monster.getGameSquare().getGameMap().getFromCords(monster.getCord().add(direction));
            if (curr.isPresent()){
                if (player.equals(curr.get().getEntity())){
                    return new Attack(monster, player);
                }
            }
        }
        switch (r.nextInt(5)){
            case 0 :
                return new Move(monster, Direction.UP);
            case 1 :
                return new Move(monster, Direction.DOWN);
            case 2 :
                return new Move(monster, Direction.LEFT);
            case 3 :
                return new Move(monster, Direction.RIGHT);
            default :
                return null;
        }
    }

}
