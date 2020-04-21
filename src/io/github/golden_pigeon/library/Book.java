package io.github.golden_pigeon.library;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
/**
 * ͼ����,�����鼮�Ļ�����Ϣ,�������ơ����߼���������Ϣ��
 * @author Golden-Pigeon
 *
 */


@SuppressWarnings("unused")
public class Book implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7031228335705593391L;
	private final String name;//�鼮����
	private final List<String> authors;//�����б�
	private final String press;//������
	private final int id;
	private static Set<Integer> existedIDs= new HashSet<Integer>();
	private static int cnt = 0;
	/**
	 * ���췽��
	 * @param name
	 * @param press
	 * @param authors һ��List<String>����
	 */
	
	public Book(String name, String press, List<String> authors) {
		this.name = name;
		this.authors = authors;
		this.press = press;
		do {
			cnt++;
		}while(!existedIDs.add(cnt));
		id = cnt;
	}
	/**
	 * ʹ�ÿɱ�����б�Ĺ��췽��
	 * @param name
	 * @param press
	 * @param authors
	 */
	public Book(String name, String press, String ... authors) {
		this(name, press, Arrays.asList(authors));
	}
	
	private Book(int id, String name, String press, List<String> authors) {
		this.id = id;
		this.name = name;
		this.authors = authors;
		this.press = press;
	}
	
	public String getName() {
		return name;
	}
	public List<String> getAuthors() {
		return authors;
	}
	public String getPress() {
		return press;
	}

	public int getID() {
		return id;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((authors == null) ? 0 : authors.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((press == null) ? 0 : press.hashCode());
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
		Book other = (Book) obj;
		if (authors == null) {
			if (other.authors != null)
				return false;
		} else if (!authors.equals(other.authors))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (press == null) {
			if (other.press != null)
				return false;
		} else if (!press.equals(other.press))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Book [name=" + name + ", authors=" + authors + ", press=" + press + ", id=" + id + "]";
	}
	
	public static Book initByID(int id, String name, String press, List<String> authors) {
		if(!existedIDs.add(id))
			throw new RuntimeException("����Ѵ���");
		return new Book(id, name, press, authors);
	}
	
	public static Book initByID(int id, String name, String press, String ... authors) {
		return initByID(id, name, press, Arrays.asList(authors));
	}
	
	
}
