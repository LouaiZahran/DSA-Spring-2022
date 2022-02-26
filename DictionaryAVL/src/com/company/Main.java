package com.company;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
	// write your code here
        Scanner scanner=new Scanner(System.in);
        AVL tree = new AVL();
        int n = Integer.parseInt(scanner.nextLine());
        for(int i=0;i<n;i++){
            tree.insert(scanner.nextLine());
        }
        tree.traverse();
    }
}
