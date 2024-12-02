import java.lang.reflect.InvocationTargetException;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Select day: ");
		int day = scanner.nextInt();
		System.out.println("Debug: ");
		String file = scanner.next().toLowerCase().charAt(0) == 'y' ? "example" : "input";

		Day task = (Day) Class.forName("Day" + day).getDeclaredConstructor(String.class).newInstance("input/" + file + day + ".txt");
		System.out.println("Answer for part 1: ");
		task.part1();
		System.out.println("Answer for part 2: ");
		task.part2();
	}
}