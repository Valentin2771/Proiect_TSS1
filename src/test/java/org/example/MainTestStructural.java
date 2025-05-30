package org.example;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.io.*;
public class MainTestStructural {


    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUpStreams() {
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    @DisplayName("Testare intrare validă")
    void testValidInput() {
        String input = "5\n4\n1.5\n3.2\n0.4\n2.7\n4.1\n1.1\n3.3\n0.5\n1\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Main.main(new String[]{});

        String output = outputStream.toString();
        assertTrue(output.contains("Numarul de elemente din primul vector:"));
        assertTrue(output.contains("Numarul de elemente din al doilea vector:"));
        assertTrue(output.contains("Incercam alte date de intrare?"));
    }

    @Test
    @DisplayName("Testare limite superioare și inferioare")
    void testBoundaryValues() {
        String input = "2\n2\n2.0\n1.0\n3.0\n0.5\n1\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Main.main(new String[]{});
        String output = outputStream.toString();

        assertTrue(output.contains("0.5 1.0 2.0 3.0"));
    }

    @Test
    @DisplayName("Testare input invalid")
    void testInvalidInput() {
        String input = "-1\n15\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Main.main(new String[]{});
        String output = outputStream.toString();

        assertTrue(output.contains("Date de intrare necorespunzatoare! Programul se va opri."));
    }

    @Test
    @DisplayName("Testare excepție pentru tip de date invalid")
    void testExceptionHandling() {
        String input = "abc\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Main.main(new String[]{});
        String output = outputStream.toString();

        assertTrue(output.contains("Date de intrare necorespunzatoare!"));
    }
}
