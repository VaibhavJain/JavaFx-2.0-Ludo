package org.vaib;

import java.util.ArrayList;
import java.util.List;

public class SpriteMap {

	public static SpriteElement[][] sprite = new SpriteElement[15][15];

	public static void initSpriteMap() {
		int row, column;
		for (int j = 1; j < 3; j++) {
			for (int i = 0; i < 15; i++) {
				for (int k = 0; k < 3; k++) {
					row = i * (j % 2) + (6 + k) * (j / 2);
					column = i * (j / 2) + (6 + k) * (j % 2);
					sprite[row][column] = new SpriteElement(
							AcessType.ALL.value(), new Cordenate(row, column));
				}

			}

		}

		for (int j = 1; j < 6; j++) {
			sprite[j][7] = new SpriteElement(AcessType.GREEN.value(),
					new Cordenate(j, 7));
			sprite[7][j] = new SpriteElement(AcessType.YELLOW.value(),
					new Cordenate(7, j));
			sprite[14 - j][7] = new SpriteElement(AcessType.RED.value(),
					new Cordenate(14 - j, 7));
			sprite[7][8 + j] = new SpriteElement(AcessType.BLUE.value(),
					new Cordenate(7, 8 + j));
		}

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				sprite[6 + i][6 + j] = new SpriteElement(
						AcessType.RISTRICTED.value(), new Cordenate(6 + i,
								6 + j));
			}
		}
	}

	public static void display() {
		for (int i = 0; i < 15; i++) {
			for (int k = 0; k < 15; k++) {
				System.out.print(" "
						+ (null == sprite[k][i] ? 0 : sprite[k][i].getAcess())
						+ " ");
			}
			System.out.println();

		}
	}

	public String toString() {
		StringBuilder builder = new StringBuilder("SpriteMap : ");
		for (int i = 0; i < 15; i++) {
			for (int k = 0; k < 15; k++) {
				builder.append(sprite[k][i]).append(" ");
			}
			builder.append("\n");
		}
		return builder.toString();
	}

	public static void updateSprite(Cordenate to, Coin coin) {
		SpriteElement toElement = sprite[to.getX()][to.getY()];
		List<Coin> temp = toElement.getCoins();
		if (null == temp) {
			temp = new ArrayList<Coin>();
		}
		temp.add(coin);
		System.out.println("ele " + toElement);
		toElement.setCoins(temp);
		System.out.println("temp " + temp);
	}

	public static void updateSprite(Cordenate from, Cordenate to, Coin coin) {
		System.out.println("From " + from + " to " + to + " coin " + coin);
		SpriteElement fromElement = sprite[from.getX()][from.getY()];
		List<Coin> temp = fromElement.getCoins();
		System.out.println("old temp " + temp);
		Coin tempCoin = null;
		int i = 0;
		if (null != temp && !temp.isEmpty()) {
			for (; i < temp.size(); i++) {
				tempCoin = temp.get(i);
				System.out.println(tempCoin);
				if (tempCoin.getStartX() == coin.getStartX()
						&& tempCoin.getStartY() == coin.getStartY()) {
					System.out.println("got equal");
					System.out.println("Value " + i);
					temp.remove(i);
					break;
				}
			}
			fromElement.setCoins(temp);

			System.out.println("temp " + temp);
		}
		// collision code
		SpriteElement toElement = sprite[to.getX()][to.getY()];
		List<Coin> temp1 = toElement.getCoins();
		if (null == temp1) {
			temp1 = new ArrayList<Coin>();
		}
		temp1.add(coin);
		toElement.setCoins(temp1);
		System.out.println(sprite[to.getX()][to.getY()]);

	}
}
