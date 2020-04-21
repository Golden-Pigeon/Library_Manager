package io.github.golden_pigeon.library;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import java.util.HashMap;
import java.util.HashSet;

/**
 * 图书馆管理类
 * @author Golden-Pigeon
 *
 */
public class Manager implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1992435383259756428L;
	private Library li;
	private List<Student> stuList;
	public Manager() {
		stuList = new ArrayList<Student>();
		li = new Library();
		li.addBooks(new Book("Thinking in Java", "Prentice Hall", "Bruce Eckel"), 10);
		li.addBooks(new Book("Core Java", "Prentice Hall", "Cay S. Horstmann", "Gary Cornell"), 15);
		li.addBooks(new Book("Effective Java", "Addison-Wesley", "Joshua Bloch"), 12);
		li.addBooks(new Book("Head First Java", "O'Reilly Media", "Kathy Sierra", "Bert Bates"), 17);
		li.addBooks(new Book("C++ Primer Plus", "Addison-Wesley", "Stephen Prata"), 20);
	}
	
	public void addBooks(Map<Book, Integer> adding) {
		for(Book book:adding.keySet()) {
			li.addBooks(book, adding.get(book));
		}
	}
	
	public void addBooks(Book book, int amount) {

		li.addBooks(book, amount);
	}
	
	public void deleteBooks(Book book) {

		li.deleteBooks(book);
	}
	
	public void deleteBooks(Set<Book> deleting) {
		for(Book book:deleting) {
			if(!li.contains(book))
				throw new RuntimeException("删除失败,列表中含有未知书目");
		}
		for(Book book:deleting) {
			li.deleteBooks(book);
		}
	}
	
	
	public void borrowBooks(User u, Book ... books) {
		for(Book book:books) {
			if(!li.contains(book))
				throw new RuntimeException("图书不存在");
		}
//		if(!stuList.contains(stu)) {
//			stuList.add(stu);
//		}
		if(!(u instanceof Student))
			throw new RuntimeException("不是学生用户");
		Student stu = (Student)u;
		stu.borrowBooks(books);
		li.borrow(books);
	}
	
	public void retBooks(User u, Book ... books) {
		if(!(u instanceof Student))
			throw new RuntimeException("不是学生用户");
		Student stu = (Student)u;
		if(!stuList.contains(stu)) {
			throw new RuntimeException("该学生未借书");
		}
//		for(Book book:books) {
//			if(!li.contains(book))
//				throw new RuntimeException("图书不存在");
//		}
		stu.retBooks(books);
		li.ret(books);
	}
	/**
	 * 通过id获取馆藏图书
	 * @param id
	 * @return
	 */
	public Book getBook(int id) {
		return li.getBook(id);
	}
	
	public Set<Book> findBookByName(String regex){
		regex = regex.toLowerCase();
		Set<Book> res = new HashSet<Book>();
		for(Book book:li.getExistedBookSet()) {
			if(book.getName().toLowerCase().matches(regex))
				res.add(book);
		}
		return res;
	}
	
	public Set<Book> findBookByPress(String regex){
		regex = regex.toLowerCase();
		Set<Book> res = new HashSet<Book>();
		for(Book book:li.getExistedBookSet()) {
			if(book.getPress().toLowerCase().matches(regex))
				res.add(book);
		}
		return res;
	}
	
	public Set<Book> findBookByAuthors(String regex){
		regex = regex.toLowerCase();
		Set<Book> res = new HashSet<Book>();
		for(Book book:li.getExistedBookSet()) {
			for(String author:book.getAuthors()) {
				if(author.toLowerCase().matches(regex))
					res.add(book);
			}
		}
		return res;
	}
