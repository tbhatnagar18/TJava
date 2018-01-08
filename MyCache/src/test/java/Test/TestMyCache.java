package Test;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.Assert;
import org.junit.Test;

import com.tjava.Cache.MyCache;

public class TestMyCache {

	// method to test cache
	// user entered three keys with same value. Only one of them get stored,
	// others get rejected and shown to user.
	@Test
	public void testPutInMyCache() {

		ConcurrentHashMap<String, ArrayList<LocalDateTime>> myCacheMap = new ConcurrentHashMap<String, ArrayList<LocalDateTime>>();
		ArrayList<LocalDateTime> arrayList = new ArrayList<LocalDateTime>();
		arrayList.add(0, LocalDateTime.of(2018, Month.JANUARY, 5, 5, 32, 32));
		arrayList.add(1, LocalDateTime.of(2018, Month.JANUARY, 6, 5, 32, 32));
		myCacheMap.put("sample1", arrayList);

		long expireAfterMinWrite = 1;
		long expireAfterMinRead = 1;
		long cacheSize = 2;

		MyCache<String> myCacheObj = new MyCache<String>(expireAfterMinWrite, expireAfterMinRead, cacheSize,
				myCacheMap);

		myCacheObj.putInMyCache("sample2");
		myCacheObj.putInMyCache("sample2");
		myCacheObj.putInMyCache("sample2");

		Assert.assertEquals(myCacheObj.getMyCacheMap().size(), 1);

	}

}
