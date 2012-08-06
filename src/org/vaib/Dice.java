package org.vaib;

import static org.vaib.LudoMain.*;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBuilder;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class Dice implements Runnable{

	// public static int diceScore;
	public Button button = null;
	public ImageView currentView = null;
	public ScheduledExecutorService excecutor;
	public Label label;
	public ScoreBoard board;
	private static Dice dice;
	
	
	// public

	public static Dice getInstance() {
		if(null == dice){
			dice = new Dice();
		}
		return dice;
	}
	public Group getDice(){
		Group group = new Group();
		VBox box = new VBox();
		box.setCache(true);
		box.getChildren().addAll(ScoreBoard.getInstance().getScoreBoard());
		box.getChildren().add(button);
		box.setTranslateX(17 * xWidth);
		box.setTranslateY(12 * yHeight);
		group.getChildren().addAll(box);
		return group;
	}

	private Dice() {
		excecutor = Executors.newSingleThreadScheduledExecutor();
		
		button = ButtonBuilder.create().text("Get Score").defaultButton(true)
				.scaleY(2).scaleX(3)
				.onMousePressed(new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent event) {
						// TODO Auto-generated method stub
						execute("start");
					}

				}).onMouseReleased(new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent event) {
						// TODO Auto-generated method stub
						execute("stop");
					}

				}).build();
	}

	
	public void execute(String command) {
		if ("start".equals(command)) {
			Coin.undoPath = null;
			excecutor = Executors.newScheduledThreadPool(1);
			excecutor
					.scheduleWithFixedDelay(new Dice(), 0, 1, TimeUnit.SECONDS);
		} else {
			ScoreBoard.getInstance().score.set(String.valueOf(move));
			excecutor.shutdown();
		}
	}

	@Override
	public void run() {
		LudoMain.move = (int) ((Math.random() * 10) % 6 + 1);
		System.out.println("LudoMain.move " + move);
	}
}
