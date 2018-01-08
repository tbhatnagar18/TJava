package com.tjava.Utilities;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map.Entry;

import com.tjava.Cache.MyCache;

public class CacheUtilities {

	// check if last read has exceeded time limit
	public static <K> void clearCacheIfLastAccessExceededTimeLimit(MyCache<K> myCache) {
		K key;
		long minutes;
		key = findLastReadWrite(myCache, 1);
		minutes = ChronoUnit.MINUTES.between(LocalDateTime.now(),
				((ArrayList<LocalDateTime>) myCache.getMyCacheMap().get(key)).get(1));
		if (minutes >= myCache.getExpireAfterMinRead()) {
			myCache.clearMyCache();
			System.out.println("Cache has been cleared as last read exceeded time limit");
		}

	}

	// check if last write has exceeded time limit.
	public static <K> void clearCacheIfLastWriteExceededTimeLimit(MyCache<K> myCache) {
		K key;
		long minutes;
		key = findLastReadWrite(myCache, 0);
		minutes = ChronoUnit.MINUTES.between(LocalDateTime.now(),
				((ArrayList<LocalDateTime>) myCache.getMyCacheMap().get(key)).get(0));
		if (minutes >= myCache.getExpireAfterMinWrite()) {
			myCache.clearMyCache();
			System.out.println("Cache has been cleared as last write exceeded time limit");
		}

	}

	// method to put values in cache after size check
	public static <K> void putInMyCacheAfterSizeCheck(MyCache<K> myCache, K key) {

		ArrayList<LocalDateTime> list;
		synchronized (myCache) {
			if (!(myCache.getMyCacheMap().containsKey(key))) {
				list = new ArrayList<LocalDateTime>();
				list.add(0, LocalDateTime.now());
				list.add(1, LocalDateTime.now());
				myCache.getMyCacheMap().put(key, list);

			} else {
				System.out.println("Value already present in cache");
				list = (ArrayList<LocalDateTime>) myCache.getMyCacheMap().get(key);
				list.set(0, LocalDateTime.now());
			}
		}

	}

	// finds last read/written object based on index
	// if index=0, finds last written object
	// if index=1, finds last read object
	public static <K> K findLastReadWrite(MyCache<K> myCache, int index) {

		Iterator<Entry<K, ArrayList<LocalDateTime>>> it = myCache.getMyCacheMap().entrySet().iterator();
		K leastKey;

		if (it != null) {

			Entry<K, ArrayList<LocalDateTime>> entry = it.next();
			LocalDateTime leastTime = entry.getValue().get(index);
			leastKey = entry.getKey();
			LocalDateTime actualLeastTime;

			while (it.hasNext()) {
				entry = it.next();
				actualLeastTime = entry.getValue().get(index);
				if (actualLeastTime.isBefore(leastTime)) {
					leastTime = actualLeastTime;
					leastKey = entry.getKey();
				}
			}
			return leastKey;
		}
		return null;

	}

}
