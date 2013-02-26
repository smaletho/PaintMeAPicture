package edu.msu.project1.barrelofmonkeys;

public class GameManager {
	
	private enum Category { Animal, Building, Object, Action, MSU };
	private enum Round { Drawing, Guessing };
	private String player1name;
	private String player2name;
	private String gameHint;
	private String gameSolution;
	private int gameRound;
	private int player1score;
	private int player2score;
	private DrawingView drawingView;
	private Category category;
	private Round round;
	private static GameManager gameManager;
	
	public static GameManager get() {
		return gameManager;
	}
	
	public DrawingView getDrawingView() {
		return drawingView;
	}

	public void setDrawingView(DrawingView drawingView) {
		this.drawingView = drawingView;
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


	public Category getCategory() {
		return category;
	}


	public void setCategory(Category category) {
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
		this.gameSolution = gameSolution;
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
		this.player1score = player1score;
	}


	public int getPlayer2score() {
		return player2score;
	}


	public void setPlayer2score(int player2score) {
		this.player2score = player2score;
	}


	public GameManager() {
		gameManager = this;
		drawingView = null;
		player1name = null;
		player2name = null;
		gameHint = null;
		gameSolution = null;
		gameRound = 1;
		player1score = 0;
		player2score = 0;
		category = null;
		round = null;
	}

}
