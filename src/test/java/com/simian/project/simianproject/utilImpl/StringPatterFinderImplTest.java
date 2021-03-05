package com.simian.project.simianproject.utilImpl;

import com.simian.project.simianproject.exception.WrongStringFormatException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
public class StringPatterFinderImplTest {


    private final static char[][] SIMIANPATTERNS = {{'A', 'A', 'A', 'A'}, {'T', 'T', 'T', 'T'}, {'C', 'C', 'C', 'C'},
            {'G', 'G', 'G', 'G'}};

    @InjectMocks
    private StringPatternFinderImpl stringPatternFinderImpl;

    @Test
    @DisplayName("Invalid Array size matrix")
    void isAnyPatternPresentInStringArray_InvalidArraySizeFailure(){
        String[] wrongSizeArray = new String[]{"CCGGTA", "GACGAC", "CGCGTC", "ACATGA", "CTACGC" };

        Exception exception = assertThrows(WrongStringFormatException.class, () -> stringPatternFinderImpl.isAnyPatternPresentInStringArray(wrongSizeArray,SIMIANPATTERNS));

        String expectedMessage = "Wrong array size or character not expected, verify your data";
        Assertions.assertTrue(exception.getMessage().contains(expectedMessage));

    }

    @Test
    @DisplayName("Invalid String size matrix")
    void isAnyPatternPresentInStringArray_InvalidStringSizeFailure(){
        String[] wrongSizeString = new String[]{"CCGGTA", "GACGAC", "CGCGTC", "ACAASTGA", "CTACGC", "TCCGCA"};

        Exception exception = assertThrows(WrongStringFormatException.class, () -> stringPatternFinderImpl.isAnyPatternPresentInStringArray(wrongSizeString,SIMIANPATTERNS));

        String expectedMessage = "Wrong array size or character not expected, verify your data";
        Assertions.assertTrue(exception.getMessage().contains(expectedMessage));

    }


    @Test
    @DisplayName("No pattern found")
    void isAnyPatternPresentInStringArray_NoPatternFound(){
        String[] noPattern = new String[]{"CCGGTA", "GACGAC", "ACACTC", "ACATGA", "CTACGC", "TCCGCA"};


        Assertions.assertFalse(stringPatternFinderImpl.isAnyPatternPresentInStringArray(noPattern,SIMIANPATTERNS));

    }

    @Test
    @DisplayName("Pattern found in horizontal")
    void isAnyPatternPresentInStringArray_PatternFoundHorizontal(){
        String[] horizontalPattern = new String[]{"CCGGTA", "GACGAC", "CCCCTC", "ACATGA", "CTACGC", "TCCGCA"};

        Assertions.assertTrue(stringPatternFinderImpl.isAnyPatternPresentInStringArray(horizontalPattern,SIMIANPATTERNS));

    }

    @Test
    @DisplayName("Pattern found in vertical")
    void isAnyPatternPresentInStringArray_PatternFoundVertical(){
        String[] verticalPattern = new String[]{"CCGGTA", "GACGAC", "CTCCTA", "ACATGA", "CTACGA", "TCCGCA"};

        Assertions.assertTrue(stringPatternFinderImpl.isAnyPatternPresentInStringArray(verticalPattern,SIMIANPATTERNS));
    }

    @Test
    @DisplayName("Pattern found in let Diagonal")
    void isAnyPatternPresentInStringArray_PatternFoundLeftToRightDiagonal(){
        String[] leftToRightPattern = new String[]{"CCGGTA", "GACGAC", "CTACTA", "ACAAGA", "CTACAT", "TCCGCA"};

        Assertions.assertTrue(stringPatternFinderImpl.isAnyPatternPresentInStringArray(leftToRightPattern,SIMIANPATTERNS));
    }

    @Test
    @DisplayName("Pattern found in right diagonal")
    void isAnyPatternPresentInStringArray_PatternFoundRightToLeftDiagonal(){
        String[] rightToLeftPatter = new String[]{"CTTGTA", "GAGGAC", "CGACTA", "GCAAGA", "CTACTT", "TCCGCA"};

        Assertions.assertTrue(stringPatternFinderImpl.isAnyPatternPresentInStringArray(rightToLeftPatter,SIMIANPATTERNS));
    }


    @Test
    @DisplayName("Valid input array,string sizes and chars")
    void isValidInput_valid(){
        String[] rightToLeftPatter = new String[]{"CTTGTA", "GAGGAC", "CGACTA", "GCAAGA", "CTACTT", "TCCGCA"};
        char[][] patterns = new char[][]{new char[]{'A'},new char[]{'C'},new char[]{'G'},new char[]{'T'}};

        Assertions.assertTrue(stringPatternFinderImpl.isValidInput(rightToLeftPatter,patterns));
    }

    @Test
    @DisplayName("Invalid array size")
    void isValidInput_invalidArraySize(){
        String[] rightToLeftPatter = new String[]{"CTTGTA", "GAGGAC", "CGACTA", "GCAAGA", "CTACTT"};
        char[][] patterns = new char[][]{new char[]{'A'},new char[]{'C'},new char[]{'G'},new char[]{'T'}};

        Assertions.assertFalse(stringPatternFinderImpl.isValidInput(rightToLeftPatter,patterns));
    }

    @Test
    @DisplayName("Invalid String size")
    void isValidInput_invalidStringSize(){
        String[] rightToLeftPatter = new String[]{"CTTGTA", "GAGGC", "CGACTA", "GCAAGA", "CTTT", "TCCGCA"};
        char[][] patterns = new char[][]{new char[]{'A'},new char[]{'C'},new char[]{'G'},new char[]{'T'}};

        Assertions.assertFalse(stringPatternFinderImpl.isValidInput(rightToLeftPatter,patterns));
    }

    @Test
    @DisplayName("Invalid characteres")
    void isValidInput_invalidChars(){
        String[] rightToLeftPatter = new String[]{"CTTGTA", "GAaGC", "CGACTA", "GCAcGA", "CTTT", "TCCGCA"};
        char[][] patterns = new char[][]{new char[]{'A'},new char[]{'C'},new char[]{'G'},new char[]{'T'}};

        Assertions.assertFalse(stringPatternFinderImpl.isValidInput(rightToLeftPatter,patterns));
    }


}
