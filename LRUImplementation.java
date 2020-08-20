import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class LRUImplementation 
{

	public static void main(String[] args) 
	{
		MultiLevelCache< String, String > cache = new MultiLevelCache< String, String >(); 
		cache.put("K1", "V1");
		cache.put("K2", "V2");
		cache.put("K3", "V3");
		cache.put("K4", "V4");

		// print the values from the cache
		System.out.println( cache.get("K1") );
		System.out.println( cache.get("K2") );
		System.out.println( cache.get("K5") );
		
		// reset the cache
		cache.resetCache();
		
		// put the values
		cache.put("K2", "V2");
		cache.put("K3", "V3");
		
		// change the accessibility of keys
		System.out.println( cache.get("K2") );
		
		cache.put("K4", "V4");
		
		// here you can see the actual logic for removing the least recently used data
		cache.put("K5", "V5");
		System.out.println( cache.get("K5") );
		cache.remove("K5");
		System.out.println( cache.get("K5"));
		
	}
}

class MultiLevelCache< K, V >
{
	private LRUCache< K, V > multiLevelCache;
	
	// defines the number of levels in the cache
	private static int LEVELS = 3;
	
	/*
	 * Defines the capacity of the first level cache
	 * Each level has capacity of level_number * MIN_CAPACITY
	 * For Ex: 
	 * 			Level1 has MIN_CAPACITY
	 * 			Level2 has 2 * MIN_CAPACITY
	 * 			Level3 has 3 * MIN_CAPACITY
	 * So each deeper level will have higher capacity than the upper ones  
	 * */
	private static int MIN_CAPACITY = 3;
	
	public MultiLevelCache() 
	{
		multiLevelCache = new LRUCache< K, V >( LEVELS, MIN_CAPACITY );
	}

	public MultiLevelCache( int levels, int minCapacity )
	{
		if( levels <= 0 || minCapacity <= 0 )
		{
			throw new IllegalArgumentException( "Parameters provided for creating Multilevel cache are not allowed.");
		}
		LEVELS = levels;
		MIN_CAPACITY = minCapacity;
		multiLevelCache = new LRUCache< K, V >( levels, minCapacity );
	}
	
	// put the key - value pair data in the cache
	public boolean put( K key, V data )
	{
		for( int  i = 0; i < LEVELS; i++ )
		{
			Map< K, V > currentCache =  multiLevelCache.getCache().get( i );
			Deque< K > cacheKeys = multiLevelCache.getCacheKeys().get( i );
			if( currentCache.size() == ( (i + 1 ) * MIN_CAPACITY ) )
			{
				removeLeastRecentlyUsed( currentCache, cacheKeys );
			}
			currentCache.put( key, data  );
			cacheKeys.add( key );
		}
		return true;
	}
	
	// remove the least recently used key value pair from current level cache
	private void removeLeastRecentlyUsed( Map< K, V > cache, Deque< K > cacheKeys  )
	{
		K lastKey = cacheKeys.removeFirst();
		cache.remove( lastKey );
	}

	// Get the data from the cache using key
	public V get( K key )
	{
		V data = null;
		for( int i = 0; i < LEVELS; i++ )
		{
			Map< K, V > currentCache = getCacheAtLevel(i);
			Deque< K > currentKeys = getCacheKeysAtLevel(i);
			if( currentCache.containsKey( key ) )
			{
				data = currentCache.get( key );
				// change the priority
				currentKeys.remove( key );
				currentKeys.add(key);
			}
		}
		return data;
	}
	
	// Remove the data using key
	public void remove( K key )
	{
		if( key == null )
		{
			throw new IllegalArgumentException("Key is null.");
		}
		for( int i = 0; i < LEVELS; i++ )
		{
			Map< K, V > currentCache = getCacheAtLevel(i);
			Deque< K > currentKeys = getCacheKeysAtLevel(i);
			if( currentCache.containsKey( key ) )
			{
				currentCache.remove(key);
				currentKeys.remove(key);
			}
		}
	}
	
	private Map< K, V > getCacheAtLevel( int level )
	{
		return multiLevelCache.getCache().get( level );
	}
	
	private Deque< K > getCacheKeysAtLevel( int level )
	{
		return multiLevelCache.getCacheKeys().get( level );
	}
	
	// clears the cache at all possible levels 
	public void resetCache()
	{
		if( this.multiLevelCache != null )
		{
			for( int i = 0; i < LEVELS; i++ )
			{
				getCacheAtLevel(i).clear();
				getCacheKeysAtLevel(i).clear();
			}
		}
	}
	
	private class LRUCache< K1, V1 > 
	{
		private List< Map< K1, V1 > > cache;
		
		private List< Deque< K1 > > cacheKeys;
		
		LRUCache( int levels, int capacity )
		{
			if( levels <= 0 )
			{
				levels = 1;
			}
			
			cache = new ArrayList<>( levels );
			cacheKeys = new ArrayList<>( levels );
			for( int i = 0; i < levels; i++ )
			{
				cache.add( new LinkedHashMap<>( i * capacity ) );
				cacheKeys.add(i, new LinkedList< K1 >() );
			}
		}
		
		public List< Map< K1, V1 > > getCache()
		{
			return cache;
		}
		
		public List< Deque< K1 > > getCacheKeys()
		{
			return cacheKeys;
		}
	}
}
