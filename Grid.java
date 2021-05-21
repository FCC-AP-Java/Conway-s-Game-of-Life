import java.util.Scanner;

public class Grid
{
  private char[][] board;

  Scanner input = new Scanner(System.in);

  public Grid()
  {
    board = new char[25][25];
    for (int row = 0; row < board.length; row++)
    {
      for (int col = 0; col < board[0].length; col++)
      {
        board[row][col] = 'b';
      }
    }
  }

  public void startGame()
  {
    while(true)
    {
      printBoard(board);
      System.out.println("Please enter the coordinates of your starting colony, 0-24, in the format [0][1], and press enter blank when you are done to begin.");
      String coordinates = input.nextLine();
      if (coordinates.toLowerCase().equals(""))
      {
        break;
      }
      else
      {
        int first = coordinates.indexOf("]");
        int second = coordinates.lastIndexOf("]");
        board[Integer.valueOf(coordinates.substring(1,first))][Integer.valueOf(coordinates.substring(first + 2,second))] = '*';
      }
    }
    String _continue = "";
    while (_continue.equals(""))
    {
      board = nextGeneration();
      System.out.println("Please press enter to continue, or enter any character to exit.");
      _continue = input.nextLine();
    }
  }

  public void printBoard(char[][] matrix)
  {
    System.out.println("Current Board:");
    for (int row = 0; row < matrix.length; row++)
    {
      for (int col = 0; col < matrix[0].length; col++)
      {
        System.out.print(matrix[row][col]);
      }
      System.out.println("");
    }
  }

  public char[][] nextGeneration()
  {
    char[][] nextGen = new char[25][25];
    for (int row = 0; row < nextGen.length; row++)
    {
      for (int col = 0; col < nextGen[0].length; col++)
      {
        nextGen[row][col] = 'b';
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
            if ((i != row || j != col) && (i >= 0 && i <= 24 && j >= 0 && j <= 24))
            {
              if (board[i][j] == '*')
              {
                counter++;
              }
            }
          }
        }
        if ((counter < 2 || counter > 3) && board[row][col] == '*')
        {
          nextGen[row][col] = 'b';
        }
        else if (counter == 3 && board[row][col] == 'b')
        {
          nextGen[row][col] = '*';
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