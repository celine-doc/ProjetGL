package map;

/**
 *
 * Représente une case dans la grille , elle peut contenir des objets ou des personnes 
 */

public class Block {
	private int line;
	private int column;

	public Block(int line, int column) {
		this.line = line;
		this.column = column;
	}

	public int getLine() {
		return line;
	}

	public int getColumn() {
		return column;
	}

	@Override
	public String toString() {
		return "Block [line=" + line + ", column=" + column + "]";
	}
	
	public boolean equals(Block block) {
		if(block.getColumn()==column && block.getLine()==line) {
			return true;
		}else {
			return false;
		}
	}
}