//	public Map<Book, Integer> findCollectedBookByName(String regex){
//		regex = regex.toLowerCase();
//		Map<Book, Integer> res = new HashMap<Book, Integer>();
//		for(Book book:li.getExistedBookSet()) {
//			String string = book.getName().toLowerCase();
//			boolean b = string.matches(regex);
//			if(b)
//				res.put(book, li.getCollectedAmount(book));
//		}
//		return res;
//	}
//	public Map<Book, Integer> findCurrentBookByPress(String regex){
//		regex = regex.toLowerCase();
//		Map<Book, Integer> res = new HashMap<Book, Integer>();
//		for(Book book:li.getExistedBookSet()) {
//			if(book.getPress().toLowerCase().matches(regex))
//				res.put(book, li.getCurrentAmount(book));
//		}
//		return res;
//	}
//	public Map<Book, Integer> findCollectedBookByPress(String regex){
//		regex = regex.toLowerCase();
//		Map<Book, Integer> res = new HashMap<Book, Integer>();
//		for(Book book:li.getExistedBookSet()) {
//			if(book.getPress().toLowerCase().matches(regex))
//				res.put(book, li.getCollectedAmount(book));
//		}
//		return res;
//	}
//	public Map<Book, Integer> findCurrentBookByAuthors(String regex){
//		regex = regex.toLowerCase();
//		Map<Book, Integer> res = new HashMap<Book, Integer>();
//		for(Book book:li.getExistedBookSet()) {
//			for(String author:book.getAuthors()) {
//				if(author.toLowerCase().matches(regex))
//					res.put(book, li.getCurrentAmount(book));
//			}
//		}
//		return res;
//	}
//	public Map<Book, Integer> findCollectedBookByAuthors(String regex){
//		regex = regex.toLowerCase();
//		Map<Book, Integer> res = new HashMap<Book, Integer>();
//		for(Book book:li.getExistedBookSet()) {
//			for(String author:book.getAuthors()) {
//				if(author.toLowerCase().matches(regex))
//					res.put(book, li.getCollectedAmount(book));
//			}
//		}
//		return res;
//	}
	
	public Integer getCurrentAmount(Book book) {
		return li.getCurrentAmount(book);
	}
	
	public Integer getCollectedAmount(Book book) {
		return li.getCollectedAmount(book);
	}
	
	
	public Student getStudent(String id){
		for(Student stu:stuList) {
			if(stu.getID().equals(id))
				return stu;
		}
		throw new RuntimeException("没有该学生");
	}
	
	public String getStudentInfo() {
		StringBuilder sb = new StringBuilder("ID\tName\n");
		for(Student stu:stuList) {
			sb.append(stu.getID()+"\t"+stu.getName()+"\n");
		}
		return sb.toString();
	}
	
	public String getBookInfo() {
		StringBuilder sb = new StringBuilder("ID\tName\t \t \t \tPress\t\t\tAuthors\t\t\t\tCollectedAmount\t\tCurrentAmount\n");
		for(Book book:li.getExistedBookSet()) {
			sb.append(book.getID()+"\t"+book.getName()+"\t\t\t"+book.getPress()+"\t\t"+book.getAuthors()+"\t\t\t\t"+li.getCollectedAmount(book)+"\t\t"+li.getCurrentAmount(book)+"\n");
		}
		return sb.toString();
	}
	
	public boolean save(String filename) {
		File file = new File(filename);
		try {
			FileOutputStream fos = new FileOutputStream(file);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(this);
			oos.flush();
			oos.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
	public static Manager read(String filename) {
		Manager res=null;
        File file =new File(filename);
        try {
        	FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois=new ObjectInputStream(fis);
            res=(Manager)ois.readObject();
            ois.close();
            System.out.println("读取成功");
        } catch (IOException e) {
            System.out.println("读取失败");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return res;
	}
	
	public Student getStudent(String name, String id) {
		Student student = new Student(name, id);
		if (!stuList.contains(student)) {
			stuList.add(student);
			return student;
		} else {
			for(Student stu1:stuList) {
				if(stu1.equals(student)) {
					return stu1;
				}
			}
		}
		return null;
	}
	
	private class Student implements Serializable, User {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1735587103086140392L;
		private final String name;
		private final String id;
		private Map<Book, Date> borrowedBooks;
		private Student(String name, String id) {
			this.name = name;
			this.id = id;
			this.borrowedBooks = new HashMap<Book, Date>();
		}
//		public Student(String name, String id, Map<Book, Date> borrowedBooks) {
//			this.name = name;
//			this.id = id;
//			this.borrowedBooks = borrowedBooks;
//		}
		
		public void borrowBooks(Book...books) {
			for(Book book:books) {
				if(borrowedBooks.containsKey(book))
					throw new RuntimeException("同一本书不可被借出两次");
			}
			if(books.length + borrowedBooks.size() > 3)
				throw new RuntimeException("不可同时借出超过三本书");
			for(Book book:books) {
				borrowedBooks.put(book, new Date());
			}
		}
		
		public void retBooks(Book...books) {
			for(Book book:books) {
				if(!borrowedBooks.containsKey(book))
					throw new RuntimeException("此书未被借出");
			}
			for(Book book:books) {
				borrowedBooks.remove(book);
			}
		}
		
		public Integer getBorrowedAmount() {
			return borrowedBooks.size();
		}
		
		public Map<Book, Date> getBorrowedBooks() {
			return borrowedBooks;
		}
		public String getName() {
			return name;
		}
		public String getID() {
			return id;
		}
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((id == null) ? 0 : id.hashCode());
			result = prime * result + ((name == null) ? 0 : name.hashCode());
			return result;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Student other = (Student) obj;
			if (id == null) {
				if (other.id != null)
					return false;
			} else if (!id.equals(other.id))
				return false;
			if (name == null) {
				if (other.name != null)
					return false;
			} else if (!name.equals(other.name))
				return false;
			return true;
		}
//		public static Student getStudent(String name, String id) {
//			Student student = new Student(name, id);
//			if (!stuList.contains(student)) {
//				stuList.add(student);
//				return student;
//			} else {
//				for(Student stu1:stuList) {
//					if(stu1.equals(student)) {
//						return stu1;
//					}
//				}
//			}
//			return null;
//		}
		
		
	}
}
