package edu.ncsu.csc216.flix_2.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import edu.ncsu.csc216.flix_2.customer.CustomerAccountManager;
import edu.ncsu.csc216.flix_2.customer.MovieCustomerAccountSystem;
import edu.ncsu.csc216.flix_2.rental_system.DVDRentalSystem;
import edu.ncsu.csc216.flix_2.rental_system.RentalManager;

/**
 * The GUI for the movie system. Uses RentalManager and CustomerAccountManager
 * interfaces to interact with the rental system.
 * 
 * @author MatthewLilly
 * @author SamuelJessee
 *         
 */
public class MovieSystemGUI extends JFrame implements ActionListener
{
   /**
    * unimportant serial version
    */
   private static final long serialVersionUID = 1L;
   
   /**
    * the accountmanager
    */
   private CustomerAccountManager accountManager;
   
   /**
    * the rental system
    */
   private RentalManager rentals;
   
   // I just made global variables because its easier to set everything up I
   // think
   // I know it's bad practice, but this is barely getting turned in two days
   // late.
   // So... too bad.
   
   private JPanel topPartOfLogin = new JPanel();
   
   private JButton browseButton = new JButton("Browse");
   
   private JButton showQueueButton = new JButton("Show My Queue");
   
   private JButton logoutButton = new JButton("Logout");
   
   private JPanel middlePartOfLogin = new JPanel(new BorderLayout());
   
   private JPanel middleMiddle = new JPanel(new BorderLayout());
   
   private JPanel userPanel = new JPanel(new BorderLayout());
   
   private JTextField username = new JTextField();
   
   private JLabel userLabel = new JLabel("Username: ");
   
   private JPanel passPanel = new JPanel(new BorderLayout());
   
   private JTextField password = new JTextField();
   
   private JLabel passLabel = new JLabel("Password: ");
   
   private JButton loginButton = new JButton("Login");
   
   private JPanel bottomPartOfLogin = new JPanel();
   
   private JButton addNewCustomerButton = new JButton("Add New Customer");
   
   private JButton cancelAccountButton = new JButton("Cancel Account");
   
   private JButton quitButton = new JButton("Quit");
   
   private JPanel moviePanel = new JPanel(new BorderLayout());
   
   private JButton reserveMovieButton = new JButton("Reserve Selected Movie");
   
   @SuppressWarnings("rawtypes")
   private JList movieList;
   
   private JScrollPane movieListScroller;
   
   private JLabel reservedMovieLabel = new JLabel("");
   
   private JPanel bottomOfReserve = new JPanel(new BorderLayout());
   
   private JPanel topOfQueueFace = new JPanel(new BorderLayout());
   
   @SuppressWarnings("rawtypes")
   private JList moviesAtHome;
   
   private JButton returnSelectedButton = new JButton("Return Selected Movie");
   
   private JPanel centerOfQueueFace = new JPanel(new BorderLayout());
   
   @SuppressWarnings("rawtypes")
   private JList myQueueList;
   
   private JButton moveSelectedUp = new JButton("Move Selected Movie Up");
   
   private JButton removeSelected = new JButton("Remove Selected Movie");
   
   private JPanel bottomQueueButtons = new JPanel(new BorderLayout());
   
   private JScrollPane myQueueListScroller;
   
   private JScrollPane moviesAtHomeScroller;
   
   private JLabel atHomeLabel = new JLabel("Movies at Home");
   
   private JLabel inReserveLabel = new JLabel("Movies in Reserve");
   
   // ****************************************************** //
   
   /**
    * Starts the GUI
    * @param args string array of command line arguments
    */
   public static void main(String[] args)
   {
      new MovieSystemGUI(args);
   }
   
