import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("Enter latitude(xx,xx):");
        double latitude = input.nextDouble();

        System.out.println("Enter longitude(xx,xx):");
        double longitude = input.nextDouble();

        System.out.println("For how many days do you want to know the weather?(Max 7 days):");
        int limit = input.nextInt();

        Api info = new Api();

        try {
            info.getData(latitude, longitude, limit);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
