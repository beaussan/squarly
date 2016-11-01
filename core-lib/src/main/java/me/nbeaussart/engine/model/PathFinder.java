package me.nbeaussart.engine.model;

import es.usc.citius.hipster.graph.HipsterDirectedGraph;
import es.usc.citius.hipster.model.problem.SearchProblem;
import me.nbeaussart.engine.model.interfaces.ICoordinateSquare;
import me.nbeaussart.engine.model.interfaces.IEntityCoordinate;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Nicolas Beaussart
 * @since 01/11/16
 */
public class PathFinder {
    public static PathFinder get(){
        if (instance == null){
            instance = new PathFinder();
        }
        return instance;
    }
    private static PathFinder instance;
    private PathFinder(){}

    private Map<IEntityCoordinate, StoredData> map = new HashMap<>();


    public <T extends ICoordinateSquare> SearchProblem getProblem(IEntityCoordinate<T> entity){
        if (map.containsKey(entity)){

        }
        return null;
    }


    private class StoredData<T extends ICoordinateSquare> {

        HipsterDirectedGraph<T, Integer> graph;
        Cord entityCord;
        IEntityCoordinate<T> entity;


        public void setEntity(IEntityCoordinate<T> entity) {
            this.entity = entity;
            entityCord = entity.getCord();
        }
    }
}
