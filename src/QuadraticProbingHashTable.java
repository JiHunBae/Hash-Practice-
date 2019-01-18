

public class QuadraticProbingHashTable {
	private Entry[] entries; // Entry객체 배열
	private int size, used; // 현재 값이 들어있는 개수와 사용되어진 배열의 개수
	private float loadFactor; // 적재율
	private final Entry NIL = new Entry(null, null); // Entry생성자
	
	int collision_Number = 0; // 충돌횟수
	
	public QuadraticProbingHashTable(int capacity, float loadFactor) { // 매개변수를 두 개 가지는 생성자
		entries = new Entry[capacity];
		this.loadFactor = loadFactor;
	}

	public QuadraticProbingHashTable(int capacity) { // 매개변수를 한 개 가지는 생성자
		this(capacity, 0.75F);
	}

	public QuadraticProbingHashTable() { // 디폴트 생성자
		this(101);
	}

	public Object get(Object key) { // key에 대한 value값을 반환하는 메소드
		int h = hash(key); // h에 key에 대한 해쉬 메소드 반환값 저장
		for (int i = 0; i < entries.length; i++) {
			int j = nextProbe(h, i); // nextProbe 값을 j에 저장
			Entry entry = entries[j]; // entries배열 인덱스 j에 해당하는 값을 entry에 저장
			if (entry == null)  // entries 배열의 인덱스 j가 계속 비어있던 경우
				break;
			if (entry == NIL) // entries 배열의 인덱스 j에 무언가가 있다가 remove된 경우
				continue;
			if (entry.key.equals(key)) // entries 배열의 인덱스 j의 key값과 매개변수로 받은 key값이 일치하는 경우
				return entry.value;
		}
		return null; // key를 찾지 못한 경우
	}
	public int getCount() { // 충돌횟수 반환 메소드
		return collision_Number;
	}
	public Object put(Object key, Object value) { // key를 테이블에 넣는 메소드
		if (used > loadFactor * entries.length) // 적재율 초과시 rehash
			rehash();
		int h = hash(key);
		int i;
		for (i = 0; i < entries.length; i++) {
			int j = nextProbe(h, i);
			Entry entry = entries[j];
			if (entry == null) { // entry가 null인 경우 entries[j]에 값 저장
				entries[j] = new Entry(key, value);
				++size;
				++used;
				collision_Number += i;
				return null; // insertion success
			}
			if (entry == NIL) // NIL인 경우 지나가라는 의미
				continue;
			if (entry.key.equals(key)) { // entry의 키가 현재 put하고자 하는 key와 같은 경우 value를 업데이트한다.
				Object oldValue = entry.value;
				entries[j].value = (int)oldValue + (int)value;
				collision_Number += i;
				return oldValue; // update success
			}
		}
		collision_Number += i;
		return null; // failure: table overflow
	}

	public Object remove(Object key) {
		int h = hash(key);
		for (int i = 0; i < entries.length; i++) {
			int j = nextProbe(h, i);
			Entry entry = entries[j];
			if (entry == null)
				break;
			if (entry == NIL)
				continue;
			if (entry.key.equals(key)) {
				Object oldValue = entry.value;
				entries[j] = NIL;
				--size;
				return oldValue; // success
			}
		}
		return null; // failure: key not found
	}

	public int size() { // size 반환 메소드
		return size;
	}

	private class Entry { // entry 클래스
		Object key, value;

		Entry(Object k, Object v) {
			key = k;
			value = v;
		}
	}

	private int hash(Object key) { // hash 메소드
		if (key == null)
			throw new IllegalArgumentException();
		return (key.hashCode() & 0x7FFFFFFF) % entries.length;
	}

	private int nextProbe(int h, int i) { // 인덱스 값 조정
		return (h + i*i) % entries.length; // QuadraticProbing
	}

	private void rehash() { // 재배열
		Entry[] oldEntries = entries;
		entries = new Entry[2 * oldEntries.length + 1];
		for (int k = 0; k < oldEntries.length; k++) {
			Entry entry = oldEntries[k];
			if (entry == null || entry == NIL)
				continue;
			int h = hash(entry.key);
			for (int i = 0; i < entries.length; i++) {
				int j = nextProbe(h, i);
				if (entries[j] == null) {
					entries[j] = entry;
					break;
				}
			}
		}
		used = size;
	}
}
