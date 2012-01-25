package vossenkonijnen;

import java.util.List;

public interface Actor {

	public abstract void act(List<Actor> newActors);
	
	public abstract boolean isAlive();
}
