package org.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.io.*;

class MainTestFuncClaude {

    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private InputStream originalIn = System.in;

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(originalOut);
        System.setIn(originalIn);
    }

    private void setInput(String input) {
        System.setIn(new ByteArrayInputStream(input.getBytes()));
    }

    @Test
    @DisplayName("Test Case 1: Funcționalitate Standard")
    public void testFunctionalitateStandard() {
        // Input: m=3, n=2, vect1=[3.5, 1.2, 2.8], vect2=[4.1, 0.9], repeat=0
        String input = "3\n2\n3.5\n1.2\n2.8\n4.1\n0.9\n0\n";
        setInput(input);

        assertDoesNotThrow(() -> {
            Main.main(new String[]{});
        });

        String output = outputStream.toString();
        // Verificăm că vectorul final conține valorile sortate și interclasate
        assertTrue(output.contains("0.9 1.2 2.8 3.5 4.1"));
    }

    @Test
    @DisplayName("Test Case 2: Valori Frontieră - Dimensiuni Minime")
    public void testDimensiuniMinime() {
        // Input: m=2, n=2, vect1=[5.0, 1.0], vect2=[3.0, 2.0], repeat=0
        String input = "2\n2\n5.0\n1.0\n3.0\n2.0\n0\n";
        setInput(input);

        assertDoesNotThrow(() -> {
            Main.main(new String[]{});
        });

        String output = outputStream.toString();
        assertTrue(output.contains("1.0 2.0 3.0 5.0"));
    }

    @Test
    @DisplayName("Test Case 3: Valori Frontieră - Dimensiuni Maxime")
    public void testDimensiuniMaxime() {
        // Input: m=10, n=10 cu vectori mari
        String input = "10\n10\n" +
                "10.0\n9.0\n8.0\n7.0\n6.0\n5.0\n4.0\n3.0\n2.0\n1.0\n" +
                "20.0\n18.0\n16.0\n14.0\n12.0\n10.0\n8.0\n6.0\n4.0\n2.0\n" +
                "0\n";
        setInput(input);

        assertDoesNotThrow(() -> {
            Main.main(new String[]{});
        });

        String output = outputStream.toString();
        // Verificăm că avem 20 de elemente în vectorul final
        String[] lines = output.split("\n");
        boolean foundMergedVector = false;
        for (String line : lines) {
            if (line.trim().split("\\s+").length == 20) {
                foundMergedVector = true;
                break;
            }
        }
        assertTrue(foundMergedVector, "Vectorul intercelasat ar trebui să aibă 20 de elemente");
    }

    @Test
    @DisplayName("Test Case 4: Cazuri Extreme - Valori Negative")
    public void testValoriNegative() {
        // Input: m=3, n=3 cu valori negative
        String input = "3\n3\n-1.5\n-3.2\n-0.8\n-2.1\n-4.7\n-1.0\n0\n";
        setInput(input);

        assertDoesNotThrow(() -> {
            Main.main(new String[]{});
        });

        String output = outputStream.toString();
        assertTrue(output.contains("-4.7 -3.2 -2.1 -1.5 -1.0 -0.8"));
    }

    @Test
    @DisplayName("Test Case 5: Gestionare Erori - Dimensiuni Invalide (Sub Limita)")
    public void testDimensiuniInvalideSubLimita() {
        // Input: m=1, n=5 (invalid), apoi m=2, n=3 (valid)
        String input = "1\n5\n2\n3\n1.0\n2.0\n3.0\n4.0\n5.0\n0\n";
        setInput(input);

        assertDoesNotThrow(() -> {
            Main.main(new String[]{});
        });

        String output = outputStream.toString();
        // Verificăm că programul a cerut reintroducerea valorilor
        long countPrompts = output.lines()
                .filter(line -> line.contains("Numarul de elemente"))
                .count();
        assertTrue(countPrompts > 2, "Programul ar trebui să fi cerut reintroducerea valorilor");
    }

    @Test
    @DisplayName("Test Case 6: Gestionare Erori - Dimensiuni Invalide (Peste Limita)")
    public void testDimensiuniInvalidePesteLimita() {
        // Input: m=11, n=5 (invalid), apoi m=3, n=2 (valid)
        String input = "11\n5\n3\n2\n1.0\n2.0\n3.0\n4.0\n5.0\n0\n";
        setInput(input);

        assertDoesNotThrow(() -> {
            Main.main(new String[]{});
        });

        String output = outputStream.toString();
        // Verificăm că programul a cerut reintroducerea valorilor
        long countPrompts = output.lines()
                .filter(line -> line.contains("Numarul de elemente"))
                .count();
        assertTrue(countPrompts > 2, "Programul ar trebui să fi cerut reintroducerea valorilor");
    }

    @Test
    @DisplayName("Test Case 7: Gestionare Erori - Input Non-numeric")
    public void testInputNonNumeric() {
        // Input non-numeric pentru dimensiuni
        String input = "abc\n";
        setInput(input);

        assertDoesNotThrow(() -> {
            Main.main(new String[]{});
        });

        String output = outputStream.toString();
        assertTrue(output.contains("Date de intrare necorespunzatoare! Programul se va opri."));
    }

    @Test
    @DisplayName("Test Case 8: Valori Duplicate")
    public void testValoriDuplicate() {
        // Input: m=3, n=2 cu valori duplicate
        String input = "3\n2\n2.5\n2.5\n1.0\n2.5\n3.0\n0\n";
        setInput(input);

        assertDoesNotThrow(() -> {
            Main.main(new String[]{});
        });

        String output = outputStream.toString();
        assertTrue(output.contains("1.0 2.5 2.5 2.5 3.0"));
    }

    @Test
    @DisplayName("Test Case 9: Flux de Control - Repetare")
    public void testFluxRepetare() {
        // First iteration, then repeat=1, then second iteration with repeat=0
        String input = "2\n2\n1.0\n2.0\n3.0\n4.0\n1\n3\n2\n5.0\n6.0\n7.0\n8.0\n0\n";
        setInput(input);

        assertDoesNotThrow(() -> {
            Main.main(new String[]{});
        });

        String output = outputStream.toString();
        // Verificăm că mesajul de repetare apare
        assertTrue(output.contains("Incercam alte date de intrare?"));
        // Verificăm că au fost procesate două seturi de date
        long countMergedVectors = output.lines()
                .filter(line -> line.trim().matches("([0-9\\.\\- ]+\\s*){4,}"))
                .filter(line -> line.trim().split("\\s+").length >= 4)
                .count();
        assertTrue(countMergedVectors >= 2, "Ar trebui să existe cel puțin 2 vectori rezultat");
    }

    @Test
    @DisplayName("Test Case 10: Flux de Control - Oprire")
    public void testFluxOprire() {
        // Input normal, apoi repeat=0 pentru oprire
        String input = "2\n2\n1.0\n2.0\n3.0\n4.0\n0\n";
        setInput(input);

        assertDoesNotThrow(() -> {
            Main.main(new String[]{});
        });

        String output = outputStream.toString();
        // Verificăm că programul s-a oprit după primul test
        assertTrue(output.contains("Incercam alte date de intrare?"));
        // Verificăm că nu mai cere alte date după repeat=0
        String[] lines = output.split("\n");
        boolean foundRepeatQuestion = false;
        int repeatQuestionIndex = -1;
        for (int i = 0; i < lines.length; i++) {
            if (lines[i].contains("Incercam alte date de intrare?")) {
                foundRepeatQuestion = true;
                repeatQuestionIndex = i;
                break;
            }
        }
        assertTrue(foundRepeatQuestion);

        // După întrebarea de repetare, nu ar trebui să mai apară cereri pentru noi dimensiuni
        boolean foundNewDimensionRequest = false;
        for (int i = repeatQuestionIndex + 1; i < lines.length; i++) {
            if (lines[i].contains("Numarul de elemente din primul vector:")) {
                foundNewDimensionRequest = true;
                break;
            }
        }
        assertFalse(foundNewDimensionRequest, "Nu ar trebui să mai ceară noi dimensiuni după repeat=0");
    }
}