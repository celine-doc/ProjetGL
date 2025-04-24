package map;

public class Map {

	private Block[][] blocks;

	private int lineX;
	private int colomnY;

	public Map(int lineCount, int columnCount) {
		init(lineCount, columnCount);

		for (int lineIndex = 0; lineIndex < lineCount; lineIndex++) {
			for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
				blocks[lineIndex][columnIndex] = new Block(lineIndex, columnIndex);
			}
		}
	}

	private void init(int lineX, int colomnY) {
		this.lineX = lineX;	
		this.colomnY = colomnY;

		blocks = new Block[lineX][colomnY];
	}

	public Block[][] getBlocks() {
		return blocks;
	}

	public int getlineX() {
		return lineX;
	}

	public int getcolomnY() {
		return colomnY;
	}

	public Block getBlock(int line, int column) {
		return blocks[line][column];
	}
	
}