package me.JayzaSapphire;

import java.util.HashSet;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class FirstJoinGod extends JavaPlugin implements Listener {

	//A HashSet, stores players name.
	private HashSet<String> set = new HashSet<String>();
	
	public void onEnable() {
		//Register events to this class for this plugin.
		getServer().getPluginManager().registerEvents(this, this);
	}
	
	@EventHandler
	public void onPlayerjoin(PlayerJoinEvent event) {
		//Get the player
		Player user = event.getPlayer();
		
		//Check if the user has played before
		if (!user.hasPlayedBefore()) {
			//If the user hasn't, add them to the timer
			startTimer(user);
		}
	}
	
	@EventHandler
	public void onEntityDamage(EntityDamageEvent event) {
		//Get the entity
		Entity ent = event.getEntity();
		
		//Check if the entity is a player
		if (ent instanceof Player) {
			//Convert the entity to a player
			Player user = (Player)ent;
			
			//Check if the entity is in the hashset
			if (set.contains(user.getName())) {
				//Cancel the event
				event.setCancelled(true);
			}
		}
	}
	
	//The timer
	public void startTimer(Player user) {
		//The players name
		final String name = user.getName();
		//The amount of seconds the timer should go for
		int seconds = 60;
		
		//Start the timer, this = the plugin
		getServer().getScheduler().scheduleAsyncDelayedTask(this, new Runnable() {
			public void run() {
				//Check if the hashset contains the name, just incase it was removed for some reason
				if (set.contains(name)) {
					//Remove the name
					set.remove(name);
				}
			}
			//How long the timer goes for, amount of seconds * 20 (20 server ticks per second)
		}, seconds * 20);
	}
}