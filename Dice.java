/** don't expect comments, this is what it is.
if for some reason you ever run into this monstrocity
of a program, rest your soul
*/

//import field

import javax.swing.*; //for JFrame, JLabel, JButton, JPanel, JTextField
import java.awt.*; //for BorderLayout
import javax.swing.Icon;
import javax.swing.event.*;
import javax.swing.border.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.event.*; //for listener events
import java.io.IOException;
import java.util.Random;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Collections;
import java.util.TreeMap;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Map;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.FileOutputStream;

public class Dice extends JFrame {

   //instance variable and list field
   private String Notes; //the file to save for notes
   
   //modifiers
   private int strength = -1;
   private int dex = 2;
   private int con = 2+2;
   private int intelligence = 1;
   private int wisdom = 1;
   private int charisma = 1;
   private int prof = 2;

   private int [] mainStates = {strength, dex, con, intelligence, wisdom, charisma};

   
   //adders
   private int acrobatics = 2; private int animalHandling = 1; private int arcana = 1; private int athletics = -1; private int deception = 3; private int history = 1; private int insight = 1; 
   private int intimidation = 1; private int investigation = 1; private int medicine = 1; private int nature = 1; private int perception = 3; private int performance = 1; 
   private int persuasion = 1; private int religion = 1; private int sleightOfHand = 6; private int stealth = 6; private int survival = 1; //note sleightOh = dex + prof
   
   private int [] adders = {acrobatics, animalHandling, arcana, athletics, deception, history, insight, intimidation, investigation, medicine, nature, perception,
   performance, persuasion, religion, sleightOfHand, stealth, survival};
   
   //panels and instance variables
   public static final int WIDTH = 1290;
   public static final int HEIGHT = 720;
   public String kathleen;
   public boolean initiative = false;
   public boolean namesGotten = false;
   public boolean playersGotten = false;
   public boolean enemiesGotten = false;
   public boolean combatantsGotten = false;
   public boolean lessThanTwenty = false;
   public boolean numberCheck;
   private String [] players;
   private String [] enemies;
   private String [] combatants;
   private ArrayList<String> initOrder = new ArrayList<String>();
   
   private JPanel noteSection = new JPanel(); //notes panel
   private Scanner input = new Scanner(System.in);
   private PrintWriter out = new PrintWriter(System.out);
   private JPanel eastPanel = new JPanel();
   private JPanel preDice = new JPanel(); //pre made dice (1d20, 1d8, etc)
   private JPanel manualInp = new JPanel(); //manual input field
   private JTextArea notesTextArea = new JTextArea (25,38);
   private JTextArea manualInput = new JTextArea(14,36);
   private JTextArea manIn = new JTextArea(1,36);
   private JScrollPane scrollPane1 = new JScrollPane(notesTextArea);
   private JScrollPane scrollPane2 = new JScrollPane(manualInput);
   
   private JFrame nameFrame = new JFrame("Input Panel");
   private JPanel namesPanel = new JPanel();
   private JTextField inputField = new JTextField(38);
   private JTextArea messageField = new JTextArea(5,38);
   private JButton doneButton = new JButton("Done");
   
   private Color buttonColor = new Color(128,128,128);
   private Color theme = new Color(128,128,128);
   private Color textTheme = new Color(100,100,100);
   
   public Random rand = new Random();
   public String dieRoll;
   private int currentInit = -1;
   
   private JButton [] diceButtons = {new JButton("d4"), new JButton("d6"), new JButton("d8"), new JButton("d10"), 
   new JButton("d12"), new JButton("d20"), new JButton("d100") };
   
   private JButton saveButton = new JButton("Save");
   private JButton startInitiative  = new JButton("Start Init");
   private JButton endInitiative = new JButton("End Init");
   private JButton nextCombatant = new JButton("Next fighter");
   private JButton crossBowAttack = new JButton("Cross Bow");
   private JButton darkStab = new JButton("Dark stab");
   private JButton healersKit = new JButton("HealersKit");
   private JButton rapier = new JButton("Rapier"); 
   
   private JMenuBar menuBar = new JMenuBar();
   private JMenu savingThrows = new JMenu("Saving Throws");
   private JMenu skillChecks = new JMenu("Skill Checks");
   private JMenuItem strengthS, dexS, conS, intelligenceS, wisdomS, charismaS = new JMenuItem();
   private JMenuItem [] mainSaveMenuItems = {strengthS, dexS, conS, intelligenceS, wisdomS, charismaS};
   private String [] saveNames = {"Strength","Dexterity","Constitution","intelligence","Wisdom","Charisma"};
   
