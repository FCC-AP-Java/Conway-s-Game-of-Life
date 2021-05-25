import java.util.Scanner;

public class Grid
{
  private char[][] board;
  private final char blank = 'M';
  private final char occupied = '*';
  private int boardSize = 25;

  Scanner input = new Scanner(System.in);

  public Grid()
  {
    board = new char[boardSize][boardSize];
    for (int row = 0; row < board.length; row++)
    {
      for (int col = 0; col < board[0].length; col++)
      {
        board[row][col] = blank;
      }
    }
  }

  public Grid(int s)
  {
    boardSize = s;
    board = new char[s][s];
    for (int row = 0; row < board.length; row++)
    {
      for (int col = 0; col < board[0].length; col++)
      {
        board[row][col] = blank;
      }
    }
  }

  public void startGame()
  {
    editBoard();
    String _continue = "";
    while (_continue.equals(""))
    {
      board = nextGeneration();
      System.out.println("Please press enter to continue, type pause to edit the board, or enter any other text to exit.");
      _continue = input.nextLine();
    }
    if (_continue.toLowerCase().equals("pause"))
    {
      startGame();
    }
  }

  public void editBoard()
  {
    while(true)
    {
      printBoard(board);
      System.out.println("Please enter the coordinates of your colony, 0-" + (boardSize - 1) + ", in the format (0,1), and press enter when you are ready to start.");
      String coordinates = input.nextLine();
      if (coordinates.equals(""))
      {
        break;
      }
      else
      {
        try
        {
          int coordSplit = coordinates.indexOf(",");
          board[Integer.valueOf(coordinates.substring(1,coordSplit))][Integer.valueOf(coordinates.substring(coordSplit + 1,coordinates.length() - 1))] = occupied;
        }
        catch (Exception e)
        {
          System.out.println("You have entered an invalid input. Try again.");
        }
      }
    }
  }

  public void printBoard(char[][] matrix)
  {
    System.out.println("Current Board:");
    System.out.print(" X");
    for (int i = 0; i < matrix.length; i++)
    {
      System.out.print((i >= 10) ? (i + " ") : (" " + i + " "));
    }
    System.out.println("");
    for (int row = 0; row < matrix.length; row++)
    {
      System.out.print((row >= 10) ? (row + " ") : (" " + row + " "));
      for (int col = 0; col < matrix[0].length; col++)
      {
        System.out.print(matrix[row][col]+ "  ");
      }
      System.out.println("");
    }
  }

  public char[][] nextGeneration()
  {
    char[][] nextGen = new char[boardSize][boardSize];
    for (int row = 0; row < nextGen.length; row++)
    {
      for (int col = 0; col < nextGen[0].length; col++)
      {
        nextGen[row][col] = blank;
      }
    }
    for (int row = 0; row < nextGen.length; row++)
    {
      for (int col = 0; col < nextGen[0].length; col++)
      {
        int counter = 0;
        for (int i = row - 1; i <= row + 1; i++)
        {
          for (int j = col - 1; j <= col + 1; j++)
          {
            if ((i != row || j != col) && (i >= 0 && i <= (boardSize - 1) && j >= 0 && j <= (boardSize - 1)))
            {
              if (board[i][j] == occupied)
              {
                counter++;
              }
            }
          }
        }
        if ((counter < 2 || counter > 3) && board[row][col] == occupied)
        {
          nextGen[row][col] = blank;
        }
        else if (counter == 3 && board[row][col] == blank)
        {
          nextGen[row][col] = occupied;
        }
        else
        {
          nextGen[row][col] = board[row][col];
        }
      }
    }
    printBoard(nextGen);
    return nextGen;
  }
}