package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size = 0;

    public MyLinkedList() {
        this.head = null;
        this.tail = null;
    }

    @Override
    public void add(T value) {
        Node<T> oldTail = tail;
        Node<T> newNode = new Node<>(oldTail, value, null);
        tail = newNode;
        if (oldTail == null) {
            head = newNode;
        } else {
            oldTail.next = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        if (index == size) {
            add(value);
        } else if (index == 0) {
            Node<T> oldHead = head;
            head = new Node<>(null, value, oldHead);
            if (oldHead == null) {
                tail = head;
            }
            oldHead.prev = head;
            size++;
        } else {
            Node<T> actualNode = head;
            for (int i = 0; i < index; i++) {
                actualNode = actualNode.next;
            }
            Node<T> newNode = new Node<>(actualNode.prev, value, actualNode);
            actualNode.prev.next = newNode;
            actualNode.prev = newNode;
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        list.forEach(element -> add(element));
    }

    @Override
    public T get(int index) {
        checkElementIndex(index);
        Node<T> actualNode = head;
        for (int i = 0; i < index; i++) {
            actualNode = actualNode.next;
        }
        return actualNode.item;
    }

    @Override
    public T set(T value, int index) {
        checkElementIndex(index);
        Node<T> actualNode = head;
        for (int i = 0; i < index; i++) {
            actualNode = actualNode.next;
        }
        T oldValue = actualNode.item;
        actualNode.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkElementIndex(index);
        Node<T> actualNode = head;
        for (int i = 0; i < index; i++) {
            actualNode = actualNode.next;
        }
        delete(actualNode);
        return actualNode.item;
    }

    @Override
    public boolean remove(T object) {
        Node<T> actualNode = head;
        while (actualNode != null) {
            if (object == null) {
                if (actualNode.item == null) {
                    delete(actualNode);
                    return true;
                }
            } else if (object.equals(actualNode.item)) {
                delete(actualNode);
                return true;
            }
            actualNode = actualNode.next;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private void checkElementIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    private void delete(Node<T> node) {
        if (node.prev == null) {
            head = node.next;
        } else {
            node.prev.next = node.next;
        }

        if (node.next == null) {
            tail = node.prev;
        } else {
            node.next.prev = node.prev;
        }
        size--;
    }


    private static class Node<T> {
        T item;
        Node<T> next;
        Node<T> prev;

        Node(Node<T> prev, T element, Node<T> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }
}
