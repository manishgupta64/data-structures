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
		
		System.out.println( cache.get("K1") );

		System.out.println( cache.get("K2") );
		
		System.out.println( cache.get("K5") );
		
		cache.resetCache();
		
	}
}

class MultiLevelCache< K, V >
{
	private LRUCache< K, V > multiLevelCache = null;
	
	private static final int LEVELS = 3;
	
	private static final int MIN_CAPACITY = 3;
	
	public MultiLevelCache() 
	{
		multiLevelCache = new LRUCache< K, V >( LEVELS, MIN_CAPACITY );
	}

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
	
	private void removeLeastRecentlyUsed( Map< K, V > cache, Deque< K > cacheKeys  )
	{
		K lastKey = cacheKeys.removeFirst();
		cache.remove( lastKey );
	}

	public V get( K key )
	{
		V data = null;
		for( int i = 0; i < LEVELS; i++ )
		{
			Map< K, V > currentCache = multiLevelCache.getCache().get( i );
			Deque< K > currentKeys = multiLevelCache.getCacheKeys().get( i );
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
	
	public void resetCache()
	{
		this.multiLevelCache = null;
	}
	
	class LRUCache< K,V > 
	{
		private List< Map< K, V > > cache;
		
		private List< Deque< K > > cacheKeys;
		
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
				cacheKeys.add(i, new LinkedList< K >() );
			}
		}
		
		public List< Map< K, V > > getCache()
		{
			return cache;
		}
		
		public List< Deque< K > > getCacheKeys()
		{
			return cacheKeys;
		}
		
	}

}
