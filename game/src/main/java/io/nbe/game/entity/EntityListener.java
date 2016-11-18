package io.nbe.game.entity;

/**
 * Created by beaussan on 31/10/16.
 */
public interface EntityListener {

    default void entityDeath(Entity entity) {}
    default void entityStrengthChanged(Entity entity) {}
    default void entityLifeChanged(Entity entity) {}
}
