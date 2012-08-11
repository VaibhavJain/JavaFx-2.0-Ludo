package org.vaib;

public class Cordenate {

	private int x;
	private int y;
	private int region;
	private int width;
	private int height;

	public Cordenate(int x, int y) {
		this.x = x;
		this.y = y;
		reset();
	}

	public void reset() {
		resetRegion();
		resetDimension();
	}

	private void resetDimension() {
		height = (int) (y * LudoMain.yHeight);
		width = (int) (x * LudoMain.xWidth);
	}

	public void resetRegion() {
		System.out.println("Cordenate.resetRegion() x : " + x + "  y : " + y);
		if (x == 6) {
			if (y < 7) {
				region = 2;
			} else {
				region = 10;
			}
		} else if (x == 8) {
			if (y < 7) {
				region = 4;
			} else {
				region = 8;
			}
		} else if (y == 6) {
			if (x < 7) {
				region = 1;
			} else {
				region = 5;
			}
		} else if (y == 8) {
			if (x < 7) {
				region = 11;
			} else {
				region = 7;
			}
		} else if (y == 7) {
			if (x == 0) {
				region = 12;
			} else if (x > 0 && x < 7) {
				region = 13;
			} else if (x == 14) {
				region = 6;
			} else if (x < 14 && x > 7) {
				region = 15;
			}
		} else if (x == 7) {
			if (y == 0) {
				region = 3;
			} else if (y > 0 && y < 7) {
				region = 14;
			} else if (y == 14) {
				region = 9;
			} else if (y < 14 && y > 7) {
				region = 16;
			}
		}
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
		reset();
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
		reset();
	}

	public void set(int x, int y) {
		this.y = y;
		this.x = x;
		reset();
	}

	public int getRegion() {
		return region;
	}

	public void setRegion(int region) {
		this.region = region;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public String toString() {
		return (new StringBuilder("x : ")).append(x).append(" | y : ")
				.append(y).append(" | region : ").append(region)
				.append(" | width : ").append(width).append(" | height : ")
				.append(height).toString();
	}

	public boolean equals(Cordenate cordenate) {
		if (this.x == cordenate.getX() && this.x == cordenate.getY()) {
			return true;
		}
		return false;
	}
}
