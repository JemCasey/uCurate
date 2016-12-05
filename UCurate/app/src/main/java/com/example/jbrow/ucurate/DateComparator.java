package com.example.jbrow.ucurate;

import java.util.Comparator;

/**
 * Created by randyflores on 12/4/16.
 */

public class DateComparator implements Comparator<Object> {
    @Override
    public int compare(Object o, Object t1) {
        if (o instanceof Tour && t1 instanceof Tour) {
            Tour obj1 = (Tour) o;
            Tour obj2 = (Tour) t1;
            return obj1.getDate().compareTo(obj2.getDate());
        }
        else if (o instanceof Tour && t1 instanceof Artwork) {
            Tour obj1 = (Tour) o;
            Artwork obj2 = (Artwork) t1;
            return obj1.getDate().compareTo(obj2.getDate());
        }
        else if (o instanceof Artwork && t1 instanceof Tour) {
            Artwork obj1 = (Artwork) o;
            Tour obj2 = (Tour) t1;
            return obj1.getDate().compareTo(obj2.getDate());
        }
        else {
            Artwork obj1 = (Artwork) o;
            Artwork obj2 = (Artwork) t1;
            return obj1.getDate().compareTo(obj2.getDate());
        }
    }
}
