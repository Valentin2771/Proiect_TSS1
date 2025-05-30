package org.example;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void BSort(double[] vect, int dim){
        boolean flag;
        do{
            flag = true;
            for(int i = 0; i < dim - 1; i++){
                double temp = vect[i];
                if(vect[i] > vect[i + 1]){
                    flag = false;
                    vect[i] = vect[i + 1];
                    vect[i + 1] = temp;
                }
            }
        } while(!flag);
    }

    public static void Merge(double[] vect1, int m, double[] vect2, int n, double[] vect3){
        int i = 0, j = 0, k = 0;
        while(i < m && j < n){
            if(vect1[i] < vect2[j]){
                vect3[k] = vect1[i];
                k++;
                i++;
            } else {
                vect3[k] = vect2[j];
                k++;
                j++;
            }
        }

        while(i < m){
            vect3[k] = vect1[i];
            i++;
            k++;
        }

        while(j < n){
            vect3[k] = vect2[j];
            j++;
            k++;
        }
    }

    public static void PrintVector(double[] vect, int dim){
        for(int i = 0; i < dim; i++){
            System.out.print(vect[i] + " ");
        }
        System.out.println();
    }
    public static void main(String[] arg) {
        Scanner scan = new Scanner(System.in);
        int m = -1, n = -1, repeat;

        while(true){
            try{
                while((m < 2) || (m > 10) || (n < 2) || (n > 10)) {
                    System.out.println("Numarul de elemente din primul vector: ");
                    m = scan.nextInt();
                    System.out.println("Numarul de elemente din al doilea vector: ");
                    n = scan.nextInt();
                }

                // creare vectori

                double[] vect1 = new double[m];
                double[] vect2 = new double[n];
                double[] vect3 = new double[m + n];

                // populare

                for(int l = 0; l < m; l++){
                    System.out.print("vect1[" + l + "]: ");
                    vect1[l] = scan.nextDouble();
                }

                for(int l = 0; l < n; l++){
                    System.out.print("vect2[" + l + "]: ");
                    vect2[l] = scan.nextDouble();
                }
                // Afisare vectori
                PrintVector(vect1, m);
                PrintVector(vect2, n);
                // sortare
                BSort(vect1, m);
                BSort(vect2, n);

                // interclasare
                Merge(vect1, m, vect2, n, vect3);
                PrintVector(vect3, (m + n));

                System.out.println("Incercam alte date de intrare? 1 pentru 'da', alt numar pentru 'nu'");
                repeat = scan.nextInt();
                if(repeat != 1) {
                    break;
                } else {
                    m = -1;
                    n = -1;
                }

            } catch (Exception e) {
                System.out.println("Date de intrare necorespunzatoare! Programul se va opri.");
                scan.close();
                break;
            }
        }
        scan.close();
    }
}