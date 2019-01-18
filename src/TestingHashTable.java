import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TestingHashTable {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		LinearProbingHashTable linear = new LinearProbingHashTable();
		QuadraticProbingHashTable quadratic = new QuadraticProbingHashTable();
		try {
			BufferedReader reader = new BufferedReader(new FileReader("Caesar.txt"));
			// ������Ʈ ������ graph.txt ������ reader�� �ִ´�.
			String line = reader.readLine();
			// line ������ ������ ù ������ �Ҵ�

			while (line != null) {
				linear.put(line.toUpperCase(), 1);
				quadratic.put(line.toUpperCase(), 1);
				line = reader.readLine();
				// �� ���� line�� �Ҵ�
			}
		} catch (IOException e) {
			System.out.println("����ó��");
		}

		System.out.println("�������� ������ �� �浹Ƚ�� : " + linear.getCount());
		System.out.println("Quadratic������ �� �浹Ƚ�� : " + quadratic.getCount());
		System.out.println("");

		System.out.println("***** �� ���̺��� ���� �ܾ���� �� (value) : < I You he Brutus evil the and >");
		System.out.println("I : ��������(" + linear.get("I") + "), ��������(" + quadratic.get("I") + ")");
		System.out.println("You : ��������(" + linear.get("YOU") + "), ��������(" + quadratic.get("YOU") + ")");
		System.out.println("he : ��������(" + linear.get("HE") + "), ��������(" + quadratic.get("HE") + ")");
		System.out.println("Brutus : ��������(" + linear.get("BRUTUS") + "), ��������(" + quadratic.get("BRUTUS") + ")");
		System.out.println("evil : ��������(" + linear.get("EVIL") + "), ��������(" + quadratic.get("EVIL") + ")");
		System.out.println("the : ��������(" + linear.get("THE") + "), ��������(" + quadratic.get("THE") + ")");
		System.out.println("and : ��������(" + linear.get("AND") + "), ��������(" + quadratic.get("AND") + ")");
	}
}