

public class QuadraticProbingHashTable {
	private Entry[] entries; // Entry��ü �迭
	private int size, used; // ���� ���� ����ִ� ������ ���Ǿ��� �迭�� ����
	private float loadFactor; // ������
	private final Entry NIL = new Entry(null, null); // Entry������
	
	int collision_Number = 0; // �浹Ƚ��
	
	public QuadraticProbingHashTable(int capacity, float loadFactor) { // �Ű������� �� �� ������ ������
		entries = new Entry[capacity];
		this.loadFactor = loadFactor;
	}

	public QuadraticProbingHashTable(int capacity) { // �Ű������� �� �� ������ ������
		this(capacity, 0.75F);
	}

	public QuadraticProbingHashTable() { // ����Ʈ ������
		this(101);
	}

	public Object get(Object key) { // key�� ���� value���� ��ȯ�ϴ� �޼ҵ�
		int h = hash(key); // h�� key�� ���� �ؽ� �޼ҵ� ��ȯ�� ����
		for (int i = 0; i < entries.length; i++) {
			int j = nextProbe(h, i); // nextProbe ���� j�� ����
			Entry entry = entries[j]; // entries�迭 �ε��� j�� �ش��ϴ� ���� entry�� ����
			if (entry == null)  // entries �迭�� �ε��� j�� ��� ����ִ� ���
				break;
			if (entry == NIL) // entries �迭�� �ε��� j�� ���𰡰� �ִٰ� remove�� ���
				continue;
			if (entry.key.equals(key)) // entries �迭�� �ε��� j�� key���� �Ű������� ���� key���� ��ġ�ϴ� ���
				return entry.value;
		}
		return null; // key�� ã�� ���� ���
	}
	public int getCount() { // �浹Ƚ�� ��ȯ �޼ҵ�
		return collision_Number;
	}
	public Object put(Object key, Object value) { // key�� ���̺� �ִ� �޼ҵ�
		if (used > loadFactor * entries.length) // ������ �ʰ��� rehash
			rehash();
		int h = hash(key);
		int i;
		for (i = 0; i < entries.length; i++) {
			int j = nextProbe(h, i);
			Entry entry = entries[j];
			if (entry == null) { // entry�� null�� ��� entries[j]�� �� ����
				entries[j] = new Entry(key, value);
				++size;
				++used;
				collision_Number += i;
				return null; // insertion success
			}
			if (entry == NIL) // NIL�� ��� ��������� �ǹ�
				continue;
			if (entry.key.equals(key)) { // entry�� Ű�� ���� put�ϰ��� �ϴ� key�� ���� ��� value�� ������Ʈ�Ѵ�.
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

	public int size() { // size ��ȯ �޼ҵ�
		return size;
	}

	private class Entry { // entry Ŭ����
		Object key, value;

		Entry(Object k, Object v) {
			key = k;
			value = v;
		}
	}

	private int hash(Object key) { // hash �޼ҵ�
		if (key == null)
			throw new IllegalArgumentException();
		return (key.hashCode() & 0x7FFFFFFF) % entries.length;
	}

	private int nextProbe(int h, int i) { // �ε��� �� ����
		return (h + i*i) % entries.length; // QuadraticProbing
	}

	private void rehash() { // ��迭
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
