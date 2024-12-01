import java.util.Scanner;

public class Main {
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Select day: ");
		int day = scanner.nextInt();
		Day task = (Day) Class.forName("Day" + day).newInstance();
		System.out.println("Answer for part 1: ");
		task.part1();
		System.out.println("Answer for part 2: ");
		task.part2();
	}
}