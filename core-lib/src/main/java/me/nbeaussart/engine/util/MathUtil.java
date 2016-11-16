package me.nbeaussart.engine.util;

import me.nbeaussart.engine.model.Cord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * MathUtil class for utilities in math
 * @author Nicolas Beaussart
 * @since 13/11/16
 */

public class MathUtil {
    private static final Logger log = LoggerFactory.getLogger(MathUtil.class);

    /**
     * return the absolute integer
     * @param a the integer
     * @return the absolute of a
     */
    static int abs(int a)
    {
        return (a < 0) ? -a : a;
    }

    /**
     * Calculate a line from c1 to c2 Coordinate
     * @param c1 the source coordinate
     * @param c2 the destination coordinate
     * @return a list of coordinates from c1 to c2
     */
    public static List<Cord> BresenhamAlgorithm(Cord c1, Cord c2) {
        log.debug("Called BresenhamAlgorithm for {} {}", c1, c2);

        // MULTIPLE OCTANTS -- ACTIVE CODE
        // MathUtil algorithm for all 8 octants.
	/* Note:  in four octants, including the entire first quadrant,
	   my code produces exactly the same results as yours.  In the
	   other four octants, it effectively makes the opposite decisions
	   about the error = .5 case mentioned in Damian's e-mail. */

        // If slope is outside the range [-1,1], swap x and y
        boolean xy_swap = false;
        int x1 = c1.getX();
        int y1 = c1.getY();
        int x2 = c2.getX();
        int y2 = c2.getY();
        List<Cord> retVal = new LinkedList<>();
        if (abs(y2 - y1) > abs(x2 - x1)) {
            xy_swap = true;
            int temp = x1;
            x1 = y1;
            y1 = temp;
            temp = x2;
            x2 = y2;
            y2 = temp;
        }

        // If line goes from right to left, swap the endpoints
        if (x2 - x1 < 0) {
            int temp = x1;
            x1 = x2;
            x2 = temp;
            temp = y1;
            y1 = y2;
            y2 = temp;
        }

        int x,                       // Current x position
                y = y1,                  // Current y position
                e = 0,                   // Current error
                m_num = y2 - y1,         // Numerator of slope
                m_denom = x2 - x1,       // Denominator of slope
                threshold  = m_denom/2;  // Threshold between E and NE increment

        for (x = x1; x < x2; x++) {
            if (xy_swap)
                retVal.add(Cord.get(y,x));
            else
            retVal.add(Cord.get(x,y));

            e += m_num;

            // Deal separately with lines sloping upward and those
            // sloping downward
            if (m_num < 0) {
                if (e < -threshold) {
                    e += m_denom;
                    y--;
                }
            }
            else if (e > threshold) {
                e -= m_denom;
                y++;
            }
        }

        if (xy_swap)
            retVal.add(Cord.get(y,x));
        else
            retVal.add(Cord.get(x,y));

        if (!retVal.get(0).equals(c1)){
            Collections.reverse(retVal);
        }
        log.trace("Called BresenhamAlgorithm for {} {}, returning i0={} and ilast={}", c1, c2, retVal.get(0), retVal.get(retVal.size()-1));

        return retVal;
    }
}