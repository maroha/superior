package ee.ut.math.tvt.salessystem.ui.model;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

import javafx.scene.control.TableColumn;

import com.sun.javafx.collections.ObservableListWrapper;

import ee.ut.math.tvt.salessystem.domain.data.DisplayableItem;

/**
 * Generic table model implementation suitable for extending.
 */
public abstract class SalesSystemTableModel<T extends DisplayableItem> extends
		ObservableListWrapper<T> {

	private static final long serialVersionUID = 1L;

	protected final List<TableColumn<T, ?>> headers;

	public SalesSystemTableModel(final List<TableColumn<T, ?>> headers) {
		super(new ArrayList<T>());
		this.headers = headers;
	}

	/**
	 * @param item
	 *            item describing selected row
	 * @param columnIndex
	 *            selected column index
	 * @return value displayed in column with specified index
	 */
	protected abstract Object getColumnValue(T item, int columnIndex);

	public int getColumnCount() {
		return headers.size();
	}

	public List<TableColumn<T, ?>> getTableColumns() {
		return headers;
	}

	public String getColumnName(final int columnIndex) {
		return headers.get(columnIndex).getText();
	}

	public int getRowCount() {
		return size();
	}

	public Object getValueAt(final int rowIndex, final int columnIndex) {
		return getColumnValue(get(rowIndex), columnIndex);
	}

	// search for item with the specified id
	public T getItemById(final long id) {
		ListIterator<T> itr = super.listIterator();
		while (itr.hasNext()) {
			final T item = itr.next();
			if (item.getId() == id)
				return item;
		}
		throw new NoSuchElementException();
	}

	public List<T> getTableRows() {
		return this;
	}

	public void populateWithData(final List<T> data) {
		setAll(data);
	}

}
