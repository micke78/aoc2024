import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Day1 implements Day
{
	List<Integer> col1 = new ArrayList<>();
	List<Integer> col2 = new ArrayList<>();

	public Day1() throws FileNotFoundException {
		super();
		Scanner scanner = new Scanner(new File("input/input1.txt"));
		while (scanner.hasNextLine()) {
			String[] line = scanner.nextLine().split(" +");
			col1.add(Integer.parseInt(line[0]));
			col2.add(Integer.parseInt(line[1]));
		}
		col1.sort(Integer::compareTo);
		col2.sort(Integer::compareTo);
	}

	@Override
	public void part1() {
		int totalDiff = 0;
		for (int i = 0; i < col1.size(); i++) {
			totalDiff += Math.abs(col1.get(i) - col2.get(i));
		}
		System.out.println(totalDiff);
	}

	@Override
	public void part2() {
		int totalDiff = 0;
		Map<Integer,Long> occurences = col2.stream().collect(Collectors.groupingBy(i -> i, Collectors.counting()));
		List<Integer> distinct = col1.stream().distinct().toList();
		for (int i = 0; i < distinct.size(); i++) {
			try {
				totalDiff += distinct.get(i) * occurences.get(distinct.get(i));
			} catch (Exception e) {}
		}
		System.out.println(totalDiff);
	}
}
