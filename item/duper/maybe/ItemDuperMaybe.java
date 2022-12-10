package item.duper.maybe;

import java.awt.Color;
import java.awt.Font;
import java.awt.TextArea;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import org.dreambot.api.Client;
import org.dreambot.api.data.GameState;
import org.dreambot.api.input.Mouse;
import org.dreambot.api.input.event.impl.mouse.impl.click.ClickMode;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.RSLoginResponse;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.bank.Bank;
import org.dreambot.api.methods.dialogues.Dialogues;
import org.dreambot.api.methods.input.mouse.MouseSettings;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.tabs.Tab;
import org.dreambot.api.methods.tabs.Tabs;
import org.dreambot.api.methods.trade.Trade;
import org.dreambot.api.methods.trade.TradeUser;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.methods.widget.Widgets;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManager;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.script.listener.ChatListener;
import org.dreambot.api.utilities.Logger;
import org.dreambot.api.utilities.Sleep;
import org.dreambot.api.utilities.Timer;
import org.dreambot.api.wrappers.interactive.Player;
import org.dreambot.api.wrappers.items.Item;
import org.dreambot.api.wrappers.widgets.WidgetChild;
import org.dreambot.api.wrappers.widgets.message.Message;
import org.dreambot.api.wrappers.widgets.message.MessageType;

@ScriptManifest(
		name = "Item Duper (Maybe)", 
		author = "420x69x420",
		version = 0.01, 
		category = Category.MONEYMAKING)

public class ItemDuperMaybe extends AbstractScript implements ChatListener{

	public static boolean closedGUI = false;
	public static List<String> names = new ArrayList<>();
	public static Timer idleTimer = null;
	public static Timer AFKTimer = null;
	public static Timer runTimer = null;
	public static boolean tradedOnce = false;
	public static boolean shouldLog = false;
	
