package dns;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Class representing a cache of stored DNS records.
 *
 * @version 1.0
 */
public class DNSCache {
	private final ArrayList<DNSRecord> records;

	public DNSCache(){
		records = new ArrayList<>();
	}

	// Add given record to cache
	public synchronized void addRecord(DNSRecord record){
		records.add(record);
	}

	// Return records matching specified params
	public synchronized ArrayList<DNSRecord> getRecords(String name, String type, String rclass){
		ArrayList<DNSRecord> matchingRecords = new ArrayList<>();
        	for (DNSRecord record : records) {
            		if (record.getName().equals(name) &&
                	record.getTypeStr().equals(type) &&
                	record.getClassStr().equals(rclass) &&
                	!record.isExpired()) {
                		matchingRecords.add(record);
            		}
        	}
        	return matchingRecords;
	}

	// Removes any expired records from cache
	public synchronized void cleanup() {
		Iterator<DNSRecord> iterator = records.iterator();
        	while (iterator.hasNext()) {
            		DNSRecord record = iterator.next();
            		if (record.isExpired()) {
                		iterator.remove();
            		}
        	}
	}
}
