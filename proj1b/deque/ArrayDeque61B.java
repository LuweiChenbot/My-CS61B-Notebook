package deque;
import java.lang.Math;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ArrayDeque61B<T> implements Deque61B<T> {
    public int size;
    public int nextFirst;
    public int nextLast;
    public T[] items;

    @Override
    public Iterator<T> iterator() {
        return new ArrayDequeIterator();
    }

    private class ArrayDequeIterator implements Iterator<T> {
        private int pos;
        private int cnt;
        public ArrayDequeIterator(){
            pos = plusOne(nextFirst);
            cnt = 0;
        }
        public boolean hasNext(){
            return cnt < size;
        }
        public T next(){
            T returnItem = items[pos];
            pos = plusOne(pos);
            cnt += 1;
            return returnItem;
        }
    }
    public int minusOne(int index){
        return (index - 1 + items.length) % items.length;
    }

    public int plusOne(int index){
        return (index + 1) % items.length;
    }

    @SuppressWarnings("unchecked")
    public ArrayDeque61B(){
        size = 0;
        items = (T[]) new Object[8];
        nextFirst = 4;
        nextLast = 5;
    }

    @Override
    public void addFirst(T x) {
        if (size==items.length){
            resize(items.length * 2);
        }
        size += 1;
        items[nextFirst] = x;
        nextFirst = minusOne(nextFirst);
    }

    @Override
    public void addLast(T x) {
        if (size==items.length){
            resize(items.length * 2);
        }
        size += 1;
        items[nextLast] = x;
        nextLast = plusOne(nextLast);

    }

    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();
        int index = (nextFirst + 1) % items.length;
        for (int i = 0; i < size; i++){
            returnList.add(items[index]);
            index = (index + 1) % items.length;
        }
        return returnList;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T removeFirst() {
        if (size < items.length / 4 || items.length > 16){
            resize(items.length / 2);
        }
        if (isEmpty()){return null;}
        int firstIndex = plusOne(nextFirst);
        T removedItem = items[firstIndex];
        items[firstIndex] = null;
        nextFirst = firstIndex;
        size -= 1;
        return removedItem;
    }

    @Override
    public T removeLast() {
        if (isEmpty()){return null;}
        int LastIndex = minusOne(nextLast);
        T removedItem = items[LastIndex];
        items[LastIndex] = null;
        nextLast = LastIndex;
        size -= 1;
        return removedItem;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size){
            return null;
        }
        int returnIndex = (nextFirst + 1 + index) % items.length;
        return items[returnIndex];
    }


    public T getRecursiveHelper(int pos, int index){
        if (index == 0){
            return items[pos];
        }
        int nextPos = (pos + 1) % items.length;
        return getRecursiveHelper(nextPos, index - 1);
    }

    @Override
    public T getRecursive(int index) {
        if (index < 0 || index >= size){
            return null;
        }
        return getRecursiveHelper((nextFirst + 1) % items.length, index);
    }

    @SuppressWarnings("unchecked")
    public void resize(int capacity){
        T[] resizedArray = (T[]) new Object[capacity];
        int index = plusOne(nextFirst);
        for (int i=0; i<size; i++){
            resizedArray[i] = items[index];
            index = (index + 1) % items.length;
        }
        items = resizedArray;
        nextFirst = capacity - 1;
        nextLast = size;
    }

    public static void main(String[] args){
        Deque61B<Integer> newDeque = new LinkedListDeque61B<>();
        newDeque.addFirst(5);
        newDeque.addLast(2);
        for (int i : newDeque){
            System.out.println(i);
        }
    }
}


