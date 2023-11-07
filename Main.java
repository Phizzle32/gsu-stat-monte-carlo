import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Scanner;

enum Status {
	SAFE,
	INFECTED
}
class SimulationResult {
	int infected = 0;
	int days = 0;
	int uniqueInfections = 0;
	boolean allInfected = false;
}

public class Main {
	public static void main(String[] args) {
		int computers = 20;
		double infection = 0.1;
		int dailyRepairs = 5;
		int trials = 10000;
		boolean validInput = true;
		Scanner scanner = new Scanner(System.in);

		System.out.print("Would you like to use custom values (yes/no)? ");
		String input = scanner.nextLine();
		if (input.toLowerCase().contains("yes"))
			validInput = false;

		while (!validInput) {
			try {
				System.out.print("How many computers? ");
				computers = scanner.nextInt();
				scanner.nextLine();
				if (computers < 0) {
					System.out.println("Number of computers cannot be less than 0.");
					continue;
				}

				System.out.print("What is the infection rate? ");
				infection = scanner.nextDouble();
				scanner.nextLine();
				if (infection < 0 || infection > 1) {
					System.out.println("Infection rate must be between 0 and 1.");
					continue;
				}

				System.out.print("How many computers get repaired daily? ");
				dailyRepairs = scanner.nextInt();
				if (dailyRepairs < 0) {
					System.out.println("Number of repairs cannot be less than 0.");
					continue;
				}

				validInput = true;
			} catch (Exception e) {
				System.out.println("Please enter a valid input.");
				scanner.nextLine();
			}
		}
		System.out.printf("\nComputers: %d\nInfection Rate: %.2f%%\nRepairs: %d\n", computers, infection * 100,
				dailyRepairs);
		scanner.close();
		
		double totalInfected = 0;
		double totalDays = 0;
		double uniqueInfections = 0;
		double allInfected = 0;
		for (int i = 0; i < trials; i++) {
			SimulationResult result = simulation(computers, infection, dailyRepairs);
			totalInfected += result.infected;
			totalDays += result.days;
			uniqueInfections += result.uniqueInfections;
			if (result.allInfected) allInfected++;
		}
		
		System.out.println("\nAverage number of computers infected: " + (totalInfected/trials));
		System.out.println("Average number of unique infections: " + (uniqueInfections/trials));
		System.out.println("Average number of days: " + (totalDays/trials));
		System.out.printf("Chance of every computer infected once: %.3f%%", (allInfected/trials) * 100);
	}
	
	// Virus simulation
	public static SimulationResult simulation(int computers, double infectRate, int dailyRepairs) {
		SimulationResult result = new SimulationResult();
		if (computers < 1) return result;
		
		ArrayList<Status> network = new ArrayList<>(Collections.nCopies(computers, Status.SAFE));
		network.set(0, Status.INFECTED);
		HashSet<Integer> log = new HashSet<>();
		
		while (network.contains(Status.INFECTED)) {
			ArrayList<Integer> infectedComputer = new ArrayList<>();
			result.infected += (int) network.stream().filter(e -> e == Status.INFECTED).count();
			
			// Repairman
			for (int i=0; i<dailyRepairs; i++) {
				if (!network.contains(Status.INFECTED)) break;
				infectedComputer.add(network.indexOf(Status.INFECTED));
				log.add(network.indexOf(Status.INFECTED));
				network.set(network.indexOf(Status.INFECTED), Status.SAFE);
			}
			
			// Infect
			for(int i=0; i<network.size(); i++) {
				// Prevents self infection
				if (infectedComputer.contains(i)) continue;
				if (Math.random() <= infectRate) network.set(i, Status.INFECTED);
			}
			result.days++;
		}
		result.allInfected = log.size() == computers;
		result.uniqueInfections = log.size();
		return result;
	}
}
