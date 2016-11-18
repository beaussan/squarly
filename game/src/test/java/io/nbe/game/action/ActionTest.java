package io.nbe.game.action;

import io.nbe.game.entity.Entity;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

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