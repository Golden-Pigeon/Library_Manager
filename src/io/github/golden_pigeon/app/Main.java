package io.github.golden_pigeon.app;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import io.github.golden_pigeon.library.Book;
import io.github.golden_pigeon.library.Manager;
import io.github.golden_pigeon.library.User;


public class Main {
	public static final String TITLE = "A LIBRARY MANAGEMENT SYSTEM BY GOLDEN PIGEON\n";
	public static final String LOGO = "================================================\n"
									+ "            GGGGG             PPPP              \n"
									+ "           GG                 PP  PP            \n"
									+ "          GG                  PP    PP          \n"
									+ "          GG   GGGG           PP  PP            \n"
									+ "           GG   GG            PPPP              \n"
									+ "            GG GG             PP                \n"
									+ "             GGG              PP                \n"
									+ "                              PP                \n"
									+ "================================================\n";
	public static final String GENERAL_MENU = "菜单(输入对应的数字选择相应的功能):\n"
											+ "  1.借阅\n"
											+ "  2.归还\n"
											+ "  3.查询\n"
											+ "  4.新增书目\n"
											+ "  5.删除书目\n"
											+ "  6.查询学生信息\n"
											+ "  7.导出数据\n"
											+ "  8.退出系统\n";
	public static final String SEARCH_MENU = "查询菜单(可输入多个数字实现多维查找,数字用空格隔开):\n"
										   + "  1.按书名查询\n"
										   + "  2.按作者查询\n"
										   + "  3.按出版社查询\n"
										   + "  4.自由查找\n"
										   + "  5.返回上级菜单\n";
	public static final String BROWING_INSTRUCTIONS = "借阅模式:\n"
													+ "输入格式:\n"
													+ "  [姓名][学号][书籍编号...]\n"
													+ "书籍编号可通过查询功能得到,可以输入多个编号,用空格隔开,请确保借阅总数不超过3本\n"
													+ "输入back返回上级菜单\n";
	public static final String RETURNING_INSTRUCTIONS = "归还模式:\n"
												      + "输入格式:\n"
													  + "  [姓名][学号][书籍编号...]\n"
													  + "书籍编号可通过查询功能得到,可以输入多个编号,用空格隔开\n"
													  + "输入back返回上级菜单";
	public static final String ADDING_INSTRUCTIONS = "新增模式:"
												   + "输入格式:"
												   + "  [新增数量][书名][出版社][作者...]"
												   + "  [新增数量][书籍编号]"
												   + "  对于已存在的书目,可以使用书籍编号"
												   + "输入back返回上级菜单\n";
	public static final String DELETING_INSTRUCTIONS = "新增模式:"
												     + "输入格式:"
												     + "  [书籍编号...]"
													 + "输入back返回上级菜单";
	public static final String FIND_STU_INFO_INSTRUCTIONS = "学生信息查询模式:"
													      + "输入格式:"
													      + "  [学号]"
														  + "输入back返回上级菜单";
	public static final String IMPORT_INSTRUCTIONS = "请选择数据导入模式:"
			                                       + "  1.导入内置数据"
			                                       + "  2.从文件导入";
	public static final String EXPORT_INSTRUCTIONS = "请输入要导出的文件路径";
	
	private static Scanner sc;
	private static Manager ma;
	
