package org.vaib;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Iterator;

import javafx.animation.PathTransition;
import javafx.animation.PathTransitionBuilder;
import javafx.application.Application;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ButtonBuilder;
import javafx.scene.control.LabelBuilder;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageViewBuilder;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBoxBuilder;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineBuilder;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.PolygonBuilder;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.RectangleBuilder;
import javafx.scene.shape.StrokeLineJoin;
import javafx.scene.shape.StrokeType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class LudoMain extends Application {

	public static int[][] matrix = new int[15][15];
	public static final Paint[] paint = { Color.GREEN, Color.YELLOW, Color.RED,
			Color.BLUE };
	public static final Dimension screenSize = Toolkit.getDefaultToolkit()
			.getScreenSize();

	public static double screenX = screenSize.getWidth() - 50;
	public static double screenY = screenSize.getHeight() - 100;
	public static int turn = AcessType.GREEN.value();
	public static int move = 10;
	public static SimpleIntegerProperty moveProperty;
	public static int playerGame = 4;
	public static Coin lastMovedCoin;

	static {
		if (screenX > screenY) {
			screenX = screenY;
		} else {
			screenY = screenX;
		}
	}
	public static double xWidth = Math.ceil(screenX / 15);
	public static double yHeight = Math.ceil(screenY / 15);

	public static void unDoMove() {
		lastMovedCoin.undoMove();
	}

	@Override
	public void start(final Stage primaryStage) {

		primaryStage.setTitle("Ludo");
		primaryStage.setResizable(false);
		final Group master = new Group();
		final Group root = new Group();

		double sceneX, sceneY;

		sceneX = screenY + 250;
		sceneY = screenY;

		Scene scene = new Scene(master, sceneX, sceneY, Color.WHITE);

		primaryStage.setScene(scene);

		Rectangle mainRectangle = RectangleBuilder.create().x(0).y(0)
				.width(screenX + 10).height(screenY).build();

		mainRectangle.setStroke(Color.POWDERBLUE);
		mainRectangle.setStrokeWidth(4);
		mainRectangle.setFill(Color.WHITE);
		mainRectangle.setStrokeType(StrokeType.OUTSIDE);

		root.getChildren().add(mainRectangle);
		root.getChildren().addAll(this.finalPath(xWidth, yHeight));
		root.getChildren().addAll(
				this.lineFrame(xWidth, yHeight, screenX, screenY));
		root.getChildren().addAll(this.centerRectangle(xWidth, yHeight));

		master.getChildren().addAll(root);

		master.getChildren().add(Dice.getInstance().getDice());

		/*
		 * getLudoInst(); master.getChildren().addAll(startPosition());
		 */

		primaryStage.show();

		// initialize the confirmation dialog
		final Stage dialog = new Stage(StageStyle.TRANSPARENT);
		dialog.initModality(Modality.WINDOW_MODAL);
		dialog.initOwner(primaryStage);
		dialog.setScene(new Scene(
				HBoxBuilder
						.create()
						.styleClass("modal-dialog")
						.children(
								ImageViewBuilder
										.create()
										.fitHeight(85)
										.fitHeight(85)
										.cursor(Cursor.DISAPPEAR)
										.preserveRatio(true)
										.styleClass("modal-image")
										.image(new Image(
												getClass().getResourceAsStream(
														"monu.jpg"))).build(),
								LabelBuilder
										.create()
										.text("Welcome to JavaFX Ludo. This is a learning \n project developed be Anubhav Jain")
										.build(),
								ButtonBuilder
										.create()
										.text("Start")
										.defaultButton(true)
										.onAction(
												new EventHandler<ActionEvent>() {
													@Override
													public void handle(
															ActionEvent actionEvent) {
														// take action and close
														// getLudoInst();
														master.getChildren()
																.addAll(startPosition());
														// the dialog.
														primaryStage
																.getScene()
																.getRoot()
																.setEffect(null);
														dialog.close();
													}
												}).build(),
								ButtonBuilder
										.create()
										.text("Exit")
										.cancelButton(true)
										.onAction(
												new EventHandler<ActionEvent>() {
													@Override
													public void handle(
															ActionEvent actionEvent) {

														// close the dialog.
														primaryStage
																.getScene()
																.getRoot()
																.setEffect(null);
														dialog.close();
														primaryStage.close();
													}
												}).build()).build(),
				Color.TRANSPARENT));
		dialog.getScene()
				.getStylesheets()
				.add(getClass().getResource("modal-dialog.css")
						.toExternalForm());

		// allow the dialog to be dragged around.
		final Node rootDialog = dialog.getScene().getRoot();
		final SimpleCordinate dragDelta = new SimpleCordinate(0, 0);
		rootDialog.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				// record a delta distance for the drag and drop operation.
				dragDelta.x = (new Double(dialog.getX()
						- mouseEvent.getScreenX())).intValue();
				dragDelta.y = (new Double(dialog.getY()
						- mouseEvent.getScreenY())).intValue();
			}
		});
		rootDialog.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				dialog.setX(mouseEvent.getScreenX() + dragDelta.x);
				dialog.setY(mouseEvent.getScreenY() + dragDelta.y);
			}
		});

		primaryStage.getScene().getRoot().setEffect(new BoxBlur());
		dialog.show();

	}

	public Group startPosition() {
		Group group = new Group();
		System.out.println("LudoMain.startPosition()");
		for (int i = 0; i < 4; i = i + (4 / playerGame)) {
			System.out.println(" (i % 2)" + (i % 2));
			int xPos = (int) (9 * (i % 2) * xWidth), yPos = (int) ((i / 2) * 9 * yHeight);

			Coin coin;
			int x = 0, y = 0;
			int array[][] = { { 1, 1 }, { 3, 1 }, { 1, 3 }, { 3, 3 } };
			for (int j = 0; j < 4; j++) {

				x = (int) (xPos + 25 + ((6 * xWidth - 50) / 4) * array[j][0]);
				y = (int) (yPos + 25 + ((6 * yHeight - 50) / 4) * array[j][1]);
				coin = new Coin((Color) paint[i]);
				coin.setCenterX(x);
				coin.setCenterY(y);
				coin.setBlockNo(new Cordenate(100, 100));

				group.getChildren().add(coin);

				coin.setStartX(x);
				coin.setStartY(y);
			}
			ObservableList<Node> list = group.getChildren();
			for (Iterator<Node> iterator = list.iterator(); iterator.hasNext();) {
				Node node = (Node) iterator.next();
				if (node instanceof Coin) {
					Coin new_name = (Coin) node;
					Path path = new Path();
					path.getElements().add(new MoveTo(0, 0));
					path.getElements().add(
							new LineTo(new_name.getCenterX(), new_name
									.getCenterY()));
					PathTransition transition = PathTransitionBuilder.create()
							.path(path).node(new_name)
							.duration(Duration.millis(1000)).autoReverse(false)
							.cycleCount(1).build();
					transition.play();
				}

			}
		}
		return group;
	}

	public Group finalPath(double xWidth, double yHeight) {
		Group finalPath = new Group();

		int intxWidth = Double.valueOf(xWidth).intValue();
		int intyHeight = Double.valueOf(yHeight).intValue();

		Dimension[] pathCoordinates = {
				new Dimension(intxWidth, 7 * intyHeight),
				new Dimension(7 * intxWidth, intyHeight),
				new Dimension(7 * intxWidth, 9 * intyHeight),
				new Dimension(9 * intxWidth, 7 * intyHeight) };

		Dimension[] pathCoordinates1 = {
				new Dimension(intxWidth, 6 * intyHeight),
				new Dimension(8 * intxWidth, intyHeight),
				new Dimension(6 * intxWidth, 13 * intyHeight),
				new Dimension(13 * intxWidth, 8 * intyHeight) };

		for (int i = 0; i < 4; i++) {
			double xPos = 9 * xWidth * (i % 2);
			double yPos = Math.ceil(i / 2) * yHeight * 9;

			finalPath.getChildren().addAll(
					this.corner(xWidth, yHeight, xPos, yPos, paint[i],
							Color.BLANCHEDALMOND));

			for (int j = 0; j < 2; j++) {
				double x = pathCoordinates[i].getWidth();
				double y = pathCoordinates[i].getHeight();

				@SuppressWarnings("rawtypes")
				RectangleBuilder rectangleBuilderpath = RectangleBuilder
						.create().x(x).y(y).stroke(paint[i])
						.strokeType(StrokeType.INSIDE).strokeWidth(20);
				Rectangle rectanglePath = null;

				switch (i) {
				case 0:
					rectanglePath = rectangleBuilderpath.width(5 * xWidth)
							.height(yHeight).build();
					break;
				case 1:
					rectanglePath = rectangleBuilderpath.width(xWidth)
							.height(5 * yHeight).build();
					break;
				case 2:
					rectanglePath = rectangleBuilderpath.width(xWidth)
							.height(yHeight * 5).build();
					break;
				case 3:
					rectanglePath = rectangleBuilderpath.width(xWidth * 5)
							.height(yHeight).build();
					break;
				}
				Rectangle rectanglePath1 = RectangleBuilder.create()
						.x(pathCoordinates1[i].getWidth())
						.y(pathCoordinates1[i].getHeight()).width(xWidth)
						.height(yHeight).stroke(paint[i])
						.strokeType(StrokeType.INSIDE).strokeWidth(20).build();

				finalPath.getChildren().add(rectanglePath);
				finalPath.getChildren().add(rectanglePath1);

			}

		}
		return finalPath;
	}

	public Group corner(double xWidth, double yHeight, double xPos,
			double yPos, Paint fillColor, Paint borderColor) {

		Group group = new Group();
		Rectangle rectangle = RectangleBuilder.create().x(xPos).y(yPos)
				.height(6 * yHeight).width(6 * xWidth).build();
		rectangle.setFill(fillColor);
		rectangle.setStroke(borderColor);
		rectangle.setStrokeWidth(5);
		rectangle.setStrokeType(StrokeType.INSIDE);

		Rectangle rectangleInside = RectangleBuilder.create().x(xPos + 25)
				.y(yPos + 25).height(6 * yHeight - 50).width(6 * xWidth - 50)
				.build();
		rectangleInside.setFill(Color.WHITE);
		rectangleInside.setStroke(borderColor);
		rectangleInside.setStrokeWidth(5);
		rectangleInside.setStrokeType(StrokeType.INSIDE);

		GridPane gridPane = new GridPane();
		gridPane.getColumnConstraints().add(
				new ColumnConstraints((6 * yHeight - 50) / 2));
		gridPane.getColumnConstraints().add(
				new ColumnConstraints((6 * yHeight - 50) / 2));
		gridPane.getRowConstraints().add(
				new RowConstraints((6 * xWidth - 50) / 2));
		gridPane.getRowConstraints().add(
				new RowConstraints((6 * xWidth - 50) / 2));
		gridPane.setLayoutX(xPos + 25);
		gridPane.setLayoutY(yPos + 25);
		gridPane.setGridLinesVisible(false);
		gridPane.setBlendMode(BlendMode.MULTIPLY);

		RectangleBuilder rectangleBuilderInside = RectangleBuilder.create()
				.height(1.2 * yHeight).width(1.2 * xWidth);

		for (int j = 0; j < 4; j++) {

			Rectangle rectangleInside1 = rectangleBuilderInside.build();
			rectangleInside1.setStroke(fillColor);
			rectangleInside1.setStrokeLineJoin(StrokeLineJoin.ROUND);
			rectangleInside1.setStrokeType(StrokeType.INSIDE);
			rectangleInside1.setStrokeWidth(xWidth / 2);

			GridPane.setValignment(rectangleInside1, VPos.CENTER);
			GridPane.setHalignment(rectangleInside1, HPos.CENTER);
			gridPane.add(rectangleInside1, j % 2, (int) Math.ceil(j / 2));

		}

		group.getChildren().add(rectangle);
		group.getChildren().add(rectangleInside);
		group.getChildren().addAll(gridPane);

		return group;

	}

	public Group centerRectangle(double xWidth, double yHeight) {
		Group centerRectangle = new Group();
		Rectangle rectangleCenter = RectangleBuilder.create().x(6 * xWidth)
				.y(6 * yHeight).height(3 * yHeight).width(3 * xWidth)
				.fill(Color.WHITE).build();
		centerRectangle.getChildren().add(rectangleCenter);
		double x1 = 6 * xWidth, y1 = 6 * yHeight, cx = x1 + 1.5 * xWidth, cy = y1
				+ 1.5 * yHeight;

		Polygon t1 = PolygonBuilder.create()
				.points(x1, y1, x1 + 3 * xWidth, y1, cx, cy).fill(Color.YELLOW)
				.stroke(Color.BLACK).strokeWidth(2.0f).build();

		Polygon t2 = PolygonBuilder
				.create()
				.points(x1, y1 + 3 * xWidth, x1 + 3 * xWidth, y1 + 3 * xWidth,
						cx, cy).fill(Color.RED).stroke(Color.BLACK)
				.strokeWidth(2.0f).build();

		Polygon t3 = PolygonBuilder
				.create()
				.points(x1 + 3 * xWidth, y1 + 3 * xWidth, x1 + 3 * xWidth, y1,
						cx, cy).fill(Color.BLUE).stroke(Color.BLACK)
				.strokeWidth(2.0f).build();

		Polygon t4 = PolygonBuilder.create()
				.points(x1, y1, x1, y1 + 3 * xWidth, cx, cy).fill(Color.GREEN)
				.stroke(Color.BLACK).strokeWidth(2.0f).build();

		centerRectangle.getChildren().add(t1);
		centerRectangle.getChildren().add(t2);
		centerRectangle.getChildren().add(t3);
		centerRectangle.getChildren().add(t4);

		return centerRectangle;
	}

	public Group lineFrame(double xWidth, double yHeight, double screenX,
			double screenY) {
		Group lineFrame = new Group();
		LineBuilder lineBuilder = LineBuilder.create().stroke(Color.BLACK)
				.strokeWidth(1);
		Group matrix = new Group();
		for (int i = 0; i < 4; i++) {
			double x = xWidth * (i + 6), y = yHeight * (i + 6);
			Line lineX = lineBuilder.startX(x).endX(x).startY(0).endY(screenY)
					.build();
			Line lineY = lineBuilder.startX(0).endX(screenX + 10).startY(y)
					.endY(y).build();
			lineFrame.getChildren().add(lineX);
			lineFrame.getChildren().add(lineY);
		}

		for (int i = 0; i < 6; i++) {
			Line lineN, lineS, lineW, lineE;
			lineN = lineBuilder.startX(6 * xWidth).endX(9 * xWidth)
					.startY(i * yHeight).endY(i * yHeight).build();

			lineS = lineBuilder.startX(6 * xWidth).endX(9 * xWidth)
					.startY((10 + i) * yHeight).endY((10 + i) * yHeight)
					.build();

			lineE = lineBuilder.startY(6 * yHeight).endY(9 * yHeight)
					.startX((10 + i) * xWidth).endX((10 + i) * xWidth).build();

			lineW = lineBuilder.startY(6 * yHeight).endY(9 * yHeight)
					.startX(i * xWidth).endX(i * xWidth).build();
			lineFrame.getChildren().add(lineN);
			lineFrame.getChildren().add(lineE);
			lineFrame.getChildren().add(lineW);
			lineFrame.getChildren().add(lineS);

		}
		return lineFrame;
	}

	public static void main(String[] args) {
		SpriteMap.initSpriteMap();
		launch(args);
	}

}
