package io.github.golden_pigeon.library;
/**
 * ͼ�����,����ʵ��ͼ��ݵĻ�������
 * @author Golden-Pigeon
 *
 */

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Library implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5810545897606707241L;
	private Map<Book, Integer> collectedBooks, currentBooks;
	
	public Library() {
		collectedBooks = new HashMap<Book, Integer>();
		currentBooks = new HashMap<Book, Integer>();
	}
	
	public Library(Map<Book, Integer> collectedBooks) {
		this(collectedBooks, collectedBooks);
	}
	
	
	
	public Library(Map<Book, Integer> collectedBooks, Map<Book, Integer> currentBooks) {
		Set<Book> col = collectedBooks.keySet(), cur = currentBooks.keySet();
		if(!col.equals(cur))
			throw new RuntimeException("��Ϣ��һ��");
		for(Book book:col) {
			if(currentBooks.get(book) > collectedBooks.get(book)||currentBooks.get(book) < 0||collectedBooks.get(book) < 0)
				throw new RuntimeException("�����Ƿ�");
		}
		this.collectedBooks = collectedBooks;
		this.currentBooks = currentBooks;
	}

	public Integer addBooks(Book book, int amount) {
		if(collectedBooks.containsKey(book)) {
			collectedBooks.put(book, collectedBooks.get(book) + amount);
			currentBooks.put(book, currentBooks.get(book) + amount);
		}
			
		else {
			collectedBooks.put(book, amount);
			currentBooks.put(book, amount);
		}
		return currentBooks.get(book);
	}
	
	public Integer deleteBooks(Book book) {
		if(!collectedBooks.containsKey(book))
			throw new RuntimeException("ɾ��ʧ��,�����ڸ���Ŀ");
		if(collectedBooks.get(book) != currentBooks.get(book))
			throw new RuntimeException("ɾ��ʧ��,����Ŀ���ڽ������");
		currentBooks.remove(book);
		return collectedBooks.remove(book);
	}
	
	public void borrow(Book...books) {
		for(Book book:books) {
			if(!collectedBooks.containsKey(book))
				throw new RuntimeException("����ʧ��,�����ڸ�ͼ��");
			if(currentBooks.get(book) == 0)
				throw new RuntimeException("����ʧ��,��ͼ���ѽ���");
		}
		for(Book book:books) {
			currentBooks.put(book, currentBooks.get(book) - 1);
		}
	}
	
	public void ret(Book...books) {
		for(Book book:books) {
			if(!collectedBooks.containsKey(book))
				throw new RuntimeException("�黹ʧ��,�����ڸ�ͼ��");
			if(currentBooks.get(book) == collectedBooks.get(book))
				throw new RuntimeException("�黹ʧ��,��ͼ��δ�����");
		}
		for(Book book:books) {
			currentBooks.put(book, currentBooks.get(book) + 1);
		}
	}
	
	public Set<Book> getExistedBookSet(){
		return collectedBooks.keySet();
	}
	
	public Integer getCurrentAmount(Book book) {
		return currentBooks.get(book);
	}
	
	public Integer getCollectedAmount(Book book) {
		return collectedBooks.get(book);
	}
	
	public boolean contains(Book book) {
		return collectedBooks.containsKey(book);
	}
	
	public Book getBook(int id) {
		Set<Book> books = collectedBooks.keySet();
		for(Book book:books) {
			if(book.getID() == id)
				return book;
		}
		throw new RuntimeException("δ�ҵ�����");
	}
}
