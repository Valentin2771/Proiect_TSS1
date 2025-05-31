package org.example;
import java.util.Scanner;
import java.util.Arrays;
import java.util.InputMismatchException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class MainTest {

    @Test
    @DisplayName("Interclaseaza si cere input nou")
    void interclaseazaCuRepeat() {
        double[] vect1 = {1, -421, 0.7, 99, 0, 67.5, 66}; // Elemente ce trebuie sortate
        double[] vect2 = {3, 2, 1}; // Elemente ce trebuie sortate
        double[] vect3 = new double[vect1.length + vect2.length];

        Main.BSort(vect1, vect1.length);
        Main.BSort(vect2, vect2.length);
        Main.Merge(vect1, vect1.length, vect2, vect2.length, vect3);

        double[] expected = {-421, 0, 0.7, 1, 1, 2, 3, 66, 67.5, 99};
        assertArrayEquals(expected, vect3);

        // Se simuleaza repeat = 1
        Scanner scan = new Scanner("1\n4\n5\n"); // se repeta programul, se introduc următoarele valori pentru m și n
        int repeat = scan.nextInt();
        int new_m = scan.nextInt();
        int new_n = scan.nextInt();

        assertEquals(1, repeat, "Programul nu a efectuat repetarea!");
        assertTrue(new_m >= 2 && new_m <= 10 && new_n >= 2 && new_n <= 10, "Dimensiuni invalide!");
    }

    @Test
    @DisplayName("Interclaseaza si stop")
    void interclaseazaFaraRepeat() {
        double[] vect1 = {1, -421, 0.7, 99, 0, 67.5,  66};
        double[] vect2 = {3, 2, 1};
        double[] vect3 = new double[vect1.length + vect2.length];
        Main.BSort(vect1, vect1.length);
        Main.BSort(vect2, vect2.length);
        Main.Merge(vect1, vect1.length, vect2, vect2.length, vect3);
        double[] expected = {-421.0, 0.0, 0.7, 1.0, 1.0, 2.0, 3.0, 66.0, 67.5, 99.0};
        assertArrayEquals(expected, vect3);

        // Se simuleaza repeat != 1
        Scanner scan = new Scanner("2\n"); // nu se repeta programul
        int repeat = scan.nextInt();
        assertTrue(repeat != 1, "Programul nu trebuie sa se repete!");
    }

    @Test
    @DisplayName("Oprire la vect2 invalid")
    void exitVector2Invalid() {
        double[] vect1 = {1, -421, 0.7, 99, 0, 67.5, 66}; // Numere valide
        Object[] vect2 = {-8, "tty"}; // Contine o valoare non-numerică

        assertThrows(ClassCastException.class, () -> {
            double[] vect2Double = Arrays.stream(vect2)
                    .mapToDouble(o -> (double) o)
                    .toArray();
        });
    }

    @Test
    @DisplayName("Oprire la vect1 invalid")
    void exitVector1Invalid() {
        Object[] vect1 = {"ggw"}; // Primul element este non-numeric
        // double[] vect2 = {4.5, 1.0, -3.5}; // Numere valide care nu vor mai fi introduse

        assertThrows(ClassCastException.class, () -> {
            double[] vect1Double = Arrays.stream(vect1)
                    .mapToDouble(o -> (double) o)
                    .toArray();
        });
    }

    @Test
    @DisplayName("Cere input nou la n invalid")
    void cereInputNInvalid() {
        Scanner scan = new Scanner("7\n-1\n3\n4\n"); // Simulăm intrarea utilizatorului
        int new_m = scan.nextInt();
        int new_n = scan.nextInt();

        assertTrue(new_n < 2 || new_n > 10, "Dimensiunea invalidă nu a fost gestionată corect!");

        new_m = scan.nextInt();
        new_n = scan.nextInt();

        assertTrue((new_m >= 2 && new_m <= 10), "Dimensiunea invalidă nu a fost gestionată corect!");
        assertTrue((new_n >= 2 && new_n <= 10), "Dimensiunea invalidă nu a fost gestionată corect!");
    }

    @Test
    @DisplayName("Cere input nou la m invalid")
    void cereInputMInvalid(){
        Scanner scan = new Scanner("-1\n2\n3\n4\n"); // Simulăm intrarea utilizatorului
        int new_m = scan.nextInt();
        int new_n = scan.nextInt();

        assertTrue(new_m < 2 || new_m > 10, "Dimensiunea invalidă nu a fost gestionată corect!");

        new_m = scan.nextInt();
        new_n = scan.nextInt();

        assertTrue((new_m >= 2 && new_m <= 10), "Dimensiunea invalidă nu a fost gestionată corect!");
        assertTrue((new_n >= 2 && new_n <= 10), "Dimensiunea invalidă nu a fost gestionată corect!");
    }

    @Test
    @DisplayName("Exit la input n invalid")
    void exitNInputInvalid(){
        Scanner scan = new Scanner("7\nghi\n"); // Simulăm inputul utilizatorului

        assertThrows(InputMismatchException.class, () -> {
            int m = scan.nextInt();
            int n = scan.nextInt(); // Ar trebui să genereze o excepție
        });
    }

    @Test
    @DisplayName("Exit la input m invalid")
    void exitMInputInvalid() {
        Scanner scan = new Scanner("qwe\n5\n"); // Simulăm inputul utilizatorului

        assertThrows(InputMismatchException.class, () -> {
            int m = scan.nextInt(); // Ar trebui să genereze o excepție
        });
    }
}