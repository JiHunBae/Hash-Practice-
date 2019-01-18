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
			// 프로젝트 내부의 graph.txt 파일을 reader에 넣는다.
			String line = reader.readLine();
			// line 변수에 파일의 첫 한줄을 할당

			while (line != null) {
				linear.put(line.toUpperCase(), 1);
				quadratic.put(line.toUpperCase(), 1);
				line = reader.readLine();
				// 한 줄을 line에 할당
			}
		} catch (IOException e) {
			System.out.println("예외처리");
		}

		System.out.println("선형조사 에서의 총 충돌횟수 : " + linear.getCount());
		System.out.println("Quadratic에서의 총 충돌횟수 : " + quadratic.getCount());
		System.out.println("");

		System.out.println("***** 각 테이블에서 다음 단어들의 값 (value) : < I You he Brutus evil the and >");
		System.out.println("I : 선형조사(" + linear.get("I") + "), 제곱조사(" + quadratic.get("I") + ")");
		System.out.println("You : 선형조사(" + linear.get("YOU") + "), 제곱조사(" + quadratic.get("YOU") + ")");
		System.out.println("he : 선형조사(" + linear.get("HE") + "), 제곱조사(" + quadratic.get("HE") + ")");
		System.out.println("Brutus : 선형조사(" + linear.get("BRUTUS") + "), 제곱조사(" + quadratic.get("BRUTUS") + ")");
		System.out.println("evil : 선형조사(" + linear.get("EVIL") + "), 제곱조사(" + quadratic.get("EVIL") + ")");
		System.out.println("the : 선형조사(" + linear.get("THE") + "), 제곱조사(" + quadratic.get("THE") + ")");
		System.out.println("and : 선형조사(" + linear.get("AND") + "), 제곱조사(" + quadratic.get("AND") + ")");
	}
}