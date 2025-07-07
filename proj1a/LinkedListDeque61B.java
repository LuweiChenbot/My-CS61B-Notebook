import java.util.ArrayList;
import java.util.List;

public class LinkedListDeque61B <T> implements Deque61B<T>{

    private class Node {
        T item;
        Node prev;
        Node next;

        Node(T item, Node prev, Node next){
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }

    private Node sentinel;
    private int size;

    public LinkedListDeque61B() {
        sentinel = new Node(null, null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;

    }

    @Override
    public void addFirst(T x) {
        size += 1;
        Node OldFirst = sentinel.next;
        Node NewNode = new Node(x, sentinel, OldFirst);

        sentinel.next = NewNode;
        OldFirst.prev = NewNode;

    }

    @Override
    public void addLast(T x) {
        size += 1;

        Node OldLast = sentinel.prev;
        Node NewLast = new Node(x, OldLast, sentinel);

        sentinel.prev = NewLast;
        OldLast.next = NewLast;

    }

    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();
        Node currentNode = sentinel.next;

        while (currentNode != sentinel){
            returnList.add(currentNode.item);
            currentNode = currentNode.next;
        }
        return returnList;
    }

    @Override
    public boolean isEmpty() {
        return sentinel.next == sentinel;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T removeFirst() {
        if (sentinel.next != sentinel){
            T removedItem = sentinel.next.item;

            sentinel.next = sentinel.next.next;
            sentinel.next.prev = sentinel;
            size -= 1;
            return removedItem;
        }
        return null;
    }

    @Override
    public T removeLast() {
        if (sentinel.next != sentinel){
            T removedItem = sentinel.prev.item;

            sentinel.prev = sentinel.prev.prev;
            sentinel.prev.next = sentinel;
            size -= 1;
            return removedItem;
        }
        return null;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size){
            return null;
        }
        Node current = sentinel.next;
        for (int i = 0 ; i < index; i++){
            current = current.next;
        }
        return current.item;
    }

    private T getRecursiveHelper(Node node, int index){
        if (index == 0){
            return node.item;
        }
        return getRecursiveHelper(node.next, index - 1);
    }

    @Override
    public T getRecursive(int index) {
        if (index < 0 || index >= size){
            return null;
        }
        return getRecursiveHelper(sentinel.next, index);
    }
}
