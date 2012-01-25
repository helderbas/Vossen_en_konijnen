package vossenkonijnen;

import java.util.Iterator;
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
        incrementHunger();
        if(isAlive()) {
            giveBirth(newBears);            
            // Move towards a source of food if found.
            Location location = getLocation();
            Location newLocation = findFood(location);
            if(newLocation == null) { 
                // No food found - try to move to a free location.
                newLocation = getField().freeAdjacentLocation(location);
            }
            // See if it was possible to move.
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
	 
 	private Location findFood(Location location)
    {
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while(it.hasNext()) {
            Location where = it.next();
            Object animal = field.getObjectAt(where);
            if(animal instanceof Rabbit) {
                Rabbit rabbit = (Rabbit) animal;
                if(rabbit.isAlive()) { 
                    rabbit.setDead();
                    return where;
                }
            }
            if(animal instanceof Fox) {
                Fox fox = (Fox) animal;
                if(fox.isAlive()) { 
                    fox.setDead(); 
                    foodLevel = FOOD_VALUE;
                    // Remove the dead rabbit from the field.
                    return where;
                }
            }
        }
        return null;
    }
	
	private void incrementHunger()
    {
        foodLevel--;
        if(foodLevel <= 0) {
            setDead();
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
	 
 
	
	
	
	
	
	


