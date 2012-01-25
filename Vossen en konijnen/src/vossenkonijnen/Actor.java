package vossenkonijnen;

import java.util.List;

public interface Actor {

	public abstract void act(List<Animal> newFoxes);
	
	public abstract boolean isAlive();
}
