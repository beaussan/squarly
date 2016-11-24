package io.nbe.game.action;

import io.nbe.game.map.GameSquareAssert;
import io.nbe.squarly.model.Cord;
import io.nbe.squarly.model.Direction;
import io.nbe.squarly.model.interfaces.IState;
import io.nbe.game.entity.Entity;
import io.nbe.game.entity.Player;
import io.nbe.game.map.GameMap;
import io.nbe.game.map.GameSquare;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;

/**
 * Created by beaussan on 31/10/16.
 */
public class MoveTest extends ActionTest{

    GameMap gameMap;

    @Override
    public Action getAction() {
        return new Move(getEntity(), Direction.UP);
    }

    @Override
    public Entity getEntity() {
        gameMap = new GameMap(3,3);
        GameSquare gameSquare = new GameSquare(Cord.get(2, 2), gameMap);
        gameSquare.setState(IState.ROOM);
        GameSquare gs2 = new GameSquare(gameSquare.getCord().add(Direction.UP.getCords()), gameMap);
        gs2.setState(IState.ROOM);
        new GameSquare(gameSquare.getCord().add(Direction.DOWN.getCords()), gameMap);
        new GameSquare(gameSquare.getCord().add(Direction.RIGHT.getCords()), gameMap);
        return new Player("",100,20,gameSquare);
    }

    @Test
    public void moveToUpSuccessful() throws Exception{
        GameSquare gameSquare = actionTested.getSource().getGameSquare();
        actionTested.act();
        GameSquareAssert.assertThat(gameSquare).hasEntity(null);

        Optional<GameSquare> fromCords = gameMap.getFromCords(gameSquare.getCord().add(Direction.UP.getCords()));

        if (!fromCords.isPresent()) {
            fail("The map is not setup correctly");
        }
        fromCords.ifPresent(gameSquare1 -> {
            GameSquareAssert.assertThat(gameSquare1).hasEntity(actionTested.getSource());
        });
    }

    @Test
    public void moveToDownDidNothing() throws Exception {
        Action actionTested2 = new Move(getEntity(), Direction.DOWN);
        GameSquare gameSquare = actionTested2.getSource().getGameSquare();
        actionTested2.act();
        GameSquareAssert.assertThat(gameSquare).hasEntity(getEntity());

        Optional<GameSquare> fromCords = gameMap.getFromCords(gameSquare.getCord().add(Direction.DOWN.getCords()));

        if(!fromCords.isPresent()) {
            fail("The map is not setup correctly");
        }
        fromCords.ifPresent(gameSquare1 -> {
            GameSquareAssert.assertThat(gameSquare1).hasState(IState.WALL).hasEntity(null);
        });
    }

    @Test
    public void moveToLeftDidNotCrash() throws Exception {
        Action actionTested3 = new Move(getEntity(), Direction.LEFT);
        GameSquare gameSquare = actionTested3.getSource().getGameSquare();
        actionTested3.act();
        GameSquareAssert.assertThat(gameSquare).hasEntity(actionTested3.getSource());

        Optional<GameSquare> fromCords = gameMap.getFromCords(gameSquare.getCord().add(Direction.LEFT.getCords()));

        if(fromCords.isPresent()) {
            fail("The map is not setup correctly");
        }
    }
}