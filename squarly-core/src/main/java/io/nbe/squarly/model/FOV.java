package io.nbe.squarly.model;

import io.nbe.squarly.model.interfaces.ICoordinateSquare;
import io.nbe.squarly.util.MathUtil;
import io.nbe.squarly.model.interfaces.IGameMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 * Class for generating a FOV
 * @param <T> The square of the map

 * @author Nicolas Beaussart
 * @since 13/11/16
 */
public class FOV<T extends ICoordinateSquare> {


    /**
     * Generate a FOV according to the map
     * @param source the cooridnate source of the item
     * @param map the map to look at
     * @return the list objects it can look
     */
    public List<T> generateFOV(Cord source, IGameMap<T> map) {
        List<T> list = new ArrayList<>();
        map.getFromCords(source).ifPresent(list::add);
        List<List<Cord>> rays = new ArrayList<>();

        for (int y = 0; y < map.sizeY(); y++) {
            if (y == 0 || y == map.sizeY() - 1 ){
                for (int x = 0; x < map.sizeX(); x++) {
                    rays.add(MathUtil.bresenhamAlgorithm(source, Cord.get(x,y)));
                }
            } else {
                rays.add(MathUtil.bresenhamAlgorithm(source, Cord.get(0,y)));
                rays.add(MathUtil.bresenhamAlgorithm(source, Cord.get(map.sizeX()-1,y)));
            }
        }

        for (List<Cord> ray : rays) {
            handleSingleRay(map, list, ray);
        }


        return list.stream().distinct().collect(Collectors.toList());
    }

    private void handleSingleRay(IGameMap<T> map, List<T> list, List<Cord> ray) {
        boolean foundBlocking = false;

        for (Cord cord : ray) {
            if (foundBlocking){
                break;
            }
            Optional<T> fromCords = map.getFromCords(cord);
            if (fromCords.isPresent()){
                T t = fromCords.get();
                if (t.isOpaque()){
                    foundBlocking = true;
                } else {
                    list.add(t);
                }
            } else {
                foundBlocking = true;
            }
        }
    }
}
