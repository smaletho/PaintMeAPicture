package edu.msu.project1.barrelofmonkeys;

public class GameManager {
	
	private enum Round { Drawing, Guessing };
	private String player1name;
	private String player2name;
	private String gameHint;
	private String gameSolution;
	private int gameRound;
	private int player1score;
	private int player2score;
	private String category;
	private Round round;
	private int numRounds;
	private static GameManager gameManager;
	private int currentPlayer;
	
	public int getCurrentPlayer() {
		return currentPlayer;
	}
	
	public void switchPlayers() {
		if(currentPlayer == 1) {
			currentPlayer = 2;
		} 
		else {
			currentPlayer = 1;
		}
	}
	
	public static GameManager get() {
		return gameManager;
	}

	public void set(GameManager game) {
		gameManager = game;
	}

	public Round getRound() {
		return round;
	}


	public void setRound(Round round) {
		this.round = round;
	}


	public String getPlayer1name() {
		return player1name;
	}


	public void setPlayer1name(String player1name) {
		this.player1name = player1name;
	}


	public String getCategory() {
		return category;
	}


	public void setCategory(String category) {
		this.category = category;
	}


	public String getPlayer2name() {
		return player2name;
	}


	public void setPlayer2name(String player2name) {
		this.player2name = player2name;
	}


	public String getGameHint() {
		return gameHint;
	}


	public void setGameHint(String gameHint) {
		this.gameHint = gameHint;
	}


	public String getGameSolution() {
		return gameSolution;
	}


	public void setGameSolution(String gameSolution) {
		this.gameSolution = gameSolution.toUpperCase();
	}


	public int getGameRound() {
		return gameRound;
	}


	public void setGameRound() {
		gameRound++;
	}


	public int getPlayer1score() {
		return player1score;
	}


	public void setPlayer1score(int player1score) {
		this.player1score += player1score;
	}


	public int getPlayer2score() {
		return player2score;
	}


	public void setPlayer2score(int player2score) {
		this.player2score += player2score;
	}


	public GameManager() {
		gameManager = this;
		player1name = null;
		player2name = null;
		gameHint = null;
		gameSolution = null;
		gameRound = 1;
		player1score = 0;
		player2score = 0;
		category = null;
		round = null;
		currentPlayer = 2;
	}

	public int getNumRounds() {
		return numRounds;
	}

	public void setNumRounds(int numRounds) {
		this.numRounds = numRounds;
	}

}
