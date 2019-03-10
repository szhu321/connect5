package game;

public class TokenPile
{
	private int player;
	private Token[] tokens;
	private int tokenLeft;
	private int size;
	private Token[] currentHand = new Token[3];
	
	/**
	 * Creates a new token pile to play with.
	 * @param player The player of this token pile.
	 * @param size This size of this token pile.
	 */
	public TokenPile(int player)
	{
		size = 10;
		this.player = player;
		tokens = new Token[size];
		tokenLeft = size;
		
		generateTokens();
		shuffleTokens();
		populateHand();
	}
	
	/**
	 * Adds more tokens to the current hand.					
	 */
	public void populateHand()
	{
		collapseLeft();
		for(int i = 0; i < currentHand.length; i++)
			if(currentHand[i] == null)
				currentHand[i] = popMasterList();
	}
	
	/**
	 * Removes the top token from the master pile.
	 * Subtracts one from tokenLeft.
	 * @return the token that was removed. 
	 * null if there are no more tokens.
	 */
	private Token popMasterList()
	{
		if(tokenLeft <= 0)
		{
			generateTokens();
			shuffleTokens();
			tokenLeft = size;
		}
		return tokens[--tokenLeft];
	}
	
	private void collapseLeft()
	{
		for(int i = 0; i < currentHand.length - 1; i++)
		{
			if(currentHand[i] == null) //if the current position has nothing swap it with something farther on in the array.
			{
				for(int j = i + 1; j < currentHand.length; j++)
				{
					if(currentHand[j] != null)
					{
						currentHand[i] = currentHand[j];
						currentHand[j] = null;
						break;
					}
				}
			}
		}
	}
	
	/**
	 * 5% 3's
	 * 10% 2's
	 * 20% 1's 
	 * rest 0's
	 */
	private void generateTokens()
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
	
	private void shuffleTokens()
	{
		for(int i = 0; i < tokens.length - 1; i++)
		{
			int ranIdx = (int)(Math.random() * (tokens.length - i));
			Token temp = tokens[i];
			tokens[i] = tokens[ranIdx];
			tokens[ranIdx] = temp; 
		}
	}
	
	/**
	 * Gets a copy of the current hand. Modification rights preserved.
	 * @return Copy of the current hand
	 */
	public Token[] getCurrentHandCopy()
	{
		Token[] result = new Token[currentHand.length];
		for(int i = 0; i < result.length; i++)
			result[i] = currentHand[i];
		return result;
	}
	
	/**
	 * Gets a token from the currentHand and then remove it.
	 * @param idx The index for the currentHand.
	 * @return The removed token.
	 * @throws IllegalArgumentException There is no token at the provided index
	 */
	public Token getToken(int idx) throws IllegalArgumentException
	{
		if(currentHand[idx] == null)
			throw new IllegalArgumentException("There is no token at index " + idx);
		Token result = currentHand[idx];
		currentHand[idx] = null;
		return result;
	}
	
	public String toString()
	{
		String result = "Master List: \n";
		for(int i = 0; i < tokens.length; i++)
			result += (i + 1) + ". " + tokens[i].toString() + "\n";
		result += "\nTokens Left: " + tokenLeft + "\n";
		for(int i = 0; i < currentHand.length; i++)
			result += (i + 1) + ". " + ((currentHand[i] == null) ? "None\n" : currentHand[i].toString() + "\n"); 
		return result;
	}
}
