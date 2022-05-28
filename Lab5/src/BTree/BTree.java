package BTree;

import java.util.Stack;

public class BTree<K extends Comparable<K>, V> implements IBTree<K, V>{

    private final int T;
    private BTreeNode<K, V> root;

    public BTree(int t) {
        T = t;
        root = new BTreeNode<K, V>(T);
        root.n = 0;
        root.leaf = true;
    }

    // Search the key
    private BTreeNode<K, V> Search(IBTreeNode<K, V> X, K key) {
        int i;
        if (X == null)
            return null;
        BTreeNode<K, V> x = (BTreeNode<K, V>) X;
        for (i = 0; i < x.n; i++) {
            if (key.compareTo(x.key.get(i)) < 0) {
                break;
            }
            if (key == x.key.get(i)) {
                return x;
            }
        }
        if (x.leaf) {
            return null;
        } else {
            return Search(x.child.get(i), key);
        }
    }

    // Split function
    private void Split(BTreeNode<K, V> x, int pos, BTreeNode<K, V> y) {
        BTreeNode<K, V> z = new BTreeNode<K, V>(T);
        z.leaf = y.leaf;
        z.n = T - 1;
        for (int j = 0; j < T - 1; j++) {
            z.key.set(j, y.key.get(j + T));
        }
        if (!y.leaf) {
            for (int j = 0; j < T; j++) {
                z.child.set(j, y.child.get(j + T));
            }
        }
        y.n = T - 1;
        for (int j = x.n; j >= pos + 1; j--) {
            x.child.set(j + 1, x.child.get(j));
        }
        x.child.set(pos + 1, z);

        for (int j = x.n - 1; j >= pos; j--) {
            x.key.set(j + 1, x.key.get(j));
        }
        x.key.set(pos, y.key.get(T - 1));
        x.n = x.n + 1;
    }

    // Insert the key
    private void Insert(final K key, final V value) {
        BTreeNode<K, V> r = root;
        if (r.n == 2 * T - 1) {
            BTreeNode<K, V> s = new BTreeNode<K, V>(T);
            root = s;
            s.leaf = false;
            s.n = 0;
            s.child.set(0, r);
            Split(s, 0, r);
            _Insert(s, key, value);
        } else {
            _Insert(r, key, value);
        }
    }

    // Insert the node
    private void _Insert(IBTreeNode<K, V> X, K k, V v) {

        BTreeNode<K, V> x = (BTreeNode<K, V>) X;
        int i;
        if (x.leaf) {
            for (i = x.n - 1; i >= 0 && k.compareTo(x.key.get(i)) < 0; i--) {
                x.key.set(i + 1, x.key.get(i));
                x.value.set(i + 1, x.value.get(i));
            }
            x.key.set(i + 1, k);
            x.value.set(i + 1, v);
            x.n = x.n + 1;
        } else {
            for (i = x.n - 1; i >= 0 && k.compareTo(x.key.get(i)) < 0; i--) { }
            i++;
            BTreeNode<K, V> tmp = (BTreeNode<K, V>) x.child.get(i);
            if (tmp.n == 2 * T - 1) {
                Split(x, i, tmp);
                if (k.compareTo(x.key.get(i)) > 0) {
                    i++;
                }
            }
            _Insert(x.child.get(i), k, v);
        }

    }

