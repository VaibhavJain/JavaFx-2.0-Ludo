package org.vaib;

public enum LudoRegion {
	R1(0, 6, 5, 6), R2(6, 6, 6, 0), R3(7, 1, 7, 1), R4(8, 0, 8, 5), R5(9,
			6, 14, 6), R6(14, 7, 14, 7), R7(15, 8, 9, 8), R8(8, 9, 8, 14), R9(
			7, 14, 7, 14), R10(6, 14, 6, 9), R11(5, 8, 0, 8), R12(0, 7, 0,
			7);

	private LudoRegion(int minX, int minY, int maxX, int maxY) {
		this.minX = minX;
		this.minY = minY;
		this.maxX = maxX;
		this.maxY = maxY;
	}

	private final int minX;
	private final int minY;
	private final int maxX;
	private final int maxY;

	public int getMinX() {
		return minX;
	}

	public int getMinY() {
		return minY;
	}

	public int getMaxX() {
		return maxX;
	}

	public int getMaxY() {
		return maxY;
	}

	public int maxCount() {
		if (maxX == minX && maxY == minY) {
			return 1;
		}
		return 5;
	}

	public int axics() {
		int ret = 0;
		if (minY == maxY) {
			if (minX < maxX) {
				ret = Axcis.POSITIVEX.getAxcisCode();
			} else {
				ret = Axcis.NEGITIVEX.getAxcisCode();
			}
		} else {
			if (minY < maxY) {
				ret = Axcis.POSITIVEY.getAxcisCode();
			} else {
				ret = Axcis.NEGITIVEY.getAxcisCode();
			}
		}
		System.out.println("asix " + ret);
		return ret;
	}
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("minX : ").append(minX)
		       .append(" | minY : ").append(minY)
		       .append(" | maxX : ").append(maxX)
		       .append(" | maxY : ").append(maxY);

		return builder.toString();
	}
}
