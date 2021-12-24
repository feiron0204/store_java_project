package main;

import java.util.Scanner;

import viewer.StoreViewer;

public class RentalStoreMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // 가게선택부터 시작
        StoreViewer storeViewer = new StoreViewer(null,null,scanner);
        storeViewer.showIndex();
        
        scanner.close();
    }
}