	public void createGUI() {
		JFrame frame = new JFrame();
		frame.setTitle("Item Duper (Maybe)");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setBounds(100, 100, 450, 300);
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		frame.setContentPane(contentPane);
		contentPane.setLayout(null);
		
		TextArea textArea = new TextArea();
		textArea.setBackground(new Color(128, 128, 128));
		textArea.setText("accountName1\r\naccountName2\r\netc");
		textArea.setBounds(44, 64, 380, 160);
		contentPane.add(textArea);
		
		JLabel lblNewLabel = new JLabel("Here you can input the names of accounts to trade.");
		lblNewLabel.setBounds(44, 11, 359, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblTheSyntaxIs = new JLabel("The syntax is one in-game username per line.");
		lblTheSyntaxIs.setBounds(44, 25, 359, 14);
		contentPane.add(lblTheSyntaxIs);
		
		JLabel lblPleaseKeepThe = new JLabel("Please keep the order of names the same");
		lblPleaseKeepThe.setBounds(44, 44, 359, 14);
		contentPane.add(lblPleaseKeepThe);
		
		JButton btnStartButton = new JButton("Start Maybe Duping");
		btnStartButton.setBounds(10, 230, 414, 23);
		btnStartButton.addActionListener(l -> {
			String input = textArea.getText();
			if(input.contains("\r"))
			{
				for(String name : input.split("\r"))
				{
					name = name.replace("\n","");
					if(name.isEmpty()) continue;
					name = name.toLowerCase();
					Logger.log("Adding name from GUI: " + name);
					names.add(name);
				}
			}
			else 
			{
				input = input.toLowerCase();
				Logger.log("Adding name from GUI: " + input);
				names.add(input);
			}
			closedGUI = true;
			Logger.log("Starting after closing GUI!");
			frame.dispose();
		});
		contentPane.add(btnStartButton);
		frame.setVisible(true);
	}
	
	@Override
	public void onStart()
	{
		Logger.log("OnStart Script!");
		try {
			SwingUtilities.invokeAndWait(() -> {
			    createGUI();
			});
		} catch (InvocationTargetException | InterruptedException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void onStart(String[] i)
	{
		closedGUI = true;
		Logger.log("OnStart Script quickstart parameters!");
		for(String param : i)
		{
			String para = param.toLowerCase();
			if(para.contains("{"))
			{
				para = para.replace("{","").replace("}","");
				if(para.contains(","))
				{
					for(String name : para.split(","))
					{
						name = name.toLowerCase();
						Logger.log("Adding name from quickstart: " + name);
						names.add(name);
					}
				} else 
				{
					para = para.toLowerCase();
					Logger.log("Adding name from quickstart: " + para);
					names.add(para);
				}
			}
		}
	}
	public static void idle()
	{
		Mouse.click(Players.getLocal(), true);
		Sleep.sleep(111,6666);
		Mouse.moveMouseOutsideScreen();
	}
	@Override
	public int onLoop() 
	{
		if(!closedGUI) return Calculations.random(500, 1000);
		if(!loggedNLoaded()) return Calculations.random(500, 1000);
		if(AFKTimer == null || AFKTimer.finished()) 
		{
			idle();
			AFKTimer = new Timer(Calculations.random(180000, 240000));
		}
		
		int maxIndex = names.size() - 1;
		int ourIndex = names.indexOf(Players.getLocal().getName().toLowerCase());
		int nextIndex = 0;
		int prevIndex = 0;
		if(maxIndex <= 1)
		{
			Logger.log("Max list of duping accs is less than or equal to 1! Need at least 2!! Stopping script...");
			return -1;
		}
		if(ourIndex == maxIndex) nextIndex = 0;
		else nextIndex = ourIndex + 1;
		String nextIndexName = names.get(nextIndex);
		
		if(ourIndex == 0) prevIndex = maxIndex;
		else prevIndex = ourIndex - 1;
		String prevIndexName = names.get(prevIndex);
		if(Trade.isOpen())
		{
			String trader = Trade.getTradingWith().toLowerCase();
			if(!names.contains(trader))
			{
				Logger.log("Found trading with un-listed acc: " + trader);
				Trade.declineTrade();
				Sleep.sleepTicks(2);
				return Calculations.random(500, 1000);
			}
			
			if(Trade.isOpen(1))
			{
				//offer all items if next person in trade queue, otherwise just accept all
				if(trader.equals(nextIndexName))
				{
					if(!Inventory.isEmpty())
					{
						for(Item i : Inventory.all())
						{
							if(i == null) continue;
							if(i.interact("Offer-All")) 
							{
								Sleep.sleepTick();
								Sleep.sleep(111,666);
							}
						}
						Sleep.sleepTicks(2);
						return Calculations.random(500, 1000);
					}
				}
				if(!Trade.hasAcceptedTrade(TradeUser.US) && 
						Trade.acceptTrade()) 
				{
					Sleep.sleepTicks(2);
				}
				return Calculations.random(500, 1000);
			}
			if(!Trade.hasAcceptedTrade(TradeUser.US) && 
					Trade.acceptTrade()) 
			{
				if(trader.equals(nextIndexName)) tradedOnce = true;
				else if(trader.equals(prevIndexName)) shouldLog = true;
				Sleep.sleepTicks(2);
			}
			return Calculations.random(500, 1000);
		}
		if(latestTrader != null)
		{
			Player latestTraderPlayer = Players.closest(latestTrader);
			if(latestTraderPlayer == null || !latestTraderPlayer.exists())
			{
				latestTrader = null;
			}
			else if(latestTrader.equals(prevIndexName))
			{
				Trade.tradeWithPlayer(latestTrader);
				Sleep.sleepUntil(() -> Trade.isOpen(),Calculations.random(4444, 6666));
				latestTrader = null;
				return Calculations.random(500, 1000);
			}
		}
		if(!Inventory.isEmpty())
		{
			if(shouldLog)
			{
				if(logout()) shouldLog = false;
				return Calculations.random(500, 1000);
			}
			if(ourIndex == 0 && tradedOnce)
			{
				if(idleTimer == null) idleTimer = new Timer(Calculations.random(900000, 1200000));
				if(idleTimer.finished())
				{
					idleTimer = null;
					tradedOnce = false;
				}
				return Calculations.random(500, 1000);
			}
			Trade.tradeWithPlayer(nextIndexName);
			Sleep.sleepUntil(() -> Trade.isOpen(),Calculations.random(4444, 6666));
			return Calculations.random(500, 1000);
		}
		return Calculations.random(500, 1000);
	}
	
	public static boolean loggedNLoaded()
	{
		if(Client.getLoginResponse() == RSLoginResponse.NO_REPLY)
		{
			Logger.log("Think we duped maybe? No reply from logout server! Exiting script...");
			ScriptManager.getScriptManager().stop();
			return false;
		}
		if(Client.getGameState() == GameState.LOADING || 
        		Client.getGameState() == GameState.GAME_LOADING)
		{
			Sleep.sleepUntil(() -> (Client.getGameState() != GameState.LOADING && 
        		Client.getGameState() != GameState.GAME_LOADING), 10000);
			Sleep.sleep(1111, 2222);
			return false;
		}
		else if(Client.getGameState() != GameState.LOGGED_IN)
		{
			if(shouldLog) shouldLog = false;
			Sleep.sleepUntil(() -> Client.getGameState() == GameState.LOGGED_IN, 7000);
			Sleep.sleep(1111, 2222);
			return false;
		}
		return true;
	}
	public static String latestTrader = null;
	@Override
	public void onMessage(Message msg)
	{
		if(!msg.getType().equals(MessageType.PLAYER))
		{
			if(msg.getMessage().contains(" wishes to trade with you."))
			{
				latestTrader = msg.getUsername().toLowerCase();
				return;
			}
		}
	}
	public static boolean logout()
    {
    	if(!Client.isLoggedIn()) 
    	{
    		Logger.log("Logged out!");
    		return true;
    	}
    	if(Dialogues.areOptionsAvailable()) 
    	{
    		Walking.clickTileOnMinimap(Players.getLocal().getTile());
    		return false;
    	}
    	if(Dialogues.canContinue()) 
    	{
    		Dialogues.continueDialogue();
    		return false;
    	}
    	if(!Tabs.isOpen(Tab.LOGOUT)) 
    	{
    		Tabs.openWithFKey(Tab.LOGOUT);
    		return false;
    	}
    	WidgetChild logoutButtonWorlds = Widgets.getWidgetChild(69, 23);
    	WidgetChild logoutButton = Widgets.getWidgetChild(182, 8);
    	if(logoutButtonWorlds != null && logoutButtonWorlds.isVisible())
    	{
    		if(logoutButtonWorlds.interact("Logout")) Logger.log("Clicked logout button! (worlds list)");
    		Sleep.sleepUntil(() -> !Client.isLoggedIn(), Calculations.random(5000,8888));
    		return !Client.isLoggedIn();
    	} 
    	if(logoutButton != null && logoutButton.isVisible())
    	{
    		if(logoutButton.interact("Logout")) Logger.log("Clicked logout button!");
    		Sleep.sleepUntil(() -> !Client.isLoggedIn(), Calculations.random(5000,8888));
    		return !Client.isLoggedIn();
    	}
    	Logger.log("Didn't execute any logout buttons when should have!!");
    	return false;
    }
}
