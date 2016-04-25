package edu.csula.datascience.acquisition;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * A mock implementation of collector for testing
 */
public class MockCollector implements Collector<SimpleModel, MockData> {
    @Override
    public Collection<SimpleModel> mungee(Collection<MockData> src) {
        // in your example, you might need to check src.hasNext() first
        Collection<SimpleModel> srcFinal =  src
            .stream()
            .filter(data -> data.getPickUpDateTime() != null)
            .map(SimpleModel::build)
            .collect(Collectors.toList());
        
        for (SimpleModel eachSrc : srcFinal) {
			if(eachSrc.getPaymentType().equals("DIS")){
				eachSrc.setPaymentType("1");
			}
			else{
				eachSrc.setPaymentType("0");
			}
			if(eachSrc.getVendorId().equals("CMT")){
				eachSrc.setVendorId("1");
			}else{
				eachSrc.setVendorId("2");
			}
		}
        
        return srcFinal;
        		
    }

    @Override
    public void save(Collection<SimpleModel> data) {
    }
}