   private JMenuItem acrobaticsS, animalHandlingS, arcanaS, athleticsS, deceptionS, historyS, insightS, intimidationS, investigationS, medicineS, natureS, perceptionS,
   performanceS, persuasionS, religionS, sleightOfHandS, stealthS, survivalS;
   private JMenuItem [] skillCheckItems = {acrobaticsS, animalHandlingS, arcanaS, athleticsS, deceptionS, historyS, insightS, intimidationS, investigationS, medicineS, natureS, perceptionS,
   performanceS, persuasionS, religionS, sleightOfHandS, stealthS, survivalS};
   private String [] skillNames = {"Acrobatics","Animal Handling","Arcana","Athletics","!Deception","History","Insight","Intimidation","Investigation","Medicine","nature","!Perception",
   "Performance","Persuasion","Religion","!Sleight of Hand","!Stealth","Survival"};
   
   //constructor
   public Dice()
   {
      //basic set up
      super();
      setDefaultLookAndFeelDecorated(true);
      setSize(WIDTH, HEIGHT);
      setTitle("Sketchy Dice roll/DnD software");
      setBackground(theme);
      eastPanel.setBackground(theme);
      noteSection.setBackground(theme);
      notesTextArea.setBackground(textTheme);
      manualInp.setBackground(theme);
      manualInput.setEditable(false);
      manIn.setBackground(theme);
      manualInput.setBackground(textTheme);
      preDice.setBackground(theme);
      setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      setVisible(true);
      
      //adding panels and buttons and other crap
      noteSection.add(scrollPane1);
      notesTextArea.setFont(notesTextArea.getFont().deriveFont(20f));
      preDice.setLayout(new GridLayout(5,5,10,10));
      
      for (int i=0; i<diceButtons.length; i++) 
      {
         diceButtons[i].setPreferredSize(new Dimension(10, 10));
         diceButtons[i].setBorder(new RoundedBorder(10));
//          diceButtons[i].setBackground(buttonColor);
         preDice.add(diceButtons[i]);
      }
      setJMenuBar(menuBar);
      for (int i=0; i<mainSaveMenuItems.length; i++)
      {
         mainSaveMenuItems[i] = savingThrows.add(saveNames[i]);
      }
      for (int i=0; i<skillCheckItems.length; i++)
      {
         skillCheckItems[i] = skillChecks.add(skillNames[i]);
      }
      menuBar.add(savingThrows);
      menuBar.add(skillChecks);
      
      //stuff for macros
      preDice.add(startInitiative);
      preDice.add(nextCombatant);
      preDice.add(endInitiative);
      preDice.add(crossBowAttack);
      preDice.add(darkStab);
      preDice.add(healersKit);
      preDice.add(rapier);
      saveButton.setBackground(theme);
      preDice.add(saveButton);
      manualInp.setLayout(new BorderLayout());
      manIn.setFont(manIn.getFont().deriveFont(20f));
      manualInput.setFont(manualInput.getFont().deriveFont(20f));

      //setting up input frame
      inputField.setBackground(theme);
      messageField.setFont(messageField.getFont().deriveFont(20f));
      messageField.setBackground(theme);
      messageField.setEditable(false);
      messageField.append("   ");
      namesPanel.setLayout(new BorderLayout());
      inputField.setFont(inputField.getFont().deriveFont(20f));
      nameFrame.setSize(800,400);
      namesPanel.add(inputField, BorderLayout.CENTER);
      namesPanel.add(messageField, BorderLayout.NORTH);
      namesPanel.add(doneButton, BorderLayout.SOUTH);
      nameFrame.setLayout(new BorderLayout());
      nameFrame.add(namesPanel, BorderLayout.CENTER);      
      
      manualInp.add(manIn, BorderLayout.NORTH);
      manualInp.add(scrollPane2, BorderLayout.SOUTH);
      setLayout(new BorderLayout());
      eastPanel.setLayout(new BorderLayout());
      eastPanel.add(preDice, BorderLayout.NORTH);
      eastPanel.add(manualInp, BorderLayout.SOUTH);
      add(eastPanel, BorderLayout.EAST);
      notesTextArea.append("Notes\n");
      add(noteSection, BorderLayout.WEST);
      manIn.append(" ");
      
      //painfully dull adding the listeners
      manIn.addKeyListener(listener);
//       inputField.addKeyListener(listener2);
      
      doneButton.addActionListener(new doneButtonListener());
      saveButton.addActionListener(new saveListener());
      startInitiative.addActionListener(new startInitiativeListener());
      nextCombatant.addActionListener(new nextCombatantListener());
      endInitiative.addActionListener(new endInitiativeListener());
      crossBowAttack.addActionListener(new crossBowAttackListener());
      darkStab.addActionListener(new darkStabListener());
      healersKit.addActionListener(new healersKitListener());
      rapier.addActionListener(new rapierListener());
      
      diceButtons[0].addActionListener(new d4ActListener());
      diceButtons[1].addActionListener(new d6ActListener());
      diceButtons[2].addActionListener(new d8ActListener());
      diceButtons[3].addActionListener(new d10ActListener());
      diceButtons[4].addActionListener(new d12ActListener());
      diceButtons[5].addActionListener(new d20ActListener());
      diceButtons[6].addActionListener(new d100ActListener());
      
      mainSaveMenuItems[0].addActionListener(new strengthMenuListener ());
      mainSaveMenuItems[1].addActionListener(new dexMenuListener ());
      mainSaveMenuItems[2].addActionListener(new conMenuListener ());
      mainSaveMenuItems[3].addActionListener(new intelligenceMenuListener ());
      mainSaveMenuItems[4].addActionListener(new wisdomMenuListener ());
      mainSaveMenuItems[5].addActionListener(new charismaMenuListener ());
      
      skillCheckItems[0].addActionListener(new acrobaticsMenuListener ());
      skillCheckItems[1].addActionListener(new animalHMenuListener ());
      skillCheckItems[2].addActionListener(new arcanaMenuListener ());
      skillCheckItems[3].addActionListener(new athleticsMenuListener ());
      skillCheckItems[4].addActionListener(new deceptionMenuListener ());
      skillCheckItems[5].addActionListener(new historyMenuListener ());
      skillCheckItems[6].addActionListener(new insightMenuListener ());
      skillCheckItems[7].addActionListener(new intimidationMenuListener ());
      skillCheckItems[8].addActionListener(new investigationMenuListener ());
      skillCheckItems[9].addActionListener(new medicineMenuListener ());
      skillCheckItems[10].addActionListener(new natureMenuListener ());
      skillCheckItems[11].addActionListener(new perceptionMenuListener ());
      skillCheckItems[12].addActionListener(new performanceMenuListener ());
      skillCheckItems[13].addActionListener(new persuasionMenuListener ());
      skillCheckItems[14].addActionListener(new religionMenuListener ());
      skillCheckItems[15].addActionListener(new sleightOHMenuListener ());
      skillCheckItems[16].addActionListener(new stealthMenuListener ());
      skillCheckItems[17].addActionListener(new survivalMenuListener ());


   }
   //methods: get rand numbers for dice rolls, save file1
   public void saveNotes() {
      PrintWriter saver =null;
      try {
         saver= new PrintWriter(new FileOutputStream( new File("C:\\Users\\Kholofelo\\notes.txt"), true ));
         String [] lines = notesTextArea.getText().split("\\n");
         saver.println("");
         for (int i=0;i<lines.length;i++) {
            saver.println(lines[i]);
         }
         saver.close();     
      }  
      catch (FileNotFoundException e) {System.out.println("Save failed"); manualInput.append("File save failed\n");}
   }
   public void addPlayers() { //where the actual initive order is made
      System.out.println("check 2");
      ArrayList<Integer> numbers = new ArrayList<Integer>();
//       int n = getNames().length;
      int value;
      TreeMap<Integer,String> fullInfo = new TreeMap<Integer, String>();
      for (int i=0;i<this.players.length;i++) {
         do { value = rand.nextInt(21); }
         while (value ==0 || (numbers.contains(value)) ); 
         numbers.add(value);
         fullInfo.put(value,players[i]);
      }
      System.out.println(Arrays.toString(players)+"\n"+numbers.toString());
      System.out.println(fullInfo.descendingMap());
      for (int key : fullInfo.descendingMap().keySet() ) {
         System.out.print(key+"  "+ fullInfo.get(key).trim() +" | ");
         initOrder.add(fullInfo.get(key).trim());
      }
   }
   public void addEnemies() { 
      System.out.println("\ncheck 2.2");
      ArrayList<Integer> numbers = new ArrayList<Integer>();
//       int n = getEnemies().length;
      int value;
      TreeMap<Integer,String> fullInfo = new TreeMap<Integer, String>();
      for (int i=0;i<this.enemies.length;i++) {
         do { value = rand.nextInt(21); }
         while (value ==0 || (numbers.contains(value)) ); 
         numbers.add(value);
         fullInfo.put(value,enemies[i]);
      }
      System.out.println(Arrays.toString(enemies)+"\n"+numbers.toString());
      System.out.println(fullInfo.descendingMap());
      for (int key : fullInfo.descendingMap().keySet() ) {
         System.out.print(key+"  "+ fullInfo.get(key).trim() +" | ");
         initOrder.add(fullInfo.get(key).trim());
      }
      initiative = true;
   }
   public void addCombatants() { 
      ArrayList<Integer> numbers = new ArrayList<Integer>();
      int value;
      TreeMap<Integer,String> fullInfo = new TreeMap<Integer, String>();
      for (int i=0;i<this.combatants.length;i++) {
         do { value = rand.nextInt(21); }
         while (value ==0 || (numbers.contains(value)) ); 
         numbers.add(value);
         fullInfo.put(value,combatants[i]);
      }
      System.out.println(Arrays.toString(combatants)+"\n"+numbers.toString());
      System.out.println(fullInfo.descendingMap());
      for (int key : fullInfo.descendingMap().keySet() ) {
         System.out.print(key+"  "+ fullInfo.get(key).trim() +" | ");
         initOrder.add(fullInfo.get(key).trim());
      }
      initiative = true;         
   }
   public String [] getInputFromUser() {
      String [] names = inputField.getText().split(",");
      inputField.setText("    ");
      messageField.setText("You have entered in\n"+Arrays.toString(names)+"\n\n");
      System.out.println("We got em\n"+Arrays.toString(names));
      return (names);
   }
   
