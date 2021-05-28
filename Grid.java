import java.util.Scanner;
import javax.swing.*;
import java.awt.*;

@SuppressWarnings("serial")
public class Grid
{
  private char[][] board;
  private final char blank = 'M';
  private final char occupied = '*';
  private int boardSize = 25;
  private JFrame frame = new JFrame("Game Of Life");

  Scanner input = new Scanner(System.in);

  public Grid()
  {
    EventQueue.invokeLater(new Runnable() 
    {
      @Override
      public void run() 
      {
        try {
          UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
          e.printStackTrace();
        }

        generateNewFrame();
      }
    });
    board = new char[boardSize][boardSize];
    for (int row = 0; row < board.length; row++)
    {
      for (int col = 0; col < board[0].length; col++)
      {
        board[row][col] = blank;
      }
    }
  }

  public class BoardPane extends JPanel
  {
    public BoardPane()
    {
      setLayout(new GridLayout(boardSize,boardSize,0,0));
      setBorder(BorderFactory.createEmptyBorder(2,2,2,2));
      for (int i = 0; i < boardSize; i++)
      {
        for (int j = 0; j < boardSize; j++)
        {
          add(generateCell(i,j));
        }
      }
    }
  }

  public JPanel generateCell(int x, int y)
  {
    JPanel cell = new JPanel();
    if (board[x][y] == '*')
    {
      cell.setBackground(Color.BLACK);
    }
    else
    {
      cell.setBackground(Color.WHITE);
    }
    cell.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    return cell;
  }

  public void generateNewFrame()
  {
    frame.getContentPane().removeAll();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.add(new BoardPane());
    frame.pack();
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
    frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
    frame.revalidate();
    frame.repaint();
  }

  public void startGame()
  {
    editBoard();
    String _continue = "";
    while (_continue.equals(""))
    {
      board = nextGeneration();
      printBoard(board);
      System.out.println("Please press enter to continue, type pause to edit the board, or enter any other text to exit.");
      _continue = input.nextLine();
    }
    if (_continue.toLowerCase().equals("pause"))
    {
      startGame();
    }
    System.exit(0);
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
        try {
          int coordSplit = coordinates.indexOf(",");
          board[Integer.valueOf(coordinates.substring(1,coordSplit))][Integer.valueOf(coordinates.substring(coordSplit + 1,coordinates.length() - 1))] = occupied;
        } catch (Exception e) {
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
    
    generateNewFrame();
    
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
    return nextGen;
  }
}