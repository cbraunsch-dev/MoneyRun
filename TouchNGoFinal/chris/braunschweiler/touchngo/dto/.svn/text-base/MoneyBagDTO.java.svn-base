package chris.braunschweiler.touchngo.dto;

/**
 * A DTO used to wrap data necessary to construct a new money bag object.
 * This DTO is used by the DataLayer to pass information regarding a new money bag up to the Game Activity.
 * @author chrisbraunschweiler
 *
 */
public class MoneyBagDTO {
	
	private String moneyBagId;
	private int numberOfCoins;
	private int xPos;
	private int yPos;
	
	public MoneyBagDTO(int xPos, int yPos){
		this.xPos = xPos;
		this.yPos = yPos;
		this.moneyBagId = "No ID";
		this.numberOfCoins = 0;
	}
	
	public MoneyBagDTO(int xPos, int yPos, int numberOfCoins){
		this.xPos = xPos;
		this.yPos = yPos;
		this.moneyBagId = "No ID";
		this.numberOfCoins = numberOfCoins;
	}
	
	public MoneyBagDTO(String moneyBagId, int numberOfCoins, int xPos, int yPos){
		this.moneyBagId = moneyBagId;
		this.numberOfCoins = numberOfCoins;
		this.xPos = xPos;
		this.yPos = yPos;
	}
	
	public String getMoneyBagId() {
		return moneyBagId;
	}

	public int getNumberOfCoins() {
		return numberOfCoins;
	}

	public int getXPos() {
		return xPos;
	}

	public int getYPos() {
		return yPos;
	}
}
