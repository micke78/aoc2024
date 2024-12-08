import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day3 implements Day {
	private String program;
	Pattern muls_re = Pattern.compile("mul\\(([0-9]+),([0-9]+)\\)");

	public Day3(String filename) throws FileNotFoundException {
		Scanner scanner = new Scanner(new File(filename));
		StringBuilder input = new StringBuilder();
		while (scanner.hasNextLine()) {
			input.append(scanner.nextLine());
		}
		program = input.toString();
	}

	private int findMuls(String input) {
		int muls = muls_re.matcher(input).results()
				.map(r -> Integer.parseInt(r.group(1)) * Integer.parseInt(r.group(2)))
				.collect(Collectors.summingInt(Integer::intValue)).intValue();
		return muls;
	}

	private String disableParts(String input) {
		String[] parts = input.split("do(n't)?");
		Pattern dodonts_re = Pattern.compile("do(n't)?");
		List<String> dodonts = dodonts_re.matcher(input).results()
				.map(r -> r.group())
				.collect(Collectors.toList());
		StringBuilder output = new StringBuilder();
		output.append(parts[0]);
		for(int i = 1; i < parts.length; i++) {
			if(dodonts.get(i-1).equals("do")) output.append(parts[i]);
		}
		return output.toString();
	}
	@Override
	public void part1() {
		System.out.println(findMuls(program));
	}

	@Override
	public void part2() {
		String newprogram = disableParts(program);
		System.out.println(findMuls(newprogram));
	}
}