   /**
    * the GUI for the Movie System
    * @param args string array of command line arguments
    */
   public MovieSystemGUI(String[] args)
   {
      if (args.length == 1)
      {
         rentals = new DVDRentalSystem(args[0]);
      }
      else if (args.length == 0)
      {
         rentals = new DVDRentalSystem(getFileName());
      }
      
      accountManager = new MovieCustomerAccountSystem(rentals);
      
      // sets up the first part, things immediately necessary for Login face
      this.setTitle("Titles are for losers");
      this.setLocation(new Point(500, 250));
      
      this.setDefaultCloseOperation(EXIT_ON_CLOSE);
      
      Container c = getContentPane();
      BorderLayout cardLayout = new BorderLayout();
      c.setLayout(cardLayout);
      
      username.setPreferredSize(new Dimension(150, 25));
      password.setPreferredSize(new Dimension(150, 25));
      
      // ****************************************************** //
      // actionlisteners
      
      loginButton.addActionListener(new ActionListener()
      {
         @Override
         public void actionPerformed(ActionEvent a)
         {
            try
            {
               accountManager.login(username.getText(), password.getText());
               
               if (accountManager.isAdminLoggedIn())
               {
                  addNewCustomerButton.setEnabled(true);
                  cancelAccountButton.setEnabled(true);
                  logoutButton.setEnabled(true);
                  quitButton.setEnabled(true);
               }
               else // customer is logged in
               {
                  loginButton.setEnabled(false);
                  browseButton.setEnabled(true);
                  showQueueButton.setEnabled(true);
                  logoutButton.setEnabled(true);
               }
            }
            catch (IllegalArgumentException e)
            {
               JOptionPane.showMessageDialog(c, "The account doesn't exist.");
            }
            catch (IllegalStateException e)
            {
               JOptionPane.showMessageDialog(c, e.getMessage());
            }
         }
      });
      
      logoutButton.addActionListener(new ActionListener()
      {
         @Override
         public void actionPerformed(ActionEvent a)
         {
            if (accountManager.isAdminLoggedIn() || accountManager.isCustomerLoggedIn())
            {
               accountManager.logout();
               
               setLoginFace(c); // hopefully changes faces back to login
               
               username.setText("");
               password.setText("");
               
            }
            else // shouldn't be able to happen
            {
               JOptionPane.showMessageDialog(c, "There ain't nobody logged in, ya dingus");
               username.setText("");
               password.setText("");
            }
         }
      });
      
      quitButton.addActionListener(new ActionListener()
      {
         @Override
         public void actionPerformed(ActionEvent actionEvent)
         {
            if (accountManager.isAdminLoggedIn())
            {
               System.exit(0); // Apparently not the best way, but who cares.
                               // It's the admin, they can do what they want
            }
            else // shouldn't happen
            {
               JOptionPane.showMessageDialog(c, "Only the admiin can close the program "
                     + "with a button other than the Close button. For some reason");
               username.setText("");
               password.setText("");
            }
         }
      });
      
      addNewCustomerButton.addActionListener(new ActionListener()
      {
         @Override
         public void actionPerformed(ActionEvent actionEvent)
         {
            if (accountManager.isAdminLoggedIn())
            {
               JPanel panel = new JPanel();
               JTextField custUsername = new JTextField();
               JLabel custUserLabel = new JLabel("Username: ");
               JTextField custPassword = new JTextField();
               JLabel custPassLabel = new JLabel("Password: ");
               JTextField custMovieLimit = new JTextField();
               JLabel custMovieLabel = new JLabel("Movie Limit: ");
               
               custUsername.setPreferredSize(new Dimension(150, 25));
               custPassword.setPreferredSize(new Dimension(150, 25));
               custMovieLimit.setPreferredSize(new Dimension(150, 25));
               
               panel.add(custUserLabel);
               panel.add(custUsername);
               panel.add(custPassLabel);
               panel.add(custPassword);
               panel.add(custMovieLabel);
               panel.add(custMovieLimit);
               
               int maxAtHome = -1;
               int n = 0;
               while (true)
               {
                  n = JOptionPane.showConfirmDialog(c, panel,
                        "Enter the new customer's username and password",
                        JOptionPane.OK_CANCEL_OPTION);
                  if (n == JOptionPane.CANCEL_OPTION)
                  {
                     break;
                  }
                  
                  try
                  {
                     maxAtHome = Integer.parseInt(custMovieLimit.getText());
                     break;
                  }
                  catch (NumberFormatException e)
                  {
                     JOptionPane.showMessageDialog(c,
                           "Movie Limit must be an integer. Try again; customer was not added");
                  }
               }
               
               if (n == JOptionPane.OK_OPTION)
               {
                  try
                  {
                     accountManager.addNewCustomer(custUsername.getText(), custPassword.getText(),
                           maxAtHome);
                  }
                  catch (IllegalArgumentException e)
                  {
                     JOptionPane.showMessageDialog(c, e.getMessage());
                  }
                  catch (IllegalStateException e)
                  {
                     JOptionPane.showMessageDialog(c, "There is no room for additional customers");
                  }
               }
            }
            else // shouldn't happen
            {
               JOptionPane.showMessageDialog(c, "You're not the admin! Only the admin "
                     + "can add new customers! Somebody call the cops!");
               username.setText("");
               password.setText("");
            }
         }
      });
      
      cancelAccountButton.addActionListener(new ActionListener()
      {
         @Override
         public void actionPerformed(ActionEvent actionEvent)
         {
            if (accountManager.isAdminLoggedIn())
            {
               JPanel panel = new JPanel();
               JTextField custUsername = new JTextField();
               JLabel custUserLabel = new JLabel("Username: ");
               
               custUsername.setPreferredSize(new Dimension(150, 25));
               
               panel.add(custUserLabel);
               panel.add(custUsername);
               
               int n = JOptionPane.showConfirmDialog(c, panel,
                     "Enter the username for the account to be cancelled",
                     JOptionPane.OK_CANCEL_OPTION);
               if (n == JOptionPane.OK_OPTION)
               {
                  try
                  {
                     accountManager.cancelAccount(custUsername.getText());
                  }
                  catch (IllegalArgumentException e)
                  {
                     JOptionPane.showMessageDialog(c, "No matching customer account found");
                  }
               }
            }
            else // shouldn't happen
            {
               JOptionPane.showMessageDialog(c, "You're not the admin! Only the admin "
                     + "can cancel customer accounts! Somebody call the cops!");
               username.setText("");
               password.setText("");
            }
         }
      });
      
      browseButton.addActionListener(new ActionListener()
      {
         
         @Override
         public void actionPerformed(ActionEvent actionEvent)
         {
            if (accountManager.isCustomerLoggedIn())
            {
               setBrowseFace(c);
            }
            else // shouldn't happen
            {
               JOptionPane.showMessageDialog(c,
                     "Looks like the admin forgot to log" + " into their personal account");
            }
         }
      });
      
      reserveMovieButton.addActionListener(new ActionListener()
      {
         
         @Override
         public void actionPerformed(ActionEvent actionEvent)
         {
            if (accountManager.isCustomerLoggedIn())
            {
               try
               {
                  int n = 0;
                  n = movieList.getSelectedIndex();
                  if (n != -1)
                  {
                     rentals.addToCustomerQueue(n);
                     reservedMovieLabel.setText("Added: " + movieList.getSelectedValue());
                  }
                  
               }
               catch (IllegalStateException e)
               {
                  JOptionPane.showMessageDialog(c, e.getMessage());
               }
               catch (IllegalArgumentException e)
               {
                  JOptionPane.showMessageDialog(c,
                        "Your queue is full. " + "Remove a movie before adding a new one.");
               }
            }
            else // shouldn't happen
            {
               JOptionPane.showMessageDialog(c, "Admins can't get dibs on reserving movies!");
            }
         }
      });
      
      returnSelectedButton.addActionListener(new ActionListener()
      {
         @Override
         public void actionPerformed(ActionEvent actionEvent)
         {
            if (accountManager.isCustomerLoggedIn())
            {
               try
               {
                  int n = 0;
                  n = moviesAtHome.getSelectedIndex();
                  if (n != -1)
                  {
                     rentals.returnItemToInventory(n);
                  }
               }
               catch (IllegalArgumentException e)
               {
                  JOptionPane.showMessageDialog(c, e.getMessage());
                  
               }
               catch (IllegalStateException e)
               {
                  JOptionPane.showMessageDialog(c, e.getMessage());
               }
            }
            else
            {
               JOptionPane.showMessageDialog(c, "I got tired of making messages for this");
            }
         }
      });
      
      moveSelectedUp.addActionListener(new ActionListener()
      {
         @Override
         public void actionPerformed(ActionEvent e)
         {
            if (accountManager.isCustomerLoggedIn())
            {
               int n = 0;
               n = myQueueList.getSelectedIndex();
               if (n != -1)
               {
                  rentals.reserveMoveAheadOne(n);
                  c.revalidate();
               }
            }
            else
            {
               JOptionPane.showMessageDialog(c, "I got tired of making messages for this");
            }
         }
      });
      
      removeSelected.addActionListener(new ActionListener()
      {
         @Override
         public void actionPerformed(ActionEvent e)
         {
            if (accountManager.isCustomerLoggedIn())
            {
               int n = 0;
               n = myQueueList.getSelectedIndex();
               if (n != -1)
               {
                  rentals.removeSelectedFromReserves(n);
                  c.revalidate();
               }
            }
            else
            {
               JOptionPane.showMessageDialog(c, "I got tired of making messages for this");
            }
         }
      });
      
      showQueueButton.addActionListener(new ActionListener()
      {
         @Override
         public void actionPerformed(ActionEvent actionEvent)
         {
            if (accountManager.isCustomerLoggedIn())
            {
               setQueueFace(c);
            }
            else // shouldn't happen
            {
               JOptionPane.showMessageDialog(c, "Admins don't have queues!");
            }
         }
      });
      
      // ****************************************************** //
      
      setLoginFace(c);
      
      this.pack();
      this.setVisible(true);
   }
   