	private static void borrow() {
		String command;
		String[] parts;
		int len;
		
		while (true) {
			System.out.println(BROWING_INSTRUCTIONS);
			command = sc.nextLine();
			parts = command.split(" ");
			len = parts.length;
			if (len == 1) {
				if(command.toLowerCase().equals("back"))
					return;
			}
			else if(len >= 3) {
				Book[] borrowing = new Book[len - 2];
				for(int i = 2; i < len; i++) {
					borrowing[i - 2] = ma.getBook(Integer.valueOf(parts[i]));
				}
				User stu = ma.getStudent(parts[0], parts[1]);
				try {
					ma.borrowBooks(stu, borrowing);
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
			else {
				System.out.println("未知命令");
				continue;
			}
		}
	}
	
	private static void ret() {
		String command;
		String[] parts;
		int len;
		
		while (true) {
			System.out.println(RETURNING_INSTRUCTIONS);
			command = sc.nextLine();
			parts = command.split(" ");
			len = parts.length;
			if (len == 1) {
				if(command.toLowerCase().equals("back"))
					return;
			}
			else if(len >= 3) {
				Book[] returning = new Book[len - 2];
				for(int i = 2; i < len; i++) {
					returning[i - 2] = ma.getBook(Integer.valueOf(parts[i]));
				}
				User stu = ma.getStudent(parts[0], parts[1]);
				try {
					ma.retBooks(stu, returning);
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
			else {
				System.out.println("未知命令");
				continue;
			}
		}
	}
	
	private static void findBook() {
		String command;
		String[] parts;
		int[] ins = new int[3];
		int t;
		while(true) {
			System.out.println(SEARCH_MENU);
			command = sc.nextLine();
			if(command.equals("back"))
				break;
			parts = command.split(" ");
			for(String s:parts) {
				try {
				t = Integer.parseInt(s);
				if(t > 5||t < 1)
					throw new RuntimeException("非法输入");
				} catch (Exception e) {
					System.out.println("未知命令，请重新输入");
					continue;
				}
				if(t == 4) {
					ins[0] = 1;
					ins[1] = 1;
					ins[2] = 1;
				}
				else if(t == 5) {
					return;
				}
				else {
					ins[t - 1] = 1;
				}
			}
			if(ins[0]==0&&ins[1]==0&&ins[2]==0) {
				System.out.println("无效输入,请确保至少选择一个查询选项,请重新输入");
				continue;
			}
			List<Set<Book>> booksets = new ArrayList<Set<Book>>();
			Set<Book> res = new HashSet<Book>();
			String regex;
			if(ins[0] == 1) {
				System.out.println("请输入查询书名的正则表达式");
				regex = sc.nextLine();
				booksets.add(ma.findBookByName(regex));
			}
			if(ins[1] == 1) {
				System.out.println("请输入查询出版社的正则表达式");
				regex = sc.nextLine();
				booksets.add(ma.findBookByPress(regex));
			}
			if(ins[2] == 1) {
				System.out.println("请输入查询作者的正则表达式");
				regex = sc.nextLine();
				booksets.add(ma.findBookByAuthors(regex));
			}
			res.addAll(booksets.get(0));
			for(Set<Book> books:booksets) {
				res.retainAll(books);
			}
			if(res.size() == 0)
				System.out.println("未找到");
			else {
				System.out.println("ID\tName\t \t \t \tPress\t\t\tAuthors\t\t\t\tCollectedAmount\t\tCurrentAmount\n");
				for(Book book:res) {
					System.out.println(book.getID()+"\t"+book.getName()+"\t\t\t"+book.getPress()+"\t\t"+book.getAuthors()+"\t\t\t\t"+ma.getCollectedAmount(book)+"\t\t"+ma.getCurrentAmount(book)+"\n");
				}
			}
			System.out.println("按回车键继续");
			sc.nextLine();
		}
		
		
	}
	
	private static void findStu() {
		String command;
		User stu;
		Map<Book, Date> books;
		while (true) {
			System.out.println(FIND_STU_INFO_INSTRUCTIONS);
			command = sc.nextLine();
			if(command.equals("back"))
				return;
			try {
				stu = ma.getStudent(command);
			} catch (Exception e) {
				System.out.println(e.getMessage());
				System.out.println("请重新输入");
				continue;
			}
			books = stu.getBorrowedBooks();
			System.out.println("ID:"+stu.getID()+"\tName:"+stu.getName()+"\t");
			System.out.println("The student has borrowed"+stu.getBorrowedAmount()+"book(s)");
			System.out.println("BookID\tName\t \t \t \tPress\t\t\tAuthors\t\t\t\tBorrowTime");
			for(Book book:books.keySet()) {
				System.out.println(book.getID()+"\t"+book.getName()+"\t"+book.getPress()+"\t"+book.getAuthors()+"\t"+books.get(book).toString());
			}
			System.out.println("按回车键继续");
			sc.nextLine();
		}
	}
	
	private static void add() {
		String command;
		String[] parts;
		int len;
		
		while (true) {
			System.out.println(ADDING_INSTRUCTIONS);
			command = sc.nextLine();
			parts = command.split(" ");
			len = parts.length;
			if (len == 1) {
				if(command.toLowerCase().equals("back"))
					return;
			}
			else if (len == 2) {
				try {
					ma.addBooks(ma.getBook(Integer.parseInt(parts[1])), Integer.parseInt(parts[0]));
				} catch (Exception e) {
					System.out.println(e.getMessage());
					System.out.println("加入失败,请重新输入");
					continue;
				}
			}
			else if(len >= 4) {
				List<String> authors = new ArrayList<String>();
				for(int i = 3; i < len; i++) {
					authors.add(parts[i]);
				}
				try {
					ma.addBooks(new Book(parts[1], parts[2], authors), Integer.parseInt(parts[0]));
				} catch (Exception e) {
					System.out.println(e.getMessage());
					System.out.println("加入失败,请重新输入");
					continue;
				}
			}
			else {
				System.out.println("未知命令");
				continue;
			}
			System.out.println("按回车键继续");
			sc.nextLine();
		}
	}
	
	private static void del() {
		String command;
		String[] parts;
		while(true) {
			System.out.println(DELETING_INSTRUCTIONS);
			command = sc.nextLine();
			parts = command.split(" ");
			Set<Book> books = new HashSet<Book>();
			for(String part:parts) {
				try{
					books.add(ma.getBook(Integer.parseInt(part)));
				} catch (Exception e) {
					System.out.println(e.getMessage());
					System.out.println("删除失败,请重新输入");
					continue;
				}
			}
			try {
				ma.deleteBooks(books);
			} catch (Exception e) {
				System.out.println(e.getMessage());
				System.out.println("删除失败,请重新输入");
				continue;
			}
			System.out.println("按回车键继续");
			sc.nextLine();
		}
		
		
	}
	
	private static void exit() {
		sc.close();
		System.out.println("程序已退出，感谢使用");
		System.exit(0);
	}
	
	private static void initialize() {
		sc = new Scanner(System.in);
		System.out.println(TITLE);
		System.out.println(LOGO);
	}
	/**
	 * 数据导入方法
	 */
	private static void exp() {
		System.out.println(EXPORT_INSTRUCTIONS);
		String filename;
		while (true) {
			filename = sc.nextLine();
			try {
				ma.save(filename);
				return;
			} catch (Exception e) {
				System.out.println(e.getMessage());
				System.out.println("请重新输入");
				continue;
			}
		}
		
	}
	/**
	 * 数据导出方法
	 */
	private static void imp() {
		System.out.println(IMPORT_INSTRUCTIONS);//TODO
		int ins;
		while (true) {
			try {
				ins = sc.nextInt();
				sc.nextLine();
			} catch (Exception e) {
				System.out.println(e.getMessage());
				System.out.println("请重新输入");
				sc.nextLine();
				continue;
			}
			if(ins == 2) {
				System.out.println("请输入要导入的文件路径");
				try {
					ma = Manager.read(sc.nextLine());
					return;
				} catch (Exception e) {
					System.out.println(e.getMessage());
					System.out.println("请重新输入");
					continue;
				}
			} else if (ins == 1) {
				ma = new Manager();
				return;
			} else {
				System.out.println("未知命令");
				continue;
			}
		}
	}
	
	public static void main(String[] args) {
		initialize();
		imp();
		int command;
		while (true) {
			System.out.println(GENERAL_MENU);
			try {
			command = sc.nextInt();
			sc.nextLine();
			} catch (Exception e) {
				System.out.println("未知命令,请重新输入");
				sc.nextLine();
				continue;
			}
			switch (command) {
			case 1:
				borrow();
				break;
			case 2:
				ret();
				break;
			case 3:
				findBook();
				break;
			case 4:
				add();
				break;
			case 5:
				del();
				break;
			case 6:
				findStu();
				break;
			case 7:
				exp();
				break;
			case 8:
				exit();
				break;
			default:
				System.out.println("未知指令,请重新输入");
				break;
			}
		}
		// TODO Auto-generated method stub
		
//		try {
//			Student stu1 = new Student("Jack", "01"), stu2 = new Student("Dick", "02");
//			manager.borrowBooks(stu1, manager.getBook(1), manager.getBook(2));
//			manager.borrowBooks(stu2, manager.getBook(2), manager.getBook(3));
//			System.out.println(manager.getBookInfo());
//			System.out.println(manager.getStudentInfo());
//			System.out.println(manager.getStudent("01").getBorrowedBooks());
//			System.out.println(manager.findCollectedBookByName("(.*)java(.*)"));
//		}catch (Exception e) {
//			System.out.println(e.getMessage());
//		}
//		
//		manager.save("C:\\Users\\86133\\eclipse-workspace\\Virtual_Library\\man.dat");
//		Manager m1 = Manager.read("C:\\Users\\86133\\eclipse-workspace\\Virtual_Library\\man.dat");
//		if(m1 != null)
//			System.out.println(m1.getBookInfo());
	}

}
