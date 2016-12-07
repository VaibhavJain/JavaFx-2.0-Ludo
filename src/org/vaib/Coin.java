package org.vaib;

import static org.vaib.LudoMain.lastMovedCoin;
import static org.vaib.LudoMain.xWidth;
import static org.vaib.LudoMain.yHeight;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import javafx.animation.PathTransition;
import javafx.animation.PathTransitionBuilder;
import javafx.beans.property.Property;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.PathElement;
import javafx.scene.shape.StrokeType;
import javafx.util.Duration;

public class Coin extends Circle {

	public static final double radius = xWidth / 2;
	private Color color;
	private Cordenate blockNo;
	private int move;
	private Path path;
	public static Path undoPath;
	private double startX;
	private double startY;

	public Coin(Color color) {
		this.color = color;
		this.setRadius(radius);
		this.setStroke(color);
		this.setStrokeType(StrokeType.INSIDE);
		this.setStrokeWidth(radius);
		this.setOnMousePressed(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent paramT) {
				Coin coin = (Coin) paramT.getSource();
				Cordenate cordenate = coin.getBlockNo();
				if (cordenate.getX() == 100 && cordenate.getY() == 100) {
					//
					coin.setOnStart();
				} else {

					coin.setMove(LudoMain.move);
					coin.transformMove();
				}
			}
		});
		DropShadow shadow = new DropShadow();
		shadow.setColor(Color.BLACK);
		shadow.setOffsetY(4.0);
		this.setEffect(shadow);
	}

	public void setOnStart() {
		System.out.println("Coin.setOnStart()" + this);
		Cordenate[] pathCoordinates = { new Cordenate(1, 6),
				new Cordenate(8, 1), new Cordenate(13, 8), new Cordenate(6, 13) };
		Color colorCode = this.getColor();
		String color = null;
		System.out.println("colorCode.getRed() == 1.0"
				+ (colorCode.getRed() == 1.0));
		int value = 128;
		// just checking
		if (colorCode.getRed() > 0 && colorCode.getGreen() > 0) {
			color = "YELLOW";
		} else if ((int) Math.floor(colorCode.getBlue() * 255) == value
				|| colorCode.getBlue() == 1.0) {
			color = "BLUE";
		} else if ((int) Math.floor(colorCode.getGreen() * 255) == value
				|| colorCode.getGreen() == 1.0) {
			color = "GREEN";
		} else {
			color = "RED";

		}

		Path path1 = new Path();
		LineTo lineTo;
		MoveTo moveTo;
		int i = 0;
		moveTo = new MoveTo(this.getCenterX(), this.getCenterY());
		if ("GREEN".equalsIgnoreCase(color)) {
			lineTo = new LineTo(pathCoordinates[i].getWidth() + radius,
					pathCoordinates[i].getHeight() + radius);
			System.out.println("Inside Green " + pathCoordinates[i]);

		} else if ("YELLOW".equalsIgnoreCase(color)) {
			i = 1;
			lineTo = new LineTo(pathCoordinates[i].getWidth() + radius,
					pathCoordinates[i].getHeight() + radius);
		} else if ("RED".equalsIgnoreCase(color)) {
			i = 3;
			lineTo = new LineTo(pathCoordinates[i].getWidth() + radius,
					pathCoordinates[i].getHeight() + radius);

		} else {
			i = 2;
			lineTo = new LineTo(pathCoordinates[i].getWidth() + radius,
					pathCoordinates[i].getHeight() + radius);

		}

		path1.getElements().add(moveTo);
		path1.getElements().add(lineTo);
		System.out.println("lineTo " + lineTo.getX() / xWidth + "  "
				+ lineTo.getY() / yHeight + " cor " + pathCoordinates[i]);
		PathTransition transition = PathTransitionBuilder.create()
				.duration(Duration.millis(500)).autoReverse(false)
				.cycleCount(1).node(this).path(path1).build();
		transition.play();

		this.setBlockNo(pathCoordinates[i]);

		// startX = this.getCenterX();
		// startY = this.getCenterY();
		SpriteMap.updateSprite(pathCoordinates[i], this);
	}

	public String toString() {
		StringBuilder builder = new StringBuilder("Coin : ")
				.append(" color : ").append(color).append(" blockNo : ")
				.append(blockNo).append(" startX : ").append(startX)
				.append(" startY : ").append(startY);
		return builder.toString();
	}

	public void moveOnFinalPath(int moveRemaning) {
		System.out.println("Coin.moveOnFinalPath() " + this);
		LudoRegion region = LudoRegion.values()[this.getBlockNo().getRegion() - 1];
		System.out.println(this.getBlockNo().getRegion() + " Coin.moveOnFinalPath() region " + region); 
		if(moveRemaning > 6){
		//invalid move
			return;
		}
		int x = 0 , y = 0;
		switch (this.getBlockNo().getRegion()) {
		case 12:
			x = region.getMinX() + moveRemaning;
			y = region.getMaxY();
			break;
		case 3:
			x = region.getMinX();
			y = region.getMinY() + moveRemaning;
			break;
		case 6:
			x = region.getMinX() - moveRemaning;
			y = region.getMinY();
			break;
		case 9:
			x = region.getMinX();
			y = region.getMinY() - moveRemaning;
		}
		System.out.println("movesReamaning " + moveRemaning);
		path.getElements().add(
				new LineTo(x * xWidth + radius, y * yHeight + radius));
		this.setBlockNo(new Cordenate(x, y));
	}

	public boolean onFinalPath() {
		if (this.getBlockNo().getRegion() > 12)
			return true;
		return false;
	}

	public boolean validMoves(int currentRegion) {
		System.out.println("Coin.validMoves() currentRegion " + currentRegion);
		int finalRegion = 0;
		switch (currentRegion) {
		case 3:
			finalRegion = 14;
			break;
		case 6:
			finalRegion = 15;
			break;
		case 9:
			finalRegion = 16;
			break;
		case 12:
			finalRegion = 13;
			break;
		}
		// System.out.println("this.color " + this.color +
		// "LudoRegion.values()[finalRegion - 1].getPaint() " +
		// LudoRegion.values()[finalRegion - 1].getPaint());
		if (this.color == LudoRegion.values()[finalRegion - 1].getPaint()) {
			System.out.println("ret true");
			return true;
		}
		return false;
	}

	public void transformMove() {
		System.out.println("Coin.transformMove()" + this);
		lastMovedCoin = this;
		LudoRegion region = null;
		path = new Path();
		// if region 4 than
		Cordenate from = new Cordenate(this.getBlockNo().getX(), this
				.getBlockNo().getY());
		MoveTo to = new MoveTo(this.getCenterX(), this.getCenterY());
		path.getElements().add(to);
		if (onFinalPath()) {
			moveOnFinalPath(move);
		} else {
			region = LudoRegion.values()[(this.getBlockNo().getRegion() - 1) % 11];

			int movesReamaning = move;
			switch (region.axics()) {
			case 1:
				movesReamaning = movesReamaning + this.getBlockNo().getX()
						- region.getMinX() + 1;
				break;
			case 2:
				movesReamaning = movesReamaning + this.getBlockNo().getY()
						- region.getMinY() + 1;
				break;
			case 3:
				movesReamaning = region.getMinX() - this.getBlockNo().getX()
						+ movesReamaning + 1;
				break;
			case 4:
				movesReamaning = region.getMinY() - this.getBlockNo().getY()
						+ movesReamaning + 1;
				break;

			}
			moveToNextRegion(this.getBlockNo().getRegion(), movesReamaning);
		}

		synchronized (path) {
			PathTransition transition = PathTransitionBuilder.create()
					.duration(Duration.millis(move * 50)).autoReverse(false)
					.cycleCount(1).node(this).path(path).build();
			transition.play();
		}

		Stack<SimpleCordinate> undoList = new Stack<SimpleCordinate>();
		ObservableList<PathElement> pathElements = path.getElements();
		List<PathElement> tempList = new ArrayList<PathElement>();
		tempList.addAll(pathElements);
		for (PathElement pathElement : tempList) {
			if (pathElement instanceof LineTo) {
				undoList.push(new SimpleCordinate(
						((LineTo) pathElement).getX(), ((LineTo) pathElement)
								.getY()));
			} else if (pathElement instanceof MoveTo) {

				undoList.push(new SimpleCordinate(
						((MoveTo) pathElement).getX(), ((MoveTo) pathElement)
								.getY()));
			}
			undoPath = new Path();
			SimpleCordinate cordinate = undoList.pop();
			path.getElements().add(new MoveTo(cordinate.x, cordinate.y));
			while (!undoList.empty()) {
				cordinate = undoList.pop();
				path.getElements().add(new LineTo(cordinate.x, cordinate.y));
			}

		}
		SpriteMap.updateSprite(from, this.getBlockNo(), this);
	}

	public void moveToNextRegion(int currentRegion, int movesRemaing) {
		System.out.println("Coin.moveToNextRegion() currentRegion"
				+ currentRegion + " movesRemaing " + movesRemaing);
		// Check for final
		
			LudoRegion region = LudoRegion.values()[(currentRegion - 1) % 12];
			int x = region.getMaxX() == 100 ? region.getMinX() : region
					.getMaxX(), y = region.getMaxY() == 100 ? region.getMinY()
					: region.getMaxY(), blockX = 0, blockY = 0;
			System.out.println("region.maxCount() " + region.maxCount()
					+ " region.axics() " + region.axics());
			// int maxCount = region.maxCount();

			System.out.println("region " + region);

			if (region.maxCount() > movesRemaing) {
				switch (region.axics()) {
				case 1:
					// PositveX
					System.out.println("PositveX");
					blockX = region.getMinX() + movesRemaing - 1;
					blockY = region.getMaxY();
					x = (int) (blockX * xWidth + radius);
					y = (int) (blockY * yHeight + radius);

					break;
				case 2:
					// PositiveY
					blockX = region.getMinX();
					blockY = region.getMinY() + movesRemaing - 1;
					x = (int) (blockX * xWidth + radius);
					y = (int) (blockY * yHeight + radius);
					break;
				case 3:
					// negtiveX
					blockX = region.getMinX() - movesRemaing + 1;
					blockY = region.getMaxY();
					x = (int) (blockX * xWidth + radius);
					y = (int) (blockY * yHeight + radius);
					break;
				case 4:
					blockX = region.getMinX();
					blockY = region.getMinY() - movesRemaing + 1;
					x = (int) (blockX * xWidth + radius);
					y = (int) (blockY * yHeight + radius);
					break;
				default:
					break;
				}
				path.getElements().add(new LineTo(x, y));
				System.out.println("from if  blockX " + blockX + " blockY "
						+ blockY);
				this.setBlockNo(new Cordenate(blockX, blockY));
				// return cordenate;
			} else {
				path.getElements().add(
						new LineTo(region.getMaxX() * xWidth + radius, region
								.getMaxY() * yHeight + radius));

				this.setBlockNo(new Cordenate(x, y));
				movesRemaing -= region.maxCount();
				System.out.println("from final blockX " + x + " blockY " + y
						+ " movesRemaing " + movesRemaing);
				if (movesRemaing != 0){
					boolean finalFlag = false;
					switch (currentRegion) {
					case 12:
					case 3:
					case 6:
					case 9:
						finalFlag = validMoves(currentRegion);
					}
					if (finalFlag) {
						moveOnFinalPath(movesRemaing);
					} else{
					moveToNextRegion(currentRegion + 1, movesRemaing);
					}
				}
			}
		// return cordenate;
	}

	public void undoMove() {
		// undoPath
		synchronized (undoPath) {
			PathTransition transition = PathTransitionBuilder.create()
					.duration(Duration.millis(move * 50)).autoReverse(false)
					.cycleCount(1).node(this).path(undoPath).build();
			transition.play();
		}
	}

	// Geter Setter
	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public int getMove() {
		return move;
	}

	public void setMove(int move) {
		this.move = move;
	}

	public Cordenate getBlockNo() {
		return blockNo;
	}

	public void setBlockNo(Cordenate blockNo) {
		this.blockNo = blockNo;
		// double x = blockNo.getX() * xWidth + radius;
		// double y = blockNo.getY() * yHeight + radius;
		if (blockNo.getX() != 100 && blockNo.getY() != 100) {
			double x = blockNo.getWidth() + radius;
			double y = blockNo.getHeight() + radius;

			this.setCenterX(x);
			this.setCenterY(y);
		}
	}

	public double getStartX() {
		return startX;
	}

	public void setStartX(double startX) {
		this.startX = startX;
	}

	public double getStartY() {
		return startY;
	}

	public void setStartY(double startY) {
		this.startY = startY;
	}

}
