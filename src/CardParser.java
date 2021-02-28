import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class CardParser 
{
	private String urlString;
	private ArrayList<HearthstoneCard> theMinions;
	
	public CardParser(String urlString)
	{
		this.urlString = urlString;
		theMinions = new ArrayList<HearthstoneCard>();
		URLReader hearthstoneURLReader = new URLReader(this.urlString);
		Object obj = JSONValue.parse(hearthstoneURLReader.gettheURLContents());
	
		if(obj instanceof JSONArray)
		{
			JSONArray array = (JSONArray)obj;
			for(int i = 0; i < array.size(); i++)
			{
				JSONObject cardData = (JSONObject)array.get(i);
		    	if(cardData.containsKey("cost") && cardData.containsKey("name"))
		    	{
		    		if(cardData.containsKey("type") && cardData.get("type").equals("MINION"))
		    		{
			    		String name = (String)cardData.get("name");
			    		int cost = Integer.parseInt(cardData.get("cost").toString());
			    		int attack = Integer.parseInt(cardData.get("attack").toString());
			    		int health = Integer.parseInt(cardData.get("health").toString());
			    		
			    		HearthstoneCard temp = new HearthstoneCard(name, cost, attack, health);
			    		theMinions.add(temp);
		    		}
		    	}
			}		
		}
	}
	
	public void showMinions()
	{
		for(int i = 0; i < this.theMinions.size(); i++)
		{
			this.theMinions.get(i).display();
		}
	}
	
	
	public void selectionSortHighestCostToLowestCost()
	{
		for(int MIN = 0; MIN < theMinions.size(); MIN++)
		{
			int currIndex = findLargest(MIN);
			
			HearthstoneCard temp = this.theMinions.get(currIndex);
			this.theMinions.set(currIndex,  this.theMinions.get(MIN));
			this.theMinions.set(MIN, temp);
		}
	}
	

	private int findLargest(int MIN)
	{
		HearthstoneCard currWinner = this.theMinions.get(MIN);
		int indexOfWinner = MIN;
		
		for(int i = MIN; i < this.theMinions.size(); i++)
		{
			if(this.theMinions.get(i).getCost() > currWinner.getCost())
			{
				currWinner = this.theMinions.get(i);
				indexOfWinner = i;
			}
		}
		
		return indexOfWinner;
	}
	
}
