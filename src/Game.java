/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2011.07.31
 */

public class Game 
{
    private Parser parser;
    private Room currentRoom;
        
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    public void createRooms()
    {
        Room entrance, danceFloor, bar, bathroomWay, smokingArea, boyBathroom, girlBathroom;
      
        // create the rooms
        entrance = new Room("outside of the club. Go inside and get that girl!!");
        danceFloor = new Room("on the dance floor. Shake that ass.");
        bar = new Room("in the bar. You can get some drinks.");
        bathroomWay = new Room("in the bathroom way. It smells of pee.");
        smokingArea = new Room("in the smoking area. Time to talk with some people.");
        boyBathroom = new Room("in the bathroom, is it the boys or the girls?");
        girlBathroom = new Room("in the bathroom, is it the boys or the girls?");
        
        // initialise room exits
        entrance.setExits(null, smokingArea, null, danceFloor);
        danceFloor.setExits(null, entrance, bathroomWay, bar);
        bar.setExits(null, danceFloor, null, null);
        bathroomWay.setExits(danceFloor, girlBathroom, null, boyBathroom);
        smokingArea.setExits(null, null, null, entrance);
        boyBathroom.setExits(null, bathroomWay, null, null);
        girlBathroom.setExits(null,null,null,bathroomWay);
        

        currentRoom = entrance;  // start game entrance
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printLocationInfo()
    {
        System.out.println("You are " + currentRoom.getDescription());
        System.out.print("Exits: ");
        if(currentRoom.northExit != null) {
        System.out.print("north ");
        }
        if(currentRoom.eastExit != null) {
        System.out.print("east ");
        }
        if(currentRoom.southExit != null) {
        System.out.print("south ");
        }
        if(currentRoom.westExit != null) {
        System.out.print("west ");
        }
        System.out.println();
    }
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to GetTheGirl!");
        System.out.println("The aim of the game is to get the most beautiful girl in the club and take her home");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        printLocationInfo();}
        
    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("go")) {
            goRoom(command);
        }
        else if(commandWord.equals("act")){
            actHere(command);
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }
        
        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You wander around at the club");
        System.out.println("Your command words are:");
        System.out.println("   go quit help act");
    }

    /** 
     * Try to go in one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     */
    
    private void actHere(Command command){
    
        if(!command.hasSecondWord()){
        
            System.out.println("Act with what or who?");
            return;
        }
        String interact = command.getSecondWord();
        
        
        //Prueba n1 del act en 'x' room
       
        /*if(currentRoom.equals(bar) && interact.equals("drink")){
            System.out.println("You have bought a drink");
        }*/
        
    }
    
    
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
           
            return;
        }
        
        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = null;
        if(direction.equals("north")) {
            nextRoom = currentRoom.northExit;
        }
        if(direction.equals("east")) {
            nextRoom = currentRoom.eastExit;
        }
        if(direction.equals("south")) {
            nextRoom = currentRoom.southExit;
        }
        if(direction.equals("west")) {
            nextRoom = currentRoom.westExit;
        }

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            currentRoom = nextRoom;
            printLocationInfo();
            }
            System.out.println();
        }
    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
}
