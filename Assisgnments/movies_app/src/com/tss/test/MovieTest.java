package com.tss.test;

import java.util.Scanner;

import com.tss.model.Movie;
import com.tss.model.MovieManager;
import com.tss.model.MovieStoreEmptyException;
import com.tss.model.MovieStoreFullException;
import com.tss.model.NoSuchMovieExceptionFound;


public class MovieTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MovieManager manager = new MovieManager();

        while (true) {
            System.out.println("\n========== Movie Management ==========");
            System.out.println("1. Display movies");
            System.out.println("2. Add movie");
            System.out.println("3. Delete movie by ID");
            System.out.println("4. Find the movie by movie ID");
            System.out.println("5. Clear all movies");
            System.out.println("6. Exit");
            System.out.print("Enter choice: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("Please enter a valid number.");
                continue;
            }

            try {
                switch (choice) {
                    case 1:
                        manager.displayMovies();
                        break;

                    case 2:
                        System.out.print("Enter movie name: ");
                        String name = scanner.nextLine();

                        String genre;
                        while (true) {
                            System.out.print("Enter genre (letters only): ");
                            genre = scanner.nextLine();
                            if (genre.matches("[a-zA-Z]+")) break;
                            System.out.println("Invalid genre. Only letters allowed.");
                        }

                        int year;
                        while (true) {
                            System.out.print("Enter year (YYYY): ");
                            String yearStr = scanner.nextLine();
                            if (yearStr.matches("\\d{4}")) {
                                year = Integer.parseInt(yearStr);
                                break;
                            }
                            System.out.println("Year must be 4 digits.");
                        }

                        manager.addMovie(name, genre, year);
                        break;

                    case 3:
                        System.out.print("Enter movie ID to delete: ");
                        String id = scanner.nextLine();
                        manager.deleteMovie(id);
                        break;
                        
                    case 4:
                        System.out.print("Enter movie ID to search: ");
                        String searchId = scanner.nextLine();
                        try {
                            Movie foundMovie = manager.findMovieById(searchId);
                            System.out.println("+--------+----------------------+------------+------+" );
                            System.out.printf("| %-6s | %-20s | %-10s | %-4s |\n", "ID", "Name", "Genre", "Year");
                            System.out.println("+--------+----------------------+------------+------+" );
                            System.out.printf("| %-6s | %-20s | %-10s | %-4d |\n",
                                    foundMovie.id,
                                    foundMovie.name.length() > 20 ? foundMovie.name.substring(0, 17) + "..." : foundMovie.name,
                                    foundMovie.genre.length() > 10 ? foundMovie.genre.substring(0, 9) + "…" : foundMovie.genre,
                                    foundMovie.year);
                            System.out.println("+--------+----------------------+------------+------+\n");
                        } catch (NoSuchMovieExceptionFound exception) {
                            System.out.println(exception.getMessage());
                        }
                        break;
    

                    case 5:
                        manager.clearMovies();
                        break;

                    case 6:
                        System.out.println("Exiting...");
                        scanner.close();
                        return;

                    default:
                        System.out.println("Invalid choice.");
                }
            } catch (MovieStoreFullException | MovieStoreEmptyException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