    private void Remove(IBTreeNode<K, V> X ,K key) {

        BTreeNode<K, V> x = (BTreeNode<K, V>) X;
        int pos = x.Find(key);
        if (pos != -1) {
            if (x.leaf) {
                int i;
                for (i = 0; i < x.n && x.key.get(i) != key; i++) { }
                for (; i < x.n; i++) {
                    if (i != 2 * T - 2) {
                        x.key.set(i, x.key.get(i + 1));
                    }
                }
                x.n--;
                return;
            }
            if (!x.leaf) {

                BTreeNode<K, V> pred = (BTreeNode<K, V>) x.child.get(pos);
                K predKey;
                if (pred.n >= T) {
                    for (;;) {
                        if (pred.leaf) {
                            System.out.println(pred.n);
                            predKey = pred.key.get(pred.n - 1);
                            break;
                        } else {
                            pred = (BTreeNode<K, V>) pred.child.get(pred.n);
                        }
                    }
                    Remove(pred, predKey);
                    x.key.set(pos, predKey);
                    return;
                }

                BTreeNode<K, V> nextNode = (BTreeNode<K, V>) x.child.get(pos + 1);
                if (nextNode.n >= T) {
                    K nextKey = nextNode.key.get(0);
                    if (!nextNode.leaf) {
                        nextNode = (BTreeNode<K, V>) nextNode.child.get(0);
                        for (;;) {
                            if (nextNode.leaf) {
                                nextKey = nextNode.key.get(nextNode.n - 1);
                                break;
                            } else {
                                nextNode = (BTreeNode<K, V>) nextNode.child.get(nextNode.n);
                            }
                        }
                    }
                    Remove(nextNode, nextKey);
                    x.key.set(pos, nextKey);
                    return;
                }

                int temp = pred.n + 1;
                pred.key.set(pred.n++, x.key.get(pos));
                for (int i = 0, j = pred.n; i < nextNode.n; i++) {
                    pred.key.set(j++, nextNode.key.get(i));
                    pred.n++;
                }
                for (int i = 0; i < nextNode.n + 1; i++) {
                    pred.child.set(temp++, nextNode.child.get(i));
                }

                x.child.set(pos, pred);
                for (int i = pos; i < x.n; i++) {
                    if (i != 2 * T - 2) {
                        x.key.set(i, x.key.get(i + 1));
                    }
                }
                for (int i = pos + 1; i < x.n + 1; i++) {
                    if (i != 2 * T - 1) {
                        x.child.set(i, x.child.get(i + 1));
                    }
                }
                x.n--;
                if (x.n == 0) {
                    if (x == root) {
                        root = (BTreeNode<K, V>) x.child.get(0);
                    }
                    x = (BTreeNode<K, V>) x.child.get(0);
                }
                Remove(pred, key);
            }
        } else {
            for (pos = 0; pos < x.n; pos++) {
                if (x.key.get(pos).compareTo(key) > 0) {
                    break;
                }
            }
            BTreeNode<K, V> tmp = (BTreeNode<K, V>) x.child.get(pos);
            if (tmp.n >= T) {
                Remove(tmp, key);
                return;
            }
            BTreeNode<K, V> nb;
            K devider;

            if (pos != x.n && x.child.get(pos + 1).getNumOfKeys() >= T) {
                devider = x.key.get(pos);
                nb = (BTreeNode<K, V>) x.child.get(pos + 1);
                x.key.set(pos, nb.key.get(0));
                tmp.key.set(tmp.n++, devider);
                tmp.child.set(tmp.n, nb.child.get(0));
                for (int i = 1; i < nb.n; i++) {
                    nb.key.set(i - 1, nb.key.get(i));
                }
                for (int i = 1; i <= nb.n; i++) {
                    nb.child.set(i - 1, nb.child.get(i));
                }
                nb.n--;
                Remove(tmp, key);
            } else if (pos != 0 && x.child.get(pos - 1).getNumOfKeys() >= T) {

                devider = x.key.get(pos - 1);
                nb = (BTreeNode<K, V>) x.child.get(pos - 1);
                x.key.set(pos - 1, nb.key.get(nb.n - 1));
                BTreeNode<K, V> child = (BTreeNode<K, V>) nb.child.get(nb.n);
                nb.n--;

                for (int i = tmp.n; i > 0; i--) {
                    tmp.key.set(i, tmp.key.get(i - 1));
                }
                tmp.key.set(0, devider);
                for (int i = tmp.n + 1; i > 0; i--) {
                    tmp.child.set(i, tmp.child.get(i - 1));
                }
                tmp.child.set(0, child);
                tmp.n++;
                Remove(tmp, key);
            } else {
                BTreeNode<K, V> lt;
                BTreeNode<K, V> rt;
                if (pos != x.n) {
                    devider = x.key.get(pos);
                    lt = (BTreeNode<K, V>) x.child.get(pos);
                    rt = (BTreeNode<K, V>) x.child.get(pos + 1);
                } else {
                    devider = x.key.get(pos - 1);
                    rt = (BTreeNode<K, V>) x.child.get(pos);
                    lt = (BTreeNode<K, V>) x.child.get(pos - 1);
                    pos--;
                }
                for (int i = pos; i < x.n - 1; i++) {
                    x.key.set(i, x.key.get(i + 1));
                }
                for (int i = pos + 1; i < x.n; i++) {
                    x.child.set(i, x.child.get(i + 1));
                }
                x.n--;
                lt.key.set(lt.n++, devider);

                for (int i = 0, j = lt.n; i < rt.n + 1; i++, j++) {
                    if (i < rt.n) {
                        lt.key.set(j, rt.key.get(i));
                    }
                    lt.child.set(j, rt.child.get(i));
                }
                lt.n += rt.n;
                if (x.n == 0) {
                    if (x == root) {
                        root = (BTreeNode<K, V>) x.child.get(0);
                    }
                    x = (BTreeNode<K, V>) x.child.get(0);
                }
                Remove(lt, key);
            }
        }
    }

    public void Remove(K key) {
        BTreeNode<K, V> x = Search(root, key);
        if (x == null) {
            return;
        }
        Remove(root, key);
    }

    public void Task(K a, K b) {
        Stack<K> st = new Stack<>();
        FindKeys(a, b, root, st);
        while (!st.isEmpty()) {
            this.Remove(root, st.pop());
        }
    }

    private void FindKeys(K a, K b, BTreeNode<K, V> x, Stack<K> st) {
        int i;
        for (i = 0; i < x.n && x.key.get(i).compareTo(b) < 0; i++) {
            if (x.key.get(i).compareTo(a) > 0) {
                st.push(x.key.get(i));
            }
        }
        if (!x.leaf) {
            for (int j = 0; j < i + 1; j++) {
                FindKeys(a, b, (BTreeNode<K, V>) x.child.get(j), st);
            }
        }
    }

    public boolean Contain(K k) {
        return this.Search(root, k) != null;
    }

    @Override
    public int getMinimumDegree() {
        return this.T;
    }

    @Override
    public IBTreeNode<K, V> getRoot() {
        return root;
    }

    @Override
    public void insert(K key, V value) {
        Insert(key, value);
    }

    @Override
    public V search(K key) {
        IBTreeNode<K, V> node = Search(root, key);
        for(int i = 0; i<node.getNumOfKeys(); i++)
            if(node.getKeys().get(i) == key)
                return node.getValues().get(i);
            return null;
    }

    @Override
    public boolean delete(K key) {
        if(!Contain(key))
            return false;
        Remove(key);
        return true;
    }
}