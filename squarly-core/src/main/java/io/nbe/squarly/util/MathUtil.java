package io.nbe.squarly.util;

import io.nbe.squarly.model.Cord;
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

public final class MathUtil {
    private static final Logger log = LoggerFactory.getLogger(MathUtil.class);

    private MathUtil(){}

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
    public static List<Cord> bresenhamAlgorithm(Cord c1, Cord c2) {
        log.debug("Called bresenhamAlgorithm for {} {}", c1, c2);

        // MULTIPLE OCTANTS -- ACTIVE CODE
        // MathUtil algorithm for all 8 octants.
	/* Note:  in four octants, including the entire first quadrant,
	   my code produces exactly the same results as yours.  In the
	   other four octants, it effectively makes the opposite decisions
	   about the error = .5 case mentioned in Damian's e-mail. */

        // If slope is outside the range [-1,1], swap x and y
        boolean xySwap = false;
        int x1 = c1.getX();
        int y1 = c1.getY();
        int x2 = c2.getX();
        int y2 = c2.getY();
        List<Cord> retVal = new LinkedList<>();
        if (abs(y2 - y1) > abs(x2 - x1)) {
            xySwap = true;
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

        int x;  // Threshold between E and NE increment
        int y = y1; // Current y position
        int e = 0;  // Current error
        int mNum = y2 - y1; // Numerator of slope
        int mDenom = x2 - x1; // Denominator of slope
        int threshold  = mDenom/2; // Threshold between E and NE increment

        for (x = x1; x < x2; x++) {
            if (xySwap) {
                retVal.add(Cord.get(y, x));
            } else {
                retVal.add(Cord.get(x, y));
            }

            e += mNum;

            // Deal separately with lines sloping upward and those
            // sloping downward
            if (mNum < 0) {
                if (e < -threshold) {
                    e += mDenom;
                    y--;
                }
            }
            else if (e > threshold) {
                e -= mDenom;
                y++;
            }
        }

        if (xySwap) {
            retVal.add(Cord.get(y, x));
        } else {
            retVal.add(Cord.get(x, y));
        }

        if (!retVal.get(0).equals(c1)){
            Collections.reverse(retVal);
        }
        log.trace("Called bresenhamAlgorithm for {} {}, returning i0={} and ilast={}", c1, c2, retVal.get(0), retVal.get(retVal.size()-1));

        return retVal;
    }
}