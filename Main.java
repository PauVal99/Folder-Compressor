import java.util.Scanner;
import models.File;

public class Main {
  public static void main(String[] args) {
    Scanner input = new Scanner(System.in);
    File f = new File();
    System.out.print("Please insert a file path.");
    f.path = input.next();
  }
}

