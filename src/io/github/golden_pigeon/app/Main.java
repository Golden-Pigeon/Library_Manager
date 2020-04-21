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
	public static final String GENERAL_MENU = "�˵�(�����Ӧ������ѡ����Ӧ�Ĺ���):\n"
											+ "  1.����\n"
											+ "  2.�黹\n"
											+ "  3.��ѯ\n"
											+ "  4.������Ŀ\n"
											+ "  5.ɾ����Ŀ\n"
											+ "  6.��ѯѧ����Ϣ\n"
											+ "  7.��������\n"
											+ "  8.�˳�ϵͳ\n";
	public static final String SEARCH_MENU = "��ѯ�˵�(������������ʵ�ֶ�ά����,�����ÿո����):\n"
										   + "  1.��������ѯ\n"
										   + "  2.�����߲�ѯ\n"
										   + "  3.���������ѯ\n"
										   + "  4.���ɲ���\n"
										   + "  5.�����ϼ��˵�\n";
	public static final String BROWING_INSTRUCTIONS = "����ģʽ:\n"
													+ "�����ʽ:\n"
													+ "  [����][ѧ��][�鼮���...]\n"
													+ "�鼮��ſ�ͨ����ѯ���ܵõ�,�������������,�ÿո����,��ȷ����������������3��\n"
													+ "����back�����ϼ��˵�\n";
	public static final String RETURNING_INSTRUCTIONS = "�黹ģʽ:\n"
												      + "�����ʽ:\n"
													  + "  [����][ѧ��][�鼮���...]\n"
													  + "�鼮��ſ�ͨ����ѯ���ܵõ�,�������������,�ÿո����\n"
													  + "����back�����ϼ��˵�";
	public static final String ADDING_INSTRUCTIONS = "����ģʽ:"
												   + "�����ʽ:"
												   + "  [��������][����][������][����...]"
												   + "  [��������][�鼮���]"
												   + "  �����Ѵ��ڵ���Ŀ,����ʹ���鼮���"
												   + "����back�����ϼ��˵�\n";
	public static final String DELETING_INSTRUCTIONS = "����ģʽ:"
												     + "�����ʽ:"
												     + "  [�鼮���...]"
													 + "����back�����ϼ��˵�";
	public static final String FIND_STU_INFO_INSTRUCTIONS = "ѧ����Ϣ��ѯģʽ:"
													      + "�����ʽ:"
													      + "  [ѧ��]"
														  + "����back�����ϼ��˵�";
	public static final String IMPORT_INSTRUCTIONS = "��ѡ�����ݵ���ģʽ:"
			                                       + "  1.������������"
			                                       + "  2.���ļ�����";
	public static final String EXPORT_INSTRUCTIONS = "������Ҫ�������ļ�·��";
	
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
				System.out.println("δ֪����");
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
				System.out.println("δ֪����");
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
					throw new RuntimeException("�Ƿ�����");
				} catch (Exception e) {
					System.out.println("δ֪�������������");
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
				System.out.println("��Ч����,��ȷ������ѡ��һ����ѯѡ��,����������");
				continue;
			}
			List<Set<Book>> booksets = new ArrayList<Set<Book>>();
			Set<Book> res = new HashSet<Book>();
			String regex;
			if(ins[0] == 1) {
				System.out.println("�������ѯ������������ʽ");
				regex = sc.nextLine();
				booksets.add(ma.findBookByName(regex));
			}
			if(ins[1] == 1) {
				System.out.println("�������ѯ�������������ʽ");
				regex = sc.nextLine();
				booksets.add(ma.findBookByPress(regex));
			}
			if(ins[2] == 1) {
				System.out.println("�������ѯ���ߵ�������ʽ");
				regex = sc.nextLine();
				booksets.add(ma.findBookByAuthors(regex));
			}
			res.addAll(booksets.get(0));
			for(Set<Book> books:booksets) {
				res.retainAll(books);
			}
			if(res.size() == 0)
				System.out.println("δ�ҵ�");
			else {
				System.out.println("ID\tName\t \t \t \tPress\t\t\tAuthors\t\t\t\tCollectedAmount\t\tCurrentAmount\n");
				for(Book book:res) {
					System.out.println(book.getID()+"\t"+book.getName()+"\t\t\t"+book.getPress()+"\t\t"+book.getAuthors()+"\t\t\t\t"+ma.getCollectedAmount(book)+"\t\t"+ma.getCurrentAmount(book)+"\n");
				}
			}
			System.out.println("���س�������");
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
				System.out.println("����������");
				continue;
			}
			books = stu.getBorrowedBooks();
			System.out.println("ID:"+stu.getID()+"\tName:"+stu.getName()+"\t");
			System.out.println("The student has borrowed"+stu.getBorrowedAmount()+"book(s)");
			System.out.println("BookID\tName\t \t \t \tPress\t\t\tAuthors\t\t\t\tBorrowTime");
			for(Book book:books.keySet()) {
				System.out.println(book.getID()+"\t"+book.getName()+"\t"+book.getPress()+"\t"+book.getAuthors()+"\t"+books.get(book).toString());
			}
			System.out.println("���س�������");
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
					System.out.println("����ʧ��,����������");
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
					System.out.println("����ʧ��,����������");
					continue;
				}
			}
			else {
				System.out.println("δ֪����");
				continue;
			}
			System.out.println("���س�������");
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
					System.out.println("ɾ��ʧ��,����������");
					continue;
				}
			}
			try {
				ma.deleteBooks(books);
			} catch (Exception e) {
				System.out.println(e.getMessage());
				System.out.println("ɾ��ʧ��,����������");
				continue;
			}
			System.out.println("���س�������");
			sc.nextLine();
		}
		
		
	}
	
	private static void exit() {
		sc.close();
		System.out.println("�������˳�����лʹ��");
		System.exit(0);
	}
	
	private static void initialize() {
		sc = new Scanner(System.in);
		System.out.println(TITLE);
		System.out.println(LOGO);
	}
	/**
	 * ���ݵ��뷽��
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
				System.out.println("����������");
				continue;
			}
		}
		
	}
	/**
	 * ���ݵ�������
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
				System.out.println("����������");
				sc.nextLine();
				continue;
			}
			if(ins == 2) {
				System.out.println("������Ҫ������ļ�·��");
				try {
					ma = Manager.read(sc.nextLine());
					return;
				} catch (Exception e) {
					System.out.println(e.getMessage());
					System.out.println("����������");
					continue;
				}
			} else if (ins == 1) {
				ma = new Manager();
				return;
			} else {
				System.out.println("δ֪����");
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
				System.out.println("δ֪����,����������");
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
				System.out.println("δָ֪��,����������");
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
