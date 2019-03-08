package game;

public class TokenPile
{
	private int player;
	private Token[] tokens;
	private int tokenLeft;
	private int size;
	
	public TokenPile(int player, int size)
	{
		this.size = size;
		this.player = player;
		tokens = new Token[this.size];
		tokenLeft = size;
		
		generateTokens();
		shuffleTokens();
	}
	
	/**
	 * 5% 3's
	 * 10% 2's
	 * 20% 1's 
	 * rest 0's
	 */
	public void generateTokens()
	{
		int tokensGenerated = 0;
		while(tokensGenerated < .05 * size)
			tokens[tokensGenerated++] = Token.createNumberToken(player, 3);
		while(tokensGenerated < .15 * size)
			tokens[tokensGenerated++] = Token.createNumberToken(player, 2);
		while(tokensGenerated < .35 * size)
			tokens[tokensGenerated++] = Token.createNumberToken(player, 1);
		while(tokensGenerated < size)
			tokens[tokensGenerated++] = Token.createNumberToken(player, 0);
	}
	
	public void shuffleTokens()
	{
		for(int i = 0; i < tokens.length - 1; i++)
		{
			int ranIdx = (int)(Math.random() * (tokens.length - i));
			Token temp = tokens[i];
			tokens[i] = tokens[ranIdx];
			tokens[ranIdx] = temp; 
		}
	}
	
	public String toString()
	{
		String result = "";
		for(int i = 0; i < tokens.length; i++)
			result += (i + 1) + ". " + tokens[i].toString() + "\n";
		return result;
	}
}