   /**
    * Returns a file name generated through interactions with a
    * {@link JFileChooser} object. taken straight outta bugtracker
    * 
    * @return the file name selected through {@link JFileChooser}
    */
   private String getFileName()
   {
      JFileChooser fc = new JFileChooser("./"); // Open JFileChoose to current
                                                // working directory
      int returnVal = fc.showOpenDialog(this);
      if (returnVal != JFileChooser.APPROVE_OPTION)
      {
         // Error or user canceled, either way no file name.
         throw new IllegalStateException();
      }
      File gameFile = fc.getSelectedFile();
      return gameFile.getAbsolutePath();
   }
   
   private void setLoginFace(Container c)
   {
      userPanel.removeAll();
      userPanel.add(userLabel, BorderLayout.WEST);
      userPanel.add(username, BorderLayout.EAST);
      
      passPanel.removeAll();
      passPanel.add(passLabel, BorderLayout.WEST);
      passPanel.add(password, BorderLayout.EAST);
      
      middleMiddle.removeAll();
      middleMiddle.add(userPanel, BorderLayout.NORTH);
      middleMiddle.add(passPanel, BorderLayout.SOUTH);
      
      topPartOfLogin.removeAll();
      topPartOfLogin.add(browseButton, "");
      topPartOfLogin.add(showQueueButton, "");
      topPartOfLogin.add(logoutButton, "");
      
      middlePartOfLogin.removeAll();
      middlePartOfLogin.add(middleMiddle, BorderLayout.NORTH);
      middlePartOfLogin.add(loginButton, BorderLayout.CENTER);
      middlePartOfLogin.setBorder(BorderFactory.createLineBorder(Color.red));
      
      bottomPartOfLogin.removeAll();
      bottomPartOfLogin.add(addNewCustomerButton);
      bottomPartOfLogin.add(cancelAccountButton);
      bottomPartOfLogin.add(quitButton);
      
      addNewCustomerButton.setEnabled(false);
      cancelAccountButton.setEnabled(false);
      logoutButton.setEnabled(false);
      quitButton.setEnabled(false);
      loginButton.setEnabled(true);
      browseButton.setEnabled(false);
      showQueueButton.setEnabled(false);
      logoutButton.setEnabled(false);
      
      c.removeAll();
      c.add(topPartOfLogin, BorderLayout.NORTH);
      c.add(middlePartOfLogin, BorderLayout.CENTER);
      c.add(bottomPartOfLogin, BorderLayout.SOUTH);
      c.revalidate();
      this.pack();
   }
   
