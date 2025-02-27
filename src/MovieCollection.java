import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class MovieCollection
{
    private ArrayList<Movie> movies;
    private Scanner scanner;
    private ArrayList<String> castList;
    private ArrayList<String> genreList;

    public MovieCollection(String fileName)
    {
        importMovieList(fileName);
        scanner = new Scanner(System.in);
        castList = new ArrayList<String>();
        for (Movie movie:movies){
            String[] cast = movie.getCast().split("\\|");
            for (String s : cast) {
                if (!castList.contains(s)) {
                    castList.add(s.toLowerCase());
                }
            }
        }
        for (int j = 1; j < castList.size(); j++) {
        String temp = castList.get(j);
        int possibleIndex = j;
        while (possibleIndex > 0 && temp.compareTo(castList.get(possibleIndex - 1)) < 0)
        {
            castList.set(possibleIndex, castList.get(possibleIndex - 1));
            possibleIndex--;
        }
        castList.set(possibleIndex, temp);
        }
        genreList = new ArrayList<String>();
        for (Movie movie:movies) {
            String[] genre = movie.getGenres().split("\\|");
            for (String s : genre) {
                if (!genreList.contains(s.toLowerCase())) {
                    genreList.add(s.toLowerCase());
                }
            }
        }
        for (int j = 1; j < genreList.size(); j++)
        {
            String temp = genreList.get(j);
            int possibleIndex = j;
            while (possibleIndex > 0 && temp.compareTo(genreList.get(possibleIndex - 1)) < 0)
            {
                genreList.set(possibleIndex, genreList.get(possibleIndex - 1));
                possibleIndex--;
            }
            genreList.set(possibleIndex, temp);
        }
    }

    public ArrayList<Movie> getMovies()
    {
        return movies;
    }

    public void menu()
    {
        String menuOption = "";

        System.out.println("Welcome to the movie collection!");
        System.out.println("Total: " + movies.size() + " movies");

        while (!menuOption.equals("q"))
        {
            System.out.println("------------ Main Menu ----------");
            System.out.println("- search (t)itles");
            System.out.println("- search (k)eywords");
            System.out.println("- search (c)ast");
            System.out.println("- see all movies of a (g)enre");
            System.out.println("- list top 50 (r)ated movies");
            System.out.println("- list top 50 (h)igest revenue movies");
            System.out.println("- (q)uit");
            System.out.print("Enter choice: ");
            menuOption = scanner.nextLine();

            if (!menuOption.equals("q"))
            {
                processOption(menuOption);
            }
        }
    }

    private void processOption(String option)
    {
        if (option.equals("t"))
        {
            searchTitles();
        }
        else if (option.equals("c"))
        {
            searchCast();
        }
        else if (option.equals("k"))
        {
            searchKeywords();
        }
        else if (option.equals("g"))
        {
            listGenres();
        }
        else if (option.equals("r"))
        {
            listHighestRated();
        }
        else if (option.equals("h"))
        {
            listHighestRevenue();
        }
        else
        {
            System.out.println("Invalid choice!");
        }
    }

    private void searchTitles()
    {
        System.out.print("Enter a title search term: ");
        String searchTerm = scanner.nextLine();

        // prevent case sensitivity
        searchTerm = searchTerm.toLowerCase();

        // arraylist to hold search results
        ArrayList<Movie> results = new ArrayList<Movie>();

        // search through ALL movies in collection
        for (int i = 0; i < movies.size(); i++)
        {
            String movieTitle = movies.get(i).getTitle();
            movieTitle = movieTitle.toLowerCase();

            if (movieTitle.indexOf(searchTerm) != -1)
            {
                //add the Movie object to the results list
                results.add(movies.get(i));
            }
        }

        // sort the results by title
        sortResults(results);

        // now, display them all to the user
        for (int i = 0; i < results.size(); i++)
        {
            String title = results.get(i).getTitle();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = results.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void sortResults(ArrayList<Movie> listToSort)
    {
        for (int j = 1; j < listToSort.size(); j++)
        {
            Movie temp = listToSort.get(j);
            String tempTitle = temp.getTitle();

            int possibleIndex = j;

            while (possibleIndex > 0 && tempTitle.compareTo(listToSort.get(possibleIndex - 1).getTitle()) < 0)
            {
                listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
                possibleIndex--;
            }
            listToSort.set(possibleIndex, temp);
        }
    }

    private void displayMovieInfo(Movie movie)
    {
        System.out.println();
        System.out.println("Title: " + movie.getTitle());
        System.out.println("Tagline: " + movie.getTagline());
        System.out.println("Runtime: " + movie.getRuntime() + " minutes");
        System.out.println("Year: " + movie.getYear());
        System.out.println("Directed by: " + movie.getDirector());
        System.out.println("Cast: " + movie.getCast());
        System.out.println("Overview: " + movie.getOverview());
        System.out.println("User rating: " + movie.getUserRating());
        System.out.println("Box office revenue: " + movie.getRevenue());
    }

    private void searchCast()
    {
        System.out.print("Enter a cast member: ");
        String castMember = scanner.nextLine();

        castMember = castMember.toLowerCase();
        ArrayList<String> results = new ArrayList<String>();

        for(String cast:castList){
            if (cast.contains(castMember)&&!results.contains(cast)){
                results.add(cast);
            }
        }

        for(int i=0; i<results.size();i++){
            System.out.println((i+1)+". "+results.get(i));
        }

        System.out.print("Enter a # to learn more: ");
        String more = scanner.nextLine();

        String actor = results.get(Integer.parseInt(more)-1);

        ArrayList<Movie> resultsCast = new ArrayList<Movie>();
            for (Movie movie : movies) {
                if (movie.getCast().toLowerCase().contains(actor.toLowerCase())) {
                    resultsCast.add(movie);
                }
            }
        sortResults(resultsCast);

        for(int i=0; i<resultsCast.size();i++){
            System.out.println(i+1+". "+resultsCast.get(i).getTitle());
        }

        System.out.print("Enter a # to learn more: ");
        int more2 = Integer.parseInt(scanner.nextLine());

        displayMovieInfo(resultsCast.get(more2-1));

    }

    private void searchKeywords()
    {
        System.out.print("Enter a keyword search term: ");
        String searchTerm = scanner.nextLine();

        // prevent case sensitivity
        searchTerm = searchTerm.toLowerCase();

        // arraylist to hold search results
        ArrayList<Movie> results = new ArrayList<Movie>();

        // search through ALL movies in collection
        for (int i = 0; i < movies.size(); i++)
        {
            String movieKeys = movies.get(i).getKeywords();
            movieKeys = movieKeys.toLowerCase();

            if (movieKeys.indexOf(searchTerm) != -1)
            {
                //add the Movie object to the results list
                results.add(movies.get(i));
            }
        }

        // sort the results by title
        sortResults(results);

        // now, display them all to the user
        for (int i = 0; i < results.size(); i++)
        {
            String title = results.get(i).getTitle();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = results.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void listGenres()
    {
        for(int i=0;i<genreList.size();i++){
            System.out.println(i+1+". "+genreList.get(i));;
        }
        System.out.print("Enter a # to learn more: ");
        String more = scanner.nextLine();

        String genre = genreList.get(Integer.parseInt(more)-1);

        ArrayList<Movie> resultsGenre = new ArrayList<Movie>();
        for (Movie movie : movies) {
            if (movie.getGenres().toLowerCase().contains(genre.toLowerCase())) {
                resultsGenre.add(movie);
            }
        }
        sortResults(resultsGenre);

        for(int i=0; i<resultsGenre.size();i++){
            System.out.println(i+1+". "+resultsGenre.get(i).getTitle());
        }

        System.out.print("Enter a # to learn more: ");
        int more2 = Integer.parseInt(scanner.nextLine());

        displayMovieInfo(resultsGenre.get(more2-1));
    }

    private void listHighestRated()
    {
        ArrayList<Double> ratings = new ArrayList<Double>();
        for(Movie movie:movies){
            ratings.add(movie.getUserRating());
        }
        ArrayList<Movie> results = new ArrayList<Movie>();
        for (int i=0; i<50;i++) {
            double greatest = 0.0;
            for (double rating : ratings) {
                if (rating > greatest) {
                    greatest = rating;
                }
            }
            for (Movie movie : movies) {
                if (movie.getUserRating() == greatest && results.size()<50 && !results.contains(movie)) {
                    results.add(movie);
                }
            }
            ratings.remove(greatest);
        }
        for(int i=0;i<results.size();i++){
            System.out.println(i+1+". "+results.get(i).getTitle()+" Rating: "+results.get(i).getUserRating());
        }

        System.out.print("Enter a # to learn more: ");
        int more2 = Integer.parseInt(scanner.nextLine());

        displayMovieInfo(results.get(more2-1));
    }

    private void listHighestRevenue()
    {
        ArrayList<Double> revenues = new ArrayList<Double>();
        for(Movie movie:movies){
            revenues.add((double) movie.getRevenue());
        }
        ArrayList<Movie> results = new ArrayList<Movie>();
        for (int i=0; i<50;i++) {
            double greatest = 0.0;
            for (Double revenue : revenues) {
                if (revenue > greatest) {
                    greatest = revenue;
                }
            }
            for (Movie movie : movies) {
                if (movie.getRevenue() == greatest && results.size()<50 && !results.contains(movie)) {
                    results.add(movie);
                }
            }
            revenues.remove(greatest);
        }
        for(int i=0;i<results.size();i++){
            System.out.println(i+1+". "+results.get(i).getTitle()+" Revenue: "+results.get(i).getRevenue());
        }

        System.out.print("Enter a # to learn more: ");
        int more2 = Integer.parseInt(scanner.nextLine());
        displayMovieInfo(results.get(more2-1));
    }

    private void importMovieList(String fileName)
    {
        try
        {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();

            movies = new ArrayList<Movie>();

            while ((line = bufferedReader.readLine()) != null)
            {
                String[] movieFromCSV = line.split(",");

                String title = movieFromCSV[0];
                String cast = movieFromCSV[1];
                String director = movieFromCSV[2];
                String tagline = movieFromCSV[3];
                String keywords = movieFromCSV[4];
                String overview = movieFromCSV[5];
                int runtime = Integer.parseInt(movieFromCSV[6]);
                String genres = movieFromCSV[7];
                double userRating = Double.parseDouble(movieFromCSV[8]);
                int year = Integer.parseInt(movieFromCSV[9]);
                int revenue = Integer.parseInt(movieFromCSV[10]);

                Movie nextMovie = new Movie(title, cast, director, tagline, keywords, overview, runtime, genres, userRating, year, revenue);
                movies.add(nextMovie);
            }
            bufferedReader.close();
        }
        catch(IOException exception)
        {
            // Print out the exception that occurred
            System.out.println("Unable to access " + exception.getMessage());
        }
    }
}