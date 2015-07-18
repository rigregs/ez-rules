package com.opnitech.rules.core.executor.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

/**
 * Allow to manage a list of ordered elements by priority
 * 
 * @author Rigre Gregorio Garciandia Sonora
 */
public class PriorityList<PriorityOrderedType extends PriorityOrdered> extends ArrayList<PriorityOrderedType> {

    private static final long serialVersionUID = 6132761241854223321L;

    public PriorityList() {

        // NOP
    }

    @Override
    public void add(int location, PriorityOrderedType object) {

        super.add(location, object);
        sort();
    }

    @Override
    public boolean addAll(int location, Collection<? extends PriorityOrderedType> collection) {

        try {
            return super.addAll(location, collection);
        }
        finally {
            sort();
        }
    }

    @Override
    public boolean addAll(Collection<? extends PriorityOrderedType> collection) {

        try {
            return super.addAll(collection);

        }
        finally {
            sort();
        }
    }

    @Override
    public boolean add(PriorityOrderedType object) {

        try {
            return super.add(object);
        }
        finally {
            sort();
        }
    }

    private void sort() {

        Collections.sort(this, new Comparator<PriorityOrderedType>() {

            @Override
            public int compare(PriorityOrderedType priorityOrdered1, PriorityOrderedType priorityOrdered2) {

                return priorityOrdered1.getPriority() == priorityOrdered2.getPriority()
                        ? 0
                        : priorityOrdered1.getPriority() > priorityOrdered2.getPriority()
                                ? 1
                                : -1;
            }
        });
    }
}
