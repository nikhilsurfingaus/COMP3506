/**
 * A 2D grid implemented as an array.
 * Each (x,y) coordinate can hold a single item of type <T>.
 *
 * @param <T> The type of element held in the data structure
 */
public class ArrayGrid<T> implements  Grid<T> {
	//The generic grid to hold the array grid
	private T[][] arrayGrid;
	//The height and width boundaries for our grid
	private int width;
	private int height;
	/**
	 * Constructs a new ArrayGrid object with a given width and height.
	 *
	 * @param width The width of the grid
	 * @param height The height of the grid
     * @throws IllegalArgumentException If the width or height is less than or
     * equal to zero
	 */
	public ArrayGrid(int width, int height) throws IllegalArgumentException {
		//Initialising our variables inside the constructor
		this.width = width;
		this.height = height;

		//Test our boundary condition for the height and width
	    if (this.width <= 0 || this.height <= 0) {
	    	throw new IllegalArgumentException("Range Out Of Bounds");
	    }
	    //Apply the concept of erasure, using a generic type and casting
	    //it to the grid object which is a multi-dimensional (2D) array
	    this.arrayGrid = (T[][]) new Object[this.width][this.height];
	}

	/**
	 * Add an element at a fixed position, overriding any existing element
	 * there.
	 *
	 * @param x The x-coordinate of the element's position
	 * @param y The y-coordinate of the element's position
	 * @param element The element to be added at the indicated position
	 * @throws IllegalArgumentException If the x or y value is out of the
	 * grid's maximum bounds
	 *
	 */
	@Override
	public void add(int x, int y, T element) throws IllegalArgumentException {
		if (this.width < x || this.height < y) {
			throw new IllegalArgumentException("Range Out Of Bounds");
		}
		this.arrayGrid[x][y] = element;
	}

	/**
	 * Returns the element at the indicated position.
	 *
	 * @param x The x-coordinate of the element to retrieve
	 * @param y The y-coordinate of the element to retrieve
	 * @return The element at this position, or null is no elements exist
	 * @throws IndexOutOfBoundsException If the x or y value is out of the
	 * grid's maximum bounds
	 */
	@Override
	public T get(int x, int y) throws IndexOutOfBoundsException {
		if (this.width < x || this.height < y) {
			throw new IndexOutOfBoundsException("Index Out Of Bounds");
		}
		return this.arrayGrid[x][y];
	}

	/**
	 * Removes the element at the indicated position.
	 *
	 * @param x The x-coordinate of the element to remove
	 * @param y The y-coordinate of the element to remove
	 * @return true if an element was successfully removed, false if no element
	 * exists at (x, y)
	 * @throws IndexOutOfBoundsException If the x or y value is out of the
	 * grid's maximum bounds
	 */
	@Override
	public boolean remove(int x, int y) throws IndexOutOfBoundsException {
		try {
			if (this.arrayGrid[x][y] != null ) {
				this.arrayGrid[x][y] = null;
				return true;
			}
			return false;
		} catch(IndexOutOfBoundsException i){
			return false;
		}

	}

	/**
	 * Removes all elements stored in the grid.
	 */
	@Override
	public void clear() {
		for(int i = 0; i < this.width; i++){
			for (int k = 0; k < this.height; k++){
				this.arrayGrid[i][k] = null;
			}
		}
	}

	/**
	 * Changes the size of the grid.
	 * Existing elements should remain at the same (x, y) coordinate
	 * If a resizing operation has invalid dimensions or causes an element to
	 * be lost,
	 *  the grid should remain unmodified and an IllegalArgumentException thrown
	 *
	 * @param newWidth The width of the grid after resizing
	 * @param newHeight The height of the grid after resizing
	 * @throws IllegalArgumentException if the width or height is less than or
	 * equal to zero, or if an element would be lost after this resizing
	 * operation
	 */
	@Override
	public void resize(int newWidth, int newHeight) throws
			IllegalArgumentException {

		try{
			//create a temporary generic grid to expand size
			T[][] temp = (T[][]) new Object[newWidth][newHeight];
			for(int i = 0; i < this.width; i++){
				for (int k = 0; k < this.height; k++){
					if (this.arrayGrid[i][k] != null) {
						//copy old grid to new grid
						temp[i][k] = this.arrayGrid[i][k];
					}
				}
			}
			//Re-Assign new variables and grid to current instance
			this.width = newWidth;
			this.height = newHeight;
			this.arrayGrid = temp;
		}catch(IllegalArgumentException i){
			throw new IllegalArgumentException("Out of Bounds Resize");
		}
	}

}