   private void setBrowseFace(Container c)
   {
      browseButton.setEnabled(true);
      showQueueButton.setEnabled(true);
      logoutButton.setEnabled(true);
      reserveMovieButton.setEnabled(true);
      
      Scanner scanner = new Scanner(rentals.showInventory());
      
      String[] movieArray = new String[getArraySize(rentals.showInventory())];
      
      for (int i = 0; i < movieArray.length; i++)
      {
         movieArray[i] = scanner.nextLine();
      }
      scanner.close();
      
      movieList = new JList<String>(movieArray);
      movieListScroller = new JScrollPane(movieList);
      
      moviePanel.removeAll();
      moviePanel.add(movieListScroller);
      
      topPartOfLogin.removeAll();
      topPartOfLogin.add(browseButton, "");
      topPartOfLogin.add(showQueueButton, "");
      topPartOfLogin.add(logoutButton, "");
      
      bottomOfReserve.removeAll();
      bottomOfReserve.add(reserveMovieButton, BorderLayout.NORTH);
      bottomOfReserve.add(reservedMovieLabel, BorderLayout.CENTER);
      
      c.removeAll();
      c.add(topPartOfLogin, BorderLayout.NORTH);
      c.add(moviePanel, BorderLayout.CENTER);
      c.add(bottomOfReserve, BorderLayout.SOUTH);
      c.revalidate();
      this.pack();
   }
   