   public void adderer() { 
      this.setVisible(false);
      nameFrame.setVisible(true);
      inputField.setText("    ");
      messageField.append("Enter in number of combatans below\n");
      
   }

   public String[] getNames() {
//       manIn.setText("   Names:\n");
      System.out.println("Enter names\n");
      String [] names = input.nextLine().split(",");
      System.out.println("check 3");
      System.out.println(Arrays.toString(names)+"\nIs this fine? Yes or no.");
      String confirm = input.nextLine().trim().toLowerCase();
      
      while (!confirm.equals("yes")) {System.out.println("Re-enter"); names = input.nextLine().split(","); System.out.println(Arrays.toString(names
      )+"\nIs THIS fine?"); confirm = input.nextLine().toLowerCase(); }
      players = names;
      System.out.println(Arrays.toString(players));
      System.out.println("check 4");
      return players;
   }
   public String[] getEnemies() {
//       manIn.setText("   Names:\n");
      System.out.println("Enter enemy names\n");
      String [] names = input.nextLine().split(",");
      System.out.println("check 3.2");
      System.out.println(Arrays.toString(names)+"\nIs this fine? Yes or no.");
      String confirm = input.nextLine().trim().toLowerCase();
      
      while (!confirm.equals("yes")) {System.out.println("Re-enter"); names = input.nextLine().split(","); System.out.println(Arrays.toString(names
      )+"\nIs THIS fine?"); confirm = input.nextLine().toLowerCase(); }
      enemies = names;
      System.out.println(Arrays.toString(enemies));
      namesGotten = true;
      System.out.println("check 4.2");
      
      return enemies;
   }

   
   public String enterHit() {
      String data = manIn.getText().trim().replace("\n","");
      System.out.println("data= "+data);
      if (!data.contains("+")) {data = data.substring(0, data.length()-1) + "+0]";}
      String mult;
      int multiplier;
      String die;
      String result="";
      if (data.contains("[") && data.contains("]")) {
         mult = Character.toString(data.charAt(1));
         if (mult.equals("d")) {multiplier = 1;}
         else {multiplier = Integer.valueOf(mult); System.out.println("mult = "+Integer.valueOf(mult));}
         int dI = data.indexOf("d");
         int addI = data.indexOf("+");
         die = data.substring(dI+1, addI); System.out.println("die= "+die);
         String add = data.substring(addI+1, data.length()-1); System.out.println("add= "+add);

         switch (Integer.parseInt(die)) {
            case 4: result = d4(multiplier, Integer.parseInt(add));System.out.println(4);break;
            case 6: result = d6(multiplier,Integer.parseInt(add)); System.out.println("result= "+result); break;
            case 8: result = d8(multiplier, Integer.parseInt(add));System.out.println(8);break;
            case 10: result = d10(multiplier, Integer.parseInt(add));System.out.println(10);break;
            case 12: result = d12(multiplier, Integer.parseInt(add));System.out.println(12);break;
            case 20: result = d20(multiplier, Integer.parseInt(add));System.out.println(20);break;
            case 100: result = d100(multiplier, Integer.parseInt(add));System.out.println(100);break;
         }
         if (Integer.parseInt(add) ==0) {dieRoll = multiplier+"d"+die; System.out.println("dieRoll= "+dieRoll);}
         else {dieRoll = multiplier+"d"+die+"+"+add; System.out.println("dieRoll= "+dieRoll);}
      }
      else {System.out.println("invalid input"); }
      manIn.setText("       ");
      return result;
   }
      
