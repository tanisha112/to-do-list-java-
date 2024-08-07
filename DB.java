import java.sql.*;
import java.util.Scanner;

public class DB {

    Connection conn = new DBManager().getConnection();

    private String task;

    public void addTask() throws SQLException {

        Scanner scanner = new Scanner(System.in);
        Task newTask = new Task();

        try{

            System.out.println("Enter the task that you want to add to your list:");
            task = scanner.nextLine().toLowerCase();
            newTask.setTask(task);

            String sql = "INSERT INTO todolist.tasks (task) VALUES (?)";

            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, newTask.getTask());

            int rowInserted = preparedStatement.executeUpdate();

            if (rowInserted > 0) {
                System.out.println("The task has been added to your list!");
                System.out.println("______________________");
            } else {
                System.out.println("Something went wrong!");
            }

            userMenu();

        }catch (SQLException e){
            System.out.println("Something went wrong: " + e);
        }
    }

    public static void printList(Connection conn) throws SQLException{

        String sql = "SELECT * FROM todolist.tasks;";

        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();

        while(resultSet.next()){

            int taskID = resultSet.getInt(1);
            String task = resultSet.getString(2);

            String output = "Task: \n\t No.: %d \n\t %s";

            System.out.println(String.format(output,taskID,task));
        }



    }

    public static void deleteTask(Connection conn) throws SQLException{

        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the number of the task that you want to delete:");
        int taskID = scanner.nextInt();

        String sql = "DELETE FROM todolist.tasks WHERE taskID = ?;";

        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setInt(1, taskID);

        int rowDeleted = preparedStatement.executeUpdate();

        if(rowDeleted >0){
            System.out.println("The task was deleted successfully!");
        }else{
            System.out.println("Something went wrong!");
        }

    }

    public void completedTasks(Connection conn) throws SQLException{

        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the number of the task that you want to mark as completed:");
        int completedTask = scanner.nextInt();

        String sql = "DELETE FROM todolist.tasks WHERE taskID = ?;";

        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setInt(1, completedTask);

        int rowDeleted = preparedStatement.executeUpdate();

        if(rowDeleted >0){
            System.out.println("The task was marked as completed successfully and removed from your list!");
            System.out.println("______________________");
        }else{
            System.out.println("Something went wrong!");
        }

        userMenu();



    }

    public void userMenu(){

        Scanner scanner = new Scanner(System.in);

        try{

            boolean quit = false;
            int choice = 0;

            System.out.println("What would you like to do?");

            while(!quit){

                printOptions();
                choice = scanner.nextInt();
                scanner.nextLine();

                switch(choice){
                    case 0:
                        //print all options
                        printOptions();
                        break;
                    case 1:
                        //add task to list
                        addTask();
                        break;
                    case 2:
                        //print the task list
                        printList(conn);
                        break;
                    case 3:
                        //delete task
                        deleteTask(conn);
                        break;
                    case 4:
                        //mark task as completed
                        completedTasks(conn);
                        break;
                    case 5:
                        quit = true;
                        System.out.println("Goodbye!");
                        System.exit(0);
                        break;
                }
            }



        }catch (SQLException e){
            System.out.println("Something went wrong: " + e);
        }

    }

    public static void printOptions(){

        System.out.println("\nPress");
        System.out.println("\t 0 - To print choice options");
        System.out.println("\t 1 - To add a new task to list");
        System.out.println("\t 2 - To print out the TO DO list");
        System.out.println("\t 3 - To delete a task from the list");
        System.out.println("\t 4 - To mark a task as completed");
        System.out.println("\t 5 - To log out");
    }

    }

