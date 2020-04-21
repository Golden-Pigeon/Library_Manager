package io.github.golden_pigeon.library;

import java.util.Date;
import java.util.Map;

public interface User {
	public Integer getBorrowedAmount();
	
	public Map<Book, Date> getBorrowedBooks();
	public String getName();
	public String getID();
}
