import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		int computers = 20;
		double infection = 0.1;
		int dailyRepairs = 5;
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
		System.out.printf("\nComputers: %d\nInfection Rate: %.3f%%\nRepairs: %d\n", computers, infection * 100,
				dailyRepairs);
		scanner.close();
		// Calculate the steps here
	}
}


