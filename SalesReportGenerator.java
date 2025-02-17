import java.util.Scanner;

public class SalesReportGenerator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the total number of products: ");
        int numberOfProducts = getValidNumber();

        double totalSales = 0.0;
        double highestSales = 0.0;
        String highestSellingProduct = "";
        double[] salesData = new double[numberOfProducts];
        String[] productNames = new String[numberOfProducts];

        for (int i = 0; i < numberOfProducts; i++) {
            System.out.print("Enter the name of product " + (i + 1) + ": ");
            productNames[i] = scanner.nextLine();  // Store product name

            double sales = -1;
            while (sales < 0) {
                System.out.print("Enter the sales value for " + productNames[i] + ": ");
                sales = getValidSales();
                if (sales < 0) {
                    System.out.println("Sales value cannot be negative. Please try again.");
                }
            }
            salesData[i] = sales;
            totalSales += sales;

            if (sales > highestSales) {
                highestSales = sales;
                highestSellingProduct = productNames[i];
            }
        }

        double averageSales = totalSales / numberOfProducts;

        System.out.println("\nSales Report:");
        System.out.println("Total Sales: $" + totalSales);
        System.out.println("Average Sales: $" + averageSales);
        System.out.println("Highest Selling Product: " + highestSellingProduct + " with sales of $" + highestSales);

        scanner.close();
    }

    private static int getValidNumber() {
        Scanner scanner = new Scanner(System.in);
        int number = -1;
        boolean valid = false;
        while (!valid) {
            try {
                number = Integer.parseInt(scanner.nextLine());
                if (number > 0) {
                    valid = true;
                } else {
                    System.out.print("Please enter a positive number: ");
                }
            } catch (NumberFormatException e) {
                System.out.print("Invalid input! Please enter a valid number: ");
            }
        }
        return number;
    }

    private static double getValidSales() {
        Scanner scanner = new Scanner(System.in);
        double sales = -1;
        boolean valid = false;
        while (!valid) {
            try {
                sales = Double.parseDouble(scanner.nextLine());
                if (sales >= 0) {
                    valid = true;
                } else {
                    System.out.print("Sales value must be non-negative. Please try again: ");
                }
            } catch (NumberFormatException e) {
                System.out.print("Invalid input! Please enter a valid sales amount: ");
            }
        }
        return sales;
    }
}
