package com.wecho.core.leetcode;

public class Solution {
    ListNode rootListNode = null;

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {


        addTwoListNodes(l1,l2,rootListNode,0);
        return rootListNode;
    }

    private int getNodesCount(ListNode listNode,int count){
        if(listNode.next != null){
            count++;
            getNodesCount(listNode.next,count);
        }
        return count;
    }

    private void addTwoListNodes(ListNode l1,ListNode l2,ListNode listNode,int c){
        if(l1 == null && l2 == null && c==0)
            return;

        if(rootListNode == null ){
            rootListNode = new ListNode(add(l1,l2,0));
            addTwoListNodes(l1.next,l2.next,rootListNode,getC(l1,l2,c));
        }else{
            ListNode aListNode = l1==null?null:l1.next;
            ListNode bListNode = l2==null?null:l2.next;
            ListNode listndoe1 = new ListNode(add(l1,l2,c));
            listNode.next = listndoe1;
            addTwoListNodes(aListNode,bListNode,listndoe1,getC(l1,l2,c));
        }
    }

    private int getC(ListNode l1,ListNode l2,int c){
        int a = (l1==null)?0:l1.val;
        int b = (l2==null)?0:l2.val;
        return (a+b+c)/10;
    }

    private int add(ListNode l1,ListNode l2,int c){
        int a = (l1==null)?0:l1.val;
        int b = (l2==null)?0:l2.val;
        return (a+b+c)%10;
    }

    public static void main(String[] args) {
        ListNode l1 = new ListNode(5);
        ListNode l2 = new ListNode(5);
        //l1.next = l2;
        Solution solution = new Solution();
        solution.addTwoNumbers(l1,l2);
        System.out.println(solution.rootListNode.next.val);
    }
}