   public void setDiceVis() {
      nameFrame.setVisible(false);
      this.setVisible(true);   
   }
   public String d6(int multiplier, int add) {
      int value;
      int tot=0;
      String ret="";
      for (int i=0;i<multiplier;i++) {
         value = rand.nextInt(7);
         while (value ==0) {value=rand.nextInt(7);}
         tot = tot+value;
      }
      if (add==0) {ret = "("+tot+")";}
      else if (add !=0) {ret ="("+tot+") + "+add+" = "+(tot+add);}
      return (ret);
   }
   public String d4(int multiplier, int add) {
      int value;
      int tot=0;
      String ret="";
      for (int i=0;i<multiplier;i++) {
         value = rand.nextInt(5);
         while (value ==0) {value=rand.nextInt(5);}
         tot = tot+value;
      }
      if (add==0) {ret = "("+tot+")";}
      else if (add !=0) {ret ="("+tot+") + "+add+" = "+(tot+add);}
      return (ret);
   }
   public String d8(int multiplier, int add) {
      int value;
      int tot=0;
      String ret="";
      for (int i=0;i<multiplier;i++) {
         value = rand.nextInt(9);
         while (value ==0) {value=rand.nextInt(9);}
         tot = tot+value;
      }
      if (add==0) {ret = "("+tot+")";}
      else if (add !=0) {ret ="("+tot+") + "+add+" = "+(tot+add);}
      return (ret);
   }
   public String d10(int multiplier, int add) {
      int value;
      int tot=0;
      String ret="";
      for (int i=0;i<multiplier;i++) {
         value = rand.nextInt(11);
         while (value ==0) {value=rand.nextInt(11);}
         tot=tot+value;
      }
      if (add==0) {ret = "("+tot+")";}
      else if (add !=0) {ret ="("+tot+") + "+add+" = "+(tot+add);}
      return (ret);
   }
      
