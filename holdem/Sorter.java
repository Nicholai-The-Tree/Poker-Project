//merge sort of cards into ascending order

import java.util.*;
@SuppressWarnings("unchecked")
public class Sorter{
	
	public static LinkedList merge (LinkedList lsta,LinkedList lstb) {
	LinkedList temp = new LinkedList();
	ListIterator<Object> i = lsta.listIterator();
	ListIterator<Object> j = lstb.listIterator();
	while(i.hasNext() && j.hasNext()){
		Comparable i1=(Comparable)i.next();
		Comparable i2=(Comparable)j.next();
		if(i1.compareTo(i2) < 0){
			temp.add(i1);
			j.previous();
		}
		else if(i1.compareTo(i2) > 0){
			temp.add(i2);
			i.previous();
		}
		else{
			temp.add(i1);
			temp.add(i2);
		}
	}
	while(i.hasNext())
		temp.add(i.next());
	while(j.hasNext())
		temp.add(j.next());
	return temp;
	}

	public static LinkedList sort (LinkedList lst) {
		LinkedList t1 = new LinkedList();
		LinkedList t2 = new LinkedList();
		if(lst.size()>1){
			int x=0;
			for(Object i:lst){
				if(x<lst.size()/2)
					t1.add(i);
				else
					t2.add(i);
				++x;
			}
			return merge(sort(t1),sort(t2));
		}
		else
			return lst;
	}
}
