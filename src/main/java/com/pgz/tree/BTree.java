package com.pgz.tree;

import org.junit.Test;

import java.util.HashMap;

/**
 * @author liquan_pgz@qq.com
 * @date 2019-12-11
 */
public class BTree {

    public int data;

    public BTree father, leftSon, rightSon;

    public static BTree root;

    public boolean hasLeft() {
        return leftSon != null;
    }

    public boolean hasRight() {
        return rightSon != null;
    }

    public BTree() {
    }

    /**
     * 直接插入方法
     *
     * @param data
     * @return
     * @author liquan_pgz@qq.com
     * date 2019-12-11
     **/
    public void insert(int data) {
        if (root == null) {
            root = new BTree();
            root.data = data;
            return;
        }

        insert(data, root);
    }

    /**
     * 递归插入方法
     *
     * @param data
     * @param father
     * @return
     * @author liquan_pgz@qq.com
     * date 2019-12-11
     **/
    public void insert(int data, BTree father) {

        //插入的数据和父节点比较大小
        int compare = data - father.data;
        if (compare == 0) {
            return;
        }

        //放在右边
        if (compare > 0) {
            //判断有没有右孩子，如果有则递归下一级
            if (father.hasRight()) {
                insert(data, father.rightSon);
            } else {
                //创建一个新的节点没有左孩子
                father.rightSon = new BTree();
                father.rightSon.data = data;
                father.rightSon.father = father;
            }
        }

        //放在左边
        if (compare < 0) {
            //判断有没有左孩子，如果有则递归下一级
            if (father.hasLeft()) {
                insert(data, father.leftSon);
            } else {
                father.leftSon = new BTree();
                father.leftSon.data = data;
                father.leftSon.father = father;
            }
        }

    }

    /**
     * 前序遍历
     *
     * @return
     * @author liquan_pgz@qq.com
     * date 2019-12-11
     **/
    public static void queryF() {
        if (root == null) {
            return;
        }
        queryF(root);
    }

    /**
     * 前序遍历
     *
     * @param tree
     * @return
     * @author liquan_pgz@qq.com
     * date 2019-12-11
     **/
    public static void queryF(BTree tree) {
        if (tree == null) {
            return;
        }

        System.out.print(tree.data + " ");

        if (tree.hasLeft()) {
            queryF(tree.leftSon);
        }

        if (tree.hasRight()) {
            queryF(tree.rightSon);
        }
    }

    /**
     * 中序遍历
     *
     * @return
     * @author liquan_pgz@qq.com
     * date 2019-12-11
     **/
    public static void queryC() {
        if (root == null) {
            return;
        }
        queryC(root);

    }

    /**
     * 中序遍历
     *
     * @return
     * @author liquan_pgz@qq.com
     * date 2019-12-11
     **/
    public static void queryC(BTree tree) {
        if (tree == null) {
            return;
        }
        if (tree.hasLeft()) {
            queryC(tree.leftSon);
        }
        System.out.print(tree.data + " ");

        if (tree.hasRight()) {
            queryC(tree.rightSon);
        }
    }

    /**
     * 后序遍历
     *
     * @return
     * @author liquan_pgz@qq.com
     * date 2019-12-11
     **/
    public static void queryR() {
        if (root == null) {
            return;
        }
        queryR(root);

    }

    /**
     * 后序遍历
     *
     * @return
     * @author liquan_pgz@qq.com
     * date 2019-12-11
     **/
    public static void queryR(BTree tree) {
        if (tree == null) {
            return;
        }
        if (tree.hasLeft()) {
            queryR(tree.leftSon);
        }
        if (tree.hasRight()) {
            queryR(tree.rightSon);
        }

        System.out.print(tree.data + " ");
    }

    public static void main(String[] args) {
        BTree tree = new BTree();
        tree.insert(56);
        tree.insert(23);
        tree.insert(98);
        tree.insert(33);
        tree.insert(54);
        tree.insert(44);
        tree.insert(66);
        tree.insert(53);
        tree.insert(75);
        tree.insert(11);
        tree.insert(32);
        tree.insert(76);
        BTree.queryF();
        System.out.println();
        BTree.queryC();
        System.out.println();
        BTree.queryR();
    }

}












































