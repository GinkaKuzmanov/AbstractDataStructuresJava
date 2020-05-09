package implementations;

public class TheMatrix {
    private final char[][] matrix;
    private final char fillChar;
    private final char toBeReplaced;
    private final int startRow;
    private final int startCol;

    public TheMatrix(char[][] matrix, char fillChar, int startRow, int startCol) {
        this.matrix = matrix;
        this.fillChar = fillChar;
        this.startRow = startRow;
        this.startCol = startCol;
        this.toBeReplaced = this.matrix[this.startRow][this.startCol];
    }

    public String toOutputString() {
        StringBuilder builder = new StringBuilder();
        for (char[] chars : this.matrix) {
            for (char aChar : chars) {
                builder.append(aChar);
            }
            builder.append(System.lineSeparator());
        }
        //not to have additional empty row at the end
        return builder.toString().trim();
    }

    public void solve() {
        fillMatrix(this.startRow, this.startCol);
    }

    private void fillMatrix(int row, int col) {

        if (isNotPassable(row, col) || this.matrix[row][col] != this.toBeReplaced) {
            return;
        }

        this.matrix[row][col] = this.fillChar;

        this.fillMatrix(row + 1, col);
        this.fillMatrix(row, col + 1);
        this.fillMatrix(row - 1, col);
        this.fillMatrix(row, col - 1);

    }

    private boolean isNotPassable(int row, int col) {
        return row < 0 || row >= this.matrix.length ||
                col < 0 || col >= this.matrix[row].length;
    }


}
