/*
 * Convex hull algorithm - Library (Java)
 *
 * Copyright (c) 2017 Project Nayuki
 * https://www.nayuki.io/page/convex-hull-algorithm
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program (see COPYING.txt and COPYING.LESSER.txt).
 * If not, see <http://www.gnu.org/licenses/>.
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

final class RandomPoint implements Comparable<RandomPoint> {

    public final double x;
    public final double y;

    public RandomPoint(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public String toString() {
        return String.format("Point(%g, %g)", x, y);
    }

    public double getX() { return x; }

    public double getY() { return y; }

    public boolean equals(Object obj) {
        if (!(obj instanceof RandomPoint))
            return false;
        else {
            RandomPoint other = (RandomPoint)obj;
            return x == other.x && y == other.y;
        }
    }

    public int hashCode() {
        return Objects.hash(x, y);
    }

    public int compareTo(RandomPoint other) {
        if (x != other.x)
            return Double.compare(x, other.x);
        else
            return Double.compare(y, other.y);
    }

}