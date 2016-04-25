package edu.csula.datascience.acquisition;

import com.google.common.collect.Lists;

import java.util.Collection;
import java.util.function.Consumer;

/**
 * A mock source to provide data
 */
public class MockSource implements Source<MockData> {
    int index = 0;

    @Override
    public boolean hasNext() {
        return index < 1;
    }

    @Override
    public Collection<MockData> next() {
        return Lists.newArrayList(
           new MockData(null, "CMT", "CRD"),
           new MockData("2015-01-27T19:30:40", "CMT", "CSH"),
           new MockData("2015-01-09T15:45:22", "VTS", "DIS")
        );
    }
}
