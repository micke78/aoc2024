import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

class Limitations {
	HashMap<Integer, List<Integer>> limitations = new HashMap<>();

	void addLimitation(String rule) {
		String[] vals = rule.split("\\|");
		Integer pageno = Integer.parseInt(vals[0]);
		Integer pageafter = Integer.parseInt(vals[1]);
		if(!limitations.containsKey(pageno)) {
			limitations.put(pageno, new ArrayList<>());
		}
		limitations.get(pageno).add(pageafter);
	}

	static boolean isLimitation(String rule) {
		return rule.contains("|");
	}

	public boolean isValidUpdate(int[] updates) {
		for (int updateNo = 1; updateNo < updates.length; updateNo++) {
			if (limitations.containsKey(updates[updateNo])) {
				for (int after : limitations.get(updates[updateNo])) {
					for(int afterNo = 0; afterNo < updateNo; afterNo++) {
						if (updates[afterNo] == after) return false;
					}
				}
			}
		}
		return true;
	}

	public int[] makeValid(int[] updates) {
		int[] newupdates = updates.clone();
		boolean wasUpdated = true;
		while(wasUpdated) {
			wasUpdated = false;
			for (int updateNo = 1; updateNo < newupdates.length; updateNo++) {
				if (limitations.containsKey(newupdates[updateNo])) {
					for (int after : limitations.get(newupdates[updateNo])) {
						for(int afterNo = 0; afterNo < updateNo; afterNo++) {
							if (newupdates[afterNo] == after) {
								newupdates[afterNo] = newupdates[updateNo];
								newupdates[updateNo] = after;
								wasUpdated = true;
							}
						}
					}
				}
			}
		}
		return newupdates;
	}
}

class Updates {
	List<int[]> updates = new ArrayList<>();

	void addUpdate(String rule) {
		updates.add(Arrays.stream(rule.split(",")).mapToInt(Integer::parseInt).toArray());
	}

	static boolean isUpdate(String rule) {
		return rule.contains(",");
	}

	public int getMatchingMiddles(Limitations limitations) {
		return updates.stream().filter(u -> limitations.isValidUpdate(u)).map(u -> u[u.length/2]).collect(Collectors.summingInt(i -> i));
	}

	public int getSortedInvalidMiddles(Limitations limitations) {
		return updates.stream().filter(u -> !limitations.isValidUpdate(u)).map(limitations::makeValid).map(u -> u[u.length/2]).collect(Collectors.summingInt(i -> i));
	}
}

public class Day5 implements Day {
	Limitations limitations = new Limitations();
	Updates updates = new Updates();

	public Day5(String filename) throws FileNotFoundException {
		Scanner scanner = new Scanner(new File(filename));
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			if (Limitations.isLimitation(line)) limitations.addLimitation(line);
			else if (Updates.isUpdate(line)) updates.addUpdate(line);
		}
	}

	@Override
	public void part1() {
		System.out.println(updates.getMatchingMiddles(limitations));
	}

	@Override
	public void part2() {
		System.out.println(updates.getSortedInvalidMiddles(limitations));

	}
}