   public String d12(int multiplier, int add) {
      int value;
      int tot=0;
      String ret="";
      for (int i=0;i<multiplier;i++) {
         value = rand.nextInt(13);
         while (value ==0) {value=rand.nextInt(13);}
         tot = tot+value;
      }
      if (add==0) {ret = "("+tot+")";}
      else if (add !=0) {ret ="("+tot+") + "+add+" = "+(tot+add);}
      return (ret);
   }
   public String d20(int multiplier, int add) {
      int value;
      int tot=0;
      String ret="";
      for (int i=0;i<multiplier;i++) {
         value = rand.nextInt(21);
         while (value ==0) {value=rand.nextInt(21);}
         tot = tot+value;
      }
      if (add==0) {ret = "("+tot+")";}
      else if (add !=0) {ret ="("+tot+") + "+add+" = "+(tot+add);}
      return (ret);
   }
   public String d100(int multiplier, int add) {
      int value;
      int tot=0;
      String ret="";
      for (int i=0;i<multiplier;i++) {
         value = rand.nextInt(101);
         while (value ==0) {value=rand.nextInt(101);}
         tot = tot+value;
      }
      if (add==0) {ret = "("+tot+")";}
      else if (add !=0) {ret ="("+tot+") + "+add+" = "+(tot+add);}
      return (ret);
   }

      
   public static void main(String[] args) {
      Dice dice = new Dice();
   }
   
   /*
   here will follow the horror that is all the other classes that I need to make this program 'work'
   I apologize in advance
   The order of the group goes: Macro button (e.g. dice rolls, melee attack) Action Listeners, 
                                Keyboard listeners, skill check listeners,
                                Player classes (in progess), whatever else we add  
   
   */
   
   //action listener classes
   public class d4ActListener implements ActionListener {
      public d4ActListener() {}
      public void actionPerformed(ActionEvent e) {
         manualInput.append("    1d4 = "+String.valueOf(d4(1,0))+"\n");
      }
   }
   public class d6ActListener implements ActionListener {
      public d6ActListener() {}
      public void actionPerformed(ActionEvent e) {
         manualInput.append("    1d6 = "+String.valueOf(d6(1,0))+"\n");
      }
   }
   public class d8ActListener implements ActionListener {
      public d8ActListener() {}
      public void actionPerformed(ActionEvent e) {
         manualInput.append("    1d8 = "+String.valueOf(d8(1,0))+"\n");
      }
   }
   public class d10ActListener implements ActionListener {
      public d10ActListener() {}
      public void actionPerformed(ActionEvent e) {
         manualInput.append("    1d10 = "+String.valueOf(d10(1,0))+"\n");
      }
   }
   public class d12ActListener implements ActionListener {
      public d12ActListener() {}
      public void actionPerformed(ActionEvent e) {
         manualInput.append("    1d12 = "+String.valueOf(d12(1,0))+"\n");
      }
   }
   public class d20ActListener implements ActionListener {
      public d20ActListener() {}
      public void actionPerformed(ActionEvent e) {
         manualInput.append("    1d20 = "+String.valueOf(d20(1,0))+"\n");
      }
   }
   public class d100ActListener implements ActionListener {
      public d100ActListener() {}
      public void actionPerformed(ActionEvent e) {
         manualInput.append("    1d100 = "+String.valueOf(d100(1,0))+"\n");
      }
   }
   
