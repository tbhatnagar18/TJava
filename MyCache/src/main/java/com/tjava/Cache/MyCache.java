package com.tjava.Cache;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import com.tjava.Utilities.CacheUtilities;

public class MyCache<K> {

	// Local Date Time stores write time stamp at position 0 and read access at
	// position 1.
	private ConcurrentHashMap<K, ArrayList<LocalDateTime>> myCacheMap;
	private long expireAfterMinWrite;
	private long expireAfterMinRead;
	private long cacheSize;

	public synchronized ConcurrentHashMap<K, ArrayList<LocalDateTime>> getMyCacheMap() {
		return myCacheMap;
	}

	public long getExpireAfterMinWrite() {
		return expireAfterMinWrite;
	}

	public long getExpireAfterMinRead() {
		return expireAfterMinRead;
	}

	public long getCacheSize() {
		return cacheSize;
	}

	// Constructor to initialize the cache with inputs
	public MyCache(long expireAfterMinWrite, long expireAfterMinRead, long cacheSize,
			ConcurrentHashMap<K, ArrayList<LocalDateTime>> myCacheMap) {
		super();
		this.expireAfterMinWrite = expireAfterMinWrite;
		this.expireAfterMinRead = expireAfterMinRead;
		this.cacheSize = cacheSize;
		this.myCacheMap = myCacheMap;
	}

	// method to check size of the cache and put the new value
	public void putInMyCache(K value) {

		// check if last write has exceeded time limit.
		CacheUtilities.clearCacheIfLastWriteExceededTimeLimit(this);

		// check if last read has exceeded time limit
		CacheUtilities.clearCacheIfLastAccessExceededTimeLimit(this);

		// if cache is not time expired, check for cache size
		if (this.myCacheMap.size() >= this.cacheSize) {
			// if size limit occurs, find last accessed element and remove it.
			synchronized (myCacheMap) {
				this.myCacheMap.remove(CacheUtilities.findLastReadWrite(this, 1));
			}

		}

		// put the value in cache after time and size checks.
		CacheUtilities.putInMyCacheAfterSizeCheck(this, value);
	}

	// method to clear cache
	public synchronized void clearMyCache() {
		this.myCacheMap.clear();
	}

	// check if present in cache and reset access timestamp.
	public boolean isPresentInMyCache(K key) {

		if (this.myCacheMap.containsKey(key)) {
			LocalDateTime localDateTime = this.myCacheMap.get(key).get(1);
			localDateTime = LocalDateTime.now();
			return true;
		}

		else
			return false;
	}

}
