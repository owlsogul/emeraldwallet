package owlsogul.com.emeraldwallet.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import owlsogul.com.emeraldwallet.EmeraldWallet;

public class EmeraldWalletOpenEvent extends Event implements Cancellable{

	boolean isCancelled = false;
	private static HandlerList handlerList = new HandlerList();
	
	private EmeraldWallet wallet;
	private Player player;
	public EmeraldWalletOpenEvent(Player player, EmeraldWallet wallet) {
		this.player = player;		
		this.wallet = wallet;
	}
	
	@Override
	public boolean isCancelled() {
		return isCancelled;
	}

	@Override
	public void setCancelled(boolean arg0) {
		isCancelled = arg0;		
	}

	@Override
	public HandlerList getHandlers() {
		return handlerList;
	}

	public EmeraldWallet getWallet() {
		return wallet;
	}


	public Player getPlayer() {
		return player;
	}

}