   public class saveListener implements ActionListener {
      public saveListener() {}
      public void actionPerformed(ActionEvent e) {
         saveNotes();
//          if (!namesGotten) { getNames(); }
      }
   } 
   
   public class startInitiativeListener implements ActionListener {
      public startInitiativeListener() {}
      public void actionPerformed(ActionEvent e) {
         if  (!initiative) {
//             getContentPane().remove(noteSection);
//             setVisible(false);
//             add(namesPanel);
//             System.out.println("Panels added");
            manualInput.append("    Initiative rolled\n"); System.out.println("check 1"); setState(JFrame.ICONIFIED);
            adderer();
//             addPlayers();
//             addEnemies();
            System.out.println("Return to program window");
//             setExtendedState(JFrame.MAXIMIZED_BOTH);
            System.out.println("\n\n"+initOrder);
            if (currentInit < 0) {currentInit = 0;}
         }
         else { manualInput.append("    Initiative already active\n"); }         
      }
   }
   
   public class endInitiativeListener implements ActionListener {
      public endInitiativeListener() {}
      public void actionPerformed(ActionEvent e) {
         if  (initiative) {
//             getContentPane().remove(noteSection);
//             setVisible(false);
//             add(namesPanel);
//             System.out.println("Panels added");
            initiative = false;
            initOrder.clear();
            System.out.println("\n\n"+initOrder);
            currentInit = -1;
            manualInput.append("    Ending Initiative\n");
            enemiesGotten = false;
            playersGotten = false; 
            combatantsGotten = false; 
            numberCheck = false;
         } 
         else { manualInput.append("     Initiative not active\n"); }
      }
   }
   public class nextCombatantListener implements ActionListener {
      public nextCombatantListener() {}
      public void actionPerformed(ActionEvent e) {
         if (!initiative) {
            manualInput.append("     Initiative is not active\n"); }
         else {
            manualInput.append("    "+initOrder.get(currentInit)+" has recieved initiative.\n"); 
         }
         if (currentInit == initOrder.size()-1) {currentInit=0;}
         else {currentInit++;}
         }
      }
            
   public class crossBowAttackListener implements ActionListener {
      public crossBowAttackListener() {}
      public void actionPerformed(ActionEvent e) {
      int addOn = 2;
      manualInput.append("  Cross bow attack: 1d20+"+prof+"= "+d20(1,prof)+"\n   Damage: 1d10+4= "+d10(1,dex+prof)+"\n");
      manualInput.append("    If Sneak: 2d6= "+d6(2,0)+"\n\n");
      }
   }
   public class darkStabListener implements ActionListener {
      public darkStabListener() {}
      public void actionPerformed(ActionEvent e) {
      int addOn = 1; //specific to the dark dagger
      manualInput.append("  Dark stabby stab: 1d20+"+prof+"= "+d20(1,prof+addOn)+"\n   Damage: 2d4+5= "+d4(2,dex+prof+addOn)+"\n");
      manualInput.append("    If bonus action: 2d4= "+d4(2,0)+"\n");
      manualInput.append("    If Sneak: 2d6= "+d6(2,0)+"\n\n");      
      }
   }
   public class rapierListener implements ActionListener {
      public void actionPerformed(ActionEvent e) {
         manualInput.append("  Rapier: 1d20+3= "+d20(1,3)+"\n   Damage: 1d8+"+(dex+prof)+"= "+d8(1,dex+prof)+"\n    If bonus action: 1d8= "+d8(1,0)+"\n");
         manualInput.append("    If Sneak: 2d6= "+d6(2,0)+"\n\n");
      }
   }
   public class healersKitListener implements ActionListener {
      public healersKitListener() {}
      public void actionPerformed(ActionEvent e) {
      int addOn = 1;
      manualInput.append("  Healer's Kit:\n");
      manualInput.append("    Medicine: 1d20+"+prof+"= "+d20(1,prof)+"\n");
      manualInput.append("    Stabilize (1 use): Instant grants 1 hp to downed creatures\n");
      manualInput.append("    Basic Heal (1 use): 1d6+4= "+d6(1,4)+"\n");
      manualInput.append("    Awakened Mind Buff (3 uses): 12DC\n");
      
      manualInput.append("\n");
      }
   }

