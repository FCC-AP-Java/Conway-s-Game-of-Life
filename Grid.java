import java.util.Scanner;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

@SuppressWarnings("serial")
public class Grid
{
  private char[][] board;
  private final char blank = 'M';
  private final char occupied = '*';
  private int boardSize;
  private JFrame frame = new JFrame("Game Of Life");
  private JPanel[][] panelArray;

  Scanner input = new Scanner(System.in);

  public Grid()
  {
    System.out.println("The default board size is 25x25. If you would like to change it, please enter your desired length as a positive whole number. Otherwise, enter any other text or press enter.");
    String size = input.nextLine();
    try {
      if (Integer.valueOf(size) <= 0)
      {
        throw new NumberFormatException();
      }
      boardSize = Integer.valueOf(size);
    } catch (NumberFormatException e) {
      boardSize = 25;
    }
    panelArray = new JPanel[boardSize][boardSize];
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
      addMouseListener(new MouseAdapter() 
      {
        @Override
        public void mousePressed(MouseEvent e)
        {
          JPanel panel = (JPanel)getComponentAt(e.getPoint());
          for (int i = 0; i < panelArray.length; i++)
          {
            for (int j = 0; j < panelArray.length; j++)
            {
              if (panelArray[i][j] == panel)
              {
                if (board[i][j] == occupied)
                {
                  board[i][j] = blank;
                }
                else if (board[i][j] == blank)
                {
                  board[i][j] = occupied;
                }
                printBoard(board);
              }
            }
          }
        }
      });
    }
  }

  public JPanel generateCell(int x, int y)
  {
    JPanel cell = new JPanel();
    if (board[x][y] == occupied)
    {
      cell.setBackground(Color.BLACK);
    }
    else
    {
      cell.setBackground(Color.WHITE);
    }
    panelArray[x][y] = cell;
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
    frame.revalidate();
    frame.repaint();
  }

  public void startGame()
  {
    frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
    editBoard();
    String _continue = "";
    while (_continue.equals(""))
    {
      board = nextGeneration();
      printBoard(board);
      System.out.println("Please press enter to continue, type pause to edit the board through text, click boxes to flip their state, or enter any other text to exit.");
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
      System.out.println("Please enter the coordinates of your colony, 0-" + (boardSize - 1) + ", in the format (0,1), or click boxes to flip their state, then press enter when you are ready to start.");
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