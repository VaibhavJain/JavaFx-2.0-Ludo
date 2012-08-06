package org.vaib;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.RectangleBuilder;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextBuilder;

public class ScoreBoard extends Group {
	private Text text;
	private Rectangle rectangle;
	public StringProperty score;
	private static ScoreBoard board;

	public static ScoreBoard getInstance() {
		if (null == board) {
			board = new ScoreBoard();
		}
		return board;
	}

	public Text getText() {
		return text;
	}

	public void setText(Text text) {
		this.text = text;
	}

	public Rectangle getRectangle() {
		return rectangle;
	}

	public void setRectangle(Rectangle rectangle) {
		this.rectangle = rectangle;
	}

	public StringProperty getScore() {
		return score;
	}

	public void setScore(StringProperty score) {
		this.score = score;
	}

	private ScoreBoard() {
		DropShadow shadow = new DropShadow();
		shadow.setColor(Color.GRAY);
		shadow.setOffsetY(3.0f);
		InnerShadow is = new InnerShadow();
		is.setOffsetX(4.0f);
		is.setOffsetY(4.0f);
		is.setInput(shadow);

		score = new SimpleStringProperty("");

		this.text = TextBuilder.create()
				.font(Font.font("Verdana", FontWeight.BOLD, 50))
				.fill(Color.RED).effect(is).build();
		this.text.textProperty().bind(this.score);

		this.rectangle = RectangleBuilder.create().build();
		this.getChildren().add(rectangle);
		this.getChildren().add(text);

	}

	public Group getScoreBoard() {
		Group group = new Group();
		StackPane.setAlignment(this.rectangle, Pos.BOTTOM_CENTER);
		StackPane.setAlignment(this.text, Pos.TOP_CENTER);
		StackPane pane = new StackPane();
		pane.getChildren().add(this.rectangle);
		pane.getChildren().add(this.text);
		group.getChildren().addAll(pane);
		return group;
	}
}
