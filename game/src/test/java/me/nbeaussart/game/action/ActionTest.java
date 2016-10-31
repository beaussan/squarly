package me.nbeaussart.game.action;

import me.nbeaussart.game.entity.Entity;
import me.nbeaussart.game.map.GameMap;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.Assert.*;

/**
 * Created by beaussan on 31/10/16.
 */
public abstract class ActionTest {

    protected Entity testedSubject;
    protected Action actionTested;

    @Before
    public void setUp() throws Exception {
        actionTested = getAction();
        testedSubject = actionTested.getSource();
    }

    public abstract Action getAction();
    public abstract Entity getEntity();
    @Test
    public void testActionCanRunOnlyOneTime() throws Exception {
        actionTested.act();
        assertThatThrownBy(() -> actionTested.act())
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Exception is already done");
    }

}