   public class doneButtonListener implements ActionListener {
      public doneButtonListener() {}
      public void actionPerformed(ActionEvent e) {
         if (!numberCheck) {
            if (Integer.valueOf(inputField.getText().trim()) <=20) {lessThanTwenty = true; messageField.append("Enter in names now\n");}
            inputField.setText("    "); 
            numberCheck = true;
         }
         else {
            if (!combatantsGotten && lessThanTwenty) {
               System.out.println("Less than twenty");
               combatants = getInputFromUser();
               messageField.append("Coolios.\n");
               enemiesGotten = true;
               playersGotten = true;
               combatantsGotten = true;
               addCombatants();
            }
            
            if (!playersGotten && !lessThanTwenty) {
               messageField.append("Enter in all combatants\n");
               if (!lessThanTwenty) {
                  System.out.println("More than twenty");
                  players = getInputFromUser();
                  messageField.append("Enter in the enemy names now\n");
                  playersGotten = true;
                  addPlayers();
               }
            }
            else if (!enemiesGotten && !lessThanTwenty) {
               enemies = getInputFromUser();
               messageField.append("Coolios.\n");
               enemiesGotten = true;
               addEnemies();
            }
            
            if (playersGotten && enemiesGotten) {
               setDiceVis();
            }
         }
      }
   }
   //keyboard listener
   KeyListener listener = new KeyListener() {
      @Override
      public void keyPressed(KeyEvent event) {
//           printEventInfo("Key Pressed", event);
          if (enterPressed(event)) {
            String forOrder = enterHit(); //because enterHit() has to run for dieRoll to work
            manualInput.append("    "+dieRoll+" = "+forOrder+"\n");
          }
      }
      @Override
      public void keyReleased(KeyEvent event) {
//           printEventInfo("Key Released", event);
      }
      @Override
      public void keyTyped(KeyEvent event) {
//           printEventInfo("Key Typed", event);
      }
      private void printEventInfo(String str, KeyEvent e) {
          System.out.println(str);
          int code = e.getKeyCode();
          System.out.println("   Code: " + code);
          System.out.println("   Char: " + e.getKeyChar());
          int mods = e.getModifiersEx();
          System.out.println("    Mods: "
                  + KeyEvent.getModifiersExText(mods));
          System.out.println("    Location: "
                  + keyboardLocation(e.getKeyLocation()));
          System.out.println("    Action? " + e.isActionKey());
      }
      public boolean enterPressed(KeyEvent e) {
         boolean gg=false;
         if (e.getKeyCode() == 10) {System.out.println("Enter hit"); gg= true;}
         return gg;
      }
      
      private String keyboardLocation(int keybrd) {
          switch (keybrd) {
              case KeyEvent.KEY_LOCATION_RIGHT:
                  return "Right";
              case KeyEvent.KEY_LOCATION_LEFT:
                  return "Left";
              case KeyEvent.KEY_LOCATION_NUMPAD:
                  return "NumPad";
              case KeyEvent.KEY_LOCATION_STANDARD:
                  return "Standard";
              case KeyEvent.KEY_LOCATION_UNKNOWN:
              default:
                  return "Unknown";
          }
      }
   }; 
      
   //rounded button coreners
   private static class RoundedBorder implements Border {
    private int radius;

    RoundedBorder(int radius) {
        this.radius = radius;
    }

    public Insets getBorderInsets(Component c) {
        return new Insets(this.radius+1, this.radius+1, this.radius+2, this.radius);
    }

