package edu.csula.datascience.acquisition;

import com.google.common.collect.Lists;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;
import java.util.List;

import static org.junit.Assert.*;

/**
 * A test case to show how to use Collector and Source
 */
public class CollectorTest {
    private Collector<SimpleModel, MockData> collector;
    private Source<MockData> source;
    List<SimpleModel> expectedList = Lists.newArrayList(
   		 new SimpleModel("2015-01-27T19:30:40", "1", "0"),
            new SimpleModel("2015-01-09T15:45:22", "2", "1")
   );
    @Before
    public void setup() {
        collector = new MockCollector();
        source = new MockSource();
    }

    @Test
    public void mungeeTestForNull() throws Exception {
        List<SimpleModel> srcFinal = (List<SimpleModel>) collector.mungee(source.next());
        

        Assert.assertEquals(srcFinal.size(), 2);
    }
    @Test
    public void mungeeTestForNonNumericToNumericConversion() throws Exception {
        List<SimpleModel> srcFinal = (List<SimpleModel>) collector.mungee(source.next());
        
        for(int i=0;i<2;i++){
        	Assert.assertEquals(expectedList.get(i).getVendorId(), srcFinal.get(i).getVendorId());
        	Assert.assertEquals(expectedList.get(i).getPaymentType(), srcFinal.get(i).getPaymentType());
        }
    }
}