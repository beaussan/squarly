package me.nbeaussart.engine.model;

import me.nbeaussart.engine.model.interfaces.ICoordinateSquare;
import me.nbeaussart.engine.model.interfaces.IGameMap;
import me.nbeaussart.engine.util.Bresenham;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Nicolas Beaussart
 * @since 13/11/16
 */
public class FOV<T extends ICoordinateSquare> {


    public List<T> generateFOV(Cord source, IGameMap<T> map) {
        List<T> list = new ArrayList<T>();
        map.getFromCords(source).ifPresent(list::add);
        List<List<Cord>> rays = new ArrayList<>();
/*
        for (int x = 0; x < map.sizeX(); x++) {
            if (x == 0 || x == map.sizeX() -1){
                for (int y = 0; y < map.sizeY(); y++) {
                    rays.add(Bresenham.BresenhamAlgorithm(source, Cord.get(x,y)));
                }
            } else {
                rays.add(Bresenham.BresenhamAlgorithm(source, Cord.get(x,0)));
                rays.add(Bresenham.BresenhamAlgorithm(source, Cord.get(x,map.sizeY()-1)));
            }
        }
        */
        for (int y = 0; y < map.sizeY(); y++) {
            if (y == 0 || y == map.sizeY() - 1 ){
                for (int x = 0; x < map.sizeX(); x++) {
                    rays.add(Bresenham.BresenhamAlgorithm(source, Cord.get(x,y)));
                }
            } else {
                rays.add(Bresenham.BresenhamAlgorithm(source, Cord.get(0,y)));
                rays.add(Bresenham.BresenhamAlgorithm(source, Cord.get(map.sizeX()-1,y)));
            }
        }

        for (List<Cord> ray : rays) {
            for (Cord cord : ray) {
                Optional<T> fromCords = map.getFromCords(cord);
                if (fromCords.isPresent()){
                    T t = fromCords.get();
                    if (t.isOpaque()){
                        break;
                    } else {
                        list.add(t);
                    }
                } else {
                    break;
                }
            }
        }


        return list;
    }
}