    public boolean isBorderOpaque() {
        return true;
    }

    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        g.drawRoundRect(x, y, width-1, height-1, radius, radius);
    }
   }
   // Overide dispose method.
   @Override
   public void dispose(){
      saveNotes();
      System.out.println("exiting...");
      System.exit(0);
   }
   
   //skill checkls. Buckle, this'll take a while
   public class strengthMenuListener implements ActionListener {
      public  strengthMenuListener() {}
      public void actionPerformed(ActionEvent e) {
         System.out.println("-");
         manualInput.append("    Strength: "+d20(1,strength)+"\n");
      }
   }
   public class dexMenuListener implements ActionListener {
      public dexMenuListener() {}
      public void actionPerformed(ActionEvent e) {
         System.out.println("-");
         manualInput.append("    Dexterity: "+d20(1,dex)+"\n");
      }
   }
   public class conMenuListener implements ActionListener {
      public conMenuListener() {}
      public void actionPerformed(ActionEvent e) {
         System.out.println("-");
         manualInput.append("    Consitution: "+d20(1,con)+"\n" );
      }
   }
   public class intelligenceMenuListener implements ActionListener {
      public intelligenceMenuListener() {}
      public void actionPerformed(ActionEvent e) {
         System.out.println("-");
         manualInput.append("    Intelligence: "+d20(1,intelligence)+"\n" );
      }
   }
   public class wisdomMenuListener implements ActionListener {
      public wisdomMenuListener() {}
      public void actionPerformed(ActionEvent e) {
         System.out.println("-");
         manualInput.append("    Wisdom: "+d20(1,wisdom)+"\n");
      }
   }
   public class charismaMenuListener implements ActionListener {
      public charismaMenuListener() {}
      public void actionPerformed(ActionEvent e) {
         System.out.println("-");
         manualInput.append("    Charisma: "+d20(1,charisma)+"\n");
      }
   }
   public class acrobaticsMenuListener implements ActionListener {
      public acrobaticsMenuListener() {}
      public void actionPerformed(ActionEvent e) {
         System.out.println("-");
         manualInput.append("    Acrobatics: "+d20(1,strength)+"\n");
      }
   }
   public class animalHMenuListener implements ActionListener {
      public animalHMenuListener() {}
      public void actionPerformed(ActionEvent e) {
         System.out.println("-");
         manualInput.append("    Animal Handling: "+d20(1,animalHandling)+"\n" );
      }
   }
   public class arcanaMenuListener implements ActionListener {
      public arcanaMenuListener() {}
      public void actionPerformed(ActionEvent e) {
         System.out.println("-");
         manualInput.append("    Arcana: "+d20(1,arcana)+"\n" );
      }
   }
   public class athleticsMenuListener implements ActionListener {
      public athleticsMenuListener() {}
      public void actionPerformed(ActionEvent e) {
         System.out.println("-");
         manualInput.append("    Athletics: "+d20(1,athletics)+"\n" );
      }
   }
   public class deceptionMenuListener implements ActionListener {
      public deceptionMenuListener() {}
      public void actionPerformed(ActionEvent e) {
         System.out.println("-");
         manualInput.append("    Deception: "+d20(1,deception)+"\n" );
      }
   }
   public class historyMenuListener implements ActionListener {
      public historyMenuListener() {}
      public void actionPerformed(ActionEvent e) {
         System.out.println("-");
         manualInput.append("    History: "+d20(1,history)+"\n" );
      }
   }
   public class insightMenuListener implements ActionListener {
      public insightMenuListener() {}
      public void actionPerformed(ActionEvent e) {
         System.out.println("-");
         manualInput.append("    Insight: "+d20(1,strength)+"\n" );
      }
   }
   public class intimidationMenuListener implements ActionListener {
      public intimidationMenuListener() {}
      public void actionPerformed(ActionEvent e) {
         System.out.println("-");
         manualInput.append("    Intimidation: "+d20(1,intimidation)+"\n" );
      }
   }
   public class investigationMenuListener implements ActionListener {
      public investigationMenuListener() {}
      public void actionPerformed(ActionEvent e) {
         System.out.println("-");
         manualInput.append("    Investigation: "+d20(1,investigation)+"\n" );
      }
   }
   public class medicineMenuListener implements ActionListener {
      public medicineMenuListener() {}
      public void actionPerformed(ActionEvent e) {
         System.out.println("-");
         manualInput.append("    Medicine: "+d20(1,medicine)+"\n" );
      }
   }
   public class natureMenuListener implements ActionListener {
      public natureMenuListener() {}
      public void actionPerformed(ActionEvent e) {
         System.out.println("-");
         manualInput.append("    Nature: "+d20(1,nature)+"\n" );
      }
   }
   public class perceptionMenuListener implements ActionListener {
      public perceptionMenuListener() {}
      public void actionPerformed(ActionEvent e) {
         System.out.println("-");
         manualInput.append("    Perception: "+d20(1,perception)+"\n" );
      }
   }
   public class performanceMenuListener implements ActionListener {
      public performanceMenuListener() {}
      public void actionPerformed(ActionEvent e) {
         System.out.println("-");
         manualInput.append("    Performance: "+d20(1,performance)+"\n" );
      }
   }
   public class persuasionMenuListener implements ActionListener {
      public persuasionMenuListener() {}
      public void actionPerformed(ActionEvent e) {
         System.out.println("-");
         manualInput.append("    Persuasion: "+d20(1,persuasion)+"\n" );
      }
   }
   public class religionMenuListener implements ActionListener {
      public religionMenuListener() {}
      public void actionPerformed(ActionEvent e) {
         System.out.println("-");
         manualInput.append("    Religion: "+d20(1,religion)+"\n" );
      }
   }
   public class sleightOHMenuListener implements ActionListener {
      public sleightOHMenuListener() {}
      public void actionPerformed(ActionEvent e) {
         System.out.println("-");
         manualInput.append("    Sleight of Hand: "+d20(1,sleightOfHand)+"\n" );
      }
   }
   public class stealthMenuListener implements ActionListener {
      public stealthMenuListener() {}
      public void actionPerformed(ActionEvent e) {
         System.out.println("-");
         manualInput.append("    Stealth: "+d20(1,stealth)+"\n" );
      }
   }
   public class survivalMenuListener implements ActionListener {
      public  survivalMenuListener() {}
      public void actionPerformed(ActionEvent e) {
         System.out.println("-");
         manualInput.append("    Survival: "+d20(1,survival)+"\n" );
      }
   }
   
   //Player classes (note: actually put this in later)
   
}
         
      
