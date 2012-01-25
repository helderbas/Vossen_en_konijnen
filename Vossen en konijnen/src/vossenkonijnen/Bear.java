package vossenkonijnen;

import java.util.List;
import java.util.Random;

public class Bear extends Animal 
{
	
	private static final int BREEDING_AGE = 200 ;
	// The age to which a fox can live.
	private static final int MAX_AGE = 300;
	// The likelihood of a fox breeding.
	private static final double BREEDING_PROBABILITY = 0.10;
	// The maximum number of births.
	private static final int MAX_LITTER_SIZE = 2;
	// The food value of a single rabbit. In effect, this is the
	// number of steps a fox can go before it has to eat again.
	private static final int FOOD_VALUE = 10;
	
	// A shared random number generator to control breeding.
	private static final Random rand = Randomizer.getRandom();
	
	// Individual characteristics (instance fields).
	// The fox's age.
	private int age;
	
	// The fox's food level, which is increased by eating rabbits.
	private int foodLevel;
	
	public Bear(boolean randomAge, Field field, Location location){
		
		super(field, location);
		age =0;
		if(randomAge) {
			age = rand.nextInt(MAX_AGE);
		}
		
		
	}
	
	public void act(List<Actor> newBears)
	{
	    incrementAge();
	    if(isAlive()) {
	        giveBirth(newBears);            
	        // Try to move into a free location.
		Location newLocation = getField().freeAdjacentLocation(getLocation());
		if(newLocation != null) {
	    setLocation(newLocation);
			}
			else {
			    // Overcrowding.
	            setDead();
	        }
	    }
	}
	
	 private void incrementAge()
	{
	    age++;
	    if(age > MAX_AGE) {
	        setDead();
	    }
	}
	
	 private void giveBirth(List<Actor> newBears)
	{
	    // New rabbits are born into adjacent locations.
	// Get a list of adjacent free locations.
	        Field field = getField();
	        List<Location> free = field.getFreeAdjacentLocations(getLocation());
	        int births = breed();
	        for(int b = 0; b < births && free.size() > 0; b++) {
	            Location loc = free.remove(0);
	            Bear young = new Bear(false, field, loc);
	            newBears.add(young);
	        }
    }
	
	private int breed()
    {
        int births = 0;
        if(canBreed() && rand.nextDouble() <= BREEDING_PROBABILITY) {
            births = rand.nextInt(MAX_LITTER_SIZE) + 1;
        }
        return births;
    }
	 
	private boolean canBreed()
    {
        return age >= BREEDING_AGE;
    }
	    
    public boolean isAlive()
    {
    	return alive;
    }
}
	 
 
	
	
	
	
	
	


