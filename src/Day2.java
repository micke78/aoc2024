import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

class Report {
	List<Integer> levels;

	public Report(List<Integer> levels) {
		this.levels = levels;
	}

	public boolean isSafe(int dampen) {
		boolean growing = isSafeGrowing(false, dampen);
		boolean shrinking = isSafeGrowing(true, dampen);
		return  growing || shrinking;
	}

	public boolean isSafeGrowing(boolean reverse, int dampen) {
		int last = levels.getFirst();
		for (int i = 1; i < levels.size(); i++) {
			int current = levels.get(i);
			boolean danger = reverse ? isDanger(current, last) : isDanger(last, current);
			if (danger) {
				if (dampen-- <= 0) return false;
				else {
					List<Integer> newlist = new ArrayList<>(levels);
					newlist.remove(i - 1);
					boolean test1 = new Report(newlist).isSafe(dampen);
					newlist = new ArrayList<>(levels);
					newlist.remove(i);
					boolean test2 = new Report(newlist).isSafe(dampen);
					return test1 || test2;
				}
			}
			last = current;
		}
		return true;
	}

	public boolean isDanger(int lower, int higher) {
		return higher <= lower || higher - lower > 3;
	}

}

public class Day2 implements Day {
	List<Report> reports = new ArrayList<>();

	public Day2(String filename) throws FileNotFoundException {
			super();
			Scanner scanner = new Scanner(new File(filename));
	//		Scanner scanner = new Scanner(new File("input/example2.txt"));
			while (scanner.hasNextLine()) {
				reports.add(new Report(Arrays.stream(scanner.nextLine().split(" +"))
						.map(Integer::parseInt).collect(Collectors.toUnmodifiableList())
				));
			}
		}

	@Override
	public void part1() {
		System.out.println(reports.stream().filter(report -> report.isSafe(0)).count());
	}

	@Override
	public void part2() {
		System.out.println(reports.stream().filter(report -> report.isSafe(1)).count());
	}
}

