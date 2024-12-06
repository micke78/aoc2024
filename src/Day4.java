import java.io.File;
import java.io.FileNotFoundException;
import java.util.Optional;
import java.util.Scanner;

class Matrix {
	private int width;
	private int height;
	String data;

	public Matrix(String data, int width) {
		this.data = data;
		this.width = width;
		this.height = data.length()/width;
	}
	public Optional<Character> getPoint(int x, int y) {
		if (x < 0 || x >= width || y < 0 || y >= height) return Optional.empty();
		return Optional.of(data.charAt(y*width + x));
	}

	public boolean pointMatches(int x, int y, char match) {
		Optional<Character> point =  getPoint(x, y);
		if(point.isPresent()) return point.get().charValue() == match;
		return false;
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public boolean checkDirection(int x, int y, int offsetX, int offsetY, String match) {
		if(!pointMatches(x , y , match.charAt(0))) return false;
		if(match.length() == 1) return true;
		return checkDirection(x+ offsetX, y + offsetY, offsetX, offsetY, match.substring(1));
	}

	public int countMatches(int x, int y, String match, int startValue) {
		if(pointMatches(x,y,match.charAt(0))) {
			for(int offsetX = -1; offsetX <= 1; offsetX++) {
				for(int offsetY = -1; offsetY <= 1; offsetY++) {
					if(offsetX == 0 && offsetY == 0) continue;
					if (checkDirection(x+ offsetX, y+ offsetY, offsetX, offsetY, match.substring(1))) startValue+=1;
				}
			}
		}
		return startValue;
	}
	public boolean isMAScross(int x, int y) {
		int[][] offsets = {{-1,-1},{1,-1}};

		if(!pointMatches(x, y, 'A')) return false;
		for(int[] offset : offsets) {
			Optional<Character> point1 = getPoint(x+offset[0], y+offset[1]);
			Optional<Character> point2 = getPoint(x-offset[0], y-offset[1]);
			if(point1.isEmpty() || point2.isEmpty()) return false;
			if(point1.get().equals('M') && point2.get().equals('S')) continue;
			if(point1.get().equals('S') && point2.get().equals('M')) continue;
			return false;
		}
		return true;
	}
}

public class Day4 implements Day {
	Matrix matrix;

	public Day4(String filename) throws FileNotFoundException {
		try (Scanner scanner = new Scanner(new File(filename))) {
			StringBuilder input = new StringBuilder();
			input.append(scanner.nextLine());
			int width = input.length();
			while (scanner.hasNextLine()) {
				input.append(scanner.nextLine());
			}
			matrix = new Matrix(input.toString(), width);
		}
	}

	@Override
	public void part1() {
		int matches = 0;
		String toFind = "XMAS";
		for (int x = 0; x < matrix.getWidth(); x++) {
			for (int y = 0; y < matrix.getHeight(); y++) {
				matches = matrix.countMatches(x, y, toFind, matches);
			}
		}
		System.out.println(matches);
	}

	@Override
	public void part2() {
		int matches = 0;
		for (int x = 1; x < matrix.getWidth() - 1; x++) {
			for (int y = 1; y < matrix.getHeight() -1; y++) {
				if(matrix.isMAScross(x, y)) matches++;
			}
		}
		System.out.println(matches);
	}
}