   private void setQueueFace(Container c)
   {
      Scanner scanner = new Scanner(rentals.traverseReserveQueue());
      
      String[] movieArray = new String[getArraySize(rentals.traverseReserveQueue())];
      
      for (int i = 0; i < movieArray.length; i++)
      {
         movieArray[i] = scanner.nextLine();
      }
      scanner.close();
      
      myQueueList = new JList<String>(movieArray);
      myQueueListScroller = new JScrollPane(myQueueList);
      
      Scanner scanner2 = new Scanner(rentals.traverseAtHomeQueue());
      
      String[] movieArray2 = new String[getArraySize(rentals.traverseAtHomeQueue())];
      
      for (int i = 0; i < movieArray2.length; i++)
      {
         movieArray2[i] = scanner2.nextLine();
      }
      scanner2.close();
      
      moviesAtHome = new JList<String>(movieArray2);
      moviesAtHomeScroller = new JScrollPane(moviesAtHome);
      
      topPartOfLogin.removeAll();
      topPartOfLogin.add(browseButton, "");
      topPartOfLogin.add(showQueueButton, "");
      topPartOfLogin.add(logoutButton, "");
      
      topOfQueueFace.removeAll();
      topOfQueueFace.add(topPartOfLogin, BorderLayout.NORTH);
      topOfQueueFace.add(moviesAtHomeScroller, BorderLayout.CENTER);
      topOfQueueFace.add(atHomeLabel, BorderLayout.EAST);
      topOfQueueFace.add(returnSelectedButton, BorderLayout.SOUTH);
      
      centerOfQueueFace.removeAll();
      centerOfQueueFace.add(myQueueListScroller, BorderLayout.CENTER);
      centerOfQueueFace.add(inReserveLabel, BorderLayout.EAST);
      centerOfQueueFace.add(bottomQueueButtons, BorderLayout.SOUTH);
      
      bottomQueueButtons.removeAll();
      bottomQueueButtons.add(moveSelectedUp, BorderLayout.WEST);
      bottomQueueButtons.add(removeSelected, BorderLayout.EAST);
      
      c.removeAll();
      c.add(topOfQueueFace, BorderLayout.NORTH);
      c.add(centerOfQueueFace, BorderLayout.CENTER);
      c.add(bottomQueueButtons, BorderLayout.SOUTH);
      c.revalidate();
      this.pack();
   }
   
   private int getArraySize(String s)
   {
      int arraySize = 0;
      Scanner scanner = new Scanner(s);
      while (scanner.hasNextLine())
      {
         scanner.nextLine();
         arraySize++;
      }
      scanner.close();
      return arraySize;
   }
   
   @Override
   public void actionPerformed(ActionEvent actionEvent)
   {
      // pmd
   